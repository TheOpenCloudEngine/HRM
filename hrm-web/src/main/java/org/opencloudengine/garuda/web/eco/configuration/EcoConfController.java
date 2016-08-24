package org.opencloudengine.garuda.web.eco.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Properties;

@Controller
@RequestMapping("/eco")
public class EcoConfController {
    @Autowired
    @Qualifier("config")
    private Properties config;

    @Autowired
    private EcoConfService ecoConfService;

    @RequestMapping(value = "/configuration", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView info(HttpSession session) {
        ModelAndView mav = new ModelAndView("/eco/configuration/info");

        EcoConf ecoConf = ecoConfService.select();
        mav.addObject("ecoConf", ecoConf);

        return mav;
    }

    @RequestMapping(value = "/configuration/edit", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView edit(HttpSession session) {
        ModelAndView mav = new ModelAndView("/eco/configuration/edit");

        EcoConf ecoConf = ecoConfService.select();
        mav.addObject("ecoConf", ecoConf);

        return mav;
    }

    @RequestMapping(value = "/configuration/edit", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView save(HttpSession session,
                             @RequestParam(defaultValue = "") String hdfsSuperUser,
                             @RequestParam(defaultValue = "") String hadoopHome,
                             @RequestParam(defaultValue = "") String hiveHome,
                             @RequestParam(defaultValue = "") String pigHome,
                             @RequestParam(defaultValue = "") String sparkHome,
                             @RequestParam(defaultValue = "") String hdfsHome,
                             @RequestParam(defaultValue = "") String mapreduceHome,
                             @RequestParam(defaultValue = "") String yarnHome) {

        ecoConfService.update(hdfsSuperUser, hadoopHome, hiveHome, pigHome, sparkHome, hdfsHome, mapreduceHome, yarnHome);

        ModelAndView mav = new ModelAndView("/eco/configuration/info");
        EcoConf ecoConf = ecoConfService.select();
        mav.addObject("ecoConf", ecoConf);
        return mav;
    }
}
