package com.beytcho.Beytcho.Mappers;

import com.beytcho.Beytcho.DTO.*;
import com.beytcho.Beytcho.Entities.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EntityDTOMapper {

    //user entity to user DTO

    public UserDTO mapUserToDTOBasic(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhone());
        userDTO.setRole(user.getRole().name());
        return userDTO;
    }

    // address to DTO basic
    public AddressDTO mapAddressToDTOBasic(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(address.getId());
        addressDTO.setCity(address.getCity());
        addressDTO.setStreet(address.getStreet());
        addressDTO.setState(address.getState());
        addressDTO.setCountry(address.getCountry());
        addressDTO.setZip(address.getZip());
        return addressDTO;
    }

    // category to DTO basic
    public CategoryDTO mapCategoryToDTOBasic(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }

    // OrderItem to DTO basic
    public OrderItemDTO mapOrderItemToDTOBasic(OrderItem orderItem) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setId(orderItem.getId());
        orderItemDTO.setQuantity(orderItem.getQuantity());
        orderItemDTO.setPrice(orderItem.getPrice());
        orderItemDTO.setStatus(orderItemDTO.getStatus());
        orderItemDTO.setCreated_at(orderItem.getCreatedAt());
        return orderItemDTO;
    }

    //Product to DTO basic
    public ProductDTO mapProductToDTOBasic(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId().toString());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setImageUrl(product.getImageUrl());
        return productDTO;
    }

    public UserDTO mapUserToDTOaddress(User user) {
        UserDTO userDTO = mapUserToDTOBasic(user);
        if(user.getAddress()!=null){
            AddressDTO addressDTO = mapAddressToDTOBasic(user.getAddress());
            userDTO.setAddress(addressDTO);
        }
        return userDTO;
    }

    //orderItem to DTO plus product

    public OrderItemDTO mapOrderItemToDTOproduct(OrderItem orderItem) {
        OrderItemDTO orderItemDTO = mapOrderItemToDTOBasic(orderItem);
        if(orderItem.getProduct()!=null) {
            ProductDTO productDTO = mapProductToDTOBasic(orderItem.getProduct());
            orderItemDTO.setProduct(productDTO);
        }
        return orderItemDTO;
    }

    //OrderItem to DTO plus product and user

    public OrderItemDTO mapOrderItemToDTOproductAndUser(OrderItem orderItem) {
        OrderItemDTO orderItemDTO = mapOrderItemToDTOproduct(orderItem);
        if(orderItem.getUser()!=null) {
            UserDTO userDTO = mapUserToDTOaddress(orderItem.getUser());
            orderItemDTO.setUser(userDTO);
        }
        return orderItemDTO;
    }

    //User to DTO with Address and Order Items History
    public UserDTO mapUserDTOAddressAndOrderHistory(User user) {
        UserDTO userDTO = mapUserToDTOBasic(user);
        if(user.getOrderItemList()!=null&&!user.getOrderItemList().isEmpty()) {
            userDTO.setOrderItenList(user.getOrderItemList()
                    .stream().
                    map(this::mapOrderItemToDTOproduct)
                    .collect(Collectors.toList()));
        }

        return userDTO;
    }

}
