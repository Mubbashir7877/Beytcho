package com.beytcho.Beytcho.Controller;

import com.beytcho.Beytcho.DTO.AddressDTO;
import com.beytcho.Beytcho.DTO.ResponseDTO;
import com.beytcho.Beytcho.Services.interfA.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> saveAndUpdateAddress (@RequestBody AddressDTO addressDTO){

        return ResponseEntity.ok(addressService.saveAndUpdateAddress(addressDTO));
    }




}
