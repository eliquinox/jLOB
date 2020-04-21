package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.inject.Inject;

import java.io.File;
import java.io.IOException;


public class Config {

    private String protocol;
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
        this.protocol = config.getProtocol();
        this.database = config.getDatabaseConfig();
        this.redis = config.getRedisConfig();
    }

    public String getProtocol() {
        return protocol;
    }

    public DatabaseConfig getDatabaseConfig() {
        return database;
    }

    public RedisConfig getRedisConfig() {
        return redis;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setDatabase(DatabaseConfig database) {
        this.database = database;
    }

    public void setRedis(RedisConfig redis) {
        this.redis = redis;
    }
}
