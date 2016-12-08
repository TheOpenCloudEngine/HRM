package org.opencloudengine.garuda.model.request;

import java.util.List;

/**
 * Created by uengine on 2016. 9. 1..
 */
public class HbaseClassRequest extends HbaseRequest {

    /**
     * Run the class named CLASSNAME
     * ex) hbase [<options>] CLASSNAME [<args>]
     * (to execute a class)
     */
    private String className;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * Run the class named CLASSNAME\n" +
                    "     * ex) hbase [<options>] CLASSNAME [<args>]\n" +
                    "     * (to execute a class)\n" +
                    "     */")
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * arguments.
     * ex) hbase [<options>] CLASSNAME [<args>]
     */
    private List<String> arguments;

    @FieldType(type = "textList",
            description = "/**\n" +
                    "     * arguments.\n" +
                    "     * ex) hbase [<options>] CLASSNAME [<args>]\n" +
                    "     */")
    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }
}
