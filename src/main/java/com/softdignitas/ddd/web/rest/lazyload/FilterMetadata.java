package com.softdignitas.ddd.web.rest.lazyload;

import java.util.LinkedHashMap;
import java.util.Map;

public class FilterMetadata {

    private String value;
    private String matchMode;
    private String operator;

    public FilterMetadata(Map<String, String> map) {
        this.value = map.get("value");
        this.matchMode = map.get("matchMode");
        this.operator = map.get("operator");
    }

    // Getters and setters
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMatchMode() {
        return matchMode;
    }

    public void setMatchMode(String matchMode) {
        this.matchMode = matchMode;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
