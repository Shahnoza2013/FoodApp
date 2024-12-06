package uz.pdp.fastfoodapp.dto;

public record ProductDTO(
        Integer id,
        ImageDto imageDto,
        String name,
        Integer price,
        String description,
        CompoundDto compoundDto,
        Integer categoryId
) {
}
