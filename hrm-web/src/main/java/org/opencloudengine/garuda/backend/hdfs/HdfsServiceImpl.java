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

    @Override
    public List<HdfsFileInfo> listDirectory(String path) throws Exception {
        FileSystem fs = fileSystemFactory.getFileSystem();
        FileStatus[] fileStatuses = fs.listStatus(new Path(path));

        List<HdfsFileInfo> listStatus = new ArrayList<>();
        for (int i = 0; i < fileStatuses.length; i++) {
            listStatus.add(new HdfsFileInfo(fileStatuses[i], fs.getContentSummary(fileStatuses[i].getPath())));
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
        fs.create(new Path(path));
    }

    @Override
    public void appendFile(String path, InputStream is) throws Exception{
        FileSystem fs = fileSystemFactory.getFileSystem();
        Path fsPath = new Path(path);
        boolean exists = fs.exists(fsPath);
        if(exists){
            FileStatus fileStatus = fs.getFileStatus(fsPath);
            if(fileStatus.isFile()){
                FSDataOutputStream out = fs.append(fsPath);
                byte[] b = new byte[1024];
                int numBytes = 0;
                while ((numBytes = is.read(b)) > 0) {
                    out.write(b, 0, numBytes);
                }

                is.close();
                out.close();
                fs.close();
            }else{
                logger.warn("Failed append HDFS file, {} is not file.", fsPath.toString());
                throw new ServiceException("디렉토리에 파일을 쓸 수 없습니다..");
            }
        }else{
            logger.warn("Failed append HDFS file, File not exist : {}", fsPath.toString());
            throw new ServiceException("파일이 존재하지 않습니다.");
        }
    }
}
