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

    /**
     * Whether to launch the driver program locally ("client") or
     * on one of the worker machines inside the cluster ("cluster")
     * (Default: client).
     * ex) --deploy-mode DEPLOY_MODE
     */
    private String deployMode;

    /**
     * The entry point for your application (e.g. org.apache.spark.examples.SparkPi)
     * ex) --class CLASS_NAME
     */
    private String className;

    /**
     * Path to a bundled jar including your application and all dependencies.
     * The URL must be globally visible inside of your cluster, for instance,
     * an hdfs:// path or a file:// path that is present on all nodes.
     * ex) /path/to/examples.jar
     */
    private String applicationJar;

    /**
     * Arguments passed to the main method of your main class, if any
     * ex) /path/to/examples.jar 10 20 30
     */
    private List<String> applicationArguments;

    /**
     * Arbitrary Spark configuration property.
     * ex)
     * --conf "spark.executor.extraJavaOptions=-Dconfig.resource=app"
     * --conf "spark.driver.extraJavaOptions=-Dconfig.resource=app"
     */
    private Map<String, String> conf;

    /**
     * A name of your application.
     * ex) --name NAME
     */
    private String name;

    /**
     * Comma-separated list of local jars to include on the driver
     * and executor classpaths.
     * ex) --jars /path/jar1,/path/jar2,/path/jar3
     */
    private List<String> jars;

    /**
     * Comma-separated list of maven coordinates of jars to include
     * on the driver and executor classpaths. Will search the local
     * maven repo, then maven central and any additional remote
     * repositories given by --repositories. The format for the
     * coordinates should be groupId:artifactId:version.
     * ex) --packages cgroupId:artifactId:version,groupId:artifactId:version
     */
    private List<String> packages;

    /**
     * Comma-separated list of groupId:artifactId, to exclude while
     * resolving the dependencies provided in --packages to avoid
     * dependency conflicts.
     * ex) --exclude-packages groupId:artifactId,groupId:artifactId
     */
    private List<String> excludePackages;

    /**
     * Comma-separated list of additional remote repositories to
     * search for the maven coordinates given with --packages.
     * ex) --repositories A,B,C
     */
    private List<String> repositories;

    /**
     * Comma-separated list of .zip, .egg, or .py files to place
     * on the PYTHONPATH for Python apps.
     * ex) --py-files /path/1.zip,/path/2.zip
     */
    private List<String> pyFiles;

    /**
     * Comma-separated list of files to be placed in the working
     * directory of each executor.
     * ex) --files /path/1.zip,/path/2.zip
     */
    private List<String> files;

    /**
     * Path to a file from which to load extra properties.
     * If not specified, this will look for conf/spark-defaults.conf.
     * ex) --properties-file /path/spark.conf
     */
    private String propertiesFile;

    /**
     * Memory for driver (e.g. 1000M, 2G) (Default: 1024M).
     * ex) --driver-memory 1G
     */
    private String driverMemory;

    /**
     * Extra Java options to pass to the driver.
     * ex) --driver-java-options "-Daa.aa=a -Dbb.bb=b"
     */
    private String driverJavaOptions;


    /**
     * Extra library path entries to pass to the driver.
     * ex) --driver-library-path /opt/cloudera/parcels/CDH-5.3.0-1.cdh5.3.0.p0.30/lib/hadoop/lib/native
     */
    private String driverLibraryPath;

    /**
     * Extra class path entries to pass to the driver. Note that
     * jars added with --jars are automatically included in the
     * classpath.
     * ex) --driver-class-path /Users/myusername/spark-1.6.1-bin-hadoop2.4/lib/postgresql-9.4.1208.jre6.jar:/Users/myusername/spark-1.6.1-bin-hadoop2.4/lib/sqljdbc4.jar
     */
    private String driverClassPath;

    /**
     * Memory per executor (e.g. 1000M, 2G) (Default: 1G).
     * ex) --executor-memory 1G
     */
    private String executorMemory;

    /**
     * User to impersonate when submitting the application.
     * This argument does not work with --principal / --keytab.
     * ex) --proxy-user NAME
     */
    private String proxyUser;


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

    /**
     * If given, kills the driver specified.
     * ex) --kill SUBMISSION_ID
     */
    private String kill;

    /**
     * If given, requests the status of the driver specified.
     * ex) --status SUBMISSION_ID
     */
    private String status;

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

    /**
     * The YARN queue to submit to (Default: "default").
     * ex) --queue QUEUE_NAME
     */
    private String queue;

    /**
     * Number of executors to launch (Default: 2).
     * ex) --num-executors NUM
     */
    private Integer numExecutors;

    /**
     * Comma separated list of archives to be extracted into the
     * working directory of each executor.
     * ex) --archives ARCHIVES
     */
    private List<String> archives;

    /**
     * Principal to be used to login to KDC, while running on
     * secure HDFS.
     * ex) --principal PRINCIPAL
     */
    private String principal;

    /**
     * The full path to the file that contains the keytab for the
     * principal specified above. This keytab will be copied to
     * the node running the Application Master via the Secure
     * Distributed Cache, for renewing the login tickets and the
     * delegation tokens periodically.
     * ex) --keytab KEYTAB
     */
    private String keytab;

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getDeployMode() {
        return deployMode;
    }

    public void setDeployMode(String deployMode) {
        this.deployMode = deployMode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getApplicationJar() {
        return applicationJar;
    }

    public void setApplicationJar(String applicationJar) {
        this.applicationJar = applicationJar;
    }

    public List<String> getApplicationArguments() {
        return applicationArguments;
    }

    public void setApplicationArguments(List<String> applicationArguments) {
        this.applicationArguments = applicationArguments;
    }

    public Map<String, String> getConf() {
        return conf;
    }

    public void setConf(Map<String, String> conf) {
        this.conf = conf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getJars() {
        return jars;
    }

    public void setJars(List<String> jars) {
        this.jars = jars;
    }

    public List<String> getPackages() {
        return packages;
    }

    public void setPackages(List<String> packages) {
        this.packages = packages;
    }

    public List<String> getExcludePackages() {
        return excludePackages;
    }

    public void setExcludePackages(List<String> excludePackages) {
        this.excludePackages = excludePackages;
    }

    public List<String> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<String> repositories) {
        this.repositories = repositories;
    }

    public List<String> getPyFiles() {
        return pyFiles;
    }

    public void setPyFiles(List<String> pyFiles) {
        this.pyFiles = pyFiles;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public String getPropertiesFile() {
        return propertiesFile;
    }

    public void setPropertiesFile(String propertiesFile) {
        this.propertiesFile = propertiesFile;
    }

    public String getDriverMemory() {
        return driverMemory;
    }

    public void setDriverMemory(String driverMemory) {
        this.driverMemory = driverMemory;
    }

    public String getDriverJavaOptions() {
        return driverJavaOptions;
    }

    public void setDriverJavaOptions(String driverJavaOptions) {
        this.driverJavaOptions = driverJavaOptions;
    }

    public String getDriverLibraryPath() {
        return driverLibraryPath;
    }

    public void setDriverLibraryPath(String driverLibraryPath) {
        this.driverLibraryPath = driverLibraryPath;
    }

    public String getDriverClassPath() {
        return driverClassPath;
    }

    public void setDriverClassPath(String driverClassPath) {
        this.driverClassPath = driverClassPath;
    }

    public String getExecutorMemory() {
        return executorMemory;
    }

    public void setExecutorMemory(String executorMemory) {
        this.executorMemory = executorMemory;
    }

    public String getProxyUser() {
        return proxyUser;
    }

    public void setProxyUser(String proxyUser) {
        this.proxyUser = proxyUser;
    }

    public Integer getDriverCores() {
        return driverCores;
    }

    public void setDriverCores(Integer driverCores) {
        this.driverCores = driverCores;
    }

    public boolean getSupervise() {
        return supervise;
    }

    public void setSupervise(boolean supervise) {
        this.supervise = supervise;
    }

    public String getKill() {
        return kill;
    }

    public void setKill(String kill) {
        this.kill = kill;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotalExecutorCores() {
        return totalExecutorCores;
    }

    public void setTotalExecutorCores(Integer totalExecutorCores) {
        this.totalExecutorCores = totalExecutorCores;
    }

    public Integer getExecutorCores() {
        return executorCores;
    }

    public void setExecutorCores(Integer executorCores) {
        this.executorCores = executorCores;
    }

    public Integer getYarnDriverCores() {
        return yarnDriverCores;
    }

    public void setYarnDriverCores(Integer yarnDriverCores) {
        this.yarnDriverCores = yarnDriverCores;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public Integer getNumExecutors() {
        return numExecutors;
    }

    public void setNumExecutors(Integer numExecutors) {
        this.numExecutors = numExecutors;
    }

    public List<String> getArchives() {
        return archives;
    }

    public void setArchives(List<String> archives) {
        this.archives = archives;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getKeytab() {
        return keytab;
    }

    public void setKeytab(String keytab) {
        this.keytab = keytab;
    }
}