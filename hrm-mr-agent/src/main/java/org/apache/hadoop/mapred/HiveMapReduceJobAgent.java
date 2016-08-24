package org.apache.hadoop.mapred;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

public class HiveMapReduceJobAgent {

    public static void start(Object[] args) throws Exception {
        if (args.length == 4) {
            JobClient.NetworkedJob networkedJob = (JobClient.NetworkedJob) args[1];

            File file = new File(System.getProperty("user.dir"), "hadoop." + networkedJob.getID());
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(getMetadata(networkedJob));
            fos.close();
        }
    }

    private static byte[] getMetadata(JobClient.NetworkedJob job) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Properties properties = new Properties();
        properties.put("jobId", job.getID().toString());
        properties.put("jobName", job.getJobName());
        properties.put("historyUrl", job.getHistoryUrl());
        properties.put("trackingUrl", job.getTrackingURL());
        properties.storeToXML(baos, "");
        return baos.toByteArray();
    }
}
