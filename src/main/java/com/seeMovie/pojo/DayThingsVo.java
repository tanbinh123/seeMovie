package com.seeMovie.pojo;

import java.io.Serializable;

public class DayThingsVo implements Serializable {
    private String id;

    private String dayId;

    private String dayThings;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDayId() {
        return dayId;
    }

    public void setDayId(String dayId) {
        this.dayId = dayId == null ? null : dayId.trim();
    }

    public String getDayThings() {
        return dayThings;
    }

    public void setDayThings(String dayThings) {
        this.dayThings = dayThings == null ? null : dayThings.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", dayId=").append(dayId);
        sb.append(", dayThings=").append(dayThings);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}