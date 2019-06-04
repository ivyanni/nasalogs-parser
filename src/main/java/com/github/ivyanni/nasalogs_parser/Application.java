package com.github.ivyanni.nasalogs_parser;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.ivyanni.nasalogs_parser.Constants.*;
import static org.apache.spark.sql.functions.*;

/**
 * @author Ilia Vianni on 03.06.2019.
 */
public class Application {
    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException(ARG_EXCEPTION_MESSAGE);
        }

        // Create Spark session and read input data
        SparkSession session = SparkSession.builder().master("local").appName(APP_NAME).getOrCreate();
        JavaSparkContext jsc = new JavaSparkContext(session.sparkContext());
        JavaRDD<LogEntry> input = jsc.textFile(HDFS_URL + args[0])
                .map(Application::convertStringToLogEntry)
                .filter(Objects::nonNull);
        Dataset<Row> dataSet = session.createDataFrame(input, LogEntry.class);

        // Task 1
        dataSet.filter(col("returnCode").between(500, 599))
                .groupBy("request")
                .count()
                .select("request", "count")
                .sort(desc("count"))
                .coalesce(1)
                .toJavaRDD()
                .saveAsTextFile(HDFS_URL + OUTPUT1_FOLDER_NAME);

        // Task 2
        dataSet.groupBy("method", "returnCode", "date")
                .count()
                .filter(col("count").geq(10))
                .select("date", "method", "returnCode", "count")
                .sort("date")
                .coalesce(1)
                .toJavaRDD()
                .saveAsTextFile(HDFS_URL + OUTPUT2_FOLDER_NAME);

        // Task 3
        dataSet.filter(col("returnCode").between(400, 599))
                .groupBy(window(to_date(col("date"), OUTPUT_DATE_FORMAT), "1 week", "1 day"))
                .count()
                .select(date_format(col("window.start"), OUTPUT_DATE_FORMAT),
                        date_format(col("window.end"), OUTPUT_DATE_FORMAT),
                        col("count"))
                .sort("window.start")
                .coalesce(1)
                .toJavaRDD()
                .saveAsTextFile(HDFS_URL + OUTPUT3_FOLDER_NAME);
    }

    private static LogEntry convertStringToLogEntry(String str) {
        Pattern logPattern = Pattern.compile(PATTERN);
        Matcher matcher = logPattern.matcher(str);
        return matcher.find() ?
                new LogEntry(matcher.group(6), matcher.group(5), matcher.group(8), formatDate(matcher.group(4))) : null;
    }

    private static String formatDate(String datetime) {
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern(INPUT_DATE_FORMAT, Locale.US);
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern(OUTPUT_DATE_FORMAT, Locale.US);
        return LocalDate.parse(datetime, inputFormat).format(outputFormat);
    }
}
