package load;

import java.io.*;
import java.util.Properties;

import lombok.Cleanup;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.datasources.DatasourcesFraction;

/**
 * @author Bob McWhirter
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Properties prop  = loadConfig(args.length > 0 ? args[0] : "local.properties");

        Swarm swarm = new Swarm();

        // Configure the Datasources subsystem with a driver
        // and a datasource
        swarm.fraction(datasourceWithMysql(prop));

        // Start the swarm and deploy the default war
        swarm.start().deploy();
    }

    private static Properties loadConfig(String dbconfig) throws Exception {
        Properties prop = new Properties();
        @Cleanup InputStream in = new FileInputStream(new File(dbconfig));
        prop.load(in);
        return prop;
    }

    private static DatasourcesFraction datasourceWithMysql(Properties prop) {
        return new DatasourcesFraction()
                .jdbcDriver("com.mysql", (d) -> {
                    d.driverClassName("com.mysql.jdbc.Driver");
                    d.xaDatasourceClass("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
                    d.driverModuleName("com.mysql");
                })
                .dataSource("datasource", (ds) -> {
                    ds.driverName("com.mysql");
                    ds.connectionUrl(prop.getProperty("dbUrl"));
                    ds.userName(prop.getProperty("dbUser"));
                    ds.password(prop.getProperty("dbPwd"));
                });
    }

}
