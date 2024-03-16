package com.mt.common.core.web.base;


import com.mt.common.core.utils.OrderByEntry;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageDTO {

    protected Long currentPage;

    protected Long pageSize;

    private long startIndex;

    private Map<String, Object> filters;

    private Map<String, Object> rawFilters;

    private Map<String, String> filterTypes;


    private List<OrderByEntry> orderBys;

    public Long getCurrentPage() {

        return currentPage;
    }

    public void setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Map<String, Object> getFilters() {
        return filters;
    }


    public void setFilters(Map<String, Object> filters) {
        this.filters = filters;
        this.rawFilters = new HashMap<>();
        this.rawFilters.putAll(filters);
        this.filterTypes = new HashMap<>();
        for (String key : filters.keySet()) {
            if (this.filters.get(key) == null) continue;
            String value = this.filters.get(key).toString();
            //TODO:value 防止sql注入
            if (StringUtils.contains(value, "$|$")) {
                this.filters.put(key, StringUtils.split(value, "$|$"));
                this.filterTypes.put(key, "数组");
            } else if (StringUtils.contains(value, "*")) {
                this.filters.put(key, StringUtils.replace(value, "*", "%"));
                this.filterTypes.put(key, "模糊");
            } else {
                this.filterTypes.put(key, "单值");
            }
        }
    }

    public void addFilter(String name, Object value) {
        this.rawFilters.put(name, value);
        this.setFilters(this.rawFilters);
    }

    public Map<String, String> getFilterTypes() {
        return filterTypes;
    }

    public void setFilterTypes(Map<String, String> filterTypes) {
        this.filterTypes = filterTypes;
    }

    public List<OrderByEntry> getOrderBys() {
        return orderBys;
    }

    public void setOrderBys(List<OrderByEntry> orderBys) {
        this.orderBys = orderBys;
    }

    public long getStartIndex() {
        return this.startIndex;
    }

    public void setStartIndex(long startIndex) {
        this.startIndex = startIndex;
    }

    public void addOrderBy(String property, String order) {
        if(this.orderBys == null )
        {
            this.orderBys = new ArrayList();
        }
        this.orderBys.add(new OrderByEntry(property,order));
    }

    public static PageDTO create(Map<String, Object> filters) {
        PageDTO pageDTO = new PageDTO();
        pageDTO.setFilters(filters);
        return pageDTO;
    }

    public static PageDTO create(String field, Object value) {
        Map<String, Object> filters = new HashMap<>();
        filters.put(field, value);
        return create(filters);
    }
}
