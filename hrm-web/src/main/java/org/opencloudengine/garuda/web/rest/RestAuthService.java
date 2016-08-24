package org.opencloudengine.garuda.web.rest;

import org.opencloudengine.garuda.web.management.Management;

import javax.servlet.http.HttpServletRequest;

public interface RestAuthService {
    Management managementParser(HttpServletRequest request);
}
