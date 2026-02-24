package com.novacart.NovaCart.Services.implE;


import com.novacart.NovaCart.DTO.AddressDTO;
import com.novacart.NovaCart.DTO.ResponseDTO;
import com.novacart.NovaCart.Entities.Address;
import com.novacart.NovaCart.Entities.User;
import com.novacart.NovaCart.Repositories.AddressRepo;
import com.novacart.NovaCart.Services.interfA.AddressService;
import com.novacart.NovaCart.Services.interfA.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImp implements AddressService {

    private final AddressRepo addyRepo;
    private final UserService userService;

    @Override
    public ResponseDTO saveAndUpdateAddress(AddressDTO addyDTO){

        User user = userService.getLoginUser();

        Address address = user.getAddress();

        if(address == null){
            address = new Address();
            address.setUser(user);
            user.setAddress(address);   // important
        }
        if( addyDTO.getStreet()!= null ) address.setStreet(addyDTO.getStreet());
        if( addyDTO.getZip()!= null ) address.setZip(addyDTO.getZip());
        if( addyDTO.getCity()!= null ) address.setCity(addyDTO.getCity());
        if( addyDTO.getState()!= null ) address.setState(addyDTO.getState());
        if( addyDTO.getCountry()!= null ) address.setCountry(addyDTO.getCountry());

        addyRepo.save(address);

        String message = (user.getAddress() == null) ? "Address Successfully Created" : "Address Successfully Updated";

        return ResponseDTO.builder()
                .status(200)
                .message(message)
                .build();
    }

}
