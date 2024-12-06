package uz.pdp.fastfoodapp.dto;

import java.time.LocalDateTime;

public record PaymentDto(
        Long cardNumber,
        LocalDateTime paymentTime
) {
}
