package com.tanmay.ecommerce;

import com.tanmay.ecommerce.dao.ProductDAO;
import com.tanmay.ecommerce.model.Product;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        ProductDAO dao = new ProductDAO();

        List<Product> products = dao.getAllProducts();

        for (Product product : products) {
            System.out.println(product);
        }
    }
}