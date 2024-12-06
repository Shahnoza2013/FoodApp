package uz.pdp.fastfoodapp.dto;

import java.time.LocalDateTime;

public record ErrorBodyDTO(
        Integer status,
        String uri,
        String url,
        String reason,
        String message,
        LocalDateTime timestamp
){

}
