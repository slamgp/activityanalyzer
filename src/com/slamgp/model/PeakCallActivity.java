package com.slamgp.model;

public class PeakCallActivity extends CallActivity {

    private long counter;

    public PeakCallActivity(CallActivity activity) {
        super(activity.getStart(), activity.getEnd());
        counter = 0;
    }

    public long getCounter() {
        return counter;
    }


    public void incraseCounter() {
        counter++;
    }

    public boolean containsActivity(CallActivity callActivity) {
        if (((callActivity.getStart() >= start) && (callActivity.getStart() <= end)) || ((callActivity.getEnd() >= start) && (callActivity.getEnd() <= end))
                || ((callActivity.getStart() < start) && (callActivity.end > end))) {
            if (callActivity.getStart() > start) {
                start = callActivity.getStart();
            }
            if (callActivity.getEnd() < end) {
                end = callActivity.getEnd();
            }
            incraseCounter();
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "PeakCallActivity is " + counter + " that occurred between " + start + " and " + end;
    }

}