package com.beytcho.Beytcho.Controller;

import com.beytcho.Beytcho.DTO.ResponseDTO;
import com.beytcho.Beytcho.Exceptions.InvalidCredentialException;
import com.beytcho.Beytcho.Services.interfA.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> createProduct(
            @RequestParam Long catID,
            @RequestParam MultipartFile image,
            @RequestParam String Name,
            @RequestParam String Description,
            @RequestParam BigDecimal price
            )
    {
        if(catID ==null||image.isEmpty()||Name.isEmpty()||Description.isEmpty()||price ==null||(price.compareTo(BigDecimal.ZERO)<0) ){
            throw new InvalidCredentialException("ALL Fields are Required");
        }


        return ResponseEntity.ok(productService.createProduct(catID, image, Name, Description, price));

    }

    @PutMapping("/update/{prodID}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> updateProduct(
            @RequestParam Long prodID,
            @RequestParam(required = false) Long catID,
            @RequestParam(required = false) MultipartFile image,
            @RequestParam(required = false) String Name,
            @RequestParam(required = false) String Description,
            @RequestParam(required = false) BigDecimal price
    )
    {

        return ResponseEntity.ok(productService.updateProduct(prodID, catID, image, Name, Description, price));

    }


    @DeleteMapping("/delete/{prodID}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> deleteProduct(@PathVariable Long prodID)
    {

        return ResponseEntity.ok(productService.deleteProduct(prodID));

    }


    @GetMapping("/get-prod-by-id/{prodID}")
    public ResponseEntity<ResponseDTO> getProduct(@PathVariable Long prodID)
    {

        return ResponseEntity.ok(productService.getProductByID(prodID));

    }


    @GetMapping("/get-prod-all")
    public ResponseEntity<ResponseDTO> getAllProducts()
    {

        return ResponseEntity.ok(productService.getALLProducts());

    }

    @GetMapping("/get-prod-all-category/{catID}")
    public ResponseEntity<ResponseDTO> getAllProductsForCategory(@PathVariable Long catID)
    {

        return ResponseEntity.ok(productService.getProductByCategory(catID));

    }

    @GetMapping("/search")
    public ResponseEntity<ResponseDTO> searchProduct(
            @RequestParam String searchValue
    ) {

        return ResponseEntity.ok(productService.searchProduct(searchValue));

    }

}
