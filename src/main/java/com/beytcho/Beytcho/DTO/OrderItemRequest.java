package com.beytcho.Beytcho.DTO;


import lombok.Data;

@Data
public class OrderItemRequest {


    private Long productid;
    private int quantity;

}
