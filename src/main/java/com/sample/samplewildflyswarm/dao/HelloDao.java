package com.sample.samplewildflyswarm.dao;

import javax.ejb.Singleton;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.util.StringMapper;

/**
 * Created by douglas on 16/05/17.
 */
@Singleton
public class HelloDao {

    public String getFirstTenant() {
        DBI dbi = new DBI("jdbc:mysql://localhost:3306/dbdouglas",
                "root", "root");
        Handle handle = dbi.open();

        String name = handle.createQuery("select subdomain from Tenant limit :lim")
                .bind("lim", 1)
                .map(StringMapper.FIRST)
                .first();
        return name;
    }
}
