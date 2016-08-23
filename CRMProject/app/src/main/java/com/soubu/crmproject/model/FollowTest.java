package com.soubu.crmproject.model;

/**
 * Created by dingsigang on 16-8-23.
 */
public class FollowTest {
    public static final int STATE_COMPLETE = 1;
    public static final int STATE_NOT_COMPLETE = 0;


    long time;
    String title;
    String content;
    int result;
    int followState;
    boolean needRecord;

    public int getFollowState() {
        return followState;
    }

    public void setFollowState(int followState) {
        this.followState = followState;
    }

    public boolean isNeedRecord() {
        return needRecord;
    }

    public void setNeedRecord(boolean needRecord) {
        this.needRecord = needRecord;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
