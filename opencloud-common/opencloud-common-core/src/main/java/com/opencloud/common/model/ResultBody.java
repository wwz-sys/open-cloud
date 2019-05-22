package com.opencloud.common.model;


import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;
import com.opencloud.common.constants.ResultEnum;
import com.opencloud.common.utils.SpringContextHolder;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * @author admin
 */
public class ResultBody<T> implements Serializable {
    private static final long serialVersionUID = -6190689122701100762L;

    /**
     * 消息码
     */
    private int code = 0;
    /**
     * 返回消息
     */
    private String message;

    /**
     * 请求路径
     */
    private String path;

    /**
     * 返回数据
     */
    private T data;

    /**
     * http状态码
     */
    private int httpStatus;

    /**
     * 附加数据
     */
    private Map<String, Object> extra;

    /**
     * 服务器时间
     */
    private long timestamp = System.currentTimeMillis();

    @JSONField(serialize = false, deserialize = false)
    @JsonIgnore
    public boolean isOk() {
        return this.code == 0;
    }

    public ResultBody() {
        super();
    }

    public static <T> ResultBody success() {
        return new ResultBody().setCode(ResultEnum.OK.getCode());
    }

    public static <T> ResultBody success(T data) {
        return new ResultBody().setData(data).setCode(ResultEnum.OK.getCode());
    }

    public static <T> ResultBody success(String msg, T result) {
        return new ResultBody().setMessage(msg).setData(result);
    }

    public static ResultBody failed(String msg) {
        return new ResultBody().setCode(ResultEnum.FAIL.getCode()).setMessage(msg);
    }

    public static ResultBody failed() {
        return new ResultBody().setCode(ResultEnum.FAIL.getCode()).setMessage(ResultEnum.FAIL.getMessage());
    }

    public static ResultBody error() {
        return new ResultBody().setCode(ResultEnum.ERROR.getCode()).setMessage(ResultEnum.ERROR.getMessage());
    }

    public static ResultBody failed(Integer code, String msg) {
        return new ResultBody().setCode(code).setMessage(msg);
    }

    public static ResultBody failed(ResultEnum code, String msg) {
        return failed(code.getCode(), msg);
    }

    public int getCode() {
        return code;
    }

    public ResultBody setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return Optional.ofNullable(this.message).orElse(i18n(ResultEnum.getResultEnum(this.code).getMessage(), this.message));
    }

    public ResultBody setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResultBody setData(T data) {
        this.data = data;
        return this;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public ResultBody setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public ResultBody setExtra(Map<String, Object> extra) {
        this.extra = extra;
        return this;
    }

    public ResultBody putExtra(String key, Object value) {
        if (this.extra == null) {
            this.extra = Maps.newHashMap();
        }
        this.extra.put(key, value);
        return this;
    }



    public String getPath() {
        return path;
    }

    public ResultBody setPath(String path) {
        this.path = path;
        return this;
    }

    @JSONField(serialize = false, deserialize = false)
    @JsonIgnore
    public int getHttpStatus() {
        return httpStatus;
    }

    public ResultBody setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    @Override
    public String toString() {
        return "ResultBody{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", path='" + path + '\'' +
                ", data=" + data +
                ", httpStatus=" + httpStatus +
                ", extra=" + extra +
                ", timestamp=" + timestamp +
                '}';
    }

    /**
     * 国际化配置
     */
    private static Locale locale = LocaleContextHolder.getLocale();

    /**
     * 提示信息国际化
     *
     * @param message
     * @param defaultMessage
     * @return
     */
    private static String i18n(String message, String defaultMessage) {
        MessageSource messageSource = SpringContextHolder.getBean(MessageSource.class);
        return messageSource.getMessage(message, null, defaultMessage, locale);
    }
}
