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
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
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

    @Override
    public List<FileStatus> getFile(String path) throws Exception {
        if (StringUtils.isEmpty(path)) {
            path = "/";
        }
        String hadoopHome = config.getProperty("hadoop.hadoop.home");

        Configuration conf = new Configuration();
        conf.addResource(new Path(hadoopHome + "/conf/core-site.xml"));
        conf.addResource(new Path(hadoopHome + "/conf/hdfs-site.xml"));
        conf.addResource(new Path(hadoopHome + "/conf/mapred-site.xml"));

        FileSystem fileSystem = FileSystem.get(conf);
        FileStatus[] fileStatuses = fileSystem.listStatus(new Path(path));

        List<FileStatus> listStatus = new ArrayList<>();
        for (int i = 0; i < fileStatuses.length; i++) {
            listStatus.add(fileStatuses[i]);
        }
        return listStatus;
    }

    @Override
    public void createFile(String path, InputStream is) throws Exception {
        if (StringUtils.isEmpty(path)) {
            path = "/";
        }
        String hadoopHome = config.getProperty("hadoop.hadoop.home");

        Configuration conf = new Configuration();
        conf.addResource(new Path(hadoopHome + "/conf/core-site.xml"));
        conf.addResource(new Path(hadoopHome + "/conf/hdfs-site.xml"));
        conf.addResource(new Path(hadoopHome + "/conf/mapred-site.xml"));

        FileSystem fileSystem = FileSystem.get(conf);
        FSDataOutputStream out = fileSystem.create(new Path(path));
        byte[] b = new byte[1024];
        int numBytes = 0;
        while ((numBytes = is.read(b)) > 0) {
            out.write(b, 0, numBytes);
        }

        is.close();
        out.close();
        fileSystem.close();
    }
}
