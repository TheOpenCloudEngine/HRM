package org.opencloudengine.garuda.web.eco.clientJob;

import org.opencloudengine.garuda.backend.clientjob.ClientJobService;
import org.opencloudengine.garuda.model.clientJob.ClientJob;
import org.opencloudengine.garuda.model.clientJob.ClientStatus;
import org.opencloudengine.garuda.model.request.*;
import org.opencloudengine.garuda.util.JsonFormatterUtils;
import org.opencloudengine.garuda.util.JsonUtils;
import org.opencloudengine.garuda.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

@Controller
@RequestMapping("/rest/v1/client/")
public class ClientJobRestController {
    @Autowired
    @Qualifier("config")
    private Properties config;

    /**
     * SLF4J Application Logging
     */
    private Logger logger = LoggerFactory.getLogger(ClientJobRestController.class);

    @Autowired
    ClientJobService clientJobService;

    @RequestMapping(value = "/hive", method = RequestMethod.POST)
    public void runHive(HttpServletResponse response, @RequestBody HiveRequest hiveRequest) throws IOException {
        this.processClientJob(response, hiveRequest);
    }

    @RequestMapping(value = "/pig", method = RequestMethod.POST)
    public void runPig(HttpServletResponse response, @RequestBody PigRequest pigRequest) throws IOException {
        this.processClientJob(response, pigRequest);
    }

    @RequestMapping(value = "/mr", method = RequestMethod.POST)
    public void runMr(HttpServletResponse response, @RequestBody MrRequest mrRequest) throws IOException {
        this.processClientJob(response, mrRequest);
    }

    @RequestMapping(value = "/spark", method = RequestMethod.POST)
    public void runSpark(HttpServletResponse response, @RequestBody SparkRequest sparkRequest) throws IOException {
        this.processClientJob(response, sparkRequest);
    }

    @RequestMapping(value = "/python", method = RequestMethod.POST)
    public void runPython(HttpServletResponse response, @RequestBody PythonRequest pythonRequest) throws IOException {
        this.processClientJob(response, pythonRequest);
    }

    @RequestMapping(value = "/shell", method = RequestMethod.POST)
    public void runShell(HttpServletResponse response, @RequestBody ShellRequest shellRequest) throws IOException {
        this.processClientJob(response, shellRequest);
    }

    @RequestMapping(value = "/java", method = RequestMethod.POST)
    public void runJava(HttpServletResponse response, @RequestBody JavaRequest javaRequest) throws IOException {
        this.processClientJob(response, javaRequest);
    }

    private void processClientJob(HttpServletResponse response, BasicClientRequest clientRequest) throws IOException {
        try {
            String clientJobId = clientRequest.getClientJobId();
            if (!StringUtils.isEmpty(clientJobId)) {
                if (clientJobService.selectByClientJobId(clientJobId) != null) {
                    logger.warn("Requested clientJobId already exist : {}", clientJobId);
                    response.sendError(400, "Requested clientJobId already exist");
                    return;
                }
            }
            ClientJob clientJob = clientJobService.run(clientRequest, ClientStatus.EXECUTE_FROM_REST);

            String marshal = JsonUtils.marshal(clientJob);
            String prettyPrint = JsonFormatterUtils.prettyPrint(marshal);
            response.setStatus(201);
            response.getWriter().write(prettyPrint);

        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendError(400, "Failed to run client job");
        }
    }

    @RequestMapping(value = "/job/{clientJobId}", method = RequestMethod.GET)
    public ResponseEntity<ClientJob> createFile(HttpServletRequest request,
                                                @PathVariable("clientJobId") String clientJobId) {
        try {
            ClientJob clientJob = clientJobService.selectByClientJobId(clientJobId);
            if (clientJob == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(clientJob, HttpStatus.OK);

        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
