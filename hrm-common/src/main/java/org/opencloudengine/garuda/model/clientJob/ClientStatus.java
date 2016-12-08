package org.opencloudengine.garuda.model.clientJob;

/**
 * Created by uengine on 2016. 9. 2..
 */
public class ClientStatus {

    public static String RUNNING = "RUNNING";
    public static String FAILED = "FAILED";
    public static String FINISHED = "FINISHED";
    public static String STOPPING = "STOPPING";
    public static String KILLED = "KILLED";
    public static String STANDBY = "STANDBY";
    public static String KILL_FAIL = "KILL_FAIL";

    public static String EXECUTE_FROM_REST = "rest";
    public static String EXECUTE_FROM_CONSOLE = "console";
    public static String EXECUTE_FROM_BATCH = "batch";

    public static String JOB_TYPE_HIVE = "hive";
    public static String JOB_TYPE_MR = "mr";
    public static String JOB_TYPE_PIG = "pig";
    public static String JOB_TYPE_SPARK = "spark";
    public static String JOB_TYPE_JAVA = "java";
    public static String JOB_TYPE_PYTHON = "python";
    public static String JOB_TYPE_SHELL = "shell";
    public static String JOB_TYPE_HBASE_SHELL = "hbaseShell";
    public static String JOB_TYPE_HBASE_CLASS = "hbaseClass";
    public static String JOB_TYPE_PHOENIX = "phoenix";
}
