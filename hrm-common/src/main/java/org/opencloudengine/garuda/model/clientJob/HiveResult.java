package org.opencloudengine.garuda.model.clientJob;

/**
 * Created by uengine on 2016. 9. 2..
 */
public class HiveResult extends ClientResult {
    private String csv;

    public String getCsv() {
        return csv;
    }

    public void setCsv(String csv) {
        this.csv = csv;
    }
}
