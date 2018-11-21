package com.hw.springbootrestful.web.filter;

import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description 跨站请求防范
 * @Author hw
 * @Date 2018/11/21 12:11
 * @Version 1.0
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private static final String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return StringUtils.isEmpty(value) ? null : HtmlUtils.htmlEscape(value);
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        return StringUtils.isEmpty(value) ? null : HtmlUtils.htmlEscape(value);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            int length = values.length;
            String[] escapseValues = new String[length];
            for (int i = 0; i < length; i++) {
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(HtmlUtils.htmlEscape(values[i]));
                escapseValues[i] = m.replaceAll("").trim();
            }
            return escapseValues;
        }
        return super.getParameterValues(name);
    }


}
