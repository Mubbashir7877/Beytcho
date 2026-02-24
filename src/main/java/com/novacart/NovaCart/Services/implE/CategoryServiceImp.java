package com.novacart.NovaCart.Services.implE;

import com.novacart.NovaCart.DTO.CategoryDTO;
import com.novacart.NovaCart.DTO.ResponseDTO;
import com.novacart.NovaCart.Entities.Category;
import com.novacart.NovaCart.Exceptions.NotFoundException;
import com.novacart.NovaCart.Mappers.EntityDTOMapper;
import com.novacart.NovaCart.Repositories.CategoryRepo;
import com.novacart.NovaCart.Services.interfA.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


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
        List<Category> categories = catRepo.findAll();
        List<CategoryDTO> categoryDTOList = categories.stream()
                .map(entityDTOMapper::mapCategoryToDTOBasic)
                .collect(Collectors.toList());


        return ResponseDTO.builder()
                .status(200)
                .categoryList(categoryDTOList)
                .build();
    }

    @Override
    public ResponseDTO getCategorybyID(Long catID) {
        Category cat = catRepo.findById(catID).orElseThrow(()->new NotFoundException("Category Not Found"));
        CategoryDTO catDTO = entityDTOMapper.mapCategoryToDTOBasic(cat);

        return ResponseDTO.builder()
                .status(200)
                .category(catDTO)

                .build();
    }

    @Override
    public ResponseDTO deleteCategory(Long catID) {

        Category cat = catRepo.findById(catID).orElseThrow(()->new NotFoundException("Category Not Found"));
        catRepo.delete(cat);

        return ResponseDTO.builder()
                .status(200)
                .message("Category was successfully deleted.")
                .build();
    }
}
