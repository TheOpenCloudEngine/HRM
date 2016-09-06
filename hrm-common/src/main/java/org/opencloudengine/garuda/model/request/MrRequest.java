package org.opencloudengine.garuda.model.request;

import java.util.List;
import java.util.Map;

/**
 * Created by uengine on 2016. 9. 1..
 */
public class MrRequest extends BasicClientRequest {


    /**
     * specify an application configuration file
     * ex) -conf <configuration file>
     */
    private String conf;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * specify an application configuration file\n" +
                    "     * ex) -conf <configuration file>\n" +
                    "     */")
    public String getConf() {
        return conf;
    }

    public void setConf(String conf) {
        this.conf = conf;
    }

    /**
     * Use value for given property
     * ex)
     * -D <property=value>
     * -D <property=value>
     */
    private Map<String, String> properties;

    @FieldType(type = "map",
            description = "/**\n" +
                    "     * Use value for given property\n" +
                    "     * ex)\n" +
                    "     * -D <property=value>\n" +
                    "     * -D <property=value>\n" +
                    "     */")
    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    /**
     * Specify a namenode.
     * ex) -fs <local|namenode:port>
     */
    private String fs;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * Specify a namenode.\n" +
                    "     * ex) -fs <local|namenode:port>\n" +
                    "     */")
    public String getFs() {
        return fs;
    }

    public void setFs(String fs) {
        this.fs = fs;
    }

    /**
     * Specify a job tracker.
     * -jt <local|jobtracker:port>
     */
    private String jt;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * Specify a job tracker.\n" +
                    "     * -jt <local|jobtracker:port>\n" +
                    "     */")
    public String getJt() {
        return jt;
    }

    public void setJt(String jt) {
        this.jt = jt;
    }

    /**
     * Specify comma separated files to be copied to the map reduce cluster.
     * ex) -files <comma separated list of files>
     */
    private List<String> files;

    @FieldType(type = "textList",
            description = "/**\n" +
                    "     * Specify comma separated files to be copied to the map reduce cluster.\n" +
                    "     * ex) -files <comma separated list of files>\n" +
                    "     */")
    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    /**
     * Specify comma separated jar files to include in the classpath.
     * ex) -libjars <comma seperated list of jars>
     */
    private List<String> libjars;

    @FieldType(type = "testList",
            description = "/**\n" +
                    "     * Specify comma separated jar files to include in the classpath.\n" +
                    "     * ex) -libjars <comma seperated list of jars>\n" +
                    "     */")
    public List<String> getLibjars() {
        return libjars;
    }

    public void setLibjars(List<String> libjars) {
        this.libjars = libjars;
    }

    /**
     * Specify comma separated archives to be unarchived on the compute machines.
     * ex) -archives <comma separated list of archives>
     */
    private List<String> archives;

    @FieldType(type = "textList",
            description = "/**\n" +
                    "     * Specify comma separated archives to be unarchived on the compute machines.\n" +
                    "     * ex) -archives <comma separated list of archives>\n" +
                    "     */")
    public List<String> getArchives() {
        return archives;
    }

    public void setArchives(List<String> archives) {
        this.archives = archives;
    }

    /**
     * Runs a jar file.
     * ex) Usage: hadoop jar <jar> [mainClass] args...
     */
    private String jar;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * Runs a jar file.\n" +
                    "     * ex) Usage: hadoop jar <jar> [mainClass] args...\n" +
                    "     */")
    public String getJar() {
        return jar;
    }

    public void setJar(String jar) {
        this.jar = jar;
    }

    /**
     * Main class of jar.
     * ex) Usage: hadoop jar <jar> [mainClass] args...
     */
    private String mainClass;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * Main class of jar.\n" +
                    "     * ex) Usage: hadoop jar <jar> [mainClass] args...\n" +
                    "     */")
    public String getMainClass() {
        return mainClass;
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    /**
     * arguments of jar.
     * ex) Usage: hadoop jar <jar> [mainClass] args...
     */
    private List<String> arguments;

    @FieldType(type = "textList",
            description = "/**\n" +
                    "     * arguments of jar.\n" +
                    "     * ex) Usage: hadoop jar <jar> [mainClass] args...\n" +
                    "     */")
    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }
}
