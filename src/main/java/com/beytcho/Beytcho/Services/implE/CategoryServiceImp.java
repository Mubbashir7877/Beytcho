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
