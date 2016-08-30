package org.opencloudengine.garuda.web.eco.hdfs;


import org.apache.hadoop.fs.FileStatus;
import org.opencloudengine.garuda.backend.hdfs.HdfsFileInfo;
import org.opencloudengine.garuda.backend.hdfs.HdfsListInfo;
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
@RequestMapping("/rest/v1/hdfs")
public class HdfsRestController {
    @Autowired
    @Qualifier("config")
    private Properties config;

    @Autowired
    HdfsService hdfsService;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<HdfsFileInfo>> list(HttpServletRequest request,
                                                   @RequestParam(defaultValue = "") String path,
                                                   @RequestParam(defaultValue = "1") int start,
                                                   @RequestParam(defaultValue = "10") int end,
                                                   @RequestParam(defaultValue = "") String filter) {
        try {
            HdfsListInfo hdfsListInfo = hdfsService.list(path, start, end, filter);
            List<HdfsFileInfo> fileStatuses = hdfsListInfo.getFileInfoList();
            return new ResponseEntity<>(fileStatuses, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public ResponseEntity<HdfsFileInfo> createFile(HttpServletRequest request,
                                           @RequestParam(defaultValue = "") String path) {
        try {
            HdfsFileInfo fileStatuses = hdfsService.getStatus(path);
            return new ResponseEntity<>(fileStatuses, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/file", method = RequestMethod.POST)
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

    @RequestMapping(value = "/file", method = RequestMethod.PUT)
    public ResponseEntity<Void> appendFile(HttpServletRequest request,
                                           @RequestParam(defaultValue = "") String path,
                                           UriComponentsBuilder ucBuilder) {
        try {
            hdfsService.appendFile(path, request.getInputStream());

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/rest/v1/file?path={path}").buildAndExpand(path).toUri());
            return new ResponseEntity<>(headers, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/emptyfile", method = RequestMethod.POST)
    public ResponseEntity<Void> createEmptyFile(HttpServletRequest request,
                                                @RequestParam(defaultValue = "") String path,
                                                @RequestParam(defaultValue = "") String owner,
                                                @RequestParam(defaultValue = "") String group,
                                                @RequestParam(defaultValue = "") String permission,
                                                @RequestParam(defaultValue = "false") boolean overwrite,
                                                UriComponentsBuilder ucBuilder) {
        try {
            hdfsService.createEmptyFile(path, owner, group, permission, overwrite);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/rest/v1/file?path={path}").buildAndExpand(path).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/directory", method = RequestMethod.POST)
    public ResponseEntity<Void> createDirectory(HttpServletRequest request,
                                                @RequestParam(defaultValue = "") String path,
                                                @RequestParam(defaultValue = "") String owner,
                                                @RequestParam(defaultValue = "") String group,
                                                @RequestParam(defaultValue = "") String permission,
                                                UriComponentsBuilder ucBuilder) {
        try {
            hdfsService.createDirectory(path, owner, group, permission);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/rest/v1/file?path={path}").buildAndExpand(path).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<Void> createDirectory(HttpServletRequest request,
                                                @RequestParam(defaultValue = "") String path) {
        try {
            hdfsService.delete(path);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/owner", method = RequestMethod.PUT)
    public ResponseEntity<Void> owner(HttpServletRequest request,
                                      @RequestParam(defaultValue = "") String path,
                                      @RequestParam(defaultValue = "") String owner,
                                      @RequestParam(defaultValue = "") String group,
                                      @RequestParam(defaultValue = "false") boolean recursive,
                                      UriComponentsBuilder ucBuilder) {
        try {
            hdfsService.setOwner(path, owner, group, recursive);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/rest/v1/file?path={path}").buildAndExpand(path).toUri());
            return new ResponseEntity<>(headers, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/permission", method = RequestMethod.PUT)
    public ResponseEntity<Void> permission(HttpServletRequest request,
                                           @RequestParam(defaultValue = "") String path,
                                           @RequestParam(defaultValue = "") String permission,
                                           @RequestParam(defaultValue = "false") boolean recursive,
                                           UriComponentsBuilder ucBuilder) {
        try {
            hdfsService.setPermission(path, permission, recursive);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/rest/v1/file?path={path}").buildAndExpand(path).toUri());
            return new ResponseEntity<>(headers, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/teragen", method = RequestMethod.POST)
    public ResponseEntity<Void> teragen(HttpServletRequest request) {
        try {

            hdfsService.teragen();
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
