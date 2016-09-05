package org.opencloudengine.garuda.model.request;

import java.util.List;

/**
 * Created by uengine on 2016. 9. 1..
 */
public class pythonRequest extends BasicClientRequest {


    /**
     * The script to execute
     * python [option] ... [-c cmd | -m mod | <generated script file> | -] [arg] ...
     */
    private String script;

    /**
     * Path to the script to execute.
     * It overrides script parameter.
     * python [option] ... [-c cmd | -m mod | <exist script file path> | -] [arg] ...
     */
    private String scriptPath;

    /**
     * arguments.
     * ex) python [option] ... [-c cmd | -m mod | file | -] [arg] ...
     */
    private List<String> arguments;

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getScriptPath() {
        return scriptPath;
    }

    public void setScriptPath(String scriptPath) {
        this.scriptPath = scriptPath;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }
}
