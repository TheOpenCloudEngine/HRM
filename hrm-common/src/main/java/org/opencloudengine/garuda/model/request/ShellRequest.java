package org.opencloudengine.garuda.model.request;

import java.util.List;

/**
 * Created by uengine on 2016. 9. 1..
 */
public class ShellRequest extends BasicClientRequest {


    /**
     * The script to execute
     * bin/sh <generated script file> [arg] ...
     */
    private String script;

    /**
     * Path to the script to execute.
     * It overrides script parameter.
     * bin/sh <exist script file> [arg] ...
     */
    private String scriptPath;

    /**
     * arguments.
     * bin/sh file [arg] ...
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
