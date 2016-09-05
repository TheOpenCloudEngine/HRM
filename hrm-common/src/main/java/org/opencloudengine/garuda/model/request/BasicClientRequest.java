package org.opencloudengine.garuda.model.request;

/**
 * Created by uengine on 2016. 9. 2..
 */
public class BasicClientRequest {

    /**
     * export HADOOP_USER_NAME variable before execute
     * ex) ubuntu
     */
    private String doAs;

    /**
     * Web hook url for action event
     * ex) http://host:port/servlet/path
     */
    private String eventHook;

    /**
     * Web hook url for action stdout and stderr
     * ex) http://host:port/servlet/path
     */
    private String stremingHook;

    /**
     * Specify clientJob id.
     * If clientJob Id is already exist, it would be failed.
     * ex) random-clientJob-Id
     */
    private String clientJobId;

    /**
     * Specify clientJob Name.
     * If null, it will return "Default {jobType} job"
     * ex) clientJobName
     */
    private String clientJobName;

    public String getDoAs() {
        return doAs;
    }

    public void setDoAs(String doAs) {
        this.doAs = doAs;
    }

    public String getEventHook() {
        return eventHook;
    }

    public void setEventHook(String eventHook) {
        this.eventHook = eventHook;
    }

    public String getStremingHook() {
        return stremingHook;
    }

    public void setStremingHook(String stremingHook) {
        this.stremingHook = stremingHook;
    }

    public String getClientJobId() {
        return clientJobId;
    }

    public void setClientJobId(String clientJobId) {
        this.clientJobId = clientJobId;
    }

    public String getClientJobName() {
        return clientJobName;
    }

    public void setClientJobName(String clientJobName) {
        this.clientJobName = clientJobName;
    }
}
