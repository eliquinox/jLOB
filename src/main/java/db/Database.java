package db;

import config.DatabaseConfig;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

public class Database {

    public static DSLContext database;

    public static void configure(DatabaseConfig databaseConfig) {
        database = DSL.using(
                databaseConfig.getUrl(),
                databaseConfig.getUsername(),
                databaseConfig.getPassword()
        );
    }
}
