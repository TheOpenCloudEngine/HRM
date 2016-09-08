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

    private String basePath = "/rest/v1/clientJob";

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
        if (StringUtils.isEmpty(jobType)) {
            throw new Exception("Failed to parse Job Type from request.");
        }

        Map<String, Object> map = JsonUtils.convertClassToMap(request);
        String marshal = JsonUtils.marshal(map);

        HttpUtils httpUtils = new HttpUtils();
        String portUrl = "";
        if (this.port != null) {
            portUrl = ":" + this.port;
        }
        String url = this.schema + "://" + this.host + portUrl + this.basePath + "/" + jobType;

        Map<String, String> headers = new HashMap();
        headers.put("Content-Type", "application/json");
        headers.put("Cache-Control", "no-cache");

        HttpResponse response = httpUtils.makeRequest("POST", url, marshal, headers);
        HttpEntity entity = response.getEntity();
        String responseText = EntityUtils.toString(entity);
        Map unmarshal = JsonUtils.unmarshal(responseText);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(unmarshal, ClientJob.class);
    }
}
