package org.opencloudengine.garuda.model.clientJob;

import org.opencloudengine.garuda.couchdb.CouchDAO;
import org.opencloudengine.garuda.model.request.BasicClientRequest;

import java.util.List;

/**
 * Created by uengine on 2016. 9. 2..
 */
public class ClientJob extends CouchDAO{

    private String clientJobId;
    private String clientJobType;
    private String clientJobName;
    private String executeFrom;
    private Long startDate;
    private String humanStartDate;
    private Long endDate;
    private String humanEndDate;
    private Long duration;
    private String humanDuration;
    private String status;
    private String executeScript;
    private String executeCli;
    private String pid;
    private String exitCode;
    private String workingDir;
    private String killCli;
    private String exception;
    private String cause;
    private String stdout;
    private String stderr;

    private BasicClientRequest clientRequest;
    private ClientResult clientResult;

    private Long regDate;
    private Long updDate;

    public String getClientJobId() {
        return clientJobId;
    }

    public void setClientJobId(String clientJobId) {
        this.clientJobId = clientJobId;
    }

    public String getClientJobType() {
        return clientJobType;
    }

    public void setClientJobType(String clientJobType) {
        this.clientJobType = clientJobType;
    }

    public String getClientJobName() {
        return clientJobName;
    }

    public void setClientJobName(String clientJobName) {
        this.clientJobName = clientJobName;
    }

    public String getExecuteFrom() {
        return executeFrom;
    }

    public void setExecuteFrom(String executeFrom) {
        this.executeFrom = executeFrom;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public String getHumanStartDate() {
        return humanStartDate;
    }

    public void setHumanStartDate(String humanStartDate) {
        this.humanStartDate = humanStartDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public String getHumanEndDate() {
        return humanEndDate;
    }

    public void setHumanEndDate(String humanEndDate) {
        this.humanEndDate = humanEndDate;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getHumanDuration() {
        return humanDuration;
    }

    public void setHumanDuration(String humanDuration) {
        this.humanDuration = humanDuration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExecuteScript() {
        return executeScript;
    }

    public void setExecuteScript(String executeScript) {
        this.executeScript = executeScript;
    }

    public String getExecuteCli() {
        return executeCli;
    }

    public void setExecuteCli(String executeCli) {
        this.executeCli = executeCli;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getExitCode() {
        return exitCode;
    }

    public void setExitCode(String exitCode) {
        this.exitCode = exitCode;
    }

    public String getWorkingDir() {
        return workingDir;
    }

    public void setWorkingDir(String workingDir) {
        this.workingDir = workingDir;
    }

    public String getKillCli() {
        return killCli;
    }

    public void setKillCli(String killCli) {
        this.killCli = killCli;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public BasicClientRequest getClientRequest() {
        return clientRequest;
    }

    public void setClientRequest(BasicClientRequest clientRequest) {
        this.clientRequest = clientRequest;
    }

    public ClientResult getClientResult() {
        return clientResult;
    }

    public void setClientResult(ClientResult clientResult) {
        this.clientResult = clientResult;
    }

    public Long getRegDate() {
        return regDate;
    }

    public void setRegDate(Long regDate) {
        this.regDate = regDate;
    }

    public Long getUpdDate() {
        return updDate;
    }

    public void setUpdDate(Long updDate) {
        this.updDate = updDate;
    }

    public String getStdout() {
        return stdout;
    }

    public void setStdout(String stdout) {
        this.stdout = stdout;
    }

    public String getStderr() {
        return stderr;
    }

    public void setStderr(String stderr) {
        this.stderr = stderr;
    }
}
