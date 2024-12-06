package uz.pdp.fastfoodapp.dto;

public record UserDTO(
        String firstName,
        String lastName,
        String username,
        String password,
        String email,
        String phone,
        AddressDto addressDto
) {
}
