package com.beytcho.Beytcho.Services.interfA;

import com.beytcho.Beytcho.DTO.ResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public interface ProductService {

    ResponseDTO createProduct (Long catID, MultipartFile image, String name, String description, BigDecimal price);

    ResponseDTO updateProduct (Long productID, Long catID, MultipartFile image, String name, String description, BigDecimal price);

    ResponseDTO deleteProduct (Long productID);

    ResponseDTO getProductByID (Long productID);

    ResponseDTO getALLProducts ();

    ResponseDTO getProductByCategory (Long catID);

    ResponseDTO searchProduct (String SearchValue);




}
