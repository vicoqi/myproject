package com.vic.handleLogRegex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//2019-04-24 13:21:31,660 INFO  [ForkJoinPool.commonPool-worker-28] -
// printPath|agvCode:CARRIER_192168001004|
// cur:SZBC_XW_TQFT|
// path:pathStr:SZBC_XW_TQFT->|turningStr:SZBC_XW_TQFTSZBC_XW_TQFT->
public class HandleLog {

//    private String timeRegex = "[0-9]{4}-[0-9]{2}-[0-9]{2}";

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("/Users/wangqipeng/Documents/path.log"));
//        List<String> lines = Arrays.asList("2019-04-24 13:21:31,660 INFO  [ForkJoinPool.commonPool-worker-28] - printPath|agvCode:CARRIER_192168001004|cur:SZBC_XW_TQFT|path:pathStr:SZBC_XW_TQFT->|turningStr:SZBC_XW_TQFTSZBC_XW_TQFT->");
        HandleLog handleLog = new HandleLog();
        for(String line:lines){
            handleLog.logRegexFilter(line);
        }
    }

    public void logRegexFilter(String line){
//        line = "2019-04-24 13:21:31,660 INFO  [ForkJoinPool.commonPool-worker-28] - printPath|agvCode:CARRIER_192168001004|cur:SZBC_XW_TQFT|path:pathStr:SZBC_XW_TQFT->|turningStr:SZBC_XW_TQFTSZBC_XW_TQFT->";
        String regex = "(.*?) INFO (.*?)agvCode:(.*?)\\|cur:(.*?)\\|path:pathStr:(.*?)\\|turningStr(.*?)";
        Pattern pattern = Pattern.compile(regex);
        Matcher mat = pattern.matcher(line);
        if(mat.find()){
            System.out.println(mat.group(1));
            System.out.println(mat.group(3));
            System.out.println(mat.group(4));
            System.out.println(mat.group(5));
        }
    }
}
