package org.opencloudengine.garuda.web.eco.configuration;

import org.opencloudengine.garuda.couchdb.CouchDAO;

/**
 * Created by uengine on 2016. 6. 1..
 */
public class EcoConf extends CouchDAO {

    private String hdfsSuperUser;
    private String hadoopHome;
    private String hiveHome;
    private String pigHome;
    private String sparkHome;
    private String hdfsHome;
    private String mapreduceHome;
    private String yarnHome;
    private Long regDate;
    private Long updDate;

    public String getHdfsSuperUser() {
        return hdfsSuperUser;
    }

    public void setHdfsSuperUser(String hdfsSuperUser) {
        this.hdfsSuperUser = hdfsSuperUser;
    }

    public String getHadoopHome() {
        return hadoopHome;
    }

    public void setHadoopHome(String hadoopHome) {
        this.hadoopHome = hadoopHome;
    }

    public String getHiveHome() {
        return hiveHome;
    }

    public void setHiveHome(String hiveHome) {
        this.hiveHome = hiveHome;
    }

    public String getPigHome() {
        return pigHome;
    }

    public void setPigHome(String pigHome) {
        this.pigHome = pigHome;
    }

    public String getSparkHome() {
        return sparkHome;
    }

    public void setSparkHome(String sparkHome) {
        this.sparkHome = sparkHome;
    }

    public String getHdfsHome() {
        return hdfsHome;
    }

    public void setHdfsHome(String hdfsHome) {
        this.hdfsHome = hdfsHome;
    }

    public String getMapreduceHome() {
        return mapreduceHome;
    }

    public void setMapreduceHome(String mapreduceHome) {
        this.mapreduceHome = mapreduceHome;
    }

    public String getYarnHome() {
        return yarnHome;
    }

    public void setYarnHome(String yarnHome) {
        this.yarnHome = yarnHome;
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
