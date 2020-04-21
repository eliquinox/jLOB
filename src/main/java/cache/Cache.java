package cache;

import com.google.inject.Inject;
import config.RedisConfig;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import state.LimitOrderBook;

public class Cache {

    private final RedissonClient cache;
    private final RBucket<LimitOrderBook> limitOrderBookBucket;

    @Inject
    public Cache(RedisConfig redisConfig) {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + redisConfig.getHost() + ":" + redisConfig.getPort());

        this.cache = Redisson.create(config);
        this.limitOrderBookBucket = cache.getBucket("book");
    }

    public boolean isConfigured() {
        return cache != null && limitOrderBookBucket != null;
    }

    public void cacheLimitOrderBook(LimitOrderBook limitOrderBook) {
        limitOrderBookBucket.set(limitOrderBook);
    }

    public LimitOrderBook getLimitOrderBook() {
        return limitOrderBookBucket.get();
    }

    public boolean bookKeyExists() {
        return isConfigured() && limitOrderBookBucket.isExists();
    }
}
