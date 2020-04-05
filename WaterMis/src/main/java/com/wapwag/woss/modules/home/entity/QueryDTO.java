package com.wapwag.woss.modules.home.entity;

/**
 * guoln
 * 2018-12-28
 */
public class QueryDTO {

    private int pageNumber;
    private int pageSize;

    private String keyword;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
