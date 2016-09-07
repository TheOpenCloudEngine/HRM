package org.opencloudengine.garuda.web.eco.clientJob;

import org.opencloudengine.garuda.backend.clientjob.JobCollectionService;
import org.opencloudengine.garuda.model.clientJob.JobCollection;
import org.opencloudengine.garuda.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Controller
@RequestMapping("/rest/v1")
public class JobCollectionRestController {
    @Autowired
    @Qualifier("config")
    private Properties config;

    @Autowired
    private JobCollectionService jobCollectionService;


    @RequestMapping(value = "/collection", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<JobCollection>> listCollection(HttpServletRequest request,
                                                              @RequestParam(defaultValue = "") String jobType) {
        try {
            List<JobCollection> jobCollections = new ArrayList<>();
            if (StringUtils.isEmpty(jobType)) {
                jobCollections = jobCollectionService.selectAll();
            } else {
                jobCollections = jobCollectionService.selectByJobType(jobType);
            }
            return new ResponseEntity<>(jobCollections, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/collection/{_id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<JobCollection> getCollection(HttpServletRequest request, @PathVariable("_id") String _id) {

        try {
            JobCollection jobCollection = jobCollectionService.selectById(_id);
            if (jobCollection == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(jobCollection, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/collection", method = RequestMethod.POST)
    public ResponseEntity<Void> createCollection(HttpServletRequest request,
                                                 @RequestBody JobCollection jobCollection, UriComponentsBuilder ucBuilder) {
        try {
            JobCollection exist = jobCollectionService.selectByJobNameAndJobType(jobCollection.getJobName(), jobCollection.getJobType());
            if (exist != null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            JobCollection insert = jobCollectionService.insert(jobCollection);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/rest/v1/collection/{_id}").buildAndExpand(insert.get_id()).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/collection/{_id}", method = RequestMethod.PUT)
    public ResponseEntity<JobCollection> updateCollection(HttpServletRequest request,
                                                          @PathVariable("_id") String _id,
                                                          @RequestBody JobCollection jobCollection) {

        try {
            JobCollection currentCollection = jobCollectionService.selectById(_id);
            if (currentCollection == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            JobCollection exist = jobCollectionService.selectByJobNameAndJobType(jobCollection.getJobName(), jobCollection.getJobType());
            if (exist != null) {
                if (!exist.get_id().equals(_id)) {
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
            }
            jobCollection.set_id(currentCollection.get_id());
            currentCollection = jobCollectionService.updateById(jobCollection);

            return new ResponseEntity<>(currentCollection, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/collection/{_id}", method = RequestMethod.DELETE)
    public ResponseEntity<JobCollection> deleteCollection(HttpServletRequest request, @PathVariable("_id") String _id) {

        try {
            JobCollection currentCollection = jobCollectionService.selectById(_id);
            if (currentCollection == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            jobCollectionService.deleteById(currentCollection.get_id());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
