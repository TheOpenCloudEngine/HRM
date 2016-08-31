package org.opencloudengine.garuda.web.eco.hdfs;


import net.minidev.json.JSONObject;
import org.opencloudengine.garuda.backend.hdfs.HdfsFileInfo;
import org.opencloudengine.garuda.backend.hdfs.HdfsListInfo;
import org.opencloudengine.garuda.backend.hdfs.HdfsService;
import org.opencloudengine.garuda.common.exception.ServiceException;
import org.opencloudengine.garuda.web.eco.sysuser.Sysuser;
import org.opencloudengine.garuda.web.eco.sysuser.SysuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
                @RequestParam(value = "path", required = false, defaultValue = "") String path) {

        Long count;
        JSONObject jsonObject = new JSONObject();
        try {
            HdfsListInfo list = hdfsService.list(path, skip + 1, skip + limit, filter);

            //디렉토리, 파일 소트
            List<HdfsFileInfo> sortList = new ArrayList<>();
            List<HdfsFileInfo> fileInfoList = list.getFileInfoList();
            for (HdfsFileInfo hdfsFileInfo : fileInfoList) {
                if(hdfsFileInfo.isDirectory()){
                    sortList.add(hdfsFileInfo);
                }
            }
            for (HdfsFileInfo hdfsFileInfo : fileInfoList) {
                if(hdfsFileInfo.isFile()){
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

//    @RequestMapping(value = "/upload", method = RequestMethod.POST)
//    public void uploadAppFile(@RequestParam("file") MultipartFile file, HttpServletResponse response, HttpSession session) throws IOException {
//        User user = (User) session.getAttribute(User.USER_KEY);
//        String orgId = user.getOrgId();
//        File appFile = appManageService.saveMultipartFile(file, orgId);
//
//        if (appFile != null) {
//            // garuda에 올린다.
//            String filePath = belugaService.uploadAppFile(orgId, appFile);
//            if (filePath != null) {
//                long length = appFile.length();
//                String fileName = appFile.getName();
//                String checksum = MessageDigestUtils.getMD5Checksum(appFile);
//                UploadFile uploadFile = new UploadFile(fileName, filePath, length, checksum, DateUtil.getNow());
//                response.setCharacterEncoding("utf-8");
//                response.getWriter().print(JsonUtil.object2String(uploadFile));
//                return;
//            } else {
//                response.sendError(500, "Cannot upload file to remote garuda server.");
//            }
//        } else {
//            response.sendError(500, "File is empty");
//        }
//    }
}
