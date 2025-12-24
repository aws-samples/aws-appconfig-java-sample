package com.amazonaws.samples.appconfig.movies;

import java.util.*;
import java.io.*;

/**
 * Utility class with legacy patterns for file handling and data processing.
 */
public class MovieUtilities {
    
    /**
     * Legacy pattern: Raw types usage
     * Should be transformed to: Proper generics
     */
    @SuppressWarnings("rawtypes")
    public List processMovieData(List movies) {
        List results = new ArrayList();
        for (Object movie : movies) {
            // Process movie data
            if (movie instanceof Movie) {
                Movie m = (Movie) movie;
                if (m.getRating() > 3.0) {
                    results.add(m);
                }
            }
        }
        return results;
    }
    
    /**
     * Legacy pattern: Try-catch without try-with-resources
     * Should be transformed to: Try-with-resources
     */
    public String readMovieDataFromFile(String filename) {
        FileReader reader = null;
        BufferedReader bufferedReader = null;
        try {
            reader = new FileReader(filename);
            bufferedReader = new BufferedReader(reader);
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            return content.toString();
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + filename, e);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    System.err.println("Error closing BufferedReader: " + e.getMessage());
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("Error closing FileReader: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Legacy pattern: Multi-line string concatenation
     * Should be transformed to: Text blocks (Java 15+)
     */
    public String generateMovieHtml(Movie movie) {
        return "<div class=\"movie\">\n" +
               "  <div class=\"header\">\n" +
               "    <h2>" + movie.getTitle() + "</h2>\n" +
               "    <span class=\"rating\">★ " + movie.getRating() + "</span>\n" +
               "  </div>\n" +
               "  <div class=\"content\">\n" +
               "    <p class=\"genre\">Genre: " + movie.getGenre() + "</p>\n" +
               "    <p class=\"description\">" + movie.getDescription() + "</p>\n" +
               "  </div>\n" +
               "</div>";
    }
    
    /**
     * Legacy pattern: Manual JSON-like string building
     * Should be transformed to: Text blocks with formatting
     */
    public String generateMovieJson(Movie movie) {
        return "{\n" +
               "  \"title\": \"" + movie.getTitle() + "\",\n" +
               "  \"rating\": " + movie.getRating() + ",\n" +
               "  \"genre\": \"" + movie.getGenre() + "\",\n" +
               "  \"description\": \"" + movie.getDescription() + "\",\n" +
               "  \"year\": " + movie.getYear() + "\n" +
               "}";
    }
    
    /**
     * Legacy pattern: Imperative style data validation
     * Should be transformed to: Functional validation with streams
     */
    public List<String> validateMovieData(List<Movie> movies) {
        List<String> errors = new ArrayList<>();
        
        for (Movie movie : movies) {
            if (movie.getTitle() == null || movie.getTitle().trim().isEmpty()) {
                errors.add("Movie title cannot be empty");
            }
            if (movie.getRating() < 0.0 || movie.getRating() > 10.0) {
                errors.add("Movie rating must be between 0.0 and 10.0: " + movie.getTitle());
            }
            if (movie.getGenre() == null || movie.getGenre().trim().isEmpty()) {
                errors.add("Movie genre cannot be empty: " + movie.getTitle());
            }
            if (movie.getYear() < 1900 || movie.getYear() > 2030) {
                errors.add("Movie year seems invalid: " + movie.getTitle());
            }
        }
        
        return errors;
    }
    
    /**
     * Legacy pattern: Manual file writing with verbose exception handling
     * Should be transformed to: Modern file operations
     */
    public void writeMovieDataToFile(List<Movie> movies, String filename) {
        FileWriter writer = null;
        PrintWriter printWriter = null;
        
        try {
            writer = new FileWriter(filename);
            printWriter = new PrintWriter(writer);
            
            for (Movie movie : movies) {
                printWriter.println(movie.getTitle() + "," + 
                                  movie.getRating() + "," + 
                                  movie.getGenre() + "," + 
                                  movie.getYear());
            }
            
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + filename, e);
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println("Error closing FileWriter: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Legacy pattern: String concatenation in loops
     * Should be transformed to: StringBuilder or String.join
     */
    public String createMovieList(List<Movie> movies) {
        String result = "";
        for (Movie movie : movies) {
            result += movie.getTitle() + " (" + movie.getYear() + "), ";
        }
        // Remove trailing comma and space
        if (result.length() > 2) {
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }
}