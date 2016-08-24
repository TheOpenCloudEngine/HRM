package org.opencloudengine.garuda.resourcepolicy;

import org.springframework.http.HttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uengine on 2016. 5. 3..
 */
public class ResourcePolicy {

    public List<ResourcePolicyItem> getResourcePolicyItemList() {
        return resourcePolicyItemList;
    }

    public void setResourcePolicyItemList(List<ResourcePolicyItem> resourcePolicyItemList) {
        this.resourcePolicyItemList = resourcePolicyItemList;
    }

    List<ResourcePolicyItem> resourcePolicyItemList;

    public List<String> getScopes(HttpRequest httpRequest){

        List<String> scopes = new ArrayList<String>();

        for(ResourcePolicyItem resourcePolicyItem : getResourcePolicyItemList()){

            boolean isMet = true;

            for(ResourcePolicyCondition resourcePolicyCondition : resourcePolicyItem.getResourcePolicyCondition()){
                if(!resourcePolicyCondition.evaluate(httpRequest)) {
                    isMet = false;
                    break;
                }
            }

            if(isMet)
                scopes.add(resourcePolicyItem.getScope());
        }

        return scopes;
    }
}
