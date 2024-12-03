package ru.otus.java.pro.spring.boot.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.java.pro.spring.boot.Product;
import ru.otus.java.pro.spring.boot.services.ProductService;
import ru.otus.java.pro.spring.boot.dtos.ProductDto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    private static final Function<Product, ProductDto> ENTITY_TO_DTO = i -> new ProductDto(i.getId(), i.getTitle(), i.getPrice());

    @GetMapping("/{id}")
    private Product getById(@PathVariable("id") int id) {
        return productService.getProductById(id);
    }

    @GetMapping
    private List<ProductDto> getAll() {
        return productService.getAllProducts().stream().map(ENTITY_TO_DTO).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private ProductDto createNewProduct(@RequestBody ProductDto productDto) {
        Product newProduct = productService.createNewProduct(productDto);
        return new ProductDto(newProduct.getId(), newProduct.getTitle(), newProduct.getPrice());
    }
}
