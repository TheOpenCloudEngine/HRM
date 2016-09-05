package org.opencloudengine.garuda.model.request;

import java.util.List;
import java.util.Map;

/**
 * Created by uengine on 2016. 9. 1..
 */
public class JavaRequest extends BasicClientRequest {


    /**
     * class execute.
     * It can not be used with <jar> parameter
     * ex) java [-options] class [args...]
     * (to execute a class)
     */
    private String className;

    /**
     * jar execute.
     * It can not be used with <className> parameter
     * ex) java [-options] -jar jarfile [args...]
     * (to execute a jar file)
     */
    private String jar;

    /**
     * arguments.
     * ex) java [-options] class [args...]
     * * ex) java [-options] -jar jarfile [args...]
     */
    private List<String> arguments;

    /**
     * ex) -classpath <class search path of directories and zip/jar files>
     * A : separated list of directories, JAR archives,
     * and ZIP archives to search for class files.
     */
    private List<String> classPath;

    /**
     * ex) -D<name>=<value>
     * set a system property
     */
    private Map<String, String> javaOpts;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getJar() {
        return jar;
    }

    public void setJar(String jar) {
        this.jar = jar;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public List<String> getClassPath() {
        return classPath;
    }

    public void setClassPath(List<String> classPath) {
        this.classPath = classPath;
    }

    public Map<String, String> getJavaOpts() {
        return javaOpts;
    }

    public void setJavaOpts(Map<String, String> javaOpts) {
        this.javaOpts = javaOpts;
    }
}
