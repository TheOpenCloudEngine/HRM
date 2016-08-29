package org.opencloudengine.garuda.web.eco.hdfs;


import org.apache.hadoop.fs.FileStatus;
import org.opencloudengine.garuda.backend.hdfs.HdfsFileInfo;
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

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<HdfsFileInfo>> listAllClients(HttpServletRequest request,
                                                             @RequestParam(defaultValue = "") String path,
                                                             @RequestParam(defaultValue = "1") int start,
                                                             @RequestParam(defaultValue = "10") int end,
                                                             @RequestParam(defaultValue = "") String filter) {
        try {
            List<HdfsFileInfo> fileStatuses = hdfsService.list(path, start, end, filter);
            return new ResponseEntity<>(fileStatuses, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public ResponseEntity<Void> createFile(HttpServletRequest request, @RequestParam(defaultValue = "") String path, UriComponentsBuilder ucBuilder) {
        try {
            hdfsService.createFile(path, request.getInputStream());

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/rest/v1/file?path={path}").buildAndExpand(path).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/teragen", method = RequestMethod.POST)
    public ResponseEntity<Void> teragen(HttpServletRequest request) {
        try {

            hdfsService.teragen();
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
