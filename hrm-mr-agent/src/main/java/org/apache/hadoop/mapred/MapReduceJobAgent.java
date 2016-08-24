package org.apache.hadoop.mapred;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

public class MapReduceJobAgent {

    public static void start(Object[] args) throws Exception {
        org.apache.hadoop.mapreduce.Job job = (org.apache.hadoop.mapreduce.Job) args[0];

        File file = new File(System.getProperty("user.dir"), "hadoop." + job.getJobID());
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getMetadata(job));
        fos.close();
    }

    private static byte[] getMetadata(org.apache.hadoop.mapreduce.Job job) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Properties properties = new Properties();
        properties.put("jobId", job.getJobID().toString());
        properties.put("jobName", job.getJobName());
        properties.put("user", job.getUser());
        if (job.getStatus().getQueue() != null) {
            properties.put("queue", job.getStatus().getQueue());
        }
        properties.put("trackingUrl", job.getStatus().getTrackingUrl());
        properties.storeToXML(baos, "");
        return baos.toByteArray();
    }
}