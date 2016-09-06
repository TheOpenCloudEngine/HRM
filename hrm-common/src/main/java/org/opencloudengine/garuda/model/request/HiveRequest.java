package org.opencloudengine.garuda.model.request;

import java.util.Map;

/**
 * Created by uengine on 2016. 9. 1..
 */
public class HiveRequest extends BasicClientRequest {

    /**
     * SQL
     * It will save as a sql file, -f <filename>
     */
    private String sql;

    @FieldType(type = "textarea",
            description = "/**\n" +
                    "     * SQL\n" +
                    "     * It will save as a sql file, -f <filename>\n" +
                    "     */")
    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    /**
     * Initialization SQL
     * It will save as a sql file, -i <filename>
     */
    private String initializationSql;
    @FieldType(type = "textarea",
            description = "/**\n" +
                    "     * Initialization SQL\n" +
                    "     * It will save as a sql file, -i <filename>\n" +
                    "     */")
    public String getInitializationSql() {
        return initializationSql;
    }

    public void setInitializationSql(String initializationSql) {
        this.initializationSql = initializationSql;
    }

    /**
     * Variable subsitution to apply to hive
     * commands. e.g. -d A=B or --define A=B
     * ex)
     * --define <key=value>
     * --define <key=value>
     */
    private Map<String, String> define;
    @FieldType(type = "map",
            description = "/**\n" +
                    "     * Variable subsitution to apply to hive\n" +
                    "     * commands. e.g. -d A=B or --define A=B\n" +
                    "     * ex)\n" +
                    "     * --define <key=value>\n" +
                    "     * --define <key=value>\n" +
                    "     */")
    public Map<String, String> getDefine() {
        return define;
    }

    public void setDefine(Map<String, String> define) {
        this.define = define;
    }

    /**
     * Specify the database to use
     * ex) --database <databasename>
     */
    private String database;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * Specify the database to use\n" +
                    "     * ex) --database <databasename>\n" +
                    "     */")
    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    /**
     * Use value for given property
     * ex)
     * --hiveconf <property=value>
     * --hiveconf <property=value>
     */
    private Map<String, String> hiveconf;

    @FieldType(type = "map",
            description = "/**\n" +
                    "     * Use value for given property\n" +
                    "     * ex)\n" +
                    "     * --hiveconf <property=value>\n" +
                    "     * --hiveconf <property=value>\n" +
                    "     */")
    public Map<String, String> getHiveconf() {
        return hiveconf;
    }

    public void setHiveconf(Map<String, String> hiveconf) {
        this.hiveconf = hiveconf;
    }

    /**
     * Variable subsitution to apply to hive
     * commands. e.g. --hivevar A=B
     * ex)
     * --hivevar <key=value>
     * --hivevar <key=value>
     */
    private Map<String, String> hivevar;

    @FieldType(type = "map",
            description = "/**\n" +
                    "     * Variable subsitution to apply to hive\n" +
                    "     * commands. e.g. --hivevar A=B\n" +
                    "     * ex)\n" +
                    "     * --hivevar <key=value>\n" +
                    "     * --hivevar <key=value>\n" +
                    "     */")
    public Map<String, String> getHivevar() {
        return hivevar;
    }

    public void setHivevar(Map<String, String> hivevar) {
        this.hivevar = hivevar;
    }
}
