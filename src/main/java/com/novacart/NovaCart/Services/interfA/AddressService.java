package com.novacart.NovaCart.Services.interfA;

import com.novacart.NovaCart.DTO.AddressDTO;
import com.novacart.NovaCart.DTO.ResponseDTO;

public interface AddressService {

    ResponseDTO saveAndUpdateAddress(AddressDTO addyDTO);


}
