package com.amazonaws.samples.appconfig.cache;

import com.amazonaws.samples.appconfig.model.ConfigurationKey;
import software.amazon.awssdk.services.appconfig.model.GetConfigurationResponse;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigurationCache {
    private final ConcurrentHashMap<ConfigurationKey, ConfigurationCacheItem<GetConfigurationResponse>> cache
            = new ConcurrentHashMap<>();

    public ConfigurationCacheItem<GetConfigurationResponse> get(final ConfigurationKey key) {
        return cache.get(key);
    }

    public void put(final ConfigurationKey key, final ConfigurationCacheItem<GetConfigurationResponse> value) {
        cache.put(key, value);
    }

    public Set<Map.Entry<ConfigurationKey, ConfigurationCacheItem<GetConfigurationResponse>>> entrySet() {
        return cache.entrySet();
    }
}
