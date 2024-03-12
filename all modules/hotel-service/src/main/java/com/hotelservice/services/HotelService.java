package com.hotelservice.services;

import com.hotelservice.entities.Hotel;

import java.util.List;

public interface HotelService {

    Hotel saveHotel(Hotel hotel);

    List<Hotel> getAll();

    Hotel get(String id);
    Hotel updateHotel(String hotelId, Hotel hotelDetails);
    void deleteHotel(String hotelId);
}
