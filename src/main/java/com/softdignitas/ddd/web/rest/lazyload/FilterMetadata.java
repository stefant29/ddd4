package com.softdignitas.ddd.web.rest.lazyload;

public class FilterMetadata {

    private Object value;
    private String matchMode;
    private String operator;

    // Getters and setters
    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
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
