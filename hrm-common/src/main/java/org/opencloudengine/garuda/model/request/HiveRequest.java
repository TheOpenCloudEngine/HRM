package org.opencloudengine.garuda.model.request;

import java.util.List;
import java.util.Map;

/**
 * Created by uengine on 2016. 9. 1..
 */
public class HiveRequest extends BasicRequest {


    /**
     * SQL
     */
    private String sql;

    /**
     * Initialization SQL
     */
    private String initializationSql;

    /**
     * Variable subsitution to apply to hive
     * commands. e.g. -d A=B or --define A=B
     * ex)
     * --define <key=value>
     * --define <key=value>
     */
    private Map<String, String> define;

    /**
     * Specify the database to use
     * ex) --database <databasename>
     */
    private String database;

    /**
     * Use value for given property
     * ex)
     * --hiveconf <property=value>
     * --hiveconf <property=value>
     */
    private Map<String, String> hiveconf;

    /**
     * Variable subsitution to apply to hive
     * commands. e.g. --hivevar A=B
     * ex)
     * --hivevar <key=value>
     * --hivevar <key=value>
     */
    private Map<String, String> hivevar;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getInitializationSql() {
        return initializationSql;
    }

    public void setInitializationSql(String initializationSql) {
        this.initializationSql = initializationSql;
    }

    public Map<String, String> getDefine() {
        return define;
    }

    public void setDefine(Map<String, String> define) {
        this.define = define;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public Map<String, String> getHiveconf() {
        return hiveconf;
    }

    public void setHiveconf(Map<String, String> hiveconf) {
        this.hiveconf = hiveconf;
    }

    public Map<String, String> getHivevar() {
        return hivevar;
    }

    public void setHivevar(Map<String, String> hivevar) {
        this.hivevar = hivevar;
    }
}
