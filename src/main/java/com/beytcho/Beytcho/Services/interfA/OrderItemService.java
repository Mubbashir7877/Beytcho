package com.beytcho.Beytcho.Services.interfA;

import com.amazonaws.Response;
import com.beytcho.Beytcho.DTO.OrderRequest;
import com.beytcho.Beytcho.Enums.OrderStatus;

import java.time.LocalDateTime;

public interface OrderItemService {

    Response placeOrder (OrderRequest orderRequest);
    Response updateOrderItemStatus (Long orderItemId, String status);
    Response filterOrderItems(OrderStatus status, LocalDateTime startDate, LocalDateTime endTime, Long itemId);

}
