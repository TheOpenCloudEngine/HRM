package org.opencloudengine.garuda.model.request;

import java.util.List;

/**
 * Created by uengine on 2016. 9. 1..
 */
public class PythonRequest extends BasicClientRequest {


    /**
     * The script to execute
     * python [option] ... [-c cmd | -m mod | <generated script file> | -] [arg] ...
     */
    private String script;

    @FieldType(type = "textarea",
            description = "/**\n" +
                    "     * The script to execute\n" +
                    "     * python [option] ... [-c cmd | -m mod | <generated script file> | -] [arg] ...\n" +
                    "     */")
    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    /**
     * Path to the script to execute.
     * It overrides script parameter.
     * python [option] ... [-c cmd | -m mod | <exist script file path> | -] [arg] ...
     */
    private String scriptPath;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * Path to the script to execute.\n" +
                    "     * It overrides script parameter.\n" +
                    "     * python [option] ... [-c cmd | -m mod | <exist script file path> | -] [arg] ...\n" +
                    "     */")
    public String getScriptPath() {
        return scriptPath;
    }

    public void setScriptPath(String scriptPath) {
        this.scriptPath = scriptPath;
    }

    /**
     * arguments.
     * ex) python [option] ... [-c cmd | -m mod | file | -] [arg] ...
     */
    private List<String> arguments;

    @FieldType(type = "textList",
            description = "/**\n" +
                    "     * arguments.\n" +
                    "     * ex) python [option] ... [-c cmd | -m mod | file | -] [arg] ...\n" +
                    "     */")
    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }
}
