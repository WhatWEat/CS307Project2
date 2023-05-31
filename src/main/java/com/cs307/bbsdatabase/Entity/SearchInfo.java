package com.cs307.bbsdatabase.Entity;

import java.sql.Timestamp;
import java.util.List;

public class SearchInfo {
    private String select;
    private String value;
    private List<Timestamp> timeValue;

    public SearchInfo() {
    }

    public String getSelect() {
        return select;
    }

    public String getValue() {
        return value;
    }
    public List<Timestamp> getTimeValue() {
        return timeValue;
    }
    public void setSelect(String select) {
        this.select = select;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setTimeValue(List<Timestamp> timeValue) {
        this.timeValue = timeValue;
    }

    @Override
    public String toString() {
        return "SearchInfo{" +
            "select='" + select + '\'' +
            ", value='" + value + '\'' +
            ", timeValue=" + timeValue +
            '}';
    }
}
