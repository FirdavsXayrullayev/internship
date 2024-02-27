package uz.intership.servise;

import uz.intership.dto.ProductDto;
import uz.intership.dto.ResponseDto;

public interface ProductService {
    ResponseDto<ProductDto> getBYId(Integer id);

    ResponseDto<ProductDto> addNewProduct(ProductDto productDto);

    ResponseDto<ProductDto> update(ProductDto productDto);

    ResponseDto<ProductDto> deleteById(Integer id);
}
