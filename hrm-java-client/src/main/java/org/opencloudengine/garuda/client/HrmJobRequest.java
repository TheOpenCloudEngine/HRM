package org.opencloudengine.garuda.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.opencloudengine.garuda.model.clientJob.ClientJob;
import org.opencloudengine.garuda.model.request.BasicClientRequest;
import org.opencloudengine.garuda.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
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

    public BasicClientRequest getRequest() {
        return request;
    }

    public void setRequest(BasicClientRequest request) {
        this.request = request;
    }

    public String send() throws IOException{
        if(request == null){
            logger.error("No request for send.");
        }
        Map<String, Object> map = JsonUtils.convertClassToMap(request);
        return JsonUtils.marshal(map);
    }
}
