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
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.permission.FsPermission;
import org.opencloudengine.garuda.common.exception.ServiceException;
import org.opencloudengine.garuda.util.ExceptionUtils;
import org.opencloudengine.garuda.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.TimeUnit;


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

    private Map<String, Integer> progressMap;

    private void setProgress(String uuid, int status) {
        if (progressMap == null) {
            progressMap = new HashMap<>();
        }
        progressMap.put(uuid, status);
        System.out.println("setProgress: " + progressMap.toString());
    }

    @Override
    public int getUploadStatus(String uuid) {
        if (progressMap == null) {
            return 0;
        }
        System.out.println("getUploadStatus: " + progressMap.toString());
        if (!progressMap.containsKey(uuid)) {
            return 0;
        }
        return (int) progressMap.get(uuid);
    }

    @Override
    public void downloadFile(String path, HttpServletResponse response) throws Exception {
        this.mustExists(path);
        FileSystem fs = fileSystemFactory.getFileSystem();
        Path fsPath = new Path(path);

        FileStatus fileStatus = fs.getFileStatus(fsPath);
        if (!fileStatus.isFile()) {
            this.notFileException(fsPath.toString());
        }
        HdfsFileInfo fileInfo = new HdfsFileInfo(fileStatus, fs.getContentSummary(fsPath));


        FSDataInputStream in = fs.open(fsPath);
        String filename = fileInfo.getFilename();
        response.setHeader("Content-Length", "" + fileInfo.getLength());
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Type", "application/force-download");
        response.setHeader("Content-Disposition", MessageFormatter.format("attachment; fullyQualifiedPath={}; filename={};",
                URLEncoder.encode(fileInfo.getFullyQualifiedPath(), "UTF-8"), filename).getMessage());
        response.setStatus(200);

        ServletOutputStream out = response.getOutputStream();

        byte[] b = new byte[1024];
        int numBytes = 0;
        while ((numBytes = in.read(b)) > 0) {
            out.write(b, 0, numBytes);
        }

        in.close();
        out.close();
        fs.close();
    }

    @Override
    public HdfsListInfo list(String path, int start, int end, final String filter) throws Exception {
        HdfsListInfo hdfsListInfo = new HdfsListInfo();

        this.indexCheck(start, end);
        this.mustExists(path);

        FileSystem fs = fileSystemFactory.getFileSystem();
        Path fsPath = new Path(path);

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

        hdfsListInfo.setFileInfoList(listStatus);
        hdfsListInfo.setCount(count);
        return hdfsListInfo;
    }

    @Override
    public HdfsFileInfo getStatus(String path) throws Exception {
        this.mustExists(path);

        FileSystem fs = fileSystemFactory.getFileSystem();
        Path fsPath = new Path(path);
        FileStatus fileStatus = fs.getFileStatus(fsPath);
        ContentSummary summary = fs.getContentSummary(fsPath);
        return new HdfsFileInfo(fileStatus, summary);
    }

    @Override
    public void createFile(String path, InputStream is, String owner, String group, String permission, boolean overwrite) throws Exception {
        if (!overwrite) {
            this.mustNotExists(path);
        }
        this._createEmptyFile(path);
        this._setOwner(path, owner, group);
        this._setPermission(path, permission);
        this._appendFile(path, is);
    }

    @Override
    public void createFileProgress(String uuid, long size, String path, InputStream is, String owner, String group, String permission, boolean overwrite) throws Exception {
        if (!overwrite) {
            this.mustNotExists(path);
        }
        this._createEmptyFile(path);
        this._setOwner(path, owner, group);
        this._setPermission(path, permission);

        double status = 0;
        FileSystem fs = fileSystemFactory.getFileSystem();
        Path fsPath = new Path(path);
        FSDataOutputStream out = fs.append(fsPath);
        byte[] b = new byte[1024];
        int numBytes = 0;
        int totalread = 0;
        int count = 0;
        while ((numBytes = is.read(b)) > 0) {
            count++;
            totalread += numBytes;
            status = (totalread / size) * 100;
            if (count % 1000 == 0) {
                System.out.println(totalread + " : " + status);
                this.setProgress(uuid, (int) status);
            }
            out.write(b, 0, numBytes);
        }
        this.setProgress(uuid, 100);

        is.close();
        out.close();
        fs.close();
    }

    @Override
    public void createEmptyFile(String path, String owner, String group, String permission, boolean overwrite) throws Exception {
        if (!overwrite) {
            this.mustNotExists(path);
        }
        this._createEmptyFile(path);
        this._setOwner(path, owner, group);
        this._setPermission(path, permission);
    }

    @Override
    public void appendFile(String path, InputStream is) throws Exception {
        this.mustExists(path);

        FileSystem fs = fileSystemFactory.getFileSystem();
        Path fsPath = new Path(path);

        FileStatus fileStatus = fs.getFileStatus(fsPath);
        if (!fileStatus.isFile()) {
            this.notFileException(fsPath.toString());
        }
        this._appendFile(path, is);
    }

    @Override
    public Path rename(String path, String rename) throws Exception {
        this.mustExists(path);
        return this._rename(path, rename);
    }

    @Override
    public boolean createDirectory(String path, String owner, String group, String permission) throws Exception {
        this.rootCheck(path);
        this.mustNotExists(path);

        try {
            FileSystem fs = fileSystemFactory.getFileSystem();
            Path fsPath = new Path(path);

            if (fs.mkdirs(fsPath)) {
                this._setOwner(path, owner, group);
                this._setPermission(path, permission);
                fs.close();
            }
            return true;
        } catch (IOException ex) {
            throw new ServiceException("디렉토리를 생성할 수 없습니다.", ex);
        }
    }

    /**
     * HDFS의 파일 또는 디렉토리를 삭제한다.
     *
     * @param path HDFS의 파일 또는 디렉토리
     * @return 정상적으로 삭제된 경우 <tt>true</tt>, 그렇지 않은 경우는 <tt>false</tt>
     * @throws ServiceException 파일 시스템에 접근할 수 없는 경우
     */
    @Override
    public boolean delete(String path) throws Exception {
        this.rootCheck(path);
        this.mustExists(path);
        try {
            FileSystem fs = fileSystemFactory.getFileSystem();
            Path fsPath = new Path(path);
            boolean delete = fs.delete(fsPath, true);
            fs.close();
            return delete;
        } catch (Exception ex) {
            throw new ServiceException("디렉토리를 삭제할 수 없습니다.", ex);
        }
    }

    @Override
    public boolean setOwner(String path, String owner, String group, boolean recursive) {
        try {
            this.rootCheck(path);
            this.mustExists(path);

            FileSystem fs = fileSystemFactory.getFileSystem();
            Path fsPath = new Path(path);

            FileStatus fileStatus = fs.getFileStatus(fsPath);
            if (fileStatus.isDirectory()) {
                this.runChown(recursive, owner, group, path);
            } else {
                this._setOwner(path, owner, group);
            }
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException("권한을 변경할 수 없습니다.", ex);
        }
    }

    @Override
    public boolean setPermission(String path, String permission, boolean recursive) {
        try {
            this.rootCheck(path);
            this.mustExists(path);

            FileSystem fs = fileSystemFactory.getFileSystem();
            Path fsPath = new Path(path);

            FileStatus fileStatus = fs.getFileStatus(fsPath);
            if (fileStatus.isDirectory()) {
                this.runChmod(recursive, permission, path);
            } else {
                this._setPermission(path, permission);
            }
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException("권한을 변경할 수 없습니다.", ex);
        }
    }

    private void _appendFile(String path, InputStream is) throws Exception {
        FileSystem fs = fileSystemFactory.getFileSystem();
        Path fsPath = new Path(path);
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

    private Path _rename(String path, String rename) throws Exception {
        FileSystem fs = fileSystemFactory.getFileSystem();
        Path fsPath = new Path(path);
        FileStatus fileStatus = fs.getFileStatus(fsPath);
        HdfsFileInfo hdfsFileInfo = new HdfsFileInfo(fileStatus, fs.getContentSummary(fsPath));
        String parentPath = hdfsFileInfo.getPath();

        String newPath = parentPath + "/" + rename;
        Path path1 = new Path(newPath);
        if (StringUtils.isEmpty(rename)) {
            logger.warn("Failed rename HDFS file, Rename is empty : {}", newPath);
            throw new ServiceException("변경 대상 이름이 정해지지 않았습니다.");
        }

        fs.rename(fsPath, path1);
        fs.close();
        return path1;
    }

    /**
     * 선택한 HDFS 경로의 권한(소유권)을 변경한다.
     *
     * @param recursive 하위 경로 포함 옵션
     * @param owner     소유자
     * @param group     그룹
     * @param srcPath   권한을 변경할 HDFS 경로
     * @return true or false
     */
    private boolean runChown(boolean recursive, String owner, String group, String srcPath) {
        try {
            if (StringUtils.isEmpty(owner)) {
                owner = config.getProperty("system.hdfs.super.user");
            }
            if (StringUtils.isEmpty(group)) {
                group = owner;
            }
            String chownRCli = config.getProperty("hadoop2.namenode.ownership.recursively.cli");
            String chownCli = config.getProperty("hadoop2.namenode.ownership.cli");
            String cli;

            if (recursive) {
                cli = MessageFormatter.arrayFormat(chownRCli, new String[]{owner, group, srcPath}).getMessage();
            } else {
                cli = MessageFormatter.arrayFormat(chownCli, new String[]{owner, group, srcPath}).getMessage();
            }

            logger.debug("선택한 HDFS 경로의 소유권을 변경합니다. CLI: {}", cli);

            Process process = Runtime.getRuntime().exec(cli);

            Expect expect = new ExpectBuilder()
                    .withInputs(process.getInputStream())
                    .withOutput(process.getOutputStream())
                    .withTimeout(1, TimeUnit.SECONDS)
                    .withExceptionOnFailure()
                    .build();

            process.waitFor();
            expect.close();

            logger.debug("선택한 HDFS 경로의 '{}' 소유권이 변경되었습니다.", srcPath);

            return true;
        } catch (Exception ex) {
            logger.warn("해당 경로'{}'가 HDFS 파일시스템에 존재하지 않거나 확인할 수 없습니다.", srcPath);
            logger.warn("{} : {}\n{}", new String[]{
                    ex.getClass().getName(), ex.getMessage(), ExceptionUtils.getFullStackTrace(ex)
            });
            return false;
        }
    }

    /**
     * 선택한 HDFS 경로의 권한(허가권)을 변경한다.
     *
     * @param recursive  하위 경로 포함 옵션
     * @param permission 적용할 권한 정보 (ex. 777)
     * @param srcPath    권한을 변경할 HDFS 경로
     * @return true of false
     */
    private boolean runChmod(boolean recursive, String permission, String srcPath) {
        try {
            if (StringUtils.isEmpty(permission)) {
                return false;
            }
            String chmodRCli = config.getProperty("hadoop2.namenode.permission.recursively.cli");
            String chmodCli = config.getProperty("hadoop2.namenode.permission.cli");
            String cli;

            if (recursive) {
                cli = MessageFormatter.arrayFormat(chmodRCli, new String[]{permission, srcPath}).getMessage();
            } else {
                cli = MessageFormatter.arrayFormat(chmodCli, new String[]{permission, srcPath}).getMessage();
            }

            logger.debug("선택한 HDFS 경로의 권한을 변경합니다. CLI : {}", cli);

            Process process = Runtime.getRuntime().exec(cli);

            Expect expect = new ExpectBuilder()
                    .withInputs(process.getInputStream())
                    .withOutput(process.getOutputStream())
                    .withTimeout(1, TimeUnit.SECONDS)
                    .withExceptionOnFailure()
                    .build();

            process.waitFor();
            expect.close();

            logger.debug("선택한 HDFS 경로의 '{}' 권한이 변경되었습니다.", srcPath);

            return true;
        } catch (Exception ex) {
            logger.warn("해당 경로'{}'가 HDFS 파일시스템에 존재하지 않거나 확인할 수 없습니다.", srcPath);
            logger.warn("{} : {}\n{}", new String[]{
                    ex.getClass().getName(), ex.getMessage(), ExceptionUtils.getFullStackTrace(ex)
            });
            return false;
        }
    }

    private void _createEmptyFile(String path) throws Exception {
        FileSystem fs = fileSystemFactory.getFileSystem();
        fs.create(new Path(path)).close();
        fs.close();
    }

    private void _setOwner(String path, String owner, String group) throws Exception {
        if (StringUtils.isEmpty(owner)) {
            owner = config.getProperty("system.hdfs.super.user");
        }
        if (StringUtils.isEmpty(group)) {
            group = owner;
        }
        FileSystem fs = fileSystemFactory.getFileSystem();
        Path fsPath = new Path(path);
        if (!fs.exists(fsPath)) {
            this.notFoundException(fsPath.toString());
        }
        fs.setOwner(fsPath, StringUtils.isEmpty(owner) ? null : owner, StringUtils.isEmpty(group) ? null : group);
        fs.close();
    }

    private void _setPermission(String path, String permission) throws Exception {
        if (StringUtils.isEmpty(permission)) {
            return;
        }
        FileSystem fs = fileSystemFactory.getFileSystem();
        Path fsPath = new Path(path);
        if (!fs.exists(fsPath)) {
            this.notFoundException(fsPath.toString());
        }
        FsPermission fsPermission = new FsPermission(permission);
        fs.setPermission(fsPath, fsPermission);
        fs.close();
    }

    private boolean exists(String path) throws Exception {
        FileSystem fs = fileSystemFactory.getFileSystem();
        Path fsPath = new Path(path);
        return fs.exists(fsPath);
    }

    private void mustNotExists(String path) throws Exception {
        FileSystem fs = fileSystemFactory.getFileSystem();
        Path fsPath = new Path(path);
        if (fs.exists(fsPath)) {
            this.alreadyExistException(fsPath.toString());
        }
    }

    private void mustExists(String path) throws Exception {
        FileSystem fs = fileSystemFactory.getFileSystem();
        Path fsPath = new Path(path);
        if (!fs.exists(fsPath)) {
            this.notFoundException(fsPath.toString());
        }
    }

    private void rootCheck(String path) {
        if (path.equalsIgnoreCase("/")) {
            logger.warn("Root can not change permission : {}", path);
            throw new ServiceException("루트는 권한을 변경할 수 없습니다.");
        }
    }

    private void notFileException(String path) {
        logger.warn("File {} is not a file : {}", path);
        throw new ServiceException("파일이 아닙니다.");
    }

    private void notDirectoryException(String path) {
        logger.warn("File {} is not a directory : {}", path);
        throw new ServiceException("디렉토리가 아닙니다.");
    }

    private void alreadyExistException(String path) {
        logger.warn("File is already exist : {}", path);
        throw new ServiceException("파일이 이미 존재합니다.");
    }

    private void notFoundException(String path) {
        logger.warn("Failed find HDFS file, File not exist : {}", path);
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
    public void teragen() throws Exception {
        FileSystem fs = fileSystemFactory.getFileSystem();
        for (int i = 0; i < 1000000; i++) {
            fs.create(new Path("/user/ubuntu/many/uuid_u" + i)).close();
        }
        fs.close();
    }
}
