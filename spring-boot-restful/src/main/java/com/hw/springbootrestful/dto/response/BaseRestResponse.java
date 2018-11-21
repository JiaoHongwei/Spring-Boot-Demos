package com.hw.springbootrestful.dto.response;


import com.hw.springbootrestful.dto.code.BaseRestCode;
import com.hw.springbootrestful.dto.constans.BaseRestConstans;

import java.io.Serializable;

/**
 * @Description
 * @Author hw
 * @Date 2018/6/5 0005
 */
public class BaseRestResponse<T> implements Serializable {
    /**
     * 业务自定义状态码
     */
    private Integer code;
    /**
     * 请求状态描述
     */
    private String msg;
    /**
     * 请求数据，对象或数组均可
     */
    private T data;
    /**
     * 全局附加数据，字段、内容不定
     */
    private Object extra;

    private BaseRestResponse(Builder<T> builder) {
        setCode(builder.code);
        setMsg(builder.msg);
        setData(builder.data);
        setExtra(builder.extra);
    }

    public static BaseRestResponse ok() {
        return new Builder(BaseRestCode.REST_OK, BaseRestConstans.REST_OK).build();
    }

    public static <T> BaseRestResponse ok(T body) {
        return new Builder<>(BaseRestCode.REST_OK, BaseRestConstans.REST_OK, body).build();
    }

    public static <T> BaseRestResponse ok(T body, Object extra) {
        return new Builder<>(BaseRestCode.REST_OK, BaseRestConstans.REST_OK, body, extra).build();
    }

    public static BaseRestResponse of(Integer code, String msg) {
        return new Builder(code, msg).build();
    }

    public static <T> BaseRestResponse of(Integer code, String msg, T body, Object extra) {
        return new Builder<>(code, msg, body, extra).build();
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }

    public BaseRestResponse(Integer code, String msg, T data, Object extra) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.extra = extra;
    }

    public BaseRestResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public BaseRestResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseRestResponse() {
    }

    /**
     * {@code BaseRestResponse} builder static inner class.
     */


    public static final class Builder<T> {
        private Integer code;
        private String msg;
        private T data;
        private Object extra;

        private Builder() {
        }

        public Builder(Integer code, String msg, T data) {
            this.code = code;
            this.msg = msg;
            this.data = data;
        }

        public Builder(Integer code, String msg, T data, Object extra) {
            this.code = code;
            this.msg = msg;
            this.data = data;
            this.extra = extra;
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
         * Returns a {@code BaseRestResponse} built from the parameters previously set.
         *
         * @return a {@code BaseRestResponse} built with parameters of this {@code BaseRestResponse.Builder}
         */
        public BaseRestResponse build() {
            return new BaseRestResponse(this);
        }
    }
}
