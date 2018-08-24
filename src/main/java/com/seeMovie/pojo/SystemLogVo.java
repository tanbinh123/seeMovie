package com.seeMovie.pojo;

import java.io.Serializable;
import java.util.Date;

public class SystemLogVo implements Serializable {
    private String logId;

    private String logName;

    private String logContent;

    private String logLevel;

    private Date logCreateDate;

    private static final long serialVersionUID = 1L;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId == null ? null : logId.trim();
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName == null ? null : logName.trim();
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent == null ? null : logContent.trim();
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel == null ? null : logLevel.trim();
    }

    public Date getLogCreateDate() {
        return logCreateDate;
    }

    public void setLogCreateDate(Date logCreateDate) {
        this.logCreateDate = logCreateDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", logId=").append(logId);
        sb.append(", logName=").append(logName);
        sb.append(", logContent=").append(logContent);
        sb.append(", logLevel=").append(logLevel);
        sb.append(", logCreateDate=").append(logCreateDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}