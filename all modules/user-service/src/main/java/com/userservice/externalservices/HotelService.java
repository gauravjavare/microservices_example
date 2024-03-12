package com.userservice.externalservices;

import com.userservice.payload.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="HOTEL-SERVICE")
public interface HotelService {
    @GetMapping("/hotels/{hotelId}")
     Hotel getHotel(@PathVariable String hotelId);



}
