/**
 * Copyright (C) 2011 Flamingo Project (http://www.cloudine.io).
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.opencloudengine.garuda.backend.task;

import org.opencloudengine.garuda.backend.clientjob.ClientJobService;
import org.opencloudengine.garuda.model.EcoConf;
import org.opencloudengine.garuda.model.clientJob.ClientJob;
import org.opencloudengine.garuda.model.request.BasicClientRequest;
import org.opencloudengine.garuda.util.ApplicationContextRegistry;
import org.opencloudengine.garuda.util.DateUtils;
import org.opencloudengine.garuda.util.StringUtils;
import org.opencloudengine.garuda.web.eco.configuration.EcoConfService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.splitPreserveAllTokens;

public abstract class AbstractTask {

    public final static String SEPARATOR = ",";

    public Properties config;

    /**
     * clientJob 객체
     */
    public ClientJob clientJob;
    public String clientJobId;
    public String clientJobType;
    public String clientJobName;
    public String workingDir;
    public ClientJobService clientJobService;
    public BasicClientRequest clientRequest;
    public EcoConfService ecoConfService;
    public EcoConf ecoConf;
    public String mrAgentPath;

    /**
     * SLF4J Logging
     */
    private Logger logger = LoggerFactory.getLogger(AbstractTask.class);

    public void executeClientJob(ClientJob clientJob) {
        ApplicationContext context = ApplicationContextRegistry.getApplicationContext();

        this.config = context.getBean("config", Properties.class);
        this.clientJobService = context.getBean(ClientJobService.class);
        this.ecoConfService = context.getBean(EcoConfService.class);

        this.clientJob = clientJob;
        this.clientJobId = clientJob.getClientJobId();
        this.clientJobType = clientJob.getClientJobType();
        this.clientJobName = clientJob.getClientJobName();
        this.ecoConf = ecoConfService.select();
        this.workingDir = clientJob.getWorkingDir();

        this.mrAgentPath = config.getProperty("mr.agent.path");

        this.doClientExecute();
    }

    abstract public void doClientExecute();

    public String makeMapToPropertyString(Map<String, String> value) {
        String properties = "";
        if (value != null) {
            if (!value.isEmpty()) {
                Properties props = new Properties();
                Set<String> keySet = value.keySet();
                for (String key : keySet) {
                    props.put(key, value.get(key));
                }
                properties = StringUtils.propertiesToString(props);
            }
        }
        return properties;
    }

    /**
     * 값들이 순차 배열되는 옵션을 구성한다.
     * @param command
     * @param value
     */
    public void buildArgs(List<String> command, List<String> value) {
        if (value != null) {
            for (int i = 0; i < value.size(); i++) {
                command.add(value.get(i));
            }
        }
    }

    /**
     * 값들이 콤마로 구분되는 옵션을 구성한다.
     * @param command
     * @param option
     * @param value
     */
    public void buildCommaSeparatedOptions(List<String> command, String option, List<String> value) {
        if (value != null) {
            command.add(option);
            String args = "\"";
            for (int i = 0; i < value.size(); i++) {
                if (i == 0) {
                    args += value.get(i);
                } else {
                    args += "," + value.get(i);
                }
            }
            args += "\"";
            command.add(args);
        }
    }

    /**
     * 값들이 스페이스값으로 구분되는 옵션을 구성한다.
     * @param command
     * @param option
     * @param value
     */
    public void buildSpaceSeparatedOptions(List<String> command, String option, List<String> value) {
        if (value != null) {
            command.add(option);
            String args = "\"";
            for (int i = 0; i < value.size(); i++) {
                if (i == 0) {
                    args += value.get(i);
                } else {
                    args += " " + value.get(i);
                }
            }
            args += "\"";
            command.add(args);
        }
    }

    /**
     * Args 가 없는 싱글 옵션을 구성한다.
     * @param command
     * @param option
     */
    public void buildSingleOption(List<String> command, String option) {
        if (!StringUtils.isEmpty(option)) {
            command.add(option);
        }
    }

    /**
     * Args 가 하나인 싱글 옵션을 구성한다.
     * @param command
     * @param option
     * @param value
     */
    public void buildBasicOption(List<String> command, String option, String value) {
        if (!StringUtils.isEmpty(value)) {
            command.add(option);
            command.add(value);
        }
    }

    /**
     * Args 가 하나인 싱글 옵션을 구성한다.(디폴트 값 지정)
     * @param command
     * @param option
     * @param value
     * @param dafultValue
     */
    public void buildBasicOption(List<String> command, String option, String value, String dafultValue) {
        if (!StringUtils.isEmpty(value)) {
            command.add(option);
            command.add(value);
        } else {
            if (!StringUtils.isEmpty(dafultValue)) {
                command.add(option);
                command.add(dafultValue);
            }
        }
    }

    /**
     * 키-벨류 형식의 옵션을 구성한다.
     * @param command
     * @param option
     * @param value
     */
    public void buildMapToMultipleOption(List<String> command, String option, Map<String, String> value) {
        if (value != null) {
            Set<String> keySet = value.keySet();
            for (String key : keySet) {
                command.add(option);
                command.add(key + "=" + value.get(key));
            }
        }
    }

    /**
     * 키-벨류 형식의 값을 -D키-벨류 형식의 옵션으로 구성한다.
     * @param command
     * @param value
     */
    public void buildJavaOpts(List<String> command, Map<String, String> value) {
        if (value != null) {
            Set<String> keySet = value.keySet();
            for (String key : keySet) {
                command.add("-D" + key + "=" + value.get(key));
            }
        }
    }

    /**
     * 주어진 내용을 파일로 저장하고 파일명을 옵션으로 구성한다.
     * @param command
     * @param option
     * @param value
     * @param filePath
     * @throws IOException
     */
    public void saveToFileOption(List<String> command, String option, String value, String filePath) throws IOException {
        if (!StringUtils.isEmpty(value)) {
            FileCopyUtils.copy(value.getBytes(), new File(filePath));
            if (!StringUtils.isEmpty(option)) {
                command.add(option);
            }
            command.add(filePath);
        }
    }

    /**
     * 주어진 내용을 파일로 저장하고 파일명을 싱글옵션으로 구성한다.
     * @param command
     * @param value
     * @param filePath
     * @throws IOException
     */
    public void saveToFileSingleOption(List<String> command, String value, String filePath) throws IOException {
        if (!StringUtils.isEmpty(value)) {
            FileCopyUtils.copy(value.getBytes(), new File(filePath));
            command.add(filePath);
        }
    }

}