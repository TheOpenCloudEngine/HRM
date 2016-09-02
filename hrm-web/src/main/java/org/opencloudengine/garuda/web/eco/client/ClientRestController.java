package org.opencloudengine.garuda.web.eco.client;

import org.opencloudengine.garuda.backend.hdfs.HdfsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@Controller
@RequestMapping("/rest/v1")
public class ClientRestController {
    @Autowired
    @Qualifier("config")
    private Properties config;

    @Autowired
    HdfsService hdfsService;

    @RequestMapping(value = "/hive", method = RequestMethod.POST)
    public ResponseEntity<Void> createFile(HttpServletRequest request,
                                           @RequestParam(defaultValue = "") String path,
                                           @RequestParam(defaultValue = "") String owner,
                                           @RequestParam(defaultValue = "") String group,
                                           @RequestParam(defaultValue = "") String permission,
                                           @RequestParam(defaultValue = "false") boolean overwrite,
                                           UriComponentsBuilder ucBuilder) {
        try {
            hdfsService.createFile(path, request.getInputStream(), owner, group, permission, overwrite);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/rest/v1/file?path={path}").buildAndExpand(path).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
