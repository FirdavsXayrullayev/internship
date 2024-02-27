package uz.intership.servise.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.intership.dto.ProductDto;
import uz.intership.dto.ResponseDto;
import uz.intership.dto.UserDto;
import uz.intership.model.Product;
import uz.intership.model.User;
import uz.intership.repository.ProductRepository;
import uz.intership.repository.UserRepository;
import uz.intership.servise.ProductService;
import uz.intership.servise.mapper.ProductMapper;
import uz.intership.servise.mapper.UserMapper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    @Override
    public ResponseDto<ProductDto> getBYId(Integer id) {
        return productRepository.findById(id).map(p->
                ResponseDto.<ProductDto>builder()
                                .code(0)
                                .info("OK")
                                .success(true)
                                .data(productMapper.toDto(p))
                                .build())
                .orElse(ResponseDto.<ProductDto>builder()
                        .success(false)
                        .code(-1)
                        .info("Not found")
                        .build());
    }

    @Override
    public ResponseDto<ProductDto> addNewProduct(ProductDto productDto) {
        try {
            return ResponseDto.<ProductDto>builder()
                    .code(0)
                    .info("OK")
                    .success(true)
                    .data(productMapper.toDto(productRepository.save(productMapper.toEntity(productDto))))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<ProductDto>builder()
                    .code(1)
                    .info("Database Error")
                    .data(productDto)
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto<ProductDto> update(ProductDto productDto) {
        if (productDto.getId() == null){
            return ResponseDto.<ProductDto>builder()
                    .code(-2)
                    .info("Validation Error")
                    .success(false)
                    .data(productDto)
                    .build();
        }
        Optional<Product> optionalProduct = productRepository.findById(productDto.getId());
        if (optionalProduct.isEmpty()){
            return ResponseDto.<ProductDto>builder()
                    .code(-1)
                    .info("Not found")
                    .success(false)
                    .data(productDto)
                    .build();
        }
        Product product = optionalProduct.get();
        if (productDto.getName() != null){
            product.setName(productDto.getName());
        }
        if (productDto.getPrice() != null){
            product.setPrice(productDto.getPrice());
        }
        if (productDto.getAmount() != null){
            product.setAmount(productDto.getAmount());
        }
        if (productDto.getDescription() != null){
            product.setDescription(productDto.getDescription());
        }
        try {
            productRepository.save(product);

            return ResponseDto.<ProductDto>builder()
                    .code(0)
                    .info("OK")
                    .data(productMapper.toDto(product))
                    .success(true)
                    .build();
        }catch (Exception e){
            return ResponseDto.<ProductDto>builder()
                    .code(-1)
                    .data(productDto)
                    .info("Database Error")
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto<ProductDto> deleteById(Integer id) {
        return productRepository.findById(id).map(product -> {
            productRepository.delete(product);
            return ResponseDto.<ProductDto>builder()
                    .code(0)
                    .info("OK")
                    .data(productMapper.toDto(product))
                    .success(true)
                    .build();
        }).orElse(
                ResponseDto.<ProductDto>builder()
                        .code(-1)
                        .info("Not found")
                        .success(false)
                        .build()
        );
    }
}
