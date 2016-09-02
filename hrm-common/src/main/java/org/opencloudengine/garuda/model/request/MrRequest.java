package org.opencloudengine.garuda.model.request;

import java.util.List;
import java.util.Map;

/**
 * Created by uengine on 2016. 9. 1..
 */
public class MrRequest extends BasicRequest {


    /**
     * specify an application configuration file
     * ex) -conf <configuration file>
     */
    private String conf;

    /**
     * Use value for given property
     * ex)
     * -D <property=value>
     * -D <property=value>
     */
    private Map<String, String> properties;

    /**
     * Specify a namenode.
     * ex) -fs <local|namenode:port>
     */
    private String fs;

    /**
     * Specify a job tracker.
     * -jt <local|jobtracker:port>
     */
    private String jt;

    /**
     * Specify comma separated files to be copied to the map reduce cluster.
     * ex) -files <comma separated list of files>
     */
    private List<String> files;

    /**
     * Specify comma separated jar files to include in the classpath.
     * ex) -libjars <comma seperated list of jars>
     */
    private List<String> libjars;

    /**
     * Specify comma separated archives to be unarchived on the compute machines.
     * ex) -archives <comma separated list of archives>
     */
    private List<String> archives;

    /**
     * Runs a jar file.
     * ex) Usage: hadoop jar <jar> [mainClass] args...
     */
    private String jar;

    /**
     * Main class of jar.
     * ex) Usage: hadoop jar <jar> [mainClass] args...
     */
    private String mainClass;

    /**
     * arguments of jar.
     * ex) Usage: hadoop jar <jar> [mainClass] args...
     */
    private List<String> arguments;

    public String getConf() {
        return conf;
    }

    public void setConf(String conf) {
        this.conf = conf;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public String getFs() {
        return fs;
    }

    public void setFs(String fs) {
        this.fs = fs;
    }

    public String getJt() {
        return jt;
    }

    public void setJt(String jt) {
        this.jt = jt;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public List<String> getLibjars() {
        return libjars;
    }

    public void setLibjars(List<String> libjars) {
        this.libjars = libjars;
    }

    public List<String> getArchives() {
        return archives;
    }

    public void setArchives(List<String> archives) {
        this.archives = archives;
    }

    public String getJar() {
        return jar;
    }

    public void setJar(String jar) {
        this.jar = jar;
    }

    public String getMainClass() {
        return mainClass;
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }
}
