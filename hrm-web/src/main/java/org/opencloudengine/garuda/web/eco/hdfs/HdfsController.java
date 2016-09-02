package org.opencloudengine.garuda.web.eco.hdfs;


import net.minidev.json.JSONObject;
import org.opencloudengine.garuda.backend.hdfs.HdfsService;
import org.opencloudengine.garuda.model.HdfsFileInfo;
import org.opencloudengine.garuda.model.HdfsListInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLDecoder;
import java.util.*;

@Controller
@RequestMapping("/hdfs")
public class HdfsController {
    @Autowired
    @Qualifier("config")
    private Properties config;

    @Autowired
    private HdfsService hdfsService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String load() {
        return "/hdfs/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    String list(HttpSession session,
                @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
                @RequestParam(value = "skip", required = false, defaultValue = "0") int skip,
                @RequestParam(value = "filter", required = false, defaultValue = "") String filter,
                @RequestParam(value = "path", defaultValue = "") String path) {

        Long count;
        JSONObject jsonObject = new JSONObject();
        try {
            HdfsListInfo list = hdfsService.list(path, skip + 1, skip + limit, filter);

            //디렉토리, 파일 소트
            List<HdfsFileInfo> sortList = new ArrayList<>();
            List<HdfsFileInfo> fileInfoList = list.getFileInfoList();
            for (HdfsFileInfo hdfsFileInfo : fileInfoList) {
                if (hdfsFileInfo.isDirectory()) {
                    sortList.add(hdfsFileInfo);
                }
            }
            for (HdfsFileInfo hdfsFileInfo : fileInfoList) {
                if (hdfsFileInfo.isFile()) {
                    sortList.add(hdfsFileInfo);
                }
            }

            jsonObject.put("recordsTotal", limit);
            jsonObject.put("recordsFiltered", list.getCount());
            jsonObject.put("displayStart", skip);
            jsonObject.put("data", sortList);
            return jsonObject.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return jsonObject.toString();
        }
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<Void> uploadFile(
            @RequestParam(value = "dir", defaultValue = "") String dir,
            @RequestParam("file") MultipartFile file,
            HttpSession session) throws IOException {
        try {
            if (file == null || file.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if ("/".equals(dir)) {
                dir = "";
            }
            String filename = file.getOriginalFilename();
            String path = dir + "/" + filename;

            InputStream is = file.getInputStream();
            hdfsService.createFile(path, is, null, null, null, false);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
