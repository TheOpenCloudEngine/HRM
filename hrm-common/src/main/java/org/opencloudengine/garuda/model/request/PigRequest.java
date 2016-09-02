package org.opencloudengine.garuda.model.request;

import java.util.Map;

/**
 * Created by uengine on 2016. 9. 2..
 */
public class PigRequest extends BasicRequest {

    /**
     * The script to execute
     */
    private String script;

    /**
     * Path to the script to execute.
     * It overrides script parameter.
     * ex) -file <script path>
     */
    private String scriptPath;

    /**
     * Key value pair of properties;
     * It will store as a file.
     * ex) -propertyFile <generated property file path>
     */
    private Map<String, String> properties;

    /**
     * Path to property file
     * It overrides properties parameter.
     * ex) -propertyFile <exist property file path>
     */
    private String propertyFile;

    /**
     * Log4j configuration file, overrides log conf
     * ex) -log4jconf <conf path>
     */
    private String log4jconf;

    /**
     * Syntax check
     * ex) -check
     */
    private boolean check;

    /**
     * Key value pair of the form param=val
     * ex)
     * -param date1=20080201
     * -param date2=20080202
     */
    private Map<String, String> param;

    /**
     * Path to the parameter file
     * It overrides param parameter.
     * ex) -param_file <parameter file path>
     */
    private String paramPath;

    /**
     * Produces script with substituted parameters. Script is not executed.
     * ex) -dryrun
     */
    private boolean dryrun;

    /**
     * Print all error messages to screen
     * ex) -verbose
     */
    private boolean verbose;

    /**
     * Turn warning logging on; also turns warning aggregation off
     * ex) -warning
     */
    private boolean warning;

    /**
     * Aborts execution on the first failed job; default is off
     * ex) -stop_on_failure
     */
    private boolean stopOnFailure;

    /**
     * Turn multiquery optimization off; default is on
     * ex) -no_multiquery
     */
    private boolean noMultiquery;

    /**
     * Turn fetch optimization off; default is on
     * ex) -no_fetch
     */
    private boolean noFetch;

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

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public String getPropertyFile() {
        return propertyFile;
    }

    public void setPropertyFile(String propertyFile) {
        this.propertyFile = propertyFile;
    }

    public String getLog4jconf() {
        return log4jconf;
    }

    public void setLog4jconf(String log4jconf) {
        this.log4jconf = log4jconf;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public Map<String, String> getParam() {
        return param;
    }

    public void setParam(Map<String, String> param) {
        this.param = param;
    }

    public String getParamPath() {
        return paramPath;
    }

    public void setParamPath(String paramPath) {
        this.paramPath = paramPath;
    }

    public boolean isDryrun() {
        return dryrun;
    }

    public void setDryrun(boolean dryrun) {
        this.dryrun = dryrun;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public boolean isWarning() {
        return warning;
    }

    public void setWarning(boolean warning) {
        this.warning = warning;
    }

    public boolean isStopOnFailure() {
        return stopOnFailure;
    }

    public void setStopOnFailure(boolean stopOnFailure) {
        this.stopOnFailure = stopOnFailure;
    }

    public boolean isNoMultiquery() {
        return noMultiquery;
    }

    public void setNoMultiquery(boolean noMultiquery) {
        this.noMultiquery = noMultiquery;
    }

    public boolean isNoFetch() {
        return noFetch;
    }

    public void setNoFetch(boolean noFetch) {
        this.noFetch = noFetch;
    }
}
