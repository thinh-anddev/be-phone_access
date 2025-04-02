package com.TMDT.api.Api.springboot.controllers;

import com.TMDT.api.Api.springboot.dto.ProductDTO;
import com.TMDT.api.Api.springboot.dto.ProductInsertDTO;
import com.TMDT.api.Api.springboot.dto.ProductUpdateDTO;
import com.TMDT.api.Api.springboot.models.Product;
import com.TMDT.api.Api.springboot.repositories.CategoryRepository;

import com.TMDT.api.Api.springboot.repositories.ProductRepository;
import com.TMDT.api.Api.springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductControllers {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    ProductService productService;

    @GetMapping("/getAll")
    ResponseEntity<ResponseObject> getProducts() {
        return ResponseEntity.ok(new ResponseObject("ok", "Success", productService.getAll()));
    }

    @GetMapping("/getByFilter")
    public ResponseEntity<ResponseObject> getProductsByFilter(
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(defaultValue = "id") String orderBy) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Success", productService.getByFilter(category == null || "".equals(category) ? null : category, page - 1, limit, order, orderBy)));
    }

    @GetMapping("/getByCategory")
    ResponseEntity<ResponseObject> getByCategory(@RequestParam String category) {
        List<Product> products = productService.getByCategory(category);
        return ResponseEntity.ok(new ResponseObject("ok", "Success", products));
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getProductById(@PathVariable int id) {
        Product foundProduct = productService.getById(id);
        return foundProduct != null ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Success", foundProduct)
                ) :
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("failed", "Cannot find product by id = " + id, "")
                );
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertProduct(@RequestBody ProductInsertDTO productDTO) {
        Product productSaved = productService.insert(productDTO);
        return (productSaved == null) ?
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ResponseObject("failed", "Cannot insert product", "")
                ) :
                ResponseEntity.ok(
                        new ResponseObject("ok", "success", productSaved)
                );
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateProduct(@PathVariable int id, @RequestBody ProductUpdateDTO productDTO) {
        Product productUpdate = productService.update(id, productDTO);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "success", productUpdate)
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteProduct(@PathVariable int id) {
        Product product = productService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "success", product)
        );
    }

    @GetMapping("/search")
    ResponseEntity<ResponseObject> searchProduct(@RequestParam String name) {
        return ResponseEntity.ok(new ResponseObject("ok", "Success", productService.search(name)));
    }

}
