package com.novacart.NovaCart.Services.interfA;

import com.novacart.NovaCart.DTO.CategoryDTO;
import com.novacart.NovaCart.DTO.ResponseDTO;

public interface CategoryService {

    ResponseDTO createCategory(CategoryDTO categoryRequest);

    ResponseDTO updateCategory(Long catID, CategoryDTO categoryRequest);

    ResponseDTO getALLCategory();

    ResponseDTO getCategorybyID(Long catID);

    ResponseDTO deleteCategory(Long catID);





}
