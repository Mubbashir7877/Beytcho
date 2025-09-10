package com.beytcho.Beytcho.DTO;


import lombok.Data;

@Data
public class OrderItemRequest {

    private String productid;
    private int quantity;

}
