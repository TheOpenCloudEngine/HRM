package org.opencloudengine.garuda.web.eco.hdfs;


import org.apache.hadoop.fs.FileStatus;
import org.opencloudengine.garuda.backend.hdfs.HdfsService;
import org.opencloudengine.garuda.web.console.oauthclient.OauthClient;
import org.opencloudengine.garuda.web.console.oauthclient.OauthClientService;
import org.opencloudengine.garuda.web.management.Management;
import org.opencloudengine.garuda.web.rest.RestAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Properties;

@Controller
@RequestMapping("/rest/v1")
public class HdfsRestController {
    @Autowired
    @Qualifier("config")
    private Properties config;

    @Autowired
    HdfsService hdfsService;

    @RequestMapping(value = "/liststatus", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<FileStatus[]> listAllClients(HttpServletRequest request, @RequestParam(defaultValue = "") String path) {

        try {
            FileStatus[] fileStatuses = hdfsService.getFile(path);

            return new ResponseEntity<>(fileStatuses, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
