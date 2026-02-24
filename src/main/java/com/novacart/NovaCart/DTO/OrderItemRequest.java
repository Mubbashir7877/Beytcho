package com.novacart.NovaCart.DTO;


import lombok.Data;

@Data
public class OrderItemRequest {


    private Long productid;
    private int quantity;

}
