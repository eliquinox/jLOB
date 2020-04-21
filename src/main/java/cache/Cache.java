package cache;

import config.RedisConfig;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import state.LimitOrderBook;

public class Cache {

    public static RedissonClient cache;
    private static RBucket<LimitOrderBook> limitOrderBookBucket;

    public static void configure(RedisConfig redisConfig) {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + redisConfig.getHost() + ":" + redisConfig.getPort());

        cache = Redisson.create(config);
        limitOrderBookBucket = cache.getBucket("book");
    }

    public static boolean isConfigured() {
        return cache != null && limitOrderBookBucket != null;
    }

    public static void cacheLimitOrderBook(LimitOrderBook limitOrderBook) {
        limitOrderBookBucket.set(limitOrderBook);
    }

    public static LimitOrderBook getLimitOrderBook() {
        return limitOrderBookBucket.get();
    }

    public static boolean bookKeyExists() {
        return isConfigured() && limitOrderBookBucket.isExists();
    }
}
