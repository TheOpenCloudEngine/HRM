package org.opencloudengine.garuda.model.request;

import java.util.List;

/**
 * Created by uengine on 2016. 9. 1..
 */
public class HbaseShellRequest extends HbaseRequest {

    /**
     * The script to execute
     * ex) echo [SCRIPT] | hbase shell [OPTIONS]
     */
    private String script;

    @FieldType(type = "textarea",
            description = "/**\n" +
                    "     * The script to execute\n" +
                    "     * ex) echo [SCRIPT] | hbase shell [OPTIONS]\n" +
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
     * ex) echo [SCRIPTFILE] | hbase shell [OPTIONS]
     */
    private String scriptPath;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * Path to the script to execute.\n" +
                    "     * It overrides script parameter.\n" +
                    "     * ex) echo [SCRIPTFILE] | hbase shell [OPTIONS]\n" +
                    "     */")
    public String getScriptPath() {
        return scriptPath;
    }

    public void setScriptPath(String scriptPath) {
        this.scriptPath = scriptPath;
    }

    /**
     * Formatter for outputting results.
     * Valid options are: console, html.
     * (Default: console)
     * ex) --format=OPTION
     */
    private String format;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * Formatter for outputting results.\n" +
                    "     * Valid options are: console, html.\n" +
                    "     * (Default: console)\n" +
                    "     * ex) --format=OPTION\n" +
                    "     */")
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }


    /**
     * Set DEBUG log levels.
     * ex) --debug
     */
    private boolean debug;

    @FieldType(type = "boolean",
            description = "/**\n" +
                    "     * Set DEBUG log levels.\n" +
                    "     * ex) --debug\n" +
                    "     */")
    public boolean getDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     * Do not run within an IRB session
     * and exit with non-zero status on
     * first error.
     * ex) --noninteractive
     */
    private boolean noninteractive;

    @FieldType(type = "boolean",
            description = "/**\n" +
                    "     * Do not run within an IRB session\n" +
                    "     * and exit with non-zero status on\n" +
                    "     * first error.\n" +
                    "     * ex) --noninteractive\n" +
                    "     */")
    public boolean getNoninteractive() {
        return noninteractive;
    }

    public void setNoninteractive(boolean noninteractive) {
        this.noninteractive = noninteractive;
    }
}
