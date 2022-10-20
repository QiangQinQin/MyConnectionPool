package com.tulun;


import com.tulun.datasource.PoolDataSource;
import com.tulun.pool.ConnectionPoolImpl;
//import org.junit.Test;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 描述:
 */
public class PoolTest {
    public static void main(String[] args) throws Exception {
        Properties pro = new Properties();
        pro.load(new FileInputStream("src/test/resources/jdbc.properties"));

        String driverName = pro.getProperty("driverName");
        String url = pro.getProperty("url");
        String username = pro.getProperty("username");
        String userpwd = pro.getProperty("userpwd");
        Integer initPoolSize = Integer.parseInt(pro.getProperty("initPoolSize"));
        Integer maxIdleTime = Integer.parseInt(pro.getProperty("maxIdleTime"));
        Integer maxPoolSize = Integer.parseInt(pro.getProperty("maxPoolSize"));
        PoolDataSource poolDataSource = new PoolDataSource(driverName, url, username, userpwd, initPoolSize, maxIdleTime, maxPoolSize);
        ConnectionPoolImpl connectionPool = new ConnectionPoolImpl(poolDataSource);


        for (int i = 0; i < 170; ++i) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(100);
                        Connection conn = connectionPool.getConnection();

                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
//
//    @Test
//    public void testjdbc() throws Exception {
//    }
}
