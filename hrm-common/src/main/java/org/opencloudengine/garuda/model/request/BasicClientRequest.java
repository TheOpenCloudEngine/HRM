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

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * export HADOOP_USER_NAME variable before execute\n" +
                    "     * ex) ubuntu\n" +
                    "     */")
    public String getDoAs() {
        return doAs;
    }

    public void setDoAs(String doAs) {
        this.doAs = doAs;
    }

    /**
     * Web hook url for action event
     * ex) http://host:port/servlet/path
     */
    private String eventHook;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * Web hook url for action event\n" +
                    "     * ex) http://host:port/servlet/path\n" +
                    "     */")
    public String getEventHook() {
        return eventHook;
    }

    public void setEventHook(String eventHook) {
        this.eventHook = eventHook;
    }

    /**
     * Web hook url for action stdout and stderr
     * ex) http://host:port/servlet/path
     */
    private String streamingHook;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * Web hook url for action stdout and stderr\n" +
                    "     * ex) http://host:port/servlet/path\n" +
                    "     */")
    public String getStreamingHook() {
        return streamingHook;
    }

    public void setStreamingHook(String streamingHook) {
        this.streamingHook = streamingHook;
    }

    /**
     * Specify clientJob id.
     * If clientJob Id is already exist, it would be failed.
     * ex) random-clientJob-Id
     */
    private String clientJobId;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * Specify clientJob id.\n" +
                    "     * If clientJob Id is already exist, it would be failed.\n" +
                    "     * ex) random-clientJob-Id\n" +
                    "     */")
    public String getClientJobId() {
        return clientJobId;
    }

    public void setClientJobId(String clientJobId) {
        this.clientJobId = clientJobId;
    }

    /**
     * Specify clientJob Name.
     * If null, it will return "Default {jobType} job"
     * ex) clientJobName
     */
    private String clientJobName;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * Specify clientJob Name.\n" +
                    "     * If null, it will return \"Default {jobType} job\"\n" +
                    "     * ex) clientJobName\n" +
                    "     */")
    public String getClientJobName() {
        return clientJobName;
    }

    public void setClientJobName(String clientJobName) {
        this.clientJobName = clientJobName;
    }
}
