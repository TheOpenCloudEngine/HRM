package org.opencloudengine.garuda.web.eco.configuration;

import org.opencloudengine.garuda.model.EcoConf;

public interface EcoConfRepository {

    public static final String NAMESPACE = EcoConfRepository.class.getName();

    EcoConf getCash();

    EcoConf insert(EcoConf ecoConf);

    EcoConf select();

    EcoConf update(EcoConf ecoConf);
}
