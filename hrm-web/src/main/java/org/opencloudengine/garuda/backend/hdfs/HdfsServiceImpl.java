/**
 * Copyright (C) 2011 Flamingo Project (http://www.opencloudengine.org).
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.opencloudengine.garuda.backend.hdfs;

import net.sf.expectit.Expect;
import net.sf.expectit.ExpectBuilder;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.permission.FsAction;
import org.apache.hadoop.fs.permission.FsPermission;
import org.opencloudengine.garuda.backend.system.SystemService;
import org.opencloudengine.garuda.common.exception.ServiceException;
import org.opencloudengine.garuda.util.ExceptionUtils;
import org.opencloudengine.garuda.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static net.sf.expectit.matcher.Matchers.eof;
import static net.sf.expectit.matcher.Matchers.regexp;

@Service
public class HdfsServiceImpl implements HdfsService {

    /**
     * SLF4J Application Logging
     */
    private Logger logger = LoggerFactory.getLogger(HdfsServiceImpl.class);


    @Autowired
    @Qualifier("config")
    private Properties config;

    @Autowired
    FileSystemFactory fileSystemFactory;

//    @Override
//    public List<HdfsFileInfo> list(String path, int start, int end, final String filter) throws Exception {
//
//        FileSystem fs = fileSystemFactory.getFileSystem();
//        Path fsPath = new Path(path);
//
//        this.indexCheck(start, end);
//
//        if (!fs.exists(fsPath)) {
//            this.notFoundException(fsPath.toString());
//        }
//        FileStatus fileStatus = fs.getFileStatus(fsPath);
//        if (!fileStatus.isDirectory()) {
//            this.notDirectoryException(fsPath.toString());
//        }
//
//        List<HdfsFileInfo> listStatus = new ArrayList<>();
//        int count = 0;
//        FileStatus[] fileStatuses = null;
//        if (StringUtils.isEmpty(filter)) {
//            fileStatuses = fs.listStatus(fsPath);
//        } else {
//            PathFilter pathfilter = new PathFilter() {
//                @Override
//                public boolean accept(Path path) {
//                    return path.getName().contains(filter);
//                }
//            };
//            fileStatuses = fs.listStatus(fsPath, pathfilter);
//        }
//        for (int i = start - 1; i < end; i++) {
//            listStatus.add(new HdfsFileInfo(fileStatuses[i], fs.getContentSummary(fileStatuses[i].getPath())));
//        }
//        fs.close();
//        return listStatus;
//    }

    @Override
    public List<HdfsFileInfo> list(String path, int start, int end, final String filter) throws Exception {

        FileSystem fs = fileSystemFactory.getFileSystem();
        Path fsPath = new Path(path);

        this.indexCheck(start, end);

        if (!fs.exists(fsPath)) {
            this.notFoundException(fsPath.toString());
        }
        FileStatus fileStatus = fs.getFileStatus(fsPath);
        if (!fileStatus.isDirectory()) {
            this.notDirectoryException(fsPath.toString());
        }

        List<HdfsFileInfo> listStatus = new ArrayList<>();
        int count = 0;
        FileStatus fileStatuses = null;
        LocatedFileStatus next = null;
        RemoteIterator<LocatedFileStatus> remoteIterator = fs.listLocatedStatus(fsPath);
        while (remoteIterator.hasNext()) {
            next = remoteIterator.next();
            if (!StringUtils.isEmpty(filter)) {
                if (next.getPath().getName().contains(filter)) {
                    count++;
                    if (count >= start && count <= end) {
                        fileStatuses = fs.getFileStatus(next.getPath());
                        listStatus.add(new HdfsFileInfo(fileStatuses, fs.getContentSummary(fileStatuses.getPath())));
                    }
                }
            } else {
                count++;
                if (count >= start && count <= end) {
                    fileStatuses = fs.getFileStatus(next.getPath());
                    listStatus.add(new HdfsFileInfo(fileStatuses, fs.getContentSummary(fileStatuses.getPath())));
                }
            }
        }
        fs.close();
        return listStatus;
    }

    @Override
    public void createFile(String path, InputStream is) throws Exception {
        FileSystem fs = fileSystemFactory.getFileSystem();
        FSDataOutputStream out = fs.create(new Path(path));
        byte[] b = new byte[1024];
        int numBytes = 0;
        while ((numBytes = is.read(b)) > 0) {
            out.write(b, 0, numBytes);
        }

        is.close();
        out.close();
        fs.close();
    }

    @Override
    public void createEmptyFile(String path) throws Exception {
        FileSystem fs = fileSystemFactory.getFileSystem();
        fs.create(new Path(path)).close();
        fs.close();
    }

    @Override
    public void teragen() throws Exception {
        FileSystem fs = fileSystemFactory.getFileSystem();
        for (int i = 0; i < 1000000; i++) {
            fs.create(new Path("/user/ubuntu/many/uuid_u" + i)).close();
        }
        fs.close();
    }

    @Override
    public void appendFile(String path, InputStream is) throws Exception {
        FileSystem fs = fileSystemFactory.getFileSystem();
        Path fsPath = new Path(path);
        if (!fs.exists(fsPath)) {
            this.notFoundException(fsPath.toString());
        }

        FileStatus fileStatus = fs.getFileStatus(fsPath);
        if (!fileStatus.isFile()) {
            this.notFileException(fsPath.toString());
        }

        FSDataOutputStream out = fs.append(fsPath);
        byte[] b = new byte[1024];
        int numBytes = 0;
        while ((numBytes = is.read(b)) > 0) {
            out.write(b, 0, numBytes);
        }

        is.close();
        out.close();
        fs.close();
    }

    private void notFileException(String path) {
        logger.warn("File {} is not a file : {}", path);
        throw new ServiceException("파일이 아닙니다.");
    }

    private void notDirectoryException(String path) {
        logger.warn("File {} is not a directory : {}", path);
        throw new ServiceException("디렉토리가 아닙니다.");
    }

    private void notFoundException(String path) {
        logger.warn("Failed append HDFS file, File not exist : {}", path);
        throw new ServiceException("파일이 존재하지 않습니다.");
    }

    private void indexCheck(int start, int end) {
        if (start < 1) {
            logger.warn("Start must more than 1. current start is : {}", start);
            throw new ServiceException("조회 시작 수가 적습니다.");
        } else if (end - start > 100) {
            logger.warn("100 count per page limit. current per count is : {}", end - start);
            throw new ServiceException("페이지 당 조회수가 100을 넘습니다.");
        } else if (end <= start) {
            logger.warn("End count is less than start count. start : {} , end : {}", start, end);
            throw new ServiceException("종료 카운트가 시작 카운트보다 작거나 큽니다.");
        }

    }

//    private void list(){
//
//        FileStatus fileStatuses = null;
//        LocatedFileStatus next = null;
//        RemoteIterator<LocatedFileStatus> remoteIterator = fs.listLocatedStatus(fsPath);
//        while (remoteIterator.hasNext()) {
//            next = remoteIterator.next();
//            if (!StringUtils.isEmpty(filter)) {
//                if (next.getPath().getName().contains(filter)) {
//                    count++;
//                    if (count >= start && count <= end) {
//                        fileStatuses = fs.getFileStatus(next.getPath());
//                        listStatus.add(new HdfsFileInfo(fileStatuses, fs.getContentSummary(fileStatuses.getPath())));
//                    }
//                }
//            } else {
//                count++;
//                if (count >= start && count <= end) {
//                    fileStatuses = fs.getFileStatus(next.getPath());
//                    listStatus.add(new HdfsFileInfo(fileStatuses, fs.getContentSummary(fileStatuses.getPath())));
//                }
//            }
//        }
//        fs.close();
//        return listStatus;
//    }
}
