package com.trading.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    public static final String COINS_PAGE_CACHE = "coins_page";
    public static final String COIN_DETAIL_CACHE = "coin_detail";
    public static final String COIN_CHART_CACHE = "coin_chart";

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        
        // Cấu hình riêng cho từng cache
        cacheManager.registerCustomCache(COINS_PAGE_CACHE,
                Caffeine.newBuilder()
                        .expireAfterWrite(1, TimeUnit.MINUTES)
                        .maximumSize(500)
                        .build());

        cacheManager.registerCustomCache(COIN_DETAIL_CACHE,
                Caffeine.newBuilder()
                        .expireAfterWrite(1, TimeUnit.MINUTES)
                        .maximumSize(1000)
                        .build());

        cacheManager.registerCustomCache(COIN_CHART_CACHE,
                Caffeine.newBuilder()
                        .expireAfterWrite(5, TimeUnit.MINUTES)
                        .maximumSize(1000)
                        .build());

        return cacheManager;
    }
}
