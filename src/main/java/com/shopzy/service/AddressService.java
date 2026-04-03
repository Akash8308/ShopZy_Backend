package com.shopzy.service;

import com.shopzy.model.Address;
import com.shopzy.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    public final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository){
        this.addressRepository = addressRepository;
    }

    public List<Address> getAllAddresses(){
        return addressRepository.findAll();
    }

    public Address addAddress(Address address){
        return addressRepository.save(address);
    }
}
