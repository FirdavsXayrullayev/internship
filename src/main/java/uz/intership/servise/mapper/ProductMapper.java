package uz.intership.servise.mapper;

import org.springframework.stereotype.Component;
import uz.intership.dto.ProductDto;
import uz.intership.model.Product;

@Component
public class ProductMapper {
    public ProductDto toDto(Product product){
        return product == null ? null : new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getAmount(),
                product.getDescription()
        );
    }
    public Product toEntity(ProductDto productDto){
        return productDto == null ? null : new Product(
                productDto.getId(),
                productDto.getName(),
                productDto.getPrice(),
                productDto.getAmount(),
                productDto.getDescription()
        );
    }
}
