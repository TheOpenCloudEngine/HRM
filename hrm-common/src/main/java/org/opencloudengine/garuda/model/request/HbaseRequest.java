package org.opencloudengine.garuda.model.request;

import java.util.List;

/**
 * Created by uengine on 2016. 9. 1..
 */
public class HbaseRequest extends BasicClientRequest {

    /**
     * Configuration direction to use. Default: ./conf
     * ex) --config DIR
     */
    private String configDir;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * Configuration direction to use. Default: ./conf\n" +
                    "     * ex) --config DIR\n" +
                    "     */")
    public String getConfigDir() {
        return configDir;
    }

    public void setConfigDir(String configDir) {
        this.configDir = configDir;
    }

    /**
     * Specify comma separated host to override the list in 'regionservers' file.
     * ex) --hosts <comma separated list of hosts>
     */
    private List<String> hosts;

    @FieldType(type = "textList",
            description = "/**\n" +
                    "     * Specify comma separated host to override the list in 'regionservers' file.\n" +
                    "     * ex) --hosts <comma separated list of hosts>\n" +
                    "     */")
    public List<String> getHosts() {
        return hosts;
    }

    public void setHosts(List<String> hosts) {
        this.hosts = hosts;
    }

    /**
     * Authenticate to ZooKeeper using servers configuration
     * ex) --auth-as-server
     */
    private boolean authAsServer;

    @FieldType(type = "boolean",
            description = "/**\n" +
                    "     * Authenticate to ZooKeeper using servers configuration\n" +
                    "     * ex) --auth-as-server\n" +
                    "     */")
    public boolean getAuthAsServer() {
        return authAsServer;
    }

    public void setAuthAsServer(boolean authAsServer) {
        this.authAsServer = authAsServer;
    }
}
