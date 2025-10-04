package io.expensetracker.ExpenseTracker.config;

import io.expensetracker.ExpenseTracker.restApi.dto.Users;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@org.springframework.cache.annotation.CacheConfig
public class CacheConfig {
    public CacheManager createCacheManager() {
        CacheConfiguration<Integer, Users> cacheConfiguration = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(
                        Integer.class,     // key type for "int" userId
                        Users.class,       // value type for User entity
                        ResourcePoolsBuilder.heap(50).offheap(5, MemoryUnit.MB))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(120)))
                .build();

        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("usersById", cacheConfiguration)
                .build(true);

        return cacheManager;
    }
}
