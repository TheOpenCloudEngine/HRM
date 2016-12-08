package org.opencloudengine.garuda.model.request;

/**
 * Created by uengine on 2016. 12. 8..
 */
public class PhoenixSource {
    public PhoenixSource() {
    }

    public static String CSV = "csv";
    public static String SQL = "sql";

    private String type = SQL;
    private String value;
    private String tableName;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
