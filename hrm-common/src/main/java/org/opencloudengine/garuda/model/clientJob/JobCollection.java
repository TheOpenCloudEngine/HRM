package org.opencloudengine.garuda.model.clientJob;

import org.opencloudengine.garuda.couchdb.CouchDAO;
import org.opencloudengine.garuda.model.request.*;

import java.util.List;

/**
 * Created by uengine on 2016. 9. 6..
 */
public class JobCollection extends CouchDAO{

    private String jobType;
    private String jobName;

    private HiveRequest hiveRequest;
    private MrRequest mrRequest;
    private PigRequest pigRequest;
    private SparkRequest sparkRequest;
    private JavaRequest javaRequest;
    private PythonRequest pythonRequest;
    private ShellRequest shellRequest;
    private HbaseShellRequest hbaseShellRequest;
    private HbaseClassRequest hbaseClassRequest;
    private PhoenixRequest phoenixRequest;

    private Long regDate;
    private Long updDate;

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public HiveRequest getHiveRequest() {
        return hiveRequest;
    }

    public void setHiveRequest(HiveRequest hiveRequest) {
        this.hiveRequest = hiveRequest;
    }

    public MrRequest getMrRequest() {
        return mrRequest;
    }

    public void setMrRequest(MrRequest mrRequest) {
        this.mrRequest = mrRequest;
    }

    public PigRequest getPigRequest() {
        return pigRequest;
    }

    public void setPigRequest(PigRequest pigRequest) {
        this.pigRequest = pigRequest;
    }

    public SparkRequest getSparkRequest() {
        return sparkRequest;
    }

    public void setSparkRequest(SparkRequest sparkRequest) {
        this.sparkRequest = sparkRequest;
    }

    public JavaRequest getJavaRequest() {
        return javaRequest;
    }

    public void setJavaRequest(JavaRequest javaRequest) {
        this.javaRequest = javaRequest;
    }

    public PythonRequest getPythonRequest() {
        return pythonRequest;
    }

    public void setPythonRequest(PythonRequest pythonRequest) {
        this.pythonRequest = pythonRequest;
    }

    public ShellRequest getShellRequest() {
        return shellRequest;
    }

    public void setShellRequest(ShellRequest shellRequest) {
        this.shellRequest = shellRequest;
    }

    public HbaseShellRequest getHbaseShellRequest() {
        return hbaseShellRequest;
    }

    public void setHbaseShellRequest(HbaseShellRequest hbaseShellRequest) {
        this.hbaseShellRequest = hbaseShellRequest;
    }

    public HbaseClassRequest getHbaseClassRequest() {
        return hbaseClassRequest;
    }

    public void setHbaseClassRequest(HbaseClassRequest hbaseClassRequest) {
        this.hbaseClassRequest = hbaseClassRequest;
    }

    public PhoenixRequest getPhoenixRequest() {
        return phoenixRequest;
    }

    public void setPhoenixRequest(PhoenixRequest phoenixRequest) {
        this.phoenixRequest = phoenixRequest;
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
}
