package com.beytcho.Beytcho.Services.interfA;

import com.beytcho.Beytcho.DTO.CategoryDTO;
import com.beytcho.Beytcho.DTO.ResponseDTO;

public interface CategoryService {

    ResponseDTO createCategory(CategoryDTO categoryRequest);

    ResponseDTO updateCategory(Long catID, CategoryDTO categoryRequest);

    ResponseDTO getALLCategory();

    ResponseDTO getCategorybyID(Long catID);

    ResponseDTO deleteCategory(Long catID);





}
