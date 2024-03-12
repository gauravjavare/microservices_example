package com.ratings.controllers;

import com.ratings.entities.Rating;
import com.ratings.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {
    @Autowired
    private RatingService ratingService;
    @PostMapping
    public ResponseEntity<?> create( @Valid @RequestBody Rating rating,BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.BAD_REQUEST);
        }
        Rating rating1 = ratingService.create(rating);
        return new ResponseEntity<>(rating1, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Rating>> getAllRatings(){
        List<Rating> allRatings = ratingService.getAllRatings();
        return new ResponseEntity<>(allRatings, HttpStatus.OK);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Rating>> getRatingByUserId(@PathVariable String userId){
        List<Rating> ratingByUserId = ratingService.getRatingByUserId(userId);
        return new ResponseEntity<>(ratingByUserId, HttpStatus.OK);


    }
    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<Rating>> getRatingByHotelId(@PathVariable String hotelId){
        List<Rating> ratingByHotelId = ratingService.getRatingByHotelId(hotelId);
        return new ResponseEntity<>(ratingByHotelId, HttpStatus.OK);

    }

    @PutMapping("/{ratingId}")
    public ResponseEntity<Rating> updateRating(@PathVariable String ratingId, @RequestBody Rating ratingDetails) {
        Rating updatedRating = ratingService.updateRating(ratingId, ratingDetails);
        if (updatedRating == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedRating, HttpStatus.OK);
    }

    @DeleteMapping("/{ratingId}")
    public ResponseEntity<?> deleteRating(@PathVariable String ratingId) {
        ratingService.deleteRating(ratingId);
        return new ResponseEntity<>("Rating is deleted",HttpStatus.OK);
    }

}
