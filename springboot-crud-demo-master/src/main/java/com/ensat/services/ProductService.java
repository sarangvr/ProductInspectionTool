package com.ensat.services;

import java.util.List;

import com.ensat.entities.Product;
import com.ensat.model.ProductDetails;
import com.ensat.model.ProductDetailsDTO;

public interface ProductService {

    List<ProductDetailsDTO> listAllProducts();

    ProductDetails getProductById(Long id);
    
    ProductDetails editProductById(ProductDetails productDetails);

    Product saveProduct(ProductDetails productDetails);

    boolean deleteProduct(Long id);

}
