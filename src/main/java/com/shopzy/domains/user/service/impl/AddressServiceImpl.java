package com.shopzy.domains.user.service.impl;

import com.shopzy.domains.user.model.Address;
import com.shopzy.domains.user.repository.AddressRepository;
import com.shopzy.domains.user.service.AddressService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public Address getAddressById(Long id) {
        return addressRepository.findById(id).orElseThrow();
    }

    @Override
    public Address updateAddress(Long id, Address address) {
        Address existing = addressRepository.findById(id).orElseThrow();
        existing.setStreet(address.getStreet());
        existing.setCity(address.getCity());
        existing.setState(address.getState());
        existing.setZipCode(address.getZipCode());
        existing.setCountry(address.getCountry());
        return addressRepository.save(existing);
    }

    @Override
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}
