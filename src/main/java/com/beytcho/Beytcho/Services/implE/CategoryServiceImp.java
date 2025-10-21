package com.beytcho.Beytcho.Services.implE;

import com.beytcho.Beytcho.DTO.CategoryDTO;
import com.beytcho.Beytcho.DTO.ResponseDTO;
import com.beytcho.Beytcho.Entities.Category;
import com.beytcho.Beytcho.Exceptions.NotFoundException;
import com.beytcho.Beytcho.Mappers.EntityDTOMapper;
import com.beytcho.Beytcho.Repositories.CategoryRepo;
import com.beytcho.Beytcho.Services.interfA.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImp implements CategoryService {

    private final CategoryRepo catRepo;
    private final EntityDTOMapper entityDTOMapper;


    @Override
    public ResponseDTO createCategory(CategoryDTO categoryRequest) {

        Category category = new Category();
        category.setName(categoryRequest.getName());
        catRepo.save(category);
        return ResponseDTO.builder()
                .status(200)
                .message("Category Made")
                .build();
    }

    @Override
    public ResponseDTO updateCategory(Long catID, CategoryDTO categoryRequest) {
        Category cat = catRepo.findById(catID).orElseThrow(()->new NotFoundException("Category Not Found"));
        cat.setName(categoryRequest.getName());
        catRepo.save(cat);
        return ResponseDTO.builder()
                .status(200)
                .message("Category Updated")
                .build();
    }

    @Override
    public ResponseDTO getALLCategory() {
        return null;
    }

    @Override
    public ResponseDTO getCategorybyID(Long catID) {
        return null;
    }

    @Override
    public ResponseDTO deleteCategory(Long catID) {
        return null;
    }

}
