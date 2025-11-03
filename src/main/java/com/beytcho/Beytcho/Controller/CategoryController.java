package com.beytcho.Beytcho.Controller;


import com.beytcho.Beytcho.DTO.CategoryDTO;
import com.beytcho.Beytcho.DTO.ResponseDTO;
import com.beytcho.Beytcho.Services.interfA.CategoryService;
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

}
