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

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * class execute.\n" +
                    "     * It can not be used with <jar> parameter\n" +
                    "     * ex) java [-options] class [args...]\n" +
                    "     * (to execute a class)\n" +
                    "     */")
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * jar execute.
     * It can not be used with <className> parameter
     * ex) java [-options] -jar jarfile [args...]
     * (to execute a jar file)
     */
    private String jar;

    @FieldType(type = "text",
            description = "**\n" +
                    "     * jar execute.\n" +
                    "     * It can not be used with <className> parameter\n" +
                    "     * ex) java [-options] -jar jarfile [args...]\n" +
                    "     * (to execute a jar file)\n" +
                    "     */")
    public String getJar() {
        return jar;
    }

    public void setJar(String jar) {
        this.jar = jar;
    }

    /**
     * arguments.
     * ex) java [-options] class [args...]
     * * ex) java [-options] -jar jarfile [args...]
     */
    private List<String> arguments;

    @FieldType(type = "textList",
            description = "/**\n" +
                    "     * arguments.\n" +
                    "     * ex) java [-options] class [args...]\n" +
                    "     * * ex) java [-options] -jar jarfile [args...]\n" +
                    "     */")
    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    /**
     * ex) -classpath <class search path of directories and zip/jar files>
     * A : separated list of directories, JAR archives,
     * and ZIP archives to search for class files.
     */
    private List<String> classPath;

    @FieldType(type = "textList",
            description = "/**\n" +
                    "     * ex) -classpath <class search path of directories and zip/jar files>\n" +
                    "     * A : separated list of directories, JAR archives,\n" +
                    "     * and ZIP archives to search for class files.\n" +
                    "     */")
    public List<String> getClassPath() {
        return classPath;
    }

    public void setClassPath(List<String> classPath) {
        this.classPath = classPath;
    }

    /**
     * ex) -D<name>=<value>
     * set a system property
     */
    private Map<String, String> javaOpts;

    @FieldType(type = "map",
            description = "/**\n" +
                    "     * ex) -D<name>=<value>\n" +
                    "     * set a system property\n" +
                    "     */")
    public Map<String, String> getJavaOpts() {
        return javaOpts;
    }

    public void setJavaOpts(Map<String, String> javaOpts) {
        this.javaOpts = javaOpts;
    }
}
