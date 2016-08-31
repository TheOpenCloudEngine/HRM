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

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.Path;

import java.io.InputStream;
import java.util.List;

public interface HdfsService {

    HdfsListInfo list(String path, int start, int end, String filter) throws Exception;

    HdfsFileInfo getStatus(String path) throws Exception;

    void createFile(String path, InputStream is, String owner, String group, String permission, boolean overwrite) throws Exception;

    void createEmptyFile(String path, String owner, String group, String permission, boolean overwrite) throws Exception;

    void appendFile(String path, InputStream is) throws Exception;

    Path rename(String path, String rename) throws Exception;

    boolean createDirectory(String path, String owner, String group, String permission) throws Exception;

    boolean delete(String path) throws Exception;

    boolean setOwner(String path, String owner, String group, boolean recursive);

    boolean setPermission(String path, String permission, boolean recursive);

    void teragen() throws Exception;
}