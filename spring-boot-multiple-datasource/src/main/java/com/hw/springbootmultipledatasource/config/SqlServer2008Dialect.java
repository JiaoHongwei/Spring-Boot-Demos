package com.hw.springbootmultipledatasource.config;

import org.hibernate.dialect.SQLServer2008Dialect;

/**
 * @Description TODO
 * @Author hw
 * @Date 2018/11/21 11:20
 * @Version 1.0
 */
public class SqlServer2008Dialect extends SQLServer2008Dialect {

    public SqlServer2008Dialect() {
        super();
        registerHibernateType(1, "string");
        registerHibernateType(-9, "string");
        registerHibernateType(-16, "string");
        registerHibernateType(3, "double");
    }
}
