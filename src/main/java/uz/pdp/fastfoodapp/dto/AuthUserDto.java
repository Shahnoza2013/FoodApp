package uz.pdp.fastfoodapp.dto;

public record AuthUserDto(
        String username,
        String password,
        String email
) {
}
