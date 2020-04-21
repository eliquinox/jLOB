import cache.Cache;
import com.google.inject.Guice;
import com.google.inject.Injector;
import config.DatabaseConfig;
import config.RedisConfig;
import connectivity.ServerRunner;
import db.Database;
import db.Migrator;


public class Application {
    public static void main(String[] args)  {
        Injector injector = Guice.createInjector(new ApplicationModule(args[0]));

        configureDatabase(injector.getInstance(DatabaseConfig.class));
        configureCache(injector.getInstance(RedisConfig.class));

        injector.getInstance(Migrator.class).migrate();
        injector.getInstance(ServerRunner.class).run();
    }

    private static void configureDatabase(DatabaseConfig databaseConfig) {
        Database.configure(databaseConfig);
    }
    private static void configureCache(RedisConfig redisConfig) {
        Cache.configure(redisConfig);
    }
}
