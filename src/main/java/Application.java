import com.google.inject.Guice;
import com.google.inject.Injector;
import connectivity.ServerRunner;
import db.Migrator;


public class Application {
    public static void main(String[] args)  {
        Injector injector = Guice.createInjector(new ApplicationModule(args[0]));
        injector.getInstance(Migrator.class).migrate();
        injector.getInstance(ServerRunner.class).run();
    }
}
