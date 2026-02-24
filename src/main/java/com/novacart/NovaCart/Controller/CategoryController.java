package com.novacart.NovaCart.Controller;


import com.novacart.NovaCart.DTO.CategoryDTO;
import com.novacart.NovaCart.DTO.ResponseDTO;
import com.novacart.NovaCart.Services.interfA.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> createCategory (@RequestBody CategoryDTO categoryDTO){
        return ResponseEntity.ok(categoryService.createCategory(categoryDTO));
    }

    @GetMapping("/get-all")
    public ResponseEntity<ResponseDTO> getALLCategory (){
        return ResponseEntity.ok(categoryService.getALLCategory());
    }

    @PutMapping ("/update/{catID}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> editCategory (@PathVariable Long catID, @RequestBody CategoryDTO categoryDTO){
        return ResponseEntity.ok(categoryService.updateCategory(catID,categoryDTO));
    }

    @DeleteMapping ("/delete/{catID}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> deleteCategory (@PathVariable Long catID){
        return ResponseEntity.ok(categoryService.deleteCategory(catID));
    }

    @GetMapping("/get-category-by-id/{catID}")
    public ResponseEntity<ResponseDTO> getCategory (@PathVariable Long catID){
        return ResponseEntity.ok(categoryService.getCategorybyID(catID));
    }


}
