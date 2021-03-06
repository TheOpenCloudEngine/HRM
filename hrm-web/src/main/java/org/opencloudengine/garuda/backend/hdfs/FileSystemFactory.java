package org.opencloudengine.garuda.backend.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.opencloudengine.garuda.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * Created by uengine on 2016. 8. 26..
 */
/**
 * @author Seungpil, Park
 * @since 0.1
 */
@Component
public class FileSystemFactory {

    /**
     * SLF4J Logging
     */
    private Logger logger = LoggerFactory.getLogger(FileSystemFactory.class);

    @Value("#{config['hadoop.hadoop.home']}")
    private String hadoopHome;

    public FileSystem getFileSystem() {
        try {
            Configuration conf = new Configuration();
            conf.addResource(new Path(hadoopHome + "/conf/core-site.xml"));
            conf.addResource(new Path(hadoopHome + "/conf/hdfs-site.xml"));
            conf.addResource(new Path(hadoopHome + "/conf/mapred-site.xml"));

            return FileSystem.get(conf);
        } catch (Exception ex) {
            logger.error("Unable create hadoop FileSystem {}", hadoopHome);
            throw new ServiceException(ex);
        }
    }

    public FileSystem getFileSystem(String path) {
        try {
            Configuration conf = new Configuration();
            conf.addResource(new Path(hadoopHome + "/conf/core-site.xml"));
            conf.addResource(new Path(hadoopHome + "/conf/hdfs-site.xml"));
            conf.addResource(new Path(hadoopHome + "/conf/mapred-site.xml"));

            return FileSystem.get(new Path(path).toUri(), conf);
        } catch (Exception ex) {
            logger.error("Unable create hadoop FileSystem {}", hadoopHome);
            throw new ServiceException(ex);
        }
    }
}
