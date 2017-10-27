package com.slamgp.service;

import com.slamgp.model.CallActivity;
import com.slamgp.model.PeakCallActivity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CallActivityAnalyzer {

    private static Logger log = Logger.getLogger(CallActivityAnalyzer.class.getName());

    private List<CallActivity> activityList;

    public CallActivityAnalyzer(String dataSource) throws IOException {
        activityList = new ArrayList<CallActivity>();
        initActivityList(dataSource);

    }

    private void initActivityList(String dataSource) throws IOException {
        activityList.clear();
        if (dataSource == null) {
            log.info("data sources undefined");
            return;
        }
        BufferedReader reader = new BufferedReader(new FileReader(dataSource));

        String currentLine;
        int counter = 1;
        log.info("Data loading started......");
        while ((currentLine = reader.readLine()) != null) {
            if (currentLine.isEmpty()) {
                continue;
            }
            String[] callActivitydatas = currentLine.split(":");
            try {
                activityList.add(new CallActivity(Long.valueOf(callActivitydatas[0]), Long.valueOf(callActivitydatas[1])));
                log.info("load line " + counter);
            } catch (NumberFormatException e) {
                log.info("In line number: " + counter + ", not correct data. String[" + currentLine + "]");
            }
            counter++;
        }
        reader.close();
        log.info("......Data loading completed");
    }

    public PeakCallActivity getPeak() {
        PeakCallActivity result;
        result = findPeak();
        return result;
    }

    private PeakCallActivity findPeak() {
        log.info("Analyze process started......");
        PeakCallActivity peack = new PeakCallActivity(activityList.get(0));
        int i = 0;
        while (i < activityList.size()) {
            PeakCallActivity currentPeack = new PeakCallActivity(activityList.get(i));
            int j;
            for (j = 0; j < activityList.size(); j++) {
                if (currentPeack.getStart() > activityList.get(j).getEnd()) {
                    continue;
                }
                if (!currentPeack.containsActivity(activityList.get(j))) {
                    if (i > j) {
                        i++;
                    } else {
                        i = j;
                    }
                    break;
                }
            }
            if (j == activityList.size()) {
                i++;
            }
            if (currentPeack.getCounter() > peack.getCounter()) {
                peack = currentPeack;
            }
        }
        log.info("Analyze process complited......");
        return peack;
    }

}
