package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.inject.Inject;

import java.io.File;
import java.io.IOException;


public class Config {

    private DatabaseConfig database;
    private RedisConfig redis;

    @Inject
    public Config(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Config config = mapper.readValue(new File(path), Config.class);
        construct(config);
    }

    public Config() {}

    private void construct(Config config) {
        this.database = config.getDatabaseConfig();
        this.redis = config.getRedisConfig();
    }

    public DatabaseConfig getDatabaseConfig() {
        return database;
    }

    public RedisConfig getRedisConfig() {
        return redis;
    }

    public void setDatabase(DatabaseConfig database) {
        this.database = database;
    }

    public void setRedis(RedisConfig redis) {
        this.redis = redis;
    }
}
