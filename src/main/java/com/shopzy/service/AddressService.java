package com.shopzy.service;

import com.shopzy.model.Address;
import java.util.List;

public interface AddressService {
    Address createAddress(Address address);
    List<Address> getAllAddresses();
    Address getAddressById(Long id);
    Address updateAddress(Long id, Address address);
    void deleteAddress(Long id);
}