package org.opencloudengine.garuda.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.opencloudengine.garuda.model.HdfsFileStatus;
import org.opencloudengine.garuda.model.HdfsListInfo;
import org.opencloudengine.garuda.model.HdfsListStatus;
import org.opencloudengine.garuda.model.request.*;
import org.opencloudengine.garuda.util.HttpUtils;
import org.opencloudengine.garuda.util.JsonUtils;
import org.opencloudengine.garuda.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by uengine on 2016. 9. 8..
 */
public class HdfsRequest {

    /**
     * SLF4J Application Logging
     */
    private Logger logger = LoggerFactory.getLogger(HdfsRequest.class);

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

    public HdfsRequest(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public HdfsListStatus listStatus(String path, int start, int end, String filter) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("path", path);
        params.put("start", start);
        params.put("end", end);
        if (!StringUtils.isEmpty(filter)) {
            params.put("filter", filter);
        }

        HttpUtils httpUtils = new HttpUtils();
        String queryString = HttpUtils.createGETQueryString(params);
        HttpResponse response =
                httpUtils.makeRequest("GET", createBaseUrl() + "/hdfs/status/list" + queryString, null, createHeaders());

        HttpEntity entity = response.getEntity();
        String responseText = EntityUtils.toString(entity);

        Map unmarshal = JsonUtils.unmarshal(responseText);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(unmarshal, HdfsListStatus.class);
    }

    public HdfsFileStatus getStatus(String path) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("path", path);

        HttpUtils httpUtils = new HttpUtils();
        String queryString = HttpUtils.createGETQueryString(params);
        HttpResponse response =
                httpUtils.makeRequest("GET", createBaseUrl() + "/hdfs/status" + queryString, null, createHeaders());

        HttpEntity entity = response.getEntity();
        String responseText = EntityUtils.toString(entity);

        Map unmarshal = JsonUtils.unmarshal(responseText);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(unmarshal, HdfsFileStatus.class);
    }

    public boolean createFile(String path, String owner, String group, String permission, boolean overwrite, File file) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("path", path);
        params.put("owner", owner);
        params.put("group", group);
        params.put("permission", permission);
        params.put("overwrite", overwrite);

        HttpUtils httpUtils = new HttpUtils();
        String queryString = HttpUtils.createGETQueryString(params);
        HttpResponse response = httpUtils.makeFileRequest("POST", createBaseUrl() + "/hdfs/file" + queryString, file, createHeaders());

        if (response.getStatusLine().getStatusCode() != 201) {
            return false;
        } else {
            return true;
        }
    }

    public boolean appnedFile(String path, File file) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("path", path);

        HttpUtils httpUtils = new HttpUtils();
        String queryString = HttpUtils.createGETQueryString(params);
        HttpResponse response = httpUtils.makeFileRequest("PUT", createBaseUrl() + "/hdfs/file" + queryString, file, createHeaders());

        if (response.getStatusLine().getStatusCode() != 200) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteFile(String path) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("path", path);

        HttpUtils httpUtils = new HttpUtils();
        String queryString = HttpUtils.createGETQueryString(params);
        HttpResponse response = httpUtils.makeRequest("DELETE", createBaseUrl() + "/hdfs/file" + queryString, null, createHeaders());

        if (response.getStatusLine().getStatusCode() != 204) {
            return false;
        } else {
            return true;
        }
    }

    public void download(String path, OutputStream outputStream) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("path", path);

        HttpUtils httpUtils = new HttpUtils();
        String queryString = HttpUtils.createGETQueryString(params);
        HttpResponse response = httpUtils.makeRequest("GET", createBaseUrl() + "/hdfs/file" + queryString, null, createHeaders());

        response.getEntity().writeTo(outputStream);
    }

    public boolean createDir(String path, String owner, String group, String permission) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("path", path);
        params.put("owner", owner);
        params.put("group", group);
        params.put("permission", permission);

        HttpUtils httpUtils = new HttpUtils();
        String queryString = HttpUtils.createGETQueryString(params);
        HttpResponse response = httpUtils.makeFileRequest("POST", createBaseUrl() + "/hdfs/directory" + queryString, null, createHeaders());

        if (response.getStatusLine().getStatusCode() != 201) {
            return false;
        } else {
            return true;
        }
    }

    public boolean rename(String path, String rename) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("path", path);
        params.put("rename", rename);

        HttpUtils httpUtils = new HttpUtils();
        String queryString = HttpUtils.createGETQueryString(params);
        HttpResponse response = httpUtils.makeFileRequest("PUT", createBaseUrl() + "/hdfs/rename" + queryString, null, createHeaders());

        if (response.getStatusLine().getStatusCode() != 200) {
            return false;
        } else {
            return true;
        }
    }

    public boolean owner(String path, String owner, String group, boolean recursive) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("path", path);
        params.put("owner", owner);
        params.put("group", group);
        params.put("recursive", recursive);

        HttpUtils httpUtils = new HttpUtils();
        String queryString = HttpUtils.createGETQueryString(params);
        HttpResponse response = httpUtils.makeFileRequest("PUT", createBaseUrl() + "/hdfs/owner" + queryString, null, createHeaders());

        if (response.getStatusLine().getStatusCode() != 200) {
            return false;
        } else {
            return true;
        }
    }

    public boolean permission(String path, String permission, boolean recursive) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("path", path);
        params.put("permission", permission);
        params.put("recursive", recursive);

        HttpUtils httpUtils = new HttpUtils();
        String queryString = HttpUtils.createGETQueryString(params);
        HttpResponse response = httpUtils.makeFileRequest("PUT", createBaseUrl() + "/hdfs/permission" + queryString, null, createHeaders());

        if (response.getStatusLine().getStatusCode() != 200) {
            return false;
        } else {
            return true;
        }
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

}
