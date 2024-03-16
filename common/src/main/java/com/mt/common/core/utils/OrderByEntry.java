package com.mt.common.core.utils;

/**
 * @author ycs
 * @since 2021/6/29 0:14
 */
public class OrderByEntry {
    private String property;
    private String order;

    public OrderByEntry() {
    }
    public OrderByEntry(String property, String order) {
        this.property = property;
        this.order = order;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return  StringUtils.toUnderScoreCase(property) + " "+order ;
    }
}
