package org.opencloudengine.garuda.resourcepolicy;

import org.springframework.http.HttpRequest;

/**
 * Created by uengine on 2016. 5. 3..
 */
public abstract class ResourcePolicyCondition {



//    String scope;
//        public String getScope() {
//            return scope;
//        }
//    public void setScope(String scope) {
//            this.scope = scope;
//        }


    abstract boolean evaluate(HttpRequest request);
}
