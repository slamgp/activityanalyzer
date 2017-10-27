package com.slamgp.model;

public class CallActivity {
    protected long start;
    protected long end;

    public CallActivity(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "Call activity[" + start + ":" + end + "]";
    }
}