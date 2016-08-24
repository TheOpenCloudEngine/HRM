package org.opencloudengine.garuda.web.eco.sysuser;


import net.minidev.json.JSONObject;
import org.opencloudengine.garuda.common.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@Controller
@RequestMapping("/eco/sysuser")
public class SysuserController {
    @Autowired
    @Qualifier("config")
    private Properties config;

    @Autowired
    private SysuserService sysuserService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String load() {
        return "/eco/sysuser/list";
    }

    // limit default value is javascript datatables _iDisplayLength
    // plz check user/list.jsp
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    String list(HttpSession session,
                @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
                @RequestParam(value = "skip", required = false, defaultValue = "0") int skip,
                @RequestParam(value = "name", required = false, defaultValue = "") String name) {

        Long count;
        List<Sysuser> sysusers;

        if (name.length() > 0) {
            count = sysuserService.countLikeName(name);
            sysusers = sysuserService.selectLikeName(name, limit, new Long(skip));

        } else {
            count = sysuserService.count();
            sysusers = sysuserService.select(limit, new Long(skip));
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("recordsTotal", limit);
        jsonObject.put("recordsFiltered", count);
        jsonObject.put("displayStart", skip);
        jsonObject.put("data", sysusers);

        return jsonObject.toString();
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView newUri(HttpSession session) {

        ModelAndView mav = new ModelAndView("/eco/sysuser/new");
        return mav;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView create(HttpSession session, HttpServletResponse response,
                               @RequestParam(defaultValue = "") String name,
                               @RequestParam(defaultValue = "") String password,
                               @RequestParam(defaultValue = "") String defaultUser,
                               @RequestParam(defaultValue = "") String description
    ) throws IOException {

        try {

            Sysuser existPolicy = sysuserService.selectByName(name);
            if (existPolicy != null) {
                ModelAndView mav = new ModelAndView("/eco/sysuser/new");
                mav.addObject("duplicate", true);
                return mav;
            }

            sysuserService.createUser(name, password, defaultUser, description);

            //리스트 페이지 반환
            response.sendRedirect("/eco/sysuser");
            return null;

        } catch (Exception ex) {
            ModelAndView mav = new ModelAndView("/eco/sysuser/new");
            mav.addObject("failed", true);
            return mav;
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView edit(HttpSession session,
                             @RequestParam(defaultValue = "") String _id) throws IOException {

        try {
            Sysuser sysuser = sysuserService.selectById(_id);
            if (sysuser == null) {
                throw new ServiceException("Invalid sysuser id");
            }

            ModelAndView mav = new ModelAndView("/eco/sysuser/edit");
            mav.addObject("sysuser", sysuser);
            return mav;
        } catch (Exception ex) {
            throw new ServiceException("Invalid sysuser id");
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView delete(HttpSession session, HttpServletResponse response,
                               @RequestParam(defaultValue = "") String _id) throws IOException {

        try {
            Sysuser sysuser = sysuserService.selectById(_id);
            if (sysuser == null) {
                throw new ServiceException("Invalid sysuser id");
            }

            sysuserService.deleteById(_id);

            response.sendRedirect("/eco/sysuser");
            return null;

        } catch (Exception ex) {
            throw new ServiceException("Invalid sysuser id");
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView update(HttpSession session, HttpServletResponse response,
                               @RequestParam(defaultValue = "") String _id,
                               @RequestParam(defaultValue = "") String name,
                               @RequestParam(defaultValue = "") String password,
                               @RequestParam(defaultValue = "") String defaultUser,
                               @RequestParam(defaultValue = "") String description
    ) throws IOException {

        Sysuser sysuser = sysuserService.selectById(_id);
        if (sysuser == null) {
            throw new ServiceException("Invalid sysuser id");
        }

        try {
            Sysuser existUser = sysuserService.selectByName(name);
            if (existUser != null) {
                if (!existUser.get_id().equals(_id)) {
                    ModelAndView mav = new ModelAndView("/eco/sysuser/edit");
                    mav.addObject("sysuser", sysuser);
                    mav.addObject("duplicate", true);
                    return mav;
                }
            }

            sysuserService.updateById(_id, password, defaultUser, description);
            response.sendRedirect("/eco/sysuser");
            return null;

        } catch (Exception ex) {
            ModelAndView mav = new ModelAndView("/eco/sysuser/edit");
            mav.addObject("sysuser", sysuser);
            mav.addObject("failed", true);
            return mav;
        }
    }
}
