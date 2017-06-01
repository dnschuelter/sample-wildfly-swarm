package com.sample.samplewildflyswarm.dao;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.sql.DataSource;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.util.StringMapper;

/**
 * Created by douglas on 16/05/17.
 */
@Singleton
public class HelloDao {

    @Resource(lookup = "java:jboss/datasources/datasource")
    DataSource datasource;

    public String getFirstTenant() {
        DBI dbi = new DBI(datasource);
        Handle handle = dbi.open();

        String name = handle.createQuery("select subdomain from Tenant limit :lim")
                .bind("lim", 1)
                .map(StringMapper.FIRST)
                .first();
        return name;
    }
}
