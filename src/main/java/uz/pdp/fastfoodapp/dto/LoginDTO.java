package uz.pdp.fastfoodapp.dto;

import java.util.List;

public record LoginDTO(
        List<ProductDataDto> products,
        List<OrderItemProductDto> orders
) {
}
