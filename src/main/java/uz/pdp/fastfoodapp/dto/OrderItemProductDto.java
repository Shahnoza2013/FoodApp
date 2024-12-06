package uz.pdp.fastfoodapp.dto;

public record OrderItemProductDto(
        Integer productId,
        String productName,
        Integer price,
        Long count,
        ImageDto imageDto,
        Integer categoryId,
        CompoundDto compoundDto
) {
}
