package org.opencloudengine.garuda.model.request;

import java.util.Map;

/**
 * Created by uengine on 2016. 9. 2..
 */
public class PigRequest extends BasicClientRequest {

    /**
     * The script to execute
     */
    private String script;

    @FieldType(type = "textarea",
            description = "/**\n" +
                    "     * The script to execute\n" +
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
     * ex) -file <script path>
     */
    private String scriptPath;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * Path to the script to execute.\n" +
                    "     * It overrides script parameter.\n" +
                    "     * ex) -file <script path>\n" +
                    "     */")
    public String getScriptPath() {
        return scriptPath;
    }

    public void setScriptPath(String scriptPath) {
        this.scriptPath = scriptPath;
    }

    /**
     * Key value pair of properties;
     * It will store as a file.
     * ex) -propertyFile <generated property file path>
     */
    private Map<String, String> properties;

    @FieldType(type = "map",
            description = "/**\n" +
                    "     * Key value pair of properties;\n" +
                    "     * It will store as a file.\n" +
                    "     * ex) -propertyFile <generated property file path>\n" +
                    "     */")
    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    /**
     * Path to property file
     * It overrides properties parameter.
     * ex) -propertyFile <exist property file path>
     */
    private String propertyFile;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * Path to property file\n" +
                    "     * It overrides properties parameter.\n" +
                    "     * ex) -propertyFile <exist property file path>\n" +
                    "     */")
    public String getPropertyFile() {
        return propertyFile;
    }

    public void setPropertyFile(String propertyFile) {
        this.propertyFile = propertyFile;
    }

    /**
     * Log4j configuration file, overrides log conf
     * ex) -log4jconf <conf path>
     */
    private String log4jconf;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * Log4j configuration file, overrides log conf\n" +
                    "     * ex) -log4jconf <conf path>\n" +
                    "     */")
    public String getLog4jconf() {
        return log4jconf;
    }

    public void setLog4jconf(String log4jconf) {
        this.log4jconf = log4jconf;
    }

    /**
     * Syntax check
     * ex) -check
     */
    private boolean check;

    @FieldType(type = "boolean",
            description = "/**\n" +
                    "     * Syntax check\n" +
                    "     * ex) -check\n" +
                    "     */")
    public boolean getCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    /**
     * Key value pair of the form param=val
     * ex)
     * -param date1=20080201
     * -param date2=20080202
     */
    private Map<String, String> param;

    @FieldType(type = "map",
            description = "/**\n" +
                    "     * Key value pair of the form param=val\n" +
                    "     * ex)\n" +
                    "     * -param date1=20080201\n" +
                    "     * -param date2=20080202\n" +
                    "     */")
    public Map<String, String> getParam() {
        return param;
    }

    public void setParam(Map<String, String> param) {
        this.param = param;
    }

    /**
     * Path to the parameter file
     * It overrides param parameter.
     * ex) -param_file <parameter file path>
     */
    private String paramPath;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * Path to the parameter file\n" +
                    "     * It overrides param parameter.\n" +
                    "     * ex) -param_file <parameter file path>\n" +
                    "     */")
    public String getParamPath() {
        return paramPath;
    }

    public void setParamPath(String paramPath) {
        this.paramPath = paramPath;
    }

    /**
     * Produces script with substituted parameters. Script is not executed.
     * ex) -dryrun
     */
    private boolean dryrun;

    @FieldType(type = "boolean",
            description = "/**\n" +
                    "     * Produces script with substituted parameters. Script is not executed.\n" +
                    "     * ex) -dryrun\n" +
                    "     */")
    public boolean getDryrun() {
        return dryrun;
    }

    public void setDryrun(boolean dryrun) {
        this.dryrun = dryrun;
    }

    /**
     * Print all error messages to screen
     * ex) -verbose
     */
    private boolean verbose;

    @FieldType(type = "boolean",
            description = "/**\n" +
                    "     * Print all error messages to screen\n" +
                    "     * ex) -verbose\n" +
                    "     */")
    public boolean getVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    /**
     * Turn warning logging on; also turns warning aggregation off
     * ex) -warning
     */
    private boolean warning;

    @FieldType(type = "boolean",
            description = "/**\n" +
                    "     * Turn warning logging on; also turns warning aggregation off\n" +
                    "     * ex) -warning\n" +
                    "     */")
    public boolean getWarning() {
        return warning;
    }

    public void setWarning(boolean warning) {
        this.warning = warning;
    }

    /**
     * Aborts execution on the first failed job; default is off
     * ex) -stop_on_failure
     */
    private boolean stopOnFailure;

    @FieldType(type = "boolean",
            description = "/**\n" +
                    "     * Aborts execution on the first failed job; default is off\n" +
                    "     * ex) -stop_on_failure\n" +
                    "     */")
    public boolean getStopOnFailure() {
        return stopOnFailure;
    }

    public void setStopOnFailure(boolean stopOnFailure) {
        this.stopOnFailure = stopOnFailure;
    }

    /**
     * Turn multiquery optimization off; default is on
     * ex) -no_multiquery
     */
    private boolean noMultiquery;

    @FieldType(type = "boolean",
            description = "/**\n" +
                    "     * Turn multiquery optimization off; default is on\n" +
                    "     * ex) -no_multiquery\n" +
                    "     */")
    public boolean getNoMultiquery() {
        return noMultiquery;
    }

    public void setNoMultiquery(boolean noMultiquery) {
        this.noMultiquery = noMultiquery;
    }

    /**
     * Turn fetch optimization off; default is on
     * ex) -no_fetch
     */
    private boolean noFetch;

    @FieldType(type = "boolean",
            description = "/**\n" +
                    "     * Turn fetch optimization off; default is on\n" +
                    "     * ex) -no_fetch\n" +
                    "     */")
    public boolean getNoFetch() {
        return noFetch;
    }

    public void setNoFetch(boolean noFetch) {
        this.noFetch = noFetch;
    }
}
