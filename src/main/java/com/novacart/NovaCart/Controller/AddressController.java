package com.novacart.NovaCart.Controller;

import com.novacart.NovaCart.DTO.AddressDTO;
import com.novacart.NovaCart.DTO.ResponseDTO;
import com.novacart.NovaCart.Services.interfA.AddressService;
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
