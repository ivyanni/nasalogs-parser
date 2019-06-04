package com.github.ivyanni.nasalogs_parser;

/**
 * @author Ilia Vianni on 03.06.2019.
 */
public class Constants {
    public static final String PATTERN =
            "^(\\S+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})] \"(\\S+) (\\S+) (\\S+)\" (\\d{3}) (\\d+)";
    public static final String HDFS_URL = "hdfs://master:9000/";
    public static final String APP_NAME = "NASA Logs Parser";
    public static final String ARG_EXCEPTION_MESSAGE = "Please pass the log file's location";

    public static final String INPUT_DATE_FORMAT = "dd/MMM/yyyy:HH:mm:ss Z";
    public static final String OUTPUT_DATE_FORMAT = "dd/MMM/yyyy";

    public static final String OUTPUT1_FOLDER_NAME = "output1";
    public static final String OUTPUT2_FOLDER_NAME = "output2";
    public static final String OUTPUT3_FOLDER_NAME = "output3";
}
