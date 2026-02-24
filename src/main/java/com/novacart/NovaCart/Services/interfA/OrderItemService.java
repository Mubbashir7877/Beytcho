package com.novacart.NovaCart.Services.interfA;

import com.novacart.NovaCart.DTO.OrderRequest;
import com.novacart.NovaCart.DTO.ResponseDTO;
import com.novacart.NovaCart.Enums.OrderStatus;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;

public interface OrderItemService {

    ResponseDTO placeOrder (OrderRequest orderRequest);
    ResponseDTO updateOrderItemStatus (Long orderItemId, String status);
    ResponseDTO filterOrderItems(OrderStatus status, LocalDateTime startDate, LocalDateTime endTime, Long itemId, Pageable pageable);

}
