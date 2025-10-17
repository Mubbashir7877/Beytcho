package com.beytcho.Beytcho.Specifications;

import com.beytcho.Beytcho.Entities.OrderItem;
import com.beytcho.Beytcho.Enums.OrderStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class OrderItemSpecification {

    ///  specification to filter  by status///

    public static Specification<OrderItem> hasStatus(OrderStatus status){
        return((root, query, criteriaBuilder)
                ->status !=null ? criteriaBuilder.equal(root.get("status"), status) : null);

    }

    ///  specification to filter date-range by item id///

    public static Specification<OrderItem> createdBetween(LocalDateTime startDate,LocalDateTime endDate){
        return ((root, query, criteriaBuilder)->{
            if(startDate!=null && endDate!=null){
                return criteriaBuilder.between(root.get("createdAt"), startDate, endDate);
            }else if (startDate!=null){
                return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), startDate);
            }else if(endDate!=null){
                return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), endDate);
            }else{
                return null;
            }
        });
    }

    ///  specification to filter orderitems by item id///

    public static Specification<OrderItem> hasItemId(Long itemId){
        return ((root, query, criteriaBuilder)->
                itemId != null ? criteriaBuilder.equal(root.get("id"), itemId): null);
    }
}
