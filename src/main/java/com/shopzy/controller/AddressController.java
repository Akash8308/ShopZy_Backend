package com.shopzy.controller;

import com.shopzy.model.Address;
import com.shopzy.service.AddressService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService){
        this.addressService = addressService;
    }

    @GetMapping
    public List<Address> getAll(){
        return addressService.getAllAddresses();
    }

    @PostMapping
    public Address create(@RequestBody Address address) {
        return addressService.addAddress(address);
    }

}
