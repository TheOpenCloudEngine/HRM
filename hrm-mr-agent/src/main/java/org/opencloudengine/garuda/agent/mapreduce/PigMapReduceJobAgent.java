package org.opencloudengine.garuda.agent.mapreduce;

import org.apache.hadoop.mapred.JobID;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PigMapReduceJobAgent {

    public static void start(Object jobId) throws IOException {
        if (jobId != null && jobId instanceof JobID) {
            JobID id = (JobID) jobId;

            File file = new File(System.getProperty("user.dir"), "hadoop." + id);
            if (!file.exists()) {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write("".getBytes());
                fos.close();
            }
        }
    }
}