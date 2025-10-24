package com.beytcho.Beytcho.Services.implE;

import com.beytcho.Beytcho.DTO.ProductDTO;
import com.beytcho.Beytcho.DTO.ResponseDTO;
import com.beytcho.Beytcho.Entities.Category;
import com.beytcho.Beytcho.Entities.Product;
import com.beytcho.Beytcho.Exceptions.NotFoundException;
import com.beytcho.Beytcho.Mappers.EntityDTOMapper;
import com.beytcho.Beytcho.Repositories.CategoryRepo;
import com.beytcho.Beytcho.Repositories.ProductRepo;
import com.beytcho.Beytcho.Services.AWS_S3_Service;
import com.beytcho.Beytcho.Services.interfA.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final EntityDTOMapper entityDTOMapper;
    private final AWS_S3_Service s3Service;


    @Override
    public ResponseDTO createProduct(Long catID, MultipartFile image, String name, String description, BigDecimal price) {

        Category category = categoryRepo.findById(catID).orElseThrow(()->new NotFoundException("Category not found."));
        String productImageUrl = s3Service.saveImageToS3(image);

        Product product = new Product();
        product.setCategory(category);
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setImageUrl(productImageUrl);

        productRepo.save(product);

        return ResponseDTO.builder()
                .status(200)
                .message("Product added.")
                .build();
    }

    @Override
    public ResponseDTO updateProduct(Long productID, Long catID, MultipartFile image, String name, String description, BigDecimal price) {

        Product product = productRepo.findById(productID).orElseThrow(()-> new NotFoundException("Product not found."));
        Category category = null;
        String productImageUrl = null;
        if(catID!=null) {
            category = categoryRepo.findById(catID).orElseThrow(() -> new NotFoundException("Category not found."));
        }
        if (image != null && !image.isEmpty()){
            productImageUrl = s3Service.saveImageToS3(image);
        }

        if (category!=null){product.setCategory(category);}
        if (name!=null){product.setName(name);}
        if (description!=null){product.setDescription(description);}
        if (price!=null){product.setPrice(price);}
        if (productImageUrl!=null){product.setImageUrl(productImageUrl);}

        productRepo.save(product);

        return ResponseDTO.builder()
                .status(200)
                .message("Product updated.")
                .build();

    }

    @Override
    public ResponseDTO deleteProduct(Long productID) {

        Product product = productRepo.findById(productID).orElseThrow(()-> new NotFoundException("Product not found."));
        productRepo.delete(product);

        return ResponseDTO.builder()
                .status(200)
                .message("Product deleted.")
                .build();
    }

    @Override
    public ResponseDTO getProductByID(Long productID) {

        Product product = productRepo.findById(productID).orElseThrow(()-> new NotFoundException("Product not found."));
        ProductDTO productDTO = entityDTOMapper.mapProductToDTOBasic(product);

        return ResponseDTO.builder()
                .status(200)
                .product(productDTO)
                .build();
    }

    @Override
    public ResponseDTO getALLProducts() {

        List<ProductDTO> productList = productRepo.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream()
                .map(entityDTOMapper::mapProductToDTOBasic)
                .collect(Collectors.toList());

        return ResponseDTO.builder()
                .status(200)
                .productList(productList)
                .build();
    }

    @Override
    public ResponseDTO getProductByCategory(Long catID) {

        List<Product> products= productRepo.findByCategoryId(catID);

        if(products.isEmpty()){
            throw new NotFoundException("No products found for this category");
        }

        List<ProductDTO> productDTOList = products.stream()
                .map(entityDTOMapper::mapProductToDTOBasic)
                .collect(Collectors.toList());

        return ResponseDTO.builder()
                .status(200)
                .productList(productDTOList)
                .build();
    }

    @Override
    public ResponseDTO searchProduct(String searchValue) {

        List<Product> products = productRepo.findByNameContainingOrDescriptionContaining(searchValue, searchValue);

        if (products.isEmpty()){
            throw new NotFoundException("No Products Found");
        }

        List<ProductDTO> productDTOList = products.stream()
                .map(entityDTOMapper::mapProductToDTOBasic)
                .collect(Collectors.toList());

        return ResponseDTO.builder()
                .status(200)

                .productList(productDTOList)
                .build();
    }
}
