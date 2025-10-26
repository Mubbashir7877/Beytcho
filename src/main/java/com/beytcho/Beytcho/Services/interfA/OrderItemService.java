package com.beytcho.Beytcho.Services.interfA;

import com.beytcho.Beytcho.DTO.OrderRequest;
import com.beytcho.Beytcho.DTO.ResponseDTO;
import com.beytcho.Beytcho.Enums.OrderStatus;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;

public interface OrderItemService {

    ResponseDTO placeOrder (OrderRequest orderRequest);
    ResponseDTO updateOrderItemStatus (Long orderItemId, String status);
    ResponseDTO filterOrderItems(OrderStatus status, LocalDateTime startDate, LocalDateTime endTime, Long itemId, Pageable pageable);

}
