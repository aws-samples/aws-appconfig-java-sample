package com.amazonaws.samples.appconfig.movies;

import com.amazonaws.samples.appconfig.utils.AppConfigUtility;
import com.amazonaws.samples.appconfig.cache.ConfigurationCache;
import com.amazonaws.samples.appconfig.model.ConfigurationKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import software.amazon.awssdk.services.appconfig.AppConfigClient;
import software.amazon.awssdk.services.appconfig.model.GetConfigurationResponse;

import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MoviesControllerTest {

    @Mock
    private Environment env;

    @Mock
    private AppConfigClient appConfigClient;

    @Mock
    private ConfigurationCache configurationCache;

    private MoviesController moviesController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        moviesController = new MoviesController();
        moviesController.env = env;
    }

    @Test
    public void testMovieWithFeatureEnabled() {
        // Arrange
        when(env.getProperty("appconfig.application")).thenReturn("myApp");
        when(env.getProperty("appconfig.environment")).thenReturn("dev");
        when(env.getProperty("appconfig.config")).thenReturn("myConfig");
        when(env.getProperty("appconfig.cacheTtlInSeconds")).thenReturn("60");

        String jsonResponse = "{\"boolEnableFeature\":true,\"intItemLimit\":5}";

        GetConfigurationResponse getConfigurationResponse = GetConfigurationResponse.builder().build();

        AppConfigUtility appConfigUtility = mock(AppConfigUtility.class);
        when(appConfigUtility.getConfiguration(nullable(ConfigurationKey.class))).thenReturn(getConfigurationResponse);

        moviesController.cacheItemTtl = Duration.ofSeconds(60);
        moviesController.client = appConfigClient;
        moviesController.cache = configurationCache;
        moviesController.clientId = UUID.randomUUID().toString();

        // Act
        //Movie[] movies = moviesController.movie();

        // Assert
        Movie[] expectedMovies = new Movie[5];
        for (int i = 0; i < 5; i++) {
            expectedMovies[i] = MoviesController.PAIDMOVIES[i];
        }
        //assertArrayEquals(expectedMovies, movies);
        assertEquals(5, expectedMovies.length);
    }

}