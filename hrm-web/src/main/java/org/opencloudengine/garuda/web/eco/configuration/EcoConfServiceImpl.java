package org.opencloudengine.garuda.web.eco.configuration;

import org.opencloudengine.garuda.web.configuration.ConfigurationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.opencloudengine.garuda.model.EcoConf;

import java.util.Properties;

@Service
public class EcoConfServiceImpl implements EcoConfService {
    @Autowired
    @Qualifier("config")
    private Properties config;

    @Autowired
    private EcoConfRepository ecoConfRepository;

    @Autowired
    ConfigurationHelper configurationHelper;

    @Override
    public EcoConf insert(EcoConf ecoConf) {
        return ecoConfRepository.insert(ecoConf);
    }

    @Override
    public EcoConf select() {
        return ecoConfRepository.select();
    }

    @Override
    public void update(EcoConf ecoConf) {
        ecoConfRepository.update(ecoConf);
    }

    @Override
    public void update(String hdfsSuperUser, String hadoopHome, String hiveHome, String pigHome,
                       String sparkHome, String hdfsHome, String mapreduceHome, String yarnHome) {
        EcoConf ecoConf = new EcoConf();
        ecoConf.setHdfsSuperUser(hdfsSuperUser);
        ecoConf.setHadoopHome(hadoopHome);
        ecoConf.setHiveHome(hiveHome);
        ecoConf.setPigHome(pigHome);
        ecoConf.setSparkHome(sparkHome);
        ecoConf.setHdfsHome(hdfsHome);
        ecoConf.setMapreduceHome(mapreduceHome);
        ecoConf.setYarnHome(yarnHome);
        ecoConfRepository.update(ecoConf);
    }
}
