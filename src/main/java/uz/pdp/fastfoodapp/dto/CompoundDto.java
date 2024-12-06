package uz.pdp.fastfoodapp.dto;

public record CompoundDto(
        Double weight,
        Long calories,
        Long fat,
        Long protein
) {
}
