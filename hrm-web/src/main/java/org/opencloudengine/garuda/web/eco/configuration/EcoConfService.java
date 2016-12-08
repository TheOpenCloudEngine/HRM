package org.opencloudengine.garuda.web.eco.configuration;

import org.opencloudengine.garuda.model.EcoConf;

public interface EcoConfService {

    EcoConf insert(EcoConf iam);

    EcoConf select();

    void update(EcoConf iam);

    void update(String hdfsSuperUser, String hadoopHome, String hiveHome, String pigHome,
                String sparkHome, String hdfsHome, String mapreduceHome, String yarnHome,
                String hbaseHome,String phoenixHome,String javaHome);
}
