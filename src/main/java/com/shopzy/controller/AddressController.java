package com.shopzy.controller;

import com.shopzy.model.Address;
import com.shopzy.service.impl.AddressServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class AddressController {

    private final AddressServiceImpl addressServiceImpl;

    public AddressController(AddressServiceImpl addressServiceImpl){
        this.addressServiceImpl = addressServiceImpl;
    }

    @GetMapping
    public List<Address> getAll(){
        return addressServiceImpl.getAllAddresses();
    }

    @PostMapping
    public Address create(@RequestBody Address address) {
        return addressServiceImpl.createAddress(address);
    }

}
