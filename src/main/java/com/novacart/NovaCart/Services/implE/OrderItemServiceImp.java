package com.novacart.NovaCart.Services.implE;

import com.novacart.NovaCart.DTO.OrderItemDTO;
import com.novacart.NovaCart.DTO.OrderRequest;
import com.novacart.NovaCart.DTO.ResponseDTO;
import com.novacart.NovaCart.Entities.Order;
import com.novacart.NovaCart.Entities.OrderItem;
import com.novacart.NovaCart.Entities.Product;
import com.novacart.NovaCart.Entities.User;
import com.novacart.NovaCart.Enums.OrderStatus;
import com.novacart.NovaCart.Exceptions.NotFoundException;
import com.novacart.NovaCart.Mappers.EntityDTOMapper;
import com.novacart.NovaCart.Repositories.OrderItemRepo;
import com.novacart.NovaCart.Repositories.OrderRepo;
import com.novacart.NovaCart.Repositories.ProductRepo;
import com.novacart.NovaCart.Services.interfA.OrderItemService;
import com.novacart.NovaCart.Services.interfA.UserService;
import com.novacart.NovaCart.Specifications.OrderItemSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderItemServiceImp implements OrderItemService {

    private final OrderRepo orderRepo;
    private final OrderItemRepo orderItemRepo;
    private final ProductRepo productRepo;
    private final UserService userService;
    private final EntityDTOMapper entityDTOMapper;

    @Override
    public ResponseDTO placeOrder(OrderRequest orderRequest) {

        User user = userService.getLoginUser();
        //map order-request items to order entities
        List<OrderItem> orderItems = orderRequest.getItems().stream().map(orderItemRequest -> {
            Product product = productRepo.findById(orderItemRequest.getProductid())
                    .orElseThrow(()->new NotFoundException("Product not Found"));
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(orderItemRequest.getQuantity());
            orderItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(orderItemRequest.getQuantity()))); // price
            orderItem.setStatus(OrderStatus.PENDING);
            orderItem.setUser(user);

            return orderItem;

        }).collect(Collectors.toList());

        BigDecimal totalPrice = orderRequest.getTotalPrice()!=null && orderRequest.getTotalPrice().compareTo(BigDecimal.ZERO) > 0
                ? orderRequest.getTotalPrice()
                : orderItems.stream().map(OrderItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);


        //make order entity
        Order order = new Order();
        order.setOrderItemList(orderItems);
        order.setTotalPrice(totalPrice);


        //set the order ref in each orderitem
        orderItems.forEach(orderItem -> orderItem.setOrder(order));

        orderRepo.save(order);
        return ResponseDTO.builder()
                .status(200)
                .message("Order placed.")
                .build();
    }

    @Override
    public ResponseDTO updateOrderItemStatus(Long orderItemId, String status) {

        OrderItem orderItem =orderItemRepo.findById(orderItemId)
                .orElseThrow(()-> new NotFoundException("Order Item not found"));


        orderItem.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        orderItemRepo.save(orderItem);
        return ResponseDTO.builder()
                .status(200)
                .message("Order Status updated successfully.")
                .build();
    }

    @Override
    public ResponseDTO filterOrderItems(OrderStatus status, LocalDateTime startDate, LocalDateTime endDate, Long itemId, Pageable pageable) {

        Specification<OrderItem> spec = OrderItemSpecification.hasStatus(status)
                .and(OrderItemSpecification.createdBetween(startDate, endDate))
                .and(OrderItemSpecification.hasItemId(itemId));

        Page<OrderItem> orderItemPage = orderItemRepo.findAll(spec, pageable);


        if(orderItemPage.isEmpty()){
            throw new NotFoundException("No Order found");
        }

        List<OrderItemDTO> orderItemDTOS = orderItemPage.getContent().stream()
                .map(entityDTOMapper::mapOrderItemToDTOproductAndUser)
                .collect(Collectors.toList());
        return ResponseDTO.builder()
                .status(200)
                .orderItemList(orderItemDTOS)
                .totalPage(orderItemPage.getTotalPages())
                .totalElement(orderItemPage.getTotalElements())
                .build();
    }
}
