package org.opencloudengine.garuda.hrm.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Properties;

@Controller
@RequestMapping("/hrm")
public class HrmController {

    /**
     * SLF4J Logging
     */
    private Logger logger = LoggerFactory.getLogger(HrmController.class);

    @Autowired
    @Qualifier("config")
    private Properties config;


    /**
     * API 페이지로 이동한다.
     *
     * @return Model And View
     */
    @RequestMapping(value = "/rest/console", method = RequestMethod.GET)
    public ModelAndView api(HttpSession session, final Locale locale) {
        session.setAttribute("lang", locale.toString());

        return new ModelAndView("api");
    }

}
