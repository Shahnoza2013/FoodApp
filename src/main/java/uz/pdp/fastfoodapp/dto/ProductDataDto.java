package uz.pdp.fastfoodapp.dto;

public record  ProductDataDto(
        Integer id,
        ImageDto imageDto,
        String name,
        Integer price
) {
}
