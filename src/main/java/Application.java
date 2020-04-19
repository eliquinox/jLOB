import com.google.inject.Guice;
import com.google.inject.Injector;
import config.DatabaseConfig;
import connectivity.ServerRunner;
import db.Database;
import db.Migrator;


public class Application {
    public static void main(String[] args)  {
        Injector injector = Guice.createInjector(new ApplicationModule(args[0]));
        configureDatabase(injector.getInstance(DatabaseConfig.class));
        injector.getInstance(Migrator.class).migrate();
        injector.getInstance(ServerRunner.class).run();
    }

    private static void configureDatabase(DatabaseConfig databaseConfig) {
        Database.configure(databaseConfig);
    }
}
