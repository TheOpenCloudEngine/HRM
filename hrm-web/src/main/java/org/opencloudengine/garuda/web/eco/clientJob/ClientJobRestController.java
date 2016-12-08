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
import java.util.List;
import java.util.Properties;

@Controller
@RequestMapping("/rest/v1/clientJob")
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
    public void runHive(HttpServletResponse response, @RequestBody HiveRequest hiveRequest,
                        @RequestParam(value = "executeFrom", defaultValue = "", required = false) String executeFrom) throws IOException {
        this.processClientJob(response, hiveRequest, executeFrom);
    }

    @RequestMapping(value = "/pig", method = RequestMethod.POST)
    public void runPig(HttpServletResponse response, @RequestBody PigRequest pigRequest,
                       @RequestParam(value = "executeFrom", defaultValue = "", required = false) String executeFrom) throws IOException {
        this.processClientJob(response, pigRequest, executeFrom);
    }

    @RequestMapping(value = "/mr", method = RequestMethod.POST)
    public void runMr(HttpServletResponse response, @RequestBody MrRequest mrRequest,
                      @RequestParam(value = "executeFrom", defaultValue = "", required = false) String executeFrom) throws IOException {
        this.processClientJob(response, mrRequest, executeFrom);
    }

    @RequestMapping(value = "/spark", method = RequestMethod.POST)
    public void runSpark(HttpServletResponse response, @RequestBody SparkRequest sparkRequest,
                         @RequestParam(value = "executeFrom", defaultValue = "", required = false) String executeFrom) throws IOException {
        this.processClientJob(response, sparkRequest, executeFrom);
    }

    @RequestMapping(value = "/python", method = RequestMethod.POST)
    public void runPython(HttpServletResponse response, @RequestBody PythonRequest pythonRequest,
                          @RequestParam(value = "executeFrom", defaultValue = "", required = false) String executeFrom) throws IOException {
        this.processClientJob(response, pythonRequest, executeFrom);
    }

    @RequestMapping(value = "/shell", method = RequestMethod.POST)
    public void runShell(HttpServletResponse response, @RequestBody ShellRequest shellRequest,
                         @RequestParam(value = "executeFrom", defaultValue = "", required = false) String executeFrom) throws IOException {
        this.processClientJob(response, shellRequest, executeFrom);
    }

    @RequestMapping(value = "/java", method = RequestMethod.POST)
    public void runJava(HttpServletResponse response, @RequestBody JavaRequest javaRequest,
                        @RequestParam(value = "executeFrom", defaultValue = "", required = false) String executeFrom) throws IOException {
        this.processClientJob(response, javaRequest, executeFrom);
    }
    @RequestMapping(value = "/hbaseShell", method = RequestMethod.POST)
    public void runHbaseShell(HttpServletResponse response, @RequestBody HbaseShellRequest hbaseShellRequest,
                        @RequestParam(value = "executeFrom", defaultValue = "", required = false) String executeFrom) throws IOException {
        this.processClientJob(response, hbaseShellRequest, executeFrom);
    }
    @RequestMapping(value = "/hbaseClass", method = RequestMethod.POST)
    public void runHbaseClass(HttpServletResponse response, @RequestBody HbaseClassRequest hbaseClassRequest,
                        @RequestParam(value = "executeFrom", defaultValue = "", required = false) String executeFrom) throws IOException {
        this.processClientJob(response, hbaseClassRequest, executeFrom);
    }
    @RequestMapping(value = "/phoenix", method = RequestMethod.POST)
    public void runPhoenix(HttpServletResponse response, @RequestBody PhoenixRequest phoenixRequest,
                        @RequestParam(value = "executeFrom", defaultValue = "", required = false) String executeFrom) throws IOException {
        this.processClientJob(response, phoenixRequest, executeFrom);
    }

    private void processClientJob(HttpServletResponse response, BasicClientRequest clientRequest, String executeFrom) throws IOException {
        logger.info("processClientJob : {}", JsonUtils.marshal(clientRequest));
        try {
            String clientJobId = clientRequest.getClientJobId();
            if (!StringUtils.isEmpty(clientJobId)) {
                if (clientJobService.selectByClientJobId(clientJobId) != null) {
                    logger.warn("Requested clientJobId already exist : {}", clientJobId);
                    response.sendError(400, "Requested clientJobId already exist");
                    return;
                }
            }
            if (!ClientStatus.EXECUTE_FROM_CONSOLE.equals(executeFrom) &&
                    !ClientStatus.EXECUTE_FROM_BATCH.equals(executeFrom) &&
                    !ClientStatus.EXECUTE_FROM_REST.equals(executeFrom)) {
                executeFrom = ClientStatus.EXECUTE_FROM_REST;
            }
            ClientJob clientJob = clientJobService.run(clientRequest, executeFrom);

            String marshal = JsonUtils.marshal(clientJob);
            response.setStatus(201);
            response.getWriter().write(marshal);

        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendError(400, "Failed to run client job");
        }
    }

    @RequestMapping(value = "/job/{clientJobId}", method = RequestMethod.GET)
    public ResponseEntity<ClientJob> getClientJob(HttpServletRequest request,
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

    @RequestMapping(value = "/kill/{clientJobId}", method = RequestMethod.DELETE)
    public ResponseEntity<ClientJob> killClientJob(HttpServletRequest request,
                                                   @PathVariable("clientJobId") String clientJobId) {
        try {
            ClientJob clientJob = clientJobService.selectByClientJobId(clientJobId);
            if (clientJob == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            clientJob = clientJobService.kill(clientJobId);
            return new ResponseEntity<>(clientJob, HttpStatus.OK);

        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/consoleJob", method = RequestMethod.GET)
    public ResponseEntity<List<ClientJob>> getConsoleJobs(HttpServletRequest request,
                                                          @RequestParam(value = "jobType", defaultValue = "") String jobType) {
        try {
            List<ClientJob> clientJobs = clientJobService.selectByClientJobTypeAndExecuteFrom(
                    7, Long.parseLong("0"), jobType, ClientStatus.EXECUTE_FROM_CONSOLE);
            return new ResponseEntity<>(clientJobs, HttpStatus.OK);

        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
