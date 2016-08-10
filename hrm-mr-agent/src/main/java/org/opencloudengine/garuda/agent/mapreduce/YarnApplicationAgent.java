package org.opencloudengine.garuda.agent.mapreduce;

import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.api.records.ApplicationSubmissionContext;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class YarnApplicationAgent {

    public static void start(Object[] object) throws IOException {
        ApplicationSubmissionContext context = (ApplicationSubmissionContext) object[0];
        ApplicationId applicationId = context.getApplicationId();

        System.out.println("************************************************************************************");
        System.out.println("** YARN App instrumented By Flamingo 2 >> Path : " + System.getProperty("user.dir"));
        System.out.println("** YARN App instrumented By Flamingo 2 >> Application ID : " + applicationId);
        System.out.println("************************************************************************************");

        File file = new File(System.getProperty("user.dir"), "app." + applicationId);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getMetadata(context));
        fos.close();
    }

    private static byte[] getMetadata(ApplicationSubmissionContext context) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Properties properties = new Properties();
        properties.put("applicationName", context.getApplicationName());
        properties.put("applicationId", context.getApplicationId().toString());
        if(context.getQueue() != null){
            properties.put("queue", context.getQueue());
        }
        properties.storeToXML(baos, "");
        return baos.toByteArray();
    }
}