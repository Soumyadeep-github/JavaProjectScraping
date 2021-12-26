package com.imdbscraper.top250;

public class Movies {
    private String serialNumber;
    private String filmName;
    private String topCastMembers;
    private Integer year;
    private String imdbRating;
    private double ratingValue;

    public Movies(Object serialNumber,
                  Object filmName,
                  Object topCastMembers,
                  Object year,
                  Object imdbRating,
                  Object ratingValue) {
        this.serialNumber = (String) serialNumber;
        this.filmName = (String) filmName;
        this.topCastMembers = (String) topCastMembers;
        this.year = (Integer) year;
        this.imdbRating = (String) imdbRating;
        this.ratingValue = (double) ratingValue;
    }
//
//    public Movies(Object serial_number,
//                  Object film_name,
//                  Object top_cast_members,
//                  Object year,
//                  Object imdb_rating,
//                  Object rating_value) {
//        this.serialNumber = (String) serial_number;
//        this.filmName = (String) film_name;
//        this.topCastMembers = (String) top_cast_members;
//        this.year = (Integer) year;
//        this.imdbRating = (String) imdb_rating;
//        this.ratingValue = (double) rating_value;
//    }

    public String getSerialNumber(){
        return serialNumber;
    }

    public String getFilmName(){
        return filmName;
    }

    public String getTopCastMembers(){
        return topCastMembers;
    }

    public Integer getYear(){
        return year;
    }

    public String getImdbRating(){
        return imdbRating;
    }

    public double getRatingValue(){
        return ratingValue;
    }


}
