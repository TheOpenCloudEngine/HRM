package org.opencloudengine.garuda.web.oauth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface OauthGrantService {

    void processCodeGrant(AccessTokenResponse accessTokenResponse) throws Exception;

    void processPasswordGrant(AccessTokenResponse accessTokenResponse) throws Exception;

    void processClientCredentialsGrant(AccessTokenResponse accessTokenResponse) throws Exception;

    void processJWTGrant(AccessTokenResponse accessTokenResponse) throws Exception;

    void processRedirect(AccessTokenResponse accessTokenResponse);

    void processTokenInfo(AccessTokenResponse accessTokenResponse) throws Exception;

    void processRefreshToken(AccessTokenResponse accessTokenResponse) throws Exception;

}
