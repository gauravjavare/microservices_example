package com.ratings.services.impl;

import com.ratings.entities.Rating;
import com.ratings.repositories.RatingRepository;
import com.ratings.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RatingServiceImpl implements RatingService {
    @Autowired
    private RatingRepository ratingRepo;
    @Override
    public Rating create(Rating rating) {
        String randomId = UUID.randomUUID().toString();
        rating.setRatingId(randomId);
        return ratingRepo.save(rating);
    }

    @Override
    public List<Rating> getAllRatings() {
        return ratingRepo.findAll();
    }

    @Override
    public List<Rating> getRatingByUserId(String userId) {
        return ratingRepo.findByUserId(userId);
    }

    @Override
    public List<Rating> getRatingByHotelId(String hotelId) {
        return ratingRepo.findByHotelId(hotelId);
    }
    @Override
    public Rating updateRating(String ratingId, Rating ratingDetails) {
        Optional<Rating> ratingOptional = ratingRepo.findById(ratingId);
        if (ratingOptional.isPresent()) {
            Rating ratingToUpdate = ratingOptional.get();
            ratingToUpdate.setUserId(ratingDetails.getUserId());
            ratingToUpdate.setHotelId(ratingDetails.getHotelId());
            ratingToUpdate.setRating(ratingDetails.getRating());
            ratingToUpdate.setFeedback(ratingDetails.getFeedback());
            return ratingRepo.save(ratingToUpdate);
        } else {
            return null;
        }
    }

    @Override
    public void deleteRating(String ratingId) {
        ratingRepo.deleteById(ratingId);
    }
}
