package com.amazonaws.samples.appconfig.movies;

import java.util.*;

/**
 * Service class demonstrating legacy patterns that need modernization.
 * This class contains intentionally outdated code patterns for workshop transformation.
 */
public class MovieAnalyticsService {
    
    /**
     * Legacy pattern: Traditional loops for filtering and mapping
     * Should be transformed to: Stream API with filter and map
     */
    public List<String> getHighRatedMovieTitles(List<Movie> movies) {
        List<String> result = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getRating() > 4.0) {
                result.add(movie.getTitle().toUpperCase());
            }
        }
        return result;
    }
    
    /**
     * Legacy pattern: Manual counting with loops
     * Should be transformed to: Stream count with filter
     */
    public int countMoviesByGenre(List<Movie> movies, String genre) {
        int count = 0;
        for (Movie movie : movies) {
            if (movie.getGenre().equals(genre)) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Legacy pattern: StringBuffer instead of StringBuilder
     * Should be transformed to: StringBuilder or Stream joining
     */
    public String generateMovieReport(List<Movie> movies) {
        StringBuffer report = new StringBuffer();
        for (Movie movie : movies) {
            report.append("Title: ").append(movie.getTitle()).append("\n");
            report.append("Rating: ").append(movie.getRating()).append("\n");
            report.append("Genre: ").append(movie.getGenre()).append("\n");
            report.append("---\n");
        }
        return report.toString();
    }
    
    /**
     * Legacy pattern: Anonymous class instead of lambda
     * Should be transformed to: Lambda expression or method reference
     */
    public List<Movie> sortMoviesByRating(List<Movie> movies) {
        List<Movie> sortedMovies = new ArrayList<>(movies);
        Collections.sort(sortedMovies, new Comparator<Movie>() {
            @Override
            public int compare(Movie m1, Movie m2) {
                return Double.compare(m2.getRating(), m1.getRating());
            }
        });
        return sortedMovies;
    }
    
    /**
     * Legacy pattern: Manual null checking instead of Optional
     * Should be transformed to: Optional usage
     */
    public String getMovieDescription(Movie movie) {
        if (movie != null && movie.getDescription() != null) {
            return movie.getDescription();
        } else {
            return "No description available";
        }
    }
    
    /**
     * Legacy pattern: Traditional switch statement
     * Should be transformed to: Switch expression (Java 14+)
     */
    public String getGenreDescription(String genre) {
        String description;
        switch (genre) {
            case "ACTION":
                description = "High-energy entertainment with thrilling sequences";
                break;
            case "COMEDY":
                description = "Humorous and light-hearted entertainment";
                break;
            case "DRAMA":
                description = "Serious and thought-provoking storytelling";
                break;
            case "HORROR":
                description = "Suspenseful and frightening entertainment";
                break;
            case "ROMANCE":
                description = "Love stories and romantic relationships";
                break;
            default:
                description = "General entertainment";
                break;
        }
        return description;
    }
    
    /**
     * Legacy pattern: Multiple loops for complex operations
     * Should be transformed to: Single stream with multiple operations
     */
    public Map<String, Double> getAverageRatingByGenre(List<Movie> movies) {
        Map<String, List<Double>> genreRatings = new HashMap<>();
        
        // First loop: Group ratings by genre
        for (Movie movie : movies) {
            String genre = movie.getGenre();
            if (!genreRatings.containsKey(genre)) {
                genreRatings.put(genre, new ArrayList<>());
            }
            genreRatings.get(genre).add(movie.getRating());
        }
        
        // Second loop: Calculate averages
        Map<String, Double> averages = new HashMap<>();
        for (Map.Entry<String, List<Double>> entry : genreRatings.entrySet()) {
            List<Double> ratings = entry.getValue();
            double sum = 0.0;
            for (Double rating : ratings) {
                sum += rating;
            }
            averages.put(entry.getKey(), sum / ratings.size());
        }
        
        return averages;
    }
    
    /**
     * Legacy pattern: Nested loops for finding relationships
     * Should be transformed to: Stream operations with flatMap
     */
    public List<String> findMoviesWithSimilarRatings(List<Movie> movies, double targetRating, double tolerance) {
        List<String> similarMovies = new ArrayList<>();
        for (Movie movie : movies) {
            for (Movie otherMovie : movies) {
                if (!movie.equals(otherMovie)) {
                    double ratingDiff = Math.abs(movie.getRating() - otherMovie.getRating());
                    if (ratingDiff <= tolerance && Math.abs(movie.getRating() - targetRating) <= tolerance) {
                        if (!similarMovies.contains(movie.getTitle())) {
                            similarMovies.add(movie.getTitle());
                        }
                    }
                }
            }
        }
        return similarMovies;
    }
}