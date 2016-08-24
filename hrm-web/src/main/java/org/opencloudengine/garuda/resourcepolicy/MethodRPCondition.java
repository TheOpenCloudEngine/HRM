package org.opencloudengine.garuda.resourcepolicy;

import org.springframework.http.HttpRequest;

/**
 * Created by uengine on 2016. 5. 3..
 */
public class MethodRPCondition extends ResourcePolicyCondition{

    String method;
        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }


    @Override
    boolean evaluate(HttpRequest request) {
        return request.getMethod().name().equals(getMethod());
    }
}
