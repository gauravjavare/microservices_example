package com.hotelservice.services.impl;

import com.hotelservice.entities.Hotel;
import com.hotelservice.exceptions.ResourceNotFoundException;
import com.hotelservice.repositories.HotelRepository;
import com.hotelservice.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepo;
    @Override
    public Hotel saveHotel(Hotel hotel) {
        String randomId = UUID.randomUUID().toString();
        hotel.setId(randomId);
        return hotelRepo.save(hotel);
    }

    @Override
    public List<Hotel> getAll() {
        return hotelRepo.findAll();
    }

    @Override
    public Hotel get(String id) {
        return hotelRepo.findById(id).orElseThrow(()->
                new ResourceNotFoundException("hotel not found with id "+id));
    }
    @Override
    public Hotel updateHotel(String hotelId, Hotel hotelDetails) {
        Optional<Hotel> hotelOptional = hotelRepo.findById(hotelId);
        if (hotelOptional.isPresent()) {
            Hotel hotelToUpdate = hotelOptional.get();
            hotelToUpdate.setName(hotelDetails.getName());
            hotelToUpdate.setLocation(hotelDetails.getLocation());
            hotelToUpdate.setAbout(hotelDetails.getAbout());
            return hotelRepo.save(hotelToUpdate);
        } else {
            return null;
        }
    }

    @Override
    public void deleteHotel(String hotelId) {
        hotelRepo.deleteById(hotelId);
    }
}
