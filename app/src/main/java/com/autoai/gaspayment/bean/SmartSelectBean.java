package com.autoai.gaspayment.bean;

public class SmartSelectBean {

    //选项文字
    private String value;
    //是否选中
    private boolean isCurrentSelect;
    //类型是否是标题
    private boolean isTitleType;

    public boolean isTitleType() {
        return isTitleType;
    }

    public void setTitleType(boolean titleType) {
        isTitleType = titleType;
    }

    public SmartSelectBean(String value, boolean isCurrentSelect) {
        this.value = value;
        this.isCurrentSelect = isCurrentSelect;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isCurrentSelect() {
        return isCurrentSelect;
    }

    public void setCurrentSelect(boolean currentSelect) {
        isCurrentSelect = currentSelect;
    }
}
