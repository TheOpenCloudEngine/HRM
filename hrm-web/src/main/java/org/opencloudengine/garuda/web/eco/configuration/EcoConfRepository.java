package org.opencloudengine.garuda.web.eco.configuration;

public interface EcoConfRepository {

    public static final String NAMESPACE = EcoConfRepository.class.getName();

    EcoConf getCash();

    EcoConf insert(EcoConf ecoConf);

    EcoConf select();

    EcoConf update(EcoConf ecoConf);
}
