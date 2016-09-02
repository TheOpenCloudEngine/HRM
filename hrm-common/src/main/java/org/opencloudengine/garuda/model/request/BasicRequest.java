package org.opencloudengine.garuda.model.request;

/**
 * Created by uengine on 2016. 9. 2..
 */
public class BasicRequest {

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
     * Specify job id.
     * If Job id is already exist, it would be failed.
     * ex) random-job-id
     */
    private String jobId;

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

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }
}
