package org.opencloudengine.garuda.model.request;

import java.util.List;
import java.util.Map;

/**
 * Created by uengine on 2016. 9. 1..
 */
public class SparkRequest extends BasicClientRequest {

    /**
     * spark://host:port, mesos://host:port, yarn, or local.
     * ex) --master MASTER_URL
     */
    private String master;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * spark://host:port, mesos://host:port, yarn, or local.\n" +
                    "     * ex) --master MASTER_URL\n" +
                    "     */")
    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    /**
     * Whether to launch the driver program locally ("client") or
     * on one of the worker machines inside the cluster ("cluster")
     * (Default: client).
     * ex) --deploy-mode DEPLOY_MODE
     */
    private String deployMode;

    @FieldType(type = "text",
            description = "**\n" +
                    "     * Whether to launch the driver program locally (\"client\") or\n" +
                    "     * on one of the worker machines inside the cluster (\"cluster\")\n" +
                    "     * (Default: client).\n" +
                    "     * ex) --deploy-mode DEPLOY_MODE\n" +
                    "     */")
    public String getDeployMode() {
        return deployMode;
    }

    public void setDeployMode(String deployMode) {
        this.deployMode = deployMode;
    }

    /**
     * The entry point for your application (e.g. org.apache.spark.examples.SparkPi)
     * ex) --class CLASS_NAME
     */
    private String className;

    @FieldType(type = "text",
            description = "**\n" +
                    "     * The entry point for your application (e.g. org.apache.spark.examples.SparkPi)\n" +
                    "     * ex) --class CLASS_NAME\n" +
                    "     */")
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Path to a bundled jar including your application and all dependencies.
     * The URL must be globally visible inside of your cluster, for instance,
     * an hdfs:// path or a file:// path that is present on all nodes.
     * ex) /path/to/examples.jar
     */
    private String applicationJar;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * Path to a bundled jar including your application and all dependencies.\n" +
                    "     * The URL must be globally visible inside of your cluster, for instance,\n" +
                    "     * an hdfs:// path or a file:// path that is present on all nodes.\n" +
                    "     * ex) /path/to/examples.jar\n" +
                    "     */")
    public String getApplicationJar() {
        return applicationJar;
    }

    public void setApplicationJar(String applicationJar) {
        this.applicationJar = applicationJar;
    }

    /**
     * Arguments passed to the main method of your main class, if any
     * ex) /path/to/examples.jar 10 20 30
     */
    private List<String> applicationArguments;

    @FieldType(type = "textList",
            description = "/**\n" +
                    "     * Arguments passed to the main method of your main class, if any\n" +
                    "     * ex) /path/to/examples.jar 10 20 30\n" +
                    "     */")
    public List<String> getApplicationArguments() {
        return applicationArguments;
    }

    public void setApplicationArguments(List<String> applicationArguments) {
        this.applicationArguments = applicationArguments;
    }

    /**
     * Arbitrary Spark configuration property.
     * ex)
     * --conf "spark.executor.extraJavaOptions=-Dconfig.resource=app"
     * --conf "spark.driver.extraJavaOptions=-Dconfig.resource=app"
     */
    private Map<String, String> conf;

    @FieldType(type = "map",
            description = "/**\n" +
                    "     * Arbitrary Spark configuration property.\n" +
                    "     * ex)\n" +
                    "     * --conf \"spark.executor.extraJavaOptions=-Dconfig.resource=app\"\n" +
                    "     * --conf \"spark.driver.extraJavaOptions=-Dconfig.resource=app\"\n" +
                    "     */")
    public Map<String, String> getConf() {
        return conf;
    }

    public void setConf(Map<String, String> conf) {
        this.conf = conf;
    }

    /**
     * A name of your application.
     * ex) --name NAME
     */
    private String name;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * A name of your application.\n" +
                    "     * ex) --name NAME\n" +
                    "     */")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Comma-separated list of local jars to include on the driver
     * and executor classpaths.
     * ex) --jars /path/jar1,/path/jar2,/path/jar3
     */
    private List<String> jars;

    @FieldType(type = "textList",
            description = "/**\n" +
                    "     * Comma-separated list of local jars to include on the driver\n" +
                    "     * and executor classpaths.\n" +
                    "     * ex) --jars /path/jar1,/path/jar2,/path/jar3\n" +
                    "     */")
    public List<String> getJars() {
        return jars;
    }

    public void setJars(List<String> jars) {
        this.jars = jars;
    }

    /**
     * Comma-separated list of maven coordinates of jars to include
     * on the driver and executor classpaths. Will search the local
     * maven repo, then maven central and any additional remote
     * repositories given by --repositories. The format for the
     * coordinates should be groupId:artifactId:version.
     * ex) --packages cgroupId:artifactId:version,groupId:artifactId:version
     */
    private List<String> packages;

    @FieldType(type = "textList",
            description = "/**\n" +
                    "     * Comma-separated list of maven coordinates of jars to include\n" +
                    "     * on the driver and executor classpaths. Will search the local\n" +
                    "     * maven repo, then maven central and any additional remote\n" +
                    "     * repositories given by --repositories. The format for the\n" +
                    "     * coordinates should be groupId:artifactId:version.\n" +
                    "     * ex) --packages cgroupId:artifactId:version,groupId:artifactId:version\n" +
                    "     */")
    public List<String> getPackages() {
        return packages;
    }

    public void setPackages(List<String> packages) {
        this.packages = packages;
    }

    /**
     * Comma-separated list of groupId:artifactId, to exclude while
     * resolving the dependencies provided in --packages to avoid
     * dependency conflicts.
     * ex) --exclude-packages groupId:artifactId,groupId:artifactId
     */
    private List<String> excludePackages;

    @FieldType(type = "textList",
            description = "/**\n" +
                    "     * Comma-separated list of groupId:artifactId, to exclude while\n" +
                    "     * resolving the dependencies provided in --packages to avoid\n" +
                    "     * dependency conflicts.\n" +
                    "     * ex) --exclude-packages groupId:artifactId,groupId:artifactId\n" +
                    "     */")
    public List<String> getExcludePackages() {
        return excludePackages;
    }

    public void setExcludePackages(List<String> excludePackages) {
        this.excludePackages = excludePackages;
    }

    /**
     * Comma-separated list of additional remote repositories to
     * search for the maven coordinates given with --packages.
     * ex) --repositories A,B,C
     */
    private List<String> repositories;

    @FieldType(type = "textList",
            description = "/**\n" +
                    "     * Comma-separated list of additional remote repositories to\n" +
                    "     * search for the maven coordinates given with --packages.\n" +
                    "     * ex) --repositories A,B,C\n" +
                    "     */")
    public List<String> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<String> repositories) {
        this.repositories = repositories;
    }

    /**
     * Comma-separated list of .zip, .egg, or .py files to place
     * on the PYTHONPATH for Python apps.
     * ex) --py-files /path/1.zip,/path/2.zip
     */
    private List<String> pyFiles;

    @FieldType(type = "textList",
            description = "/**\n" +
                    "     * Comma-separated list of .zip, .egg, or .py files to place\n" +
                    "     * on the PYTHONPATH for Python apps.\n" +
                    "     * ex) --py-files /path/1.zip,/path/2.zip\n" +
                    "     */")
    public List<String> getPyFiles() {
        return pyFiles;
    }

    public void setPyFiles(List<String> pyFiles) {
        this.pyFiles = pyFiles;
    }

    /**
     * Comma-separated list of files to be placed in the working
     * directory of each executor.
     * ex) --files /path/1.zip,/path/2.zip
     */
    private List<String> files;

    @FieldType(type = "textList",
            description = "/**\n" +
                    "     * Comma-separated list of files to be placed in the working\n" +
                    "     * directory of each executor.\n" +
                    "     * ex) --files /path/1.zip,/path/2.zip\n" +
                    "     */")
    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    /**
     * Path to a file from which to load extra properties.
     * If not specified, this will look for conf/spark-defaults.conf.
     * ex) --properties-file /path/spark.conf
     */
    private String propertiesFile;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * Path to a file from which to load extra properties.\n" +
                    "     * If not specified, this will look for conf/spark-defaults.conf.\n" +
                    "     * ex) --properties-file /path/spark.conf\n" +
                    "     */")
    public String getPropertiesFile() {
        return propertiesFile;
    }

    public void setPropertiesFile(String propertiesFile) {
        this.propertiesFile = propertiesFile;
    }

    /**
     * Memory for driver (e.g. 1000M, 2G) (Default: 1024M).
     * ex) --driver-memory 1G
     */
    private String driverMemory;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * Memory for driver (e.g. 1000M, 2G) (Default: 1024M).\n" +
                    "     * ex) --driver-memory 1G\n" +
                    "     */")
    public String getDriverMemory() {
        return driverMemory;
    }

    public void setDriverMemory(String driverMemory) {
        this.driverMemory = driverMemory;
    }

    /**
     * Extra Java options to pass to the driver.
     * ex) --driver-java-options "-Daa.aa=a -Dbb.bb=b"
     */
    private String driverJavaOptions;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * Extra Java options to pass to the driver.\n" +
                    "     * ex) --driver-java-options \"-Daa.aa=a -Dbb.bb=b\"\n" +
                    "     */")
    public String getDriverJavaOptions() {
        return driverJavaOptions;
    }

    public void setDriverJavaOptions(String driverJavaOptions) {
        this.driverJavaOptions = driverJavaOptions;
    }

    /**
     * Extra library path entries to pass to the driver.
     * ex) --driver-library-path /opt/cloudera/parcels/CDH-5.3.0-1.cdh5.3.0.p0.30/lib/hadoop/lib/native
     */
    private String driverLibraryPath;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * Extra library path entries to pass to the driver.\n" +
                    "     * ex) --driver-library-path /opt/cloudera/parcels/CDH-5.3.0-1.cdh5.3.0.p0.30/lib/hadoop/lib/native\n" +
                    "     */")
    public String getDriverLibraryPath() {
        return driverLibraryPath;
    }

    public void setDriverLibraryPath(String driverLibraryPath) {
        this.driverLibraryPath = driverLibraryPath;
    }

    /**
     * Extra class path entries to pass to the driver. Note that
     * jars added with --jars are automatically included in the
     * classpath.
     * ex) --driver-class-path /Users/myusername/spark-1.6.1-bin-hadoop2.4/lib/postgresql-9.4.1208.jre6.jar:/Users/myusername/spark-1.6.1-bin-hadoop2.4/lib/sqljdbc4.jar
     */
    private String driverClassPath;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * Extra class path entries to pass to the driver. Note that\n" +
                    "     * jars added with --jars are automatically included in the\n" +
                    "     * classpath.\n" +
                    "     * ex) --driver-class-path /Users/myusername/spark-1.6.1-bin-hadoop2.4/lib/postgresql-9.4.1208.jre6.jar:/Users/myusername/spark-1.6.1-bin-hadoop2.4/lib/sqljdbc4.jar\n" +
                    "     */")
    public String getDriverClassPath() {
        return driverClassPath;
    }

    public void setDriverClassPath(String driverClassPath) {
        this.driverClassPath = driverClassPath;
    }

    /**
     * Memory per executor (e.g. 1000M, 2G) (Default: 1G).
     * ex) --executor-memory 1G
     */
    private String executorMemory;

    @FieldType(type = "text",
            description = " /**\n" +
                    "     * Memory per executor (e.g. 1000M, 2G) (Default: 1G).\n" +
                    "     * ex) --executor-memory 1G\n" +
                    "     */")
    public String getExecutorMemory() {
        return executorMemory;
    }

    public void setExecutorMemory(String executorMemory) {
        this.executorMemory = executorMemory;
    }

    /**
     * User to impersonate when submitting the application.
     * This argument does not work with --principal / --keytab.
     * ex) --proxy-user NAME
     */
    private String proxyUser;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * User to impersonate when submitting the application.\n" +
                    "     * This argument does not work with --principal / --keytab.\n" +
                    "     * ex) --proxy-user NAME\n" +
                    "     */")
    public String getProxyUser() {
        return proxyUser;
    }

    public void setProxyUser(String proxyUser) {
        this.proxyUser = proxyUser;
    }
    /**
     * ======================================
     * Spark standalone with cluster deploy mode only:
     * ======================================
     */

    /**
     * Cores for driver (Default: 1).
     * ex) --driver-cores NUM
     */
    private Integer driverCores;

    @FieldType(type = "number",
            description = "/**\n" +
                    "     * ======================================\n" +
                    "     * Spark standalone with cluster deploy mode only:\n" +
                    "     * ======================================\n" +
                    "     */\n" +
                    "\n" +
                    "    /**\n" +
                    "     * Cores for driver (Default: 1).\n" +
                    "     * ex) --driver-cores NUM\n" +
                    "     */")
    public Integer getDriverCores() {
        return driverCores;
    }

    public void setDriverCores(Integer driverCores) {
        this.driverCores = driverCores;
    }
    /**
     * ======================================
     * Spark standalone or Mesos with cluster deploy mode only:
     * ======================================
     */
    /**
     * If given, restarts the driver on failure.
     * ex) --supervise
     */
    private boolean supervise;

    @FieldType(type = "boolean",
            description = "/**\n" +
                    "     * ======================================\n" +
                    "     * Spark standalone or Mesos with cluster deploy mode only:\n" +
                    "     * ======================================\n" +
                    "     */\n" +
                    "    /**\n" +
                    "     * If given, restarts the driver on failure.\n" +
                    "     * ex) --supervise\n" +
                    "     */")
    public boolean getSupervise() {
        return supervise;
    }

    public void setSupervise(boolean supervise) {
        this.supervise = supervise;
    }
    /**
     * ======================================
     * Spark standalone or Mesos with cluster deploy mode only:
     * ======================================
     */
    /**
     * If given, kills the driver specified.
     * ex) --kill SUBMISSION_ID
     */
    private String kill;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * ======================================\n" +
                    "     * Spark standalone or Mesos with cluster deploy mode only:\n" +
                    "     * ======================================\n" +
                    "     */\n" +
                    "    /**\n" +
                    "     * If given, kills the driver specified.\n" +
                    "     * ex) --kill SUBMISSION_ID\n" +
                    "     */")
    public String getKill() {
        return kill;
    }

    public void setKill(String kill) {
        this.kill = kill;
    }
    /**
     * ======================================
     * Spark standalone or Mesos with cluster deploy mode only:
     * ======================================
     */
    /**
     * If given, requests the status of the driver specified.
     * ex) --status SUBMISSION_ID
     */
    private String status;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * ======================================\n" +
                    "     * Spark standalone or Mesos with cluster deploy mode only:\n" +
                    "     * ======================================\n" +
                    "     */\n" +
                    "    /**\n" +
                    "     * If given, requests the status of the driver specified.\n" +
                    "     * ex) --status SUBMISSION_ID\n" +
                    "     */")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * ======================================
     * Spark standalone and Mesos only:
     * ======================================
     */
    /**
     * Total cores for all executors.
     * ex) --total-executor-cores NUM
     */
    private Integer totalExecutorCores;

    @FieldType(type = "number",
            description = "/**\n" +
                    "     * ======================================\n" +
                    "     * Spark standalone and Mesos only:\n" +
                    "     * ======================================\n" +
                    "     */\n" +
                    "    /**\n" +
                    "     * Total cores for all executors.\n" +
                    "     * ex) --total-executor-cores NUM\n" +
                    "     */")
    public Integer getTotalExecutorCores() {
        return totalExecutorCores;
    }

    public void setTotalExecutorCores(Integer totalExecutorCores) {
        this.totalExecutorCores = totalExecutorCores;
    }
    /**
     * ======================================
     * Spark standalone and YARN only:
     * ======================================
     */
    /**
     * Number of cores per executor. (Default: 1 in YARN mode,
     * or all available cores on the worker in standalone mode)
     * ex) --executor-cores NUM
     */
    private Integer executorCores;

    @FieldType(type = "number",
            description = "/**\n" +
                    "     * ======================================\n" +
                    "     * Spark standalone and YARN only:\n" +
                    "     * ======================================\n" +
                    "     */\n" +
                    "    /**\n" +
                    "     * Number of cores per executor. (Default: 1 in YARN mode,\n" +
                    "     * or all available cores on the worker in standalone mode)\n" +
                    "     * ex) --executor-cores NUM\n" +
                    "     */")
    public Integer getExecutorCores() {
        return executorCores;
    }

    public void setExecutorCores(Integer executorCores) {
        this.executorCores = executorCores;
    }
    /**
     * ======================================
     * YARN-only:
     * ======================================
     */
    /**
     * Number of cores used by the driver, only in cluster mode
     * (Default: 1).
     * ex) --driver-cores NUM
     */
    private Integer yarnDriverCores;

    @FieldType(type = "number",
            description = "/**\n" +
                    "     * ======================================\n" +
                    "     * YARN-only:\n" +
                    "     * ======================================\n" +
                    "     */\n" +
                    "    /**\n" +
                    "     * Number of cores used by the driver, only in cluster mode\n" +
                    "     * (Default: 1).\n" +
                    "     * ex) --driver-cores NUM\n" +
                    "     */")
    public Integer getYarnDriverCores() {
        return yarnDriverCores;
    }

    public void setYarnDriverCores(Integer yarnDriverCores) {
        this.yarnDriverCores = yarnDriverCores;
    }
    /**
     * ======================================
     * YARN-only:
     * ======================================
     */
    /**
     * The YARN queue to submit to (Default: "default").
     * ex) --queue QUEUE_NAME
     */
    private String queue;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * ======================================\n" +
                    "     * YARN-only:\n" +
                    "     * ======================================\n" +
                    "     */\n" +
                    "    /**\n" +
                    "     * The YARN queue to submit to (Default: \"default\").\n" +
                    "     * ex) --queue QUEUE_NAME\n" +
                    "     */")
    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }
    /**
     * ======================================
     * YARN-only:
     * ======================================
     */
    /**
     * Number of executors to launch (Default: 2).
     * ex) --num-executors NUM
     */
    private Integer numExecutors;

    @FieldType(type = "number",
            description = "/**\n" +
                    "     * ======================================\n" +
                    "     * YARN-only:\n" +
                    "     * ======================================\n" +
                    "     */\n" +
                    "    /**\n" +
                    "     * Number of executors to launch (Default: 2).\n" +
                    "     * ex) --num-executors NUM\n" +
                    "     */")
    public Integer getNumExecutors() {
        return numExecutors;
    }

    public void setNumExecutors(Integer numExecutors) {
        this.numExecutors = numExecutors;
    }
    /**
     * ======================================
     * YARN-only:
     * ======================================
     */
    /**
     * Comma separated list of archives to be extracted into the
     * working directory of each executor.
     * ex) --archives ARCHIVES
     */
    private List<String> archives;

    @FieldType(type = "textList",
            description = "/**\n" +
                    "     * ======================================\n" +
                    "     * YARN-only:\n" +
                    "     * ======================================\n" +
                    "     */\n" +
                    "    /**\n" +
                    "     * Comma separated list of archives to be extracted into the\n" +
                    "     * working directory of each executor.\n" +
                    "     * ex) --archives ARCHIVES\n" +
                    "     */")
    public List<String> getArchives() {
        return archives;
    }

    public void setArchives(List<String> archives) {
        this.archives = archives;
    }
    /**
     * ======================================
     * YARN-only:
     * ======================================
     */
    /**
     * Principal to be used to login to KDC, while running on
     * secure HDFS.
     * ex) --principal PRINCIPAL
     */
    private String principal;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * ======================================\n" +
                    "     * YARN-only:\n" +
                    "     * ======================================\n" +
                    "     */\n" +
                    "    /**\n" +
                    "     * Principal to be used to login to KDC, while running on\n" +
                    "     * secure HDFS.\n" +
                    "     * ex) --principal PRINCIPAL\n" +
                    "     */")
    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }
    /**
     * ======================================
     * YARN-only:
     * ======================================
     */
    /**
     * The full path to the file that contains the keytab for the
     * principal specified above. This keytab will be copied to
     * the node running the Application Master via the Secure
     * Distributed Cache, for renewing the login tickets and the
     * delegation tokens periodically.
     * ex) --keytab KEYTAB
     */
    private String keytab;

    @FieldType(type = "text",
            description = "/**\n" +
                    "     * ======================================\n" +
                    "     * YARN-only:\n" +
                    "     * ======================================\n" +
                    "     */\n" +
                    "    /**\n" +
                    "     * The full path to the file that contains the keytab for the\n" +
                    "     * principal specified above. This keytab will be copied to\n" +
                    "     * the node running the Application Master via the Secure\n" +
                    "     * Distributed Cache, for renewing the login tickets and the\n" +
                    "     * delegation tokens periodically.\n" +
                    "     * ex) --keytab KEYTAB\n" +
                    "     */")
    public String getKeytab() {
        return keytab;
    }

    public void setKeytab(String keytab) {
        this.keytab = keytab;
    }
}