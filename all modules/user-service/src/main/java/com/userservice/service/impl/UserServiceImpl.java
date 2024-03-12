package com.userservice.service.impl;

import com.userservice.payload.Hotel;
import com.userservice.payload.Rating;
import com.userservice.exceptions.ResourceNotFoundException;
import com.userservice.entities.User;
import com.userservice.externalservices.HotelService;
import com.userservice.repositories.UserRepository;
import com.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HotelService hotelService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public User saveUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepo.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    @Override
    public User getUser(String userId) {
        User user = userRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found on server with given id: " + userId));
        // here we fetch all the ratings fo a user by userId
        Rating[] ratingsByUser = restTemplate.getForObject
                ("http://RATING-SERVICE/ratings/user/" + user.getUserId(), Rating[].class);
        List<Rating> ratings = Arrays.stream(ratingsByUser).collect(Collectors.toList());
        // here we fetch hotels for the particular ratings
        ratings.stream().map(rating -> {
            //ResponseEntity<Hotel> forEntity = restTemplate.getForEntity
            // ("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);
            //Hotel hotel = forEntity.getBody();
            Hotel hotel = hotelService.getHotel(rating.getHotelId());
            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());
        user.setRatings(ratings);
        return user;
    }

    @Override
    public User updateUser(String userId, User userDetails) {
        User user = userRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("user not found with id " + userId));
        // Update the user details
       user.setName(userDetails.getName());
       user.setEmail(userDetails.getEmail());
       user.setAbout(userDetails.getAbout());
       return user;
    }

    @Override
    public void deleteUser(String userId) {
        userRepo.deleteById(userId);
    }
}
