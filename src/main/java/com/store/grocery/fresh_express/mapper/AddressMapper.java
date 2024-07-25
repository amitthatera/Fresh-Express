package com.store.grocery.fresh_express.mapper;

import com.store.grocery.fresh_express.dto.AddressDTO;
import com.store.grocery.fresh_express.model.Address;
import com.store.grocery.fresh_express.shared.kernel.Mapper;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper implements Mapper<Address, AddressDTO> {

    @Override
    public Address mapToEntity(AddressDTO dto) {
        return Address.builder()
                .houseNumber(dto.houseNumber())
                .street(dto.street())
                .landmark(dto.landmark())
                .city(dto.city())
                .state(dto.state())
                .postalCode(dto.postalCode())
                .build();
    }

    @Override
    public AddressDTO mapToDTO(Address entity) {
        return new AddressDTO(entity.getAddressId(), entity.getHouseNumber(), entity.getStreet(), entity.getLandmark(), entity.getCity(),
                entity.getState(), entity.getPostalCode());
    }
}
