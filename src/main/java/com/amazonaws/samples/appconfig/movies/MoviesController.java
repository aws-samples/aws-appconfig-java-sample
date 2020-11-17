package com.amazonaws.samples.appconfig.movies;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import com.amazonaws.samples.appconfig.AppConfigUtility;
import com.amazonaws.samples.appconfig.cache.ConfigurationCache;
import com.amazonaws.samples.appconfig.model.ConfigurationKey;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.appconfig.AppConfigClient;
import software.amazon.awssdk.services.appconfig.model.GetConfigurationResponse;



@RestController
public class MoviesController {

    /**
     * Static Movie Array containing all the list of Movies.
     */
    private static final Movie[] PAIDMOVIES = {
        new Movie(1L, "Paid Movie 1"),
        new Movie(2L, "Paid Movie 2"),
        new Movie(3L, "Paid Movie 3"),
        new Movie(4L, "Paid Movie 4"),
        new Movie(5L, "Paid Movie 5"),
        new Movie(6L, "Paid Movie 6"),
        new Movie(7L, "Paid Movie 7"),
        new Movie(8L, "Paid Movie 8"),
        new Movie(9L, "Paid Movie 9"),
        new Movie(10L, "Paid Movie 10")
    };
    public Duration cacheItemTtl = Duration.ofSeconds(30);
    private Boolean boolEnableFeature;
    private int intItemLimit;
    private AppConfigClient client;
    private String clientId;
    private ConfigurationCache cache;

    @Autowired
    private Environment env;

    /**
     * REST API method to get all the Movies based on AWS App Config parameter.
     *
     * @return List of Movies
     */
    @GetMapping("/movies/getMovies")
    public Movie[] movie() {

        final String application = env.getProperty("appconfig.application");
        final String environment = env.getProperty("appconfig.environment");
        final String config = env.getProperty("appconfig.config");
        cacheItemTtl = Duration.ofSeconds(Long.parseLong(env.getProperty("appconfig.cacheTtlInSeconds")));

        final AppConfigUtility appConfigUtility = new AppConfigUtility(getOrDefault(this::getClient, this::getDefaultClient),
                getOrDefault(this::getConfigurationCache, ConfigurationCache::new),
                getOrDefault(this::getCacheItemTtl, () -> cacheItemTtl),
                getOrDefault(this::getClientId, this::getDefaultClientId));

        final GetConfigurationResponse response = appConfigUtility.getConfiguration(new ConfigurationKey(application, environment, config));
        final String appConfigResponse = response.content().asUtf8String();

        final JSONObject jsonResponseObject = new JSONObject(appConfigResponse);
        boolEnableFeature = jsonResponseObject.getBoolean("boolEnableFeature");
        intItemLimit = jsonResponseObject.getInt("intItemLimit");
        final Movie[] limitedMovies = new Movie[intItemLimit];

        for (int i = 0; i < intItemLimit; i++) {
            limitedMovies[i] = PAIDMOVIES[i];
        }

        if (boolEnableFeature) {
            return limitedMovies;
        } else {
            return PAIDMOVIES;
        }
    }


    private <T> T getOrDefault(final Supplier<T> optionalGetter, final Supplier<T> defaultGetter) {
        return Optional.ofNullable(optionalGetter.get()).orElseGet(defaultGetter);
    }

    private String getDefaultClientId() {
        return UUID.randomUUID().toString();
    }

    protected AppConfigClient getDefaultClient() {
        return AppConfigClient.create();
    }

    public ConfigurationCache getConfigurationCache() {
        return cache;
    }


    public AppConfigClient getClient() {
        return client;
    }


    public Duration getCacheItemTtl() {
        return cacheItemTtl;
    }

    public String getClientId() {
        return clientId;
    }


}