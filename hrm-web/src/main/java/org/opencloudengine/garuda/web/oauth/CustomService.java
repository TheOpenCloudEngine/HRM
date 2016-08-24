package org.opencloudengine.garuda.web.oauth;

import org.opencloudengine.garuda.web.console.oauthclient.OauthClient;
import org.opencloudengine.garuda.web.console.oauthuser.OauthUser;
import org.opencloudengine.garuda.web.management.Management;

import java.util.List;
import java.util.Map;

/**
 * Created by uengine on 2016. 5. 9..
 */
public interface CustomService {

    boolean processTokenScript(Management management, OauthClient oauthClient, OauthUser oauthUser,
                               String scope, String tokenType, String claimJson, String type);

    Map processTokenTest(Management management, String userId, String clientId, String scopes,
                         String tokenType, String claimJson, String script);

    List<String> getUseCaseList(Management management);

    boolean inCase(Management management, String useCase);
}
