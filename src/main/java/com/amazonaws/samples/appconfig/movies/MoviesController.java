package com.amazonaws.samples.appconfig.movies;

import com.amazonaws.samples.appconfig.utils.MovieUtils;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import com.amazonaws.samples.appconfig.utils.AppConfigUtility;
import com.amazonaws.samples.appconfig.cache.ConfigurationCache;
import com.amazonaws.samples.appconfig.model.ConfigurationKey;
import com.amazonaws.samples.appconfig.utils.HTMLBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.appconfig.AppConfigClient;
import software.amazon.awssdk.services.appconfig.model.GetConfigurationResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class MoviesController {
    private static final Logger logger = LogManager.getLogger(MoviesController.class);

    // Legacy services for transformation demonstration
    private final MovieAnalyticsService analyticsService = new MovieAnalyticsService();
    private final MovieUtilities movieUtilities = new MovieUtilities();

    /**
     * Static Movie Array containing all the list of Movies with enhanced data for transformation.
     */
    static final Movie[] PAIDMOVIES = {
        new Movie(1L, "The Matrix", 8.7, "ACTION", 1999, "A computer hacker learns about the true nature of reality"),
        new Movie(2L, "Inception", 8.8, "ACTION", 2010, "A thief who steals corporate secrets through dream-sharing technology"),
        new Movie(3L, "The Godfather", 9.2, "DRAMA", 1972, "The aging patriarch of an organized crime dynasty transfers control"),
        new Movie(4L, "Pulp Fiction", 8.9, "DRAMA", 1994, "The lives of two mob hitmen, a boxer, and others intertwine"),
        new Movie(5L, "The Dark Knight", 9.0, "ACTION", 2008, "Batman faces the Joker in this dark superhero tale"),
        new Movie(6L, "Forrest Gump", 8.8, "DRAMA", 1994, "The presidencies of Kennedy and Johnson through the eyes of an Alabama man"),
        new Movie(7L, "The Shawshank Redemption", 9.3, "DRAMA", 1994, "Two imprisoned men bond over years, finding solace and redemption"),
        new Movie(8L, "Goodfellas", 8.7, "DRAMA", 1990, "The story of Henry Hill and his life in the mob"),
        new Movie(9L, "The Silence of the Lambs", 8.6, "HORROR", 1991, "A young FBI cadet must receive help from Hannibal Lecter"),
        new Movie(10L, "Casablanca", 8.5, "ROMANCE", 1942, "A cynical American expatriate struggles to decide whether to help his former lover")
    };
    public Duration cacheItemTtl = Duration.ofSeconds(30);
    private Boolean boolEnableFeature;
    private int intItemLimit;
    AppConfigClient client;
    String clientId;
    ConfigurationCache cache;

    @Autowired
    Environment env;

    /**
     * Root endpoint to display welcome message for Movie Service Application.
     *
     * @return Welcome message
     */
    @GetMapping("/")
    public String welcome() {
        logger.info("Welcome endpoint accessed");
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>");
        html.append("<html><head><title>Movie Service Application</title>");
        html.append("<style>body{font-family:Arial,sans-serif;margin:40px;background:#f5f5f5;}");
        html.append("h1{color:#333;}.container{background:white;padding:30px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}");
        html.append("a{color:#0066cc;text-decoration:none;margin:10px 0;display:block;}a:hover{text-decoration:underline;}</style>");
        html.append("</head><body><div class='container'>");
        html.append("<h1>Welcome to Movie Service Application</h1>");
        html.append("<p>This is a Spring Boot application demonstrating AWS AppConfig integration.</p>");
        html.append("<h2>Available Endpoints:</h2>");
        html.append("<a href='movies/getMovies'>View Movies List</a>");
        html.append("<a href='movies/analytics/high-rated'>High-Rated Movies</a>");
        html.append("<a href='movies/analytics/genre/ACTION'>Movies by Genre (ACTION)</a>");
        html.append("<a href='movies/export/html'>Export Movies as HTML</a>");
        html.append("<a href='movies/analytics/summary'>Movies Summary</a>");
        html.append("</div></body></html>");
        return html.toString();
    }

    /**
     * REST API method to get all the Movies based on AWS App Config parameter.
     *
     * @return List of Movies
     */
    @GetMapping("/movies/getMovies")
    public String movie() {
        logger.info("Fetching movies from AWS App Config");
        try {


        cacheItemTtl = Duration.ofSeconds(Long.parseLong(env.getProperty("appconfig.cacheTtlInSeconds")));

        final AppConfigUtility appConfigUtility = new AppConfigUtility(getOrDefault(this::getClient, this::getDefaultClient),
                getOrDefault(this::getConfigurationCache, ConfigurationCache::new),
                getOrDefault(this::getCacheItemTtl, () -> cacheItemTtl),
                getOrDefault(this::getClientId, this::getDefaultClientId));

            final String application = env.getProperty("appconfig.application");
            final String environment = env.getProperty("appconfig.environment");
            final String config = env.getProperty("appconfig.config");
        final GetConfigurationResponse response = appConfigUtility.getConfiguration(new ConfigurationKey(application, environment, config));
        final String appConfigResponse = response.content().asUtf8String();

        final JSONObject jsonResponseObject = new JSONObject(appConfigResponse);
        System.out.println("json is "+jsonResponseObject);

        JSONArray moviesArray = jsonResponseObject.getJSONArray("movies");
        System.out.println("movies array is "+moviesArray);
        List<Movie> movieList = new ArrayList<>();
        for (int i = 0; i < moviesArray.length(); i++) {
            JSONObject movieObj = moviesArray.getJSONObject(i);
            long id = movieObj.getLong("id");
            String movieName = movieObj.getString("movieName");
            // Extract other fields as needed
            Movie movie = new Movie(id, movieName);
            movieList.add(movie);
        }
        Movie[] movies = movieList.toArray(new Movie[movieList.size()]);
        HTMLBuilder htmlBuilder = new HTMLBuilder();
        String moviesHtml = htmlBuilder.getMoviesHtml(movies);

        return moviesHtml;
        } catch (Exception e) {
            logger.error("Error fetching movies from AWS App Config", e);
            HTMLBuilder htmlBuilder = new HTMLBuilder();
            String moviesHtml = htmlBuilder.getMoviesHtml(PAIDMOVIES);
            return moviesHtml;
        }
    }

    @RequestMapping(value = "/movies/{movie}/edit", method = POST)
    public String processUpdateMovie(@Valid Movie movie, BindingResult result, @PathVariable("movieId") int movieId) {
        if (!MovieUtils.isValidMovieName(movie.getMovieName())) {
            result.rejectValue("name", "error.name", "Invalid movie name");
            return "editMovieForm";
        }
        final AppConfigUtility appConfigUtility = new AppConfigUtility(getOrDefault(this::getClient, this::getDefaultClient),
                getOrDefault(this::getConfigurationCache, ConfigurationCache::new),
                getOrDefault(this::getCacheItemTtl, () -> cacheItemTtl),
                getOrDefault(this::getClientId, this::getDefaultClientId));


        final String application = env.getProperty("appconfig.application");
        final String environment = env.getProperty("appconfig.environment");
        final String config = env.getProperty("appconfig.config");

        final GetConfigurationResponse response = appConfigUtility.updateConfiguration(new ConfigurationKey(application, environment, config),movie.toString());
        final String appConfigResponse = response.content().asUtf8String();

        final JSONObject jsonResponseObject = new JSONObject(appConfigResponse);
        System.out.println("json is "+jsonResponseObject);

        JSONArray moviesArray = jsonResponseObject.getJSONArray("movies");
        System.out.println("movies array is "+moviesArray);
        List<Movie> movieList = new ArrayList<>();
        for (int i = 0; i < moviesArray.length(); i++) {
            JSONObject movieObj = moviesArray.getJSONObject(i);
            long id = movieObj.getLong("id");
            String movieName = movieObj.getString("movieName");
            // Extract other fields as needed
            movieList.add(movie);
        }
        Movie[] movies = movieList.toArray(new Movie[movieList.size()]);
        HTMLBuilder htmlBuilder = new HTMLBuilder();
        String moviesHtml = htmlBuilder.getMoviesHtml(movies);

        return moviesHtml;

    }

    private <T> T getOrDefault(final Supplier<T> optionalGetter, final Supplier<T> defaultGetter) {
        return Optional.ofNullable(optionalGetter.get()).orElseGet(defaultGetter);
    }

    String getDefaultClientId() {
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

    /**
     * Legacy endpoint demonstrating traditional loop patterns
     * This endpoint uses legacy code patterns that should be transformed
     */
    @GetMapping("/movies/analytics/high-rated")
    public String getHighRatedMovies() {
        logger.info("Getting high-rated movies using legacy patterns");
        List<Movie> movieList = Arrays.asList(PAIDMOVIES);
        
        // Use legacy analytics service (contains transformation targets)
        List<String> highRatedTitles = analyticsService.getHighRatedMovieTitles(movieList);
        String report = analyticsService.generateMovieReport(movieList);
        
        HTMLBuilder htmlBuilder = new HTMLBuilder();
        StringBuilder html = new StringBuilder();
        html.append("<h2>High-Rated Movies (Legacy Implementation)</h2>");
        html.append("<ul>");
        for (String title : highRatedTitles) {
            html.append("<li>").append(title).append("</li>");
        }
        html.append("</ul>");
        html.append("<h3>Movie Report</h3>");
        html.append("<pre>").append(report).append("</pre>");
        
        return html.toString();
    }
    
    /**
     * Legacy endpoint demonstrating genre analysis with traditional patterns
     */
    @GetMapping("/movies/analytics/genre/{genre}")
    public String getMoviesByGenre(@PathVariable String genre) {
        logger.info("Analyzing movies by genre: " + genre);
        List<Movie> movieList = Arrays.asList(PAIDMOVIES);
        
        // Use legacy analytics methods
        int count = analyticsService.countMoviesByGenre(movieList, genre.toUpperCase());
        String genreDescription = analyticsService.getGenreDescription(genre.toUpperCase());
        List<Movie> sortedMovies = analyticsService.sortMoviesByRating(movieList);
        
        StringBuilder html = new StringBuilder();
        html.append("<h2>Genre Analysis: ").append(genre).append("</h2>");
        html.append("<p>Description: ").append(genreDescription).append("</p>");
        html.append("<p>Count: ").append(count).append(" movies</p>");
        html.append("<h3>All Movies Sorted by Rating (Legacy Sort)</h3>");
        html.append("<ul>");
        for (Movie movie : sortedMovies) {
            html.append("<li>").append(movie.getTitle())
                .append(" (").append(movie.getRating()).append(")</li>");
        }
        html.append("</ul>");
        
        return html.toString();
    }
    
    /**
     * Legacy endpoint demonstrating file operations and string building
     */
    @GetMapping("/movies/export/html")
    public String exportMoviesAsHtml() {
        logger.info("Exporting movies as HTML using legacy patterns");
        List<Movie> movieList = Arrays.asList(PAIDMOVIES);
        
        StringBuilder result = new StringBuilder();
        result.append("<h2>Movie Export (Legacy Implementation)</h2>");
        
        // Use legacy utility methods
        for (Movie movie : movieList) {
            String movieHtml = movieUtilities.generateMovieHtml(movie);
            result.append(movieHtml);
        }
        
        // Demonstrate legacy validation patterns
        List<String> validationErrors = movieUtilities.validateMovieData(movieList);
        if (!validationErrors.isEmpty()) {
            result.append("<h3>Validation Errors</h3><ul>");
            for (String error : validationErrors) {
                result.append("<li>").append(error).append("</li>");
            }
            result.append("</ul>");
        }
        
        return result.toString();
    }
    
    /**
     * Legacy endpoint demonstrating complex data processing
     */
    @GetMapping("/movies/analytics/summary")
    public String getMoviesSummary() {
        logger.info("Generating movies summary using legacy patterns");
        List<Movie> movieList = Arrays.asList(PAIDMOVIES);
        
        // Use multiple legacy methods
        Map<String, Double> averagesByGenre = analyticsService.getAverageRatingByGenre(movieList);
        List<String> similarMovies = analyticsService.findMoviesWithSimilarRatings(movieList, 8.5, 0.3);
        String movieListString = movieUtilities.createMovieList(movieList);
        
        StringBuilder html = new StringBuilder();
        html.append("<h2>Movies Summary (Legacy Implementation)</h2>");
        
        html.append("<h3>Average Ratings by Genre</h3><ul>");
        for (Map.Entry<String, Double> entry : averagesByGenre.entrySet()) {
            html.append("<li>").append(entry.getKey()).append(": ")
                .append(String.format("%.2f", entry.getValue())).append("</li>");
        }
        html.append("</ul>");
        
        html.append("<h3>Movies with Similar Ratings to 8.5</h3><ul>");
        for (String title : similarMovies) {
            html.append("<li>").append(title).append("</li>");
        }
        html.append("</ul>");
        
        html.append("<h3>All Movies List</h3>");
        html.append("<p>").append(movieListString).append("</p>");
        
        return html.toString();
    }

}