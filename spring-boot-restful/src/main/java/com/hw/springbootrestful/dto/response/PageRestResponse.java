package com.hw.springbootrestful.dto.response;

import com.qm.products.prodmp.web.dto.code.BaseRestCode;
import com.qm.products.prodmp.web.dto.constans.BaseRestConstans;

import java.io.Serializable;

/**
 * @Description restful 分页返回结果集
 * @Author hw
 * @Date 2018/5/28
 */
public class PageRestResponse<T> extends BaseRestResponse<T> implements Serializable {

    /**
     * 当前页数
     */
    private Integer currentPage;
    /**
     * 总记录数
     */
    private Long totalRecord;
    /**
     * 总页数
     */
    private Integer totalPage;
    /**
     * 每页显示的记录数
     */
    private Integer limit;
    /**
     * 排序字段，多个字段用","隔开
     */
    private String sortColumn;
    /**
     * 排序方式  DESC降序；ASC升序
     */
    private String sortord;


    public PageRestResponse(Integer code, String msg, T data, Object extra, Integer currentPage, Long totalRecord, Integer totalPage, Integer limit, String sortColumn, String sortord) {
        super(code, msg, data, extra);
        this.currentPage = currentPage;
        this.totalRecord = totalRecord;
        this.totalPage = totalPage;
        this.limit = limit;
        this.sortColumn = sortColumn;
        this.sortord = sortord;
    }

    public PageRestResponse() {
        super(BaseRestCode.REST_OK, BaseRestConstans.REST_OK);
    }

    private PageRestResponse(Builder<T> builder) {
        setCode(builder.code);
        setMsg(builder.msg);
        setData(builder.data);
        setExtra(builder.extra);
        setCurrentPage(builder.currentPage);
        setTotalRecord(builder.totalRecord);
        setTotalPage(builder.totalPage);
        setLimit(builder.limit);
        setSortColumn(builder.sortColumn);
        setSortord(builder.sortord);
    }

    //    public static Builder newBuilder() {
//        return new Builder();
//    }
    public static Builder success() {
        return new Builder(BaseRestCode.REST_OK, BaseRestConstans.REST_OK);
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Long totalRecord) {
        this.totalRecord = totalRecord;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public String getSortord() {
        return sortord;
    }

    public void setSortord(String sortord) {
        this.sortord = sortord;
    }


    /**
     * {@code PageRestResponse} builder static inner class.
     */
    public static final class Builder<T> {
        private Integer code;
        private String msg;
        private T data;
        private Object extra;
        private Integer currentPage;
        private Long totalRecord;
        private Integer totalPage;
        private Integer limit;
        private String sortColumn;
        private String sortord;

        private Builder() {
        }

        public Builder(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        /**
         * Sets the {@code code} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code code} to set
         * @return a reference to this Builder
         */
        public Builder code(Integer val) {
            code = val;
            return this;
        }

        /**
         * Sets the {@code msg} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code msg} to set
         * @return a reference to this Builder
         */
        public Builder msg(String val) {
            msg = val;
            return this;
        }

        /**
         * Sets the {@code data} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code data} to set
         * @return a reference to this Builder
         */
        public Builder data(T val) {
            data = val;
            return this;
        }

        /**
         * Sets the {@code extra} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code extra} to set
         * @return a reference to this Builder
         */
        public Builder extra(Object val) {
            extra = val;
            return this;
        }

        /**
         * Sets the {@code currentPage} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code currentPage} to set
         * @return a reference to this Builder
         */
        public Builder currentPage(Integer val) {
            currentPage = val;
            return this;
        }

        /**
         * Sets the {@code totalRecord} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code totalRecord} to set
         * @return a reference to this Builder
         */
        public Builder totalRecord(Long val) {
            totalRecord = val;
            return this;
        }

        /**
         * Sets the {@code totalPage} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code totalPage} to set
         * @return a reference to this Builder
         */
        public Builder totalPage(Integer val) {
            totalPage = val;
            return this;
        }

        /**
         * Sets the {@code limit} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code limit} to set
         * @return a reference to this Builder
         */
        public Builder limit(Integer val) {
            limit = val;
            return this;
        }

        /**
         * Sets the {@code sortColumn} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code sortColumn} to set
         * @return a reference to this Builder
         */
        public Builder sortColumn(String val) {
            sortColumn = val;
            return this;
        }

        /**
         * Sets the {@code sortord} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code sortord} to set
         * @return a reference to this Builder
         */
        public Builder sortord(String val) {
            sortord = val;
            return this;
        }

        /**
         * Returns a {@code PageRestResponse} built from the parameters previously set.
         *
         * @return a {@code PageRestResponse} built with parameters of this {@code PageRestResponse.Builder}
         */
        public PageRestResponse build() {
            return new PageRestResponse(this);
        }
    }
}
