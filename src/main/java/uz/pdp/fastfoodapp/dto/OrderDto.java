package uz.pdp.fastfoodapp.dto;

import java.time.LocalDateTime;

public record OrderDto(
        LocalDateTime date,
        PaymentDto paymentDto,
        Integer basketId,
        Integer  userId
) {
}
