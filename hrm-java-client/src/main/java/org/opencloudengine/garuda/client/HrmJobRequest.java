package org.opencloudengine.garuda.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.opencloudengine.garuda.model.clientJob.ClientJob;
import org.opencloudengine.garuda.model.clientJob.ClientStatus;
import org.opencloudengine.garuda.model.request.*;
import org.opencloudengine.garuda.util.HttpUtils;
import org.opencloudengine.garuda.util.JsonUtils;
import org.opencloudengine.garuda.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by uengine on 2016. 9. 8..
 */
public class HrmJobRequest {

    /**
     * SLF4J Application Logging
     */
    private Logger logger = LoggerFactory.getLogger(HrmJobRequest.class);

    private BasicClientRequest request;

    private String schema = "http";

    private String host;

    private Integer port;

    private String basePath = "/rest/v1";

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public BasicClientRequest getRequest() {
        return request;
    }

    public void setRequest(BasicClientRequest request) {
        this.request = request;
    }

    public HrmJobRequest(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public ClientJob createJob() throws Exception {
        if (request == null) {
            throw new Exception("No request for send.");
        }

        String jobType = "";
        if (request instanceof HiveRequest) {
            jobType = ClientStatus.JOB_TYPE_HIVE;
        }
        if (request instanceof PigRequest) {
            jobType = ClientStatus.JOB_TYPE_PIG;
        }
        if (request instanceof SparkRequest) {
            jobType = ClientStatus.JOB_TYPE_SPARK;
        }
        if (request instanceof MrRequest) {
            jobType = ClientStatus.JOB_TYPE_MR;
        }
        if (request instanceof JavaRequest) {
            jobType = ClientStatus.JOB_TYPE_JAVA;
        }
        if (request instanceof PythonRequest) {
            jobType = ClientStatus.JOB_TYPE_PYTHON;
        }
        if (request instanceof ShellRequest) {
            jobType = ClientStatus.JOB_TYPE_SHELL;
        }
        if (request instanceof HbaseShellRequest) {
            jobType = ClientStatus.JOB_TYPE_HBASE_SHELL;
        }
        if (request instanceof HbaseClassRequest) {
            jobType = ClientStatus.JOB_TYPE_HBASE_CLASS;
        }
        if (request instanceof PhoenixRequest) {
            jobType = ClientStatus.JOB_TYPE_PHOENIX;
        }
        if (StringUtils.isEmpty(jobType)) {
            throw new Exception("Failed to parse Job Type from request.");
        }

        Map<String, Object> map = JsonUtils.convertClassToMap(request);
        String marshal = JsonUtils.marshal(map);

        HttpUtils httpUtils = new HttpUtils();
        String url = createBaseUrl() + "/clientJob/" + jobType;

        HttpResponse response = httpUtils.makeRequest("POST", url, marshal, createHeaders());
        return this.responseToClientJob(response);
    }

    public ClientJob getJob(String clientJobId) throws Exception {
        HttpUtils httpUtils = new HttpUtils();
        HttpResponse response = httpUtils.makeRequest("GET", createBaseUrl() + "/clientJob/job/" + clientJobId, null, createHeaders());
        return this.responseToClientJob(response);
    }

    public ClientJob killJob(String clientJobId) throws Exception {
        HttpUtils httpUtils = new HttpUtils();
        HttpResponse response = httpUtils.makeRequest("DELETE", createBaseUrl() + "/clientJob/kill/" + clientJobId, null, createHeaders());
        return this.responseToClientJob(response);
    }

    private String createBaseUrl() {
        String portUrl = "";
        if (this.port != null) {
            portUrl = ":" + this.port;
        }
        return this.schema + "://" + this.host + portUrl + this.basePath;
    }

    private Map<String, String> createHeaders() {
        Map<String, String> headers = new HashMap();
        headers.put("Content-Type", "application/json");
        return headers;
    }

    private ClientJob responseToClientJob(HttpResponse response) throws Exception {
        HttpEntity entity = response.getEntity();
        String responseText = EntityUtils.toString(entity);

        Map unmarshal = JsonUtils.unmarshal(responseText);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(unmarshal, ClientJob.class);
    }
}
