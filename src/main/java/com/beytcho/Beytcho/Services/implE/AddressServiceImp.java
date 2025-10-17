package com.beytcho.Beytcho.Services.implE;


import com.beytcho.Beytcho.DTO.AddressDTO;
import com.beytcho.Beytcho.DTO.ResponseDTO;
import com.beytcho.Beytcho.Entities.Address;
import com.beytcho.Beytcho.Entities.User;
import com.beytcho.Beytcho.Repositories.AddressRepo;
import com.beytcho.Beytcho.Services.interfA.AddressService;
import com.beytcho.Beytcho.Services.interfA.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdressServiceImp implements AddressService {

    private final AddressRepo addyRepo;
    private final UserService userService;

    @Override
    public ResponseDTO saveAndUpdateAddress(AddressDTO addyDTO){

        User user = userService.getLoginUser();

        Address address = user.getAddress();

        if(address == null){
            address= new Address();
            address.setUser(user);
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
