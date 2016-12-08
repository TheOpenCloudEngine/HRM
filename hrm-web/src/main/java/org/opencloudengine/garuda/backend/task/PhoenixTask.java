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

import org.apache.commons.io.FileUtils;
import org.opencloudengine.garuda.model.request.BasicClientRequest;
import org.opencloudengine.garuda.model.request.HbaseClassRequest;
import org.opencloudengine.garuda.model.request.PhoenixRequest;
import org.opencloudengine.garuda.model.request.PhoenixSource;
import org.opencloudengine.garuda.util.StringUtils;
import org.opencloudengine.garuda.util.cli.FileWriter;
import org.opencloudengine.garuda.util.cli.ManagedProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * @author Seungpil, Park
 * @since 2.0
 */
public class PhoenixTask extends InterceptorAbstractTask {

    /**
     * SLF4J Logging
     */
    private Logger logger = LoggerFactory.getLogger(PhoenixTask.class);

    private PhoenixRequest phoenixRequest;

    @Override
    public void runTask() throws Exception {
        this.phoenixRequest = clientJob.getPhoenixRequest();
        this.clientRequest = (BasicClientRequest) this.phoenixRequest;

        FileUtils.forceMkdir(new File(workingDir));

        saveScriptFile(buildCommand(), workingDir);

        String cli = MessageFormatter.arrayFormat("sh {}/script.sh", new Object[]{workingDir}).getMessage();
        saveCommandFile(cli, workingDir);
        String[] cmds = StringUtils.splitPreserveAllTokens(cli, " ");

        FileWriter fileWriter = new FileWriter(logger, workingDir + "/task.log");

        Map<String, Object> socketParams = new HashMap<>();
        socketParams.put("clientJobId", clientJobId);
        socketParams.put("type", "clientJob");

        ManagedProcess managedProcess = new ManagedProcess(cmds, getDefaultEnvs(), workingDir, logger, fileWriter);
        managedProcess.setSocketParams(socketParams);
        managedProcess.run();
    }

    /**
     * 커맨드라인을 <tt>script.hive</tt> 파일로 저장한다.
     *
     * @return 저장한 파일의 절대 경로
     */
    private String buildCommand() throws Exception {


        List<String> command = new LinkedList<>();

        Map<String, String> defaultEnvs = getDefaultEnvs();
        Set<String> keys = defaultEnvs.keySet();
        for (String key : keys) {
            if (!isEmpty(defaultEnvs.get(key))) {
                command.add(MessageFormatter.arrayFormat("export {}={}\n", new Object[]{
                        key, defaultEnvs.get(key)
                }).getMessage());
            }
        }

        //phoenix psql.py
        command.add(ecoConf.getPhoenixHome() + "/bin/psql.py");

        //--array-separator
        buildBasicOption(command, "--array-separator", phoenixRequest.getArraySeparator());

        //--bypass-upgrade
        if (phoenixRequest.getBypassUpgrade()) {
            buildSingleOption(command, "--bypass-upgrade");
        }

        //--delimiter
        buildBasicOption(command, "--delimiter", phoenixRequest.getDelimiter());

        //--escape-character
        buildBasicOption(command, "--escape-character", phoenixRequest.getEscapeCharacter());

        //--header
        buildBasicOption(command, "--header", phoenixRequest.getHeader());

        //--local-index-upgrade
        if (phoenixRequest.getLocalIndexUpgrade()) {
            buildSingleOption(command, "--local-index-upgrade");
        }

        //--map-namespace
        buildBasicOption(command, "--map-namespace", phoenixRequest.getMapNamespace());

        //--quote-character
        buildBasicOption(command, "--quote-character", phoenixRequest.getQuoteCharacter());

        //--strict
        if (phoenixRequest.getStrict()) {
            buildSingleOption(command, "--strict");
        }

        //--table
        buildBasicOption(command, "--table", phoenixRequest.getTable());

        //--upgrade
        if (phoenixRequest.getUpgrade()) {
            buildSingleOption(command, "--upgrade");
        }

        //--zookeeper
        buildSingleOption(command, phoenixRequest.getZookeeper());

        //source
        List<PhoenixSource> sources = phoenixRequest.getSources();
        for (int i = 0; i < sources.size(); i++) {
            PhoenixSource phoenixSource = sources.get(i);
            String type = phoenixSource.getType();
            String source = phoenixSource.getValue();
            if (!StringUtils.isEmpty(source)) {
                if (source.startsWith("local:")) {
                    source = source.replace("local:", "");
                    buildSingleOption(command, source);
                } else {
                    saveToFileSingleOption(command, source, workingDir + "/source.phoenix." + i + "." + type);
                }
            }
        }

        return StringUtils.listToDelimitedString(command, " ");
    }

    /**
     * 커맨드라인을 <tt>cli.sh</tt> 파일로 저장한다.
     *
     * @param script  커맨드 라인
     * @param baseDir 파일을 저장할 기준경로
     * @return 저장한 파일의 절대 경로
     * @throws IOException 파일을 저장할 수 없는 경우
     */
    private String saveScriptFile(String script, String baseDir) throws IOException {
        File cliPath = new File(baseDir, "script.sh");
        FileCopyUtils.copy(script.getBytes(), cliPath);
        return cliPath.getAbsolutePath();
    }

    /**
     * 스크립트를 <tt>script</tt> 파일로 저장한다.
     *
     * @param command 스크립트
     * @param baseDir 파일을 저장할 기준경로
     * @return 저장한 파일의 절대 경로
     * @throws IOException 파일을 저장할 수 없는 경우
     */
    private String saveCommandFile(String command, String baseDir) throws IOException {
        File cliPath = new File(baseDir, "command.sh");
        FileCopyUtils.copy(command.getBytes(), cliPath);
        return cliPath.getAbsolutePath();
    }

    /**
     * 스크립트를 실행하기 위해서 필요한 환경변수를 가져온다.
     *
     * @return 환경변수
     */
    public Map<String, String> getDefaultEnvs() {
        Map<String, String> envs = new HashMap<>();

        envs.put("PATH", "/bin:/usr/bin:/usr/local/bin" + ":" + ecoConf.getHadoopHome() + "/bin" + ":" + ecoConf.getHiveHome() + "/bin" + ":" + ecoConf.getPigHome() + "/bin");
        envs.put("HADOOP_CLIENT_OPTS", MessageFormatter.format("-javaagent:{}=resourcescript:mr2.bm", mrAgentPath).getMessage());

        //do as 가 지정된 경우 해당 유저로 적용을 한다.
        if (!StringUtils.isEmpty(clientRequest.getDoAs())) {
            envs.put("HADOOP_USER_NAME", clientRequest.getDoAs());
        } else {
            if (!StringUtils.isEmpty(ecoConf.getHdfsSuperUser())) {
                envs.put("HADOOP_USER_NAME", ecoConf.getHdfsSuperUser());
            } else {
                envs.put("HADOOP_USER_NAME", "hdfs");
            }
        }
        return envs;
    }
}