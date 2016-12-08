package org.opencloudengine.garuda.model.request;

import java.util.List;

/**
 * Created by uengine on 2016. 9. 1..
 */
public class PhoenixRequest extends BasicClientRequest {

    /**
     * Define zookeeper,
     * defaults to 'localhost[:default port]'
     * ex) psql <zookeeper> <path-to-sql-or-csv-file>...
     */
    private String zookeeper;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * Define zookeeper,\n" +
                    "     * defaults to 'localhost[:default port]'\n" +
                    "     * ex) psql <zookeeper> <path-to-sql-or-csv-file>...\n" +
                    "     */")
    public String getZookeeper() {
        return zookeeper;
    }

    public void setZookeeper(String zookeeper) {
        this.zookeeper = zookeeper;
    }

    /**
     * array of sql-or-csv source
     * if source start with 'local:', it will take sql-or-csv source from local filesystem
     * ex) psql <zookeeper> <path-to-sql-or-csv-file>...
     */
    private List<PhoenixSource> sources;

    @FieldType(type = "phoenixSourceList",
            description = "/**\n" +
                    "     * array of sql-or-csv source\n" +
                    "     * if source start with 'local:', it will take sql-or-csv source from local filesystem\n" +
                    "     * ex) psql <zookeeper> <path-to-sql-or-csv-file>...\n" +
                    "     */")
    public List<PhoenixSource> getSources() {
        return sources;
    }

    public void setSources(List<PhoenixSource> sources) {
        this.sources = sources;
    }

    /**
     * Define the array element separator,
     * defaults to ':'
     * ex) --array-separator <arg>
     */
    private String arraySeparator;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * Define the array element separator,\n" +
                    "     * defaults to ':'\n" +
                    "     * ex) -a,--array-separator <arg>\n" +
                    "     */")
    public String getArraySeparator() {
        return arraySeparator;
    }

    public void setArraySeparator(String arraySeparator) {
        this.arraySeparator = arraySeparator;
    }


    /**
     * Used in conjunction with the -u option to
     * bypass the rewrite during upgrade if you
     * know that your data does not need to be
     * upgrade. This would only be the case if you
     * have not relied on auto padding for BINARY
     * and CHAR data, but instead have always
     * provided data up to the full max length of
     * the column. See PHOENIX-2067 and
     * PHOENIX-2120 for more information.
     * ex) --bypass-upgrade
     */
    private boolean bypassUpgrade;

    @FieldType(type = "boolean",
            description = "/**\n" +
                    "     * Used in conjunction with the -u option to\n" +
                    "     * bypass the rewrite during upgrade if you\n" +
                    "     * know that your data does not need to be\n" +
                    "     * upgrade. This would only be the case if you\n" +
                    "     * have not relied on auto padding for BINARY\n" +
                    "     * and CHAR data, but instead have always\n" +
                    "     * provided data up to the full max length of\n" +
                    "     * the column. See PHOENIX-2067 and\n" +
                    "     * PHOENIX-2120 for more information.\n" +
                    "     * ex) --bypass-upgrade\n" +
                    "     */")
    public boolean getBypassUpgrade() {
        return bypassUpgrade;
    }

    public void setBypassUpgrade(boolean bypassUpgrade) {
        this.bypassUpgrade = bypassUpgrade;
    }

    /**
     * Field delimiter for CSV loader. A digit is
     * interpreted as 1 -> ctrl A, 2 -> ctrl B ...
     * 9 -> ctrl I.
     * ex) --delimiter <arg>
     */
    private String delimiter;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * Field delimiter for CSV loader. A digit is\n" +
                    "     * interpreted as 1 -> ctrl A, 2 -> ctrl B ...\n" +
                    "     * 9 -> ctrl I.\n" +
                    "     * ex) --delimiter <arg>\n" +
                    "     */")
    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    /**
     * Escape character for CSV loader. A digit is
     * interpreted as a control character
     * ex) --escape-character <arg>
     */
    private String escapeCharacter;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * Escape character for CSV loader. A digit is\n" +
                    "     * interpreted as a control character\n" +
                    "     * ex) --escape-character <arg>\n" +
                    "     */")
    public String getEscapeCharacter() {
        return escapeCharacter;
    }

    public void setEscapeCharacter(String escapeCharacter) {
        this.escapeCharacter = escapeCharacter;
    }

    /**
     * Overrides the column names to which the CSV
     * data maps and is case sensitive. A special
     * value of in-line indicating that the first
     * line of the CSV file determines the column
     * to which the data maps
     * ex) --header <arg>
     */
    private String header;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * Overrides the column names to which the CSV\n" +
                    "     * data maps and is case sensitive. A special\n" +
                    "     * value of in-line indicating that the first\n" +
                    "     * line of the CSV file determines the column\n" +
                    "     * to which the data maps\n" +
                    "     * ex) --header <arg>\n" +
                    "     */")
    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * Used to upgrade local index data by moving
     * index data from separate table to separate
     * column families in the same table.
     * ex) --local-index-upgrade
     */
    private boolean localIndexUpgrade;

    @FieldType(type = "boolean",
            description = "/**\n" +
                    "     * Used to upgrade local index data by moving\n" +
                    "     * index data from separate table to separate\n" +
                    "     * column families in the same table.\n" +
                    "     * ex) --local-index-upgrade\n" +
                    "     */")
    public boolean getLocalIndexUpgrade() {
        return localIndexUpgrade;
    }

    public void setLocalIndexUpgrade(boolean localIndexUpgrade) {
        this.localIndexUpgrade = localIndexUpgrade;
    }

    /**
     * Used to map table to a namespace matching
     * with schema, require
     * phoenix.schema.isNamespaceMappingEnabled to
     * be enabled
     * ex) --map-namespace <arg>
     */
    private String mapNamespace;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * Used to map table to a namespace matching\n" +
                    "     * with schema, require\n" +
                    "     * phoenix.schema.isNamespaceMappingEnabled to\n" +
                    "     * be enabled\n" +
                    "     * ex) --map-namespace <arg>\n" +
                    "     */")
    public String getMapNamespace() {
        return mapNamespace;
    }

    public void setMapNamespace(String mapNamespace) {
        this.mapNamespace = mapNamespace;
    }

    /**
     * Quote character for CSV loader. A digit is
     * interpreted as a control character
     * ex) --quote-character <arg>
     */
    private String quoteCharacter;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * Quote character for CSV loader. A digit is\n" +
                    "     * interpreted as a control character\n" +
                    "     * ex) --quote-character <arg>\n" +
                    "     */")
    public String getQuoteCharacter() {
        return quoteCharacter;
    }

    public void setQuoteCharacter(String quoteCharacter) {
        this.quoteCharacter = quoteCharacter;
    }

    /**
     * Use strict mode by throwing an exception if
     * a column name doesn't match during CSV
     * loading
     * ex) --strict
     */
    private boolean strict;

    @FieldType(type = "boolean",
            description = "/**\n" +
                    "     * Use strict mode by throwing an exception if\n" +
                    "     * a column name doesn't match during CSV\n" +
                    "     * loading\n" +
                    "     * ex) --strict\n" +
                    "     */")
    public boolean getStrict() {
        return strict;
    }

    public void setStrict(boolean strict) {
        this.strict = strict;
    }

    /**
     * Overrides the table into which the CSV data
     * is loaded and is case sensitive
     * ex) --table <arg>
     */
    private String table;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * Overrides the table into which the CSV data\n" +
                    "     is loaded and is case sensitive\n" +
                    "     * ex) --table <arg>\n" +
                    "     */")
    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    /**
     * Upgrades tables specified as arguments by
     * rewriting them with the correct row key for
     * descending columns. If no arguments are
     * specified, then tables that need to be
     * upgraded will be displayed without being
     * upgraded. Use the -b option to bypass the
     * rewrite if you know that your data does not
     * need to be upgrade. This would only be the
     * case if you have not relied on auto padding
     * for BINARY and CHAR data, but instead have
     * always provided data up to the full max
     * length of the column. See PHOENIX-2067 and
     * PHOENIX-2120 for more information. Note
     * that phoenix.query.timeoutMs and
     * hbase.regionserver.lease.period parameters
     * must be set very high to prevent timeouts
     * when upgrading.
     * ex) --upgrade
     */
    private boolean upgrade;

    @FieldType(type = "boolean",
            description = "/**\n" +
                    "     * Upgrades tables specified as arguments by\n" +
                    "     * rewriting them with the correct row key for\n" +
                    "     * descending columns. If no arguments are\n" +
                    "     * specified, then tables that need to be\n" +
                    "     * upgraded will be displayed without being\n" +
                    "     * upgraded. Use the -b option to bypass the\n" +
                    "     * rewrite if you know that your data does not\n" +
                    "     * need to be upgrade. This would only be the\n" +
                    "     * case if you have not relied on auto padding\n" +
                    "     * for BINARY and CHAR data, but instead have\n" +
                    "     * always provided data up to the full max\n" +
                    "     * length of the column. See PHOENIX-2067 and\n" +
                    "     * PHOENIX-2120 for more information. Note\n" +
                    "     * that phoenix.query.timeoutMs and\n" +
                    "     * hbase.regionserver.lease.period parameters\n" +
                    "     * must be set very high to prevent timeouts\n" +
                    "     * when upgrading.\n" +
                    "     * ex) --upgrade\n" +
                    "     */")
    public boolean getUpgrade() {
        return upgrade;
    }

    public void setUpgrade(boolean upgrade) {
        this.upgrade = upgrade;
    }
}
