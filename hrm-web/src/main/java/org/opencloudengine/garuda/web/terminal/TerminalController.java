package org.opencloudengine.garuda.web.terminal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Properties;

@Controller
@RequestMapping("/terminal")
public class TerminalController {

    /**
     * SLF4J Logging
     */
    private Logger logger = LoggerFactory.getLogger(TerminalController.class);

    @Autowired
    @Qualifier("config")
    private Properties config;

    /**
     * 터미널 페이지로 이동한다.
     *
     * @return Model And View
     */
    @RequestMapping(value = "terminal", method = RequestMethod.GET)
    public ModelAndView index(HttpSession session) {
        String host = config.getProperty("system.web.terminal.host");
        ModelAndView modelAndView = new ModelAndView("terminal/terminal");
        modelAndView.addObject("host", host);
        return modelAndView;
    }
}
