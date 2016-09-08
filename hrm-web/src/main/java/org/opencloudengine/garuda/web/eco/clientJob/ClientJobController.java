package org.opencloudengine.garuda.web.eco.clientJob;


import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.opencloudengine.garuda.common.exception.ServiceException;
import org.opencloudengine.garuda.model.Sysuser;
import org.opencloudengine.garuda.model.clientJob.ClientStatus;
import org.opencloudengine.garuda.model.request.*;
import org.opencloudengine.garuda.util.JsonUtils;
import org.opencloudengine.garuda.web.eco.sysuser.SysuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

@Controller
@RequestMapping("/eco/clientJob")
public class ClientJobController {
    @Autowired
    @Qualifier("config")
    private Properties config;

    //TODO 해야할것.
    // eco 시스템 호출하는 rest api 제작 (executeFrom : console)
    // 각 eco 시스템의 execute 페이지 제작 (동일 페이지로 공유할 수 있도록 제작)
    // eco 시스템의 호출 페이지는, 폼 빌드, 로그 스트리밍, 상단 히스토리, 우측 저장리스트.  부가기능으로는 curl 보이기.
    // 저장리스트의 객체는 도메인클래스를 하나 만들것.
    // 폼 빌드탭에서 Save 클릭시 우측의 저장리스트로 이름입력과 함께 리퀘스트가 저장이 되어 들어감.

    @RequestMapping(value = "/editor/hive", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView hive() {
        ModelAndView mav = new ModelAndView("/eco/clientJob/editor");
        mav.addObject("jobType", "hive");
        return mav;
    }
    @RequestMapping(value = "/editor/mr", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView mr() {
        ModelAndView mav = new ModelAndView("/eco/clientJob/editor");
        mav.addObject("jobType", "mr");
        return mav;
    }
    @RequestMapping(value = "/editor/java", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView java() {
        ModelAndView mav = new ModelAndView("/eco/clientJob/editor");
        mav.addObject("jobType", "java");
        return mav;
    }
    @RequestMapping(value = "/editor/pig", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView pig() {
        ModelAndView mav = new ModelAndView("/eco/clientJob/editor");
        mav.addObject("jobType", "pig");
        return mav;
    }
    @RequestMapping(value = "/editor/spark", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView spark() {
        ModelAndView mav = new ModelAndView("/eco/clientJob/editor");
        mav.addObject("jobType", "spark");
        return mav;
    }
    @RequestMapping(value = "/editor/python", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView python() {
        ModelAndView mav = new ModelAndView("/eco/clientJob/editor");
        mav.addObject("jobType", "python");
        return mav;
    }
    @RequestMapping(value = "/editor/shell", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView shell() {
        ModelAndView mav = new ModelAndView("/eco/clientJob/editor");
        mav.addObject("jobType", "shell");
        return mav;
    }


    @RequestMapping(value = "/requestDesc", method = RequestMethod.GET)
    public ResponseEntity<List<Map>> getRequestDescription(
            @RequestParam(value = "jobType", defaultValue = "") String jobType) {
        try {
            List<Map> list = new ArrayList<>();

            Method[] basicMethods = BasicClientRequest.class.getDeclaredMethods();
            for (Method method : basicMethods) {
                if (method.getName().startsWith("get")) {
                    if (method.isAnnotationPresent(FieldType.class)) {
                        Map map = new HashMap();
                        String name = method.getName();
                        name = name.replaceFirst("get", "");
                        name = Character.toLowerCase(name.charAt(0)) + name.substring(1);

                        FieldType fieldType = method.getAnnotation(FieldType.class);
                        map.put("name", name);
                        map.put("type", fieldType.type());
                        map.put("description", fieldType.description());
                        map.put("basic", true);
                        list.add(map);
                    }
                }
            }

            Method methods[] = null;
            if (ClientStatus.JOB_TYPE_PIG.equalsIgnoreCase(jobType)) {
                methods = PigRequest.class.getDeclaredMethods();
            }
            if (ClientStatus.JOB_TYPE_HIVE.equalsIgnoreCase(jobType)) {
                methods = HiveRequest.class.getDeclaredMethods();
            }
            if (ClientStatus.JOB_TYPE_SHELL.equalsIgnoreCase(jobType)) {
                methods = ShellRequest.class.getDeclaredMethods();
            }
            if (ClientStatus.JOB_TYPE_PYTHON.equalsIgnoreCase(jobType)) {
                methods = PythonRequest.class.getDeclaredMethods();
            }
            if (ClientStatus.JOB_TYPE_JAVA.equalsIgnoreCase(jobType)) {
                methods = JavaRequest.class.getDeclaredMethods();
            }
            if (ClientStatus.JOB_TYPE_MR.equalsIgnoreCase(jobType)) {
                methods = MrRequest.class.getDeclaredMethods();
            }
            if (ClientStatus.JOB_TYPE_SPARK.equalsIgnoreCase(jobType)) {
                methods = SparkRequest.class.getDeclaredMethods();
            }
            if (methods == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            for (Method method : methods) {
                if (method.getName().startsWith("get")) {
                    if (method.isAnnotationPresent(FieldType.class)) {
                        Map map = new HashMap();
                        String name = method.getName();
                        name = name.replaceFirst("get", "");
                        name = Character.toLowerCase(name.charAt(0)) + name.substring(1);

                        FieldType fieldType = method.getAnnotation(FieldType.class);
                        map.put("name", name);
                        map.put("type", fieldType.type());
                        map.put("description", fieldType.description());
                        list.add(map);
                    }
                }
            }
            return new ResponseEntity<>(list, HttpStatus.OK);

        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(value = "/curl", method = RequestMethod.POST)
    public ResponseEntity<String> getCurl(HttpServletRequest req, @RequestBody Map map,
                                          @RequestParam(value = "jobType", defaultValue = "") String jobType) {
        try {

            String url;
            final int serverPort = req.getServerPort();
            if ((serverPort == 80) || (serverPort == 443)) {
                url = String.format("%s://%s/", req.getScheme(), req.getServerName());
            } else {
                url = String.format("%s://%s:%s/", req.getScheme(), req.getServerName(), serverPort);
            }


            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);

            String curl = "curl -X POST -H \"Content-Type: application/json\" -H \"Cache-Control: no-cache\" -d '";
            curl += json;
            curl += "' " + url + "rest/v1/clientJob/" + jobType;
            return new ResponseEntity<>(curl, HttpStatus.OK);

        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
