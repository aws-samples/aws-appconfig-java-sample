package com.amazonaws.samples.appconfig.movies;

public class Movie {

    private long id;
    private final String movieName;
    private double rating;
    private String genre;
    private int year;
    private String description;

    public Movie(Long id, String movieName) {
        this.id = id;
        this.movieName = movieName;
        this.rating = 0.0;
        this.genre = "UNKNOWN";
        this.year = 2000;
        this.description = "No description available";
    }

    public Movie(Long id, String movieName, double rating, String genre, int year, String description) {
        this.id = id;
        this.movieName = movieName;
        this.rating = rating;
        this.genre = genre;
        this.year = year;
        this.description = description;
    }

    public long getId() {
        return this.id;
    }

    public long setId(int movieId){
        return this.id = movieId;
    }
    
    public String getMovieName() {
        return this.movieName;
    }
    
    // Legacy pattern: Use getTitle for consistency with workshop examples
    public String getTitle() {
        return this.movieName;
    }
    
    public double getRating() {
        return rating;
    }
    
    public void setRating(double rating) {
        this.rating = rating;
    }
    
    public String getGenre() {
        return genre;
    }
    
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    public int getYear() {
        return year;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Legacy pattern: Verbose equals method
     * Should be transformed to: Modern equals with Objects.equals
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Movie other = (Movie) obj;
        
        if (movieName == null) {
            if (other.movieName != null) {
                return false;
            }
        } else if (!movieName.equals(other.movieName)) {
            return false;
        }
        
        if (Double.doubleToLongBits(rating) != Double.doubleToLongBits(other.rating)) {
            return false;
        }
        
        if (genre == null) {
            if (other.genre != null) {
                return false;
            }
        } else if (!genre.equals(other.genre)) {
            return false;
        }
        
        if (year != other.year) {
            return false;
        }
        
        return true;
    }

    /**
     * Legacy pattern: Manual hashCode calculation
     * Should be transformed to: Objects.hash
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((movieName == null) ? 0 : movieName.hashCode());
        long temp = Double.doubleToLongBits(rating);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((genre == null) ? 0 : genre.hashCode());
        result = prime * result + year;
        return result;
    }
    
    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", movieName='" + movieName + '\'' +
                ", rating=" + rating +
                ", genre='" + genre + '\'' +
                ", year=" + year +
                ", description='" + description + '\'' +
                '}';
    }
}