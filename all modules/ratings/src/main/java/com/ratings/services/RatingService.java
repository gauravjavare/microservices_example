package com.ratings.services;

import com.ratings.entities.Rating;

import java.util.List;

public interface RatingService {

    Rating create(Rating rating);

    List<Rating> getAllRatings();

    List <Rating> getRatingByUserId(String userId);

    List <Rating> getRatingByHotelId(String hotelId);
    Rating updateRating(String ratingId, Rating ratingDetails);
    void deleteRating(String ratingId);

}
