package com.seeMovie.pojo;

public class MovieCategoryVo {
    private String movieCategoryCode;

    private String movieCategoryName;

    public String getMovieCategoryCode() {
        return movieCategoryCode;
    }

    public void setMovieCategoryCode(String movieCategoryCode) {
        this.movieCategoryCode = movieCategoryCode == null ? null : movieCategoryCode.trim();
    }

    public String getMovieCategoryName() {
        return movieCategoryName;
    }

    public void setMovieCategoryName(String movieCategoryName) {
        this.movieCategoryName = movieCategoryName == null ? null : movieCategoryName.trim();
    }
}