package com.TMDT.api.Api.springboot.service;

import com.TMDT.api.Api.springboot.dto.*;
import com.TMDT.api.Api.springboot.mapper.CategoryMapper;
import com.TMDT.api.Api.springboot.mapper.ProductMapper;
import com.TMDT.api.Api.springboot.models.*;
import com.TMDT.api.Api.springboot.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductPhoneCategoryRepository productPhoneCategoryRepository;

    @Autowired
    PhoneCategoryRepository phoneCategoryRepository;

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    public ProductDTO getById(int id) {
        Product product = productRepository.findFirstByIdAndStatusNot(id, 0);
        return productMapper.toDTO(product);
    }

    public List<ProductDTO> getAll() {
        return productMapper.toListDTO(productRepository.findAll());
    }

    public ListProductDTO getByFilter(String category, int page, int limit, String order, String orderBy) {
        Sort sort = Sort.by(orderBy);
        sort = order.equalsIgnoreCase("desc") ? sort.descending() : sort.ascending();
        Pageable pageable = PageRequest.of(page, limit, sort);

        Page<Product> productPage = productRepository.findByCategoryAndStatusNot(category, pageable);
        List<ProductDTO> productDTOList = productMapper.toListDTO(productPage.getContent());
        return new ListProductDTO(productPage.getTotalPages(), productDTOList);
    }

    public List<ProductDTO> getByCategory(String category) {
        Category foundCategory = categoryRepository.findByName(category);
        CategoryDTO categoryDTOList = categoryMapper.toDto(foundCategory);
        if (foundCategory == null) {
            return new ArrayList<>();
        }
        List<Product> products = foundCategory.getProducts();
        return productMapper.toListDTO(products);
    }


    public Product insert(ProductInsertDTO productDTO) {
        Category category = categoryService.getById(productDTO.getCategoryId());

        Product newProduct = new Product();
        newProduct.setName(productDTO.getName());
        newProduct.setDescription(productDTO.getDescription());
        newProduct.setPrice(productDTO.getPrice());
        newProduct.setDiscount(productDTO.getDiscount());
        newProduct.setSold(productDTO.getSold());
        newProduct.setQuantity(productDTO.getQuantity());
        newProduct.setStatus(1);
        newProduct.setCategory(category);
        newProduct.setCreateAt(LocalDateTime.now());
        Product productSaved = productRepository.save(newProduct);


        List<Image> images = new ArrayList<>();
        productDTO.getImages().forEach(url -> {
            Image image = new Image();
            image.setProduct(productSaved);
            image.setUrl(url);
            Image imageSaved = imageRepository.save(image);
            images.add(imageSaved);
        });
        productSaved.setImages(images);

        List<ProductPhoneCategory> productPhoneCategories = new ArrayList<>();
        productDTO.getPhoneCategoryIds().forEach(id -> {
            ProductPhoneCategory productPhoneCategory = new ProductPhoneCategory();
            ProductPhoneCategoryId productPhoneCategoryId = new ProductPhoneCategoryId();
            productPhoneCategoryId.setProductId(productSaved.getId());
            productPhoneCategoryId.setPhoneCategoryId(id);
            productPhoneCategory.setId(productPhoneCategoryId);
            productPhoneCategory.setProduct(productSaved);
            productPhoneCategory.setPhoneCategory(phoneCategoryRepository.getById(id));
            productPhoneCategoryRepository.save(productPhoneCategory);
        });
        productSaved.setProductPhoneCategories(productPhoneCategories);

        return productSaved;
    }

    public Product update(int id, ProductUpdateDTO productDTO) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return null;
        }
        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElse(null);
        if (category == null) {
            return null;
        }
        product.setCategory(category);
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setDiscount(productDTO.getDiscount());
        product.setSold(productDTO.getSold());
        product.setQuantity(productDTO.getQuantity());
        product.setStatus(productDTO.getStatus());
        product.setCategory(category);

        List<Image> images = new ArrayList<>();
        productDTO.getImages().forEach(url -> {
            Image image = new Image();
            image.setProduct(product);
            image.setUrl(url);
            Image imageSaved = imageRepository.save(image);
            images.add(imageSaved);
        });
        product.setImages(images);

        List<ProductPhoneCategory> productPhoneCategories = new ArrayList<>();
        productDTO.getPhoneCategoryIds().forEach(itemId -> {
            ProductPhoneCategory productPhoneCategory = new ProductPhoneCategory();
            ProductPhoneCategoryId productPhoneCategoryId = new ProductPhoneCategoryId();
            productPhoneCategoryId.setProductId(product.getId());
            productPhoneCategoryId.setPhoneCategoryId(itemId);
            productPhoneCategory.setId(productPhoneCategoryId);
            productPhoneCategory.setProduct(product);
            productPhoneCategory.setPhoneCategory(phoneCategoryRepository.getById(itemId));
            productPhoneCategoryRepository.save(productPhoneCategory);
        });
        product.setProductPhoneCategories(productPhoneCategories);

        return productRepository.save(product);
    }

    public List<Product> search(String name) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        for (Product p : products) {
            clearProperty(p);
        }
        return products;
    }


    public Product delete(int id) {
        Product product = productRepository.findById(id).map(p -> {
            p.setStatus(0);
            return productRepository.save(p);
        }).orElse(null);
        assert product != null;
        clearProperty(product);
        return product;
    }

    public void clearProperty(Product product) {
        product.getProductPhoneCategories().forEach(productPhoneCategory -> {
            productPhoneCategory.setProduct(null);
            if (productPhoneCategory.getPhoneCategory() != null)
                productPhoneCategory.getPhoneCategory().setProductPhoneCategories(null);
        });
        product.getImages().forEach(image -> {
            image.setProduct(null);
        });
        if (product.getCategory() != null)
            product.getCategory().setProducts(null);
    }

    public List<Product> clearProperties(List<Product> products) {
        for (Product p : products) {
            clearProperty(p);
        }
        return products;
    }

    public void updateSold(int id, int quantity) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return;
        }
        product.setSold(product.getSold() + quantity);
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
    }

}
