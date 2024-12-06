package uz.pdp.fastfoodapp.dto;

public record AddressDto(
        Integer id,
        String street,
        String houseNumber,
        Double latitude,
        Double longitude

) {
}
