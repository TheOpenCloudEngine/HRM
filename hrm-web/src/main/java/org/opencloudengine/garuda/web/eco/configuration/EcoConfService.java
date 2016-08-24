package org.opencloudengine.garuda.web.eco.configuration;

import java.util.Map;

public interface EcoConfService {

    EcoConf insert(EcoConf iam);

    EcoConf select();

    void update(EcoConf iam);

    void update(String hdfsSuperUser, String hadoopHome, String hiveHome, String pigHome,
                String sparkHome, String hdfsHome, String mapreduceHome, String yarnHome);
}
