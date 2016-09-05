package org.opencloudengine.garuda.model.request;

/**
 * Created by uengine on 2016. 9. 1..
 */
public class WorkflowRequest extends BasicClientRequest {

    private String workflowId;

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }
}