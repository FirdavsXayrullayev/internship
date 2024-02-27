package uz.intership.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.intership.dto.ProductDto;
import uz.intership.dto.ResponseDto;
import uz.intership.servise.ProductService;
import uz.intership.servise.UserService;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductResources {
    private final ProductService productService;

    @GetMapping("get-by-id")
    public ResponseDto<ProductDto> getById(@RequestParam Integer id){
        return productService.getBYId(id);
    }

    @PostMapping("add-new-product")
    public ResponseDto<ProductDto> addNewProduct(@RequestBody ProductDto productDto){
        return productService.addNewProduct(productDto);
    }

    @PatchMapping("update")
    public ResponseDto<ProductDto> update(@RequestBody ProductDto productDto){
        return productService.update(productDto);
    }

    @DeleteMapping("delete-product")
    public ResponseDto<ProductDto> deleteById(@RequestParam Integer id){
        return productService.deleteById(id);
    }
}
