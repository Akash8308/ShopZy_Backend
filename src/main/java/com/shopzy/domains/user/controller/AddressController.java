package com.shopzy.domains.user.controller;

import com.shopzy.domains.user.model.Address;
import com.shopzy.domains.user.service.impl.AddressServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressServiceImpl addressServiceImpl;

    public AddressController(AddressServiceImpl addressServiceImpl) {
        this.addressServiceImpl = addressServiceImpl;
    }

    @PostMapping
    public Address createAddress(@RequestBody Address address) {
        return addressServiceImpl.createAddress(address);
    }

    @GetMapping
    public List<Address> getAllAddresses() {
        return addressServiceImpl.getAllAddresses();
    }

    @GetMapping("/{id}")
    public Address getAddressById(@PathVariable Long id) {
        return addressServiceImpl.getAddressById(id);
    }

    @PutMapping("/{id}")
    public Address updateAddress(@PathVariable Long id, @RequestBody Address address) {
        return addressServiceImpl.updateAddress(id, address);
    }

    @DeleteMapping("/{id}")
    public String deleteAddress(@PathVariable Long id) {
        addressServiceImpl.deleteAddress(id);
        return "Address deleted successfully";
    }
}
