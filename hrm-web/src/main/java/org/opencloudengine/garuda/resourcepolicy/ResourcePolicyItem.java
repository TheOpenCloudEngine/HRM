package org.opencloudengine.garuda.resourcepolicy;

import java.util.List;

/**
 * Created by uengine on 2016. 5. 3..
 */
public class ResourcePolicyItem {

    String scope;
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }


    List<ResourcePolicyCondition> resourcePolicyCondition;
        public List<ResourcePolicyCondition> getResourcePolicyCondition() {
            return resourcePolicyCondition;
        }
        public void setResourcePolicyCondition(List<ResourcePolicyCondition> resourcePolicyCondition) {
            this.resourcePolicyCondition = resourcePolicyCondition;
        }



}
