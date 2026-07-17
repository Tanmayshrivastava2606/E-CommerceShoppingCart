package com.tanmay.ecommerce.dao;

import com.tanmay.ecommerce.database.DatabaseConnection;
import com.tanmay.ecommerce.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public List<Product> getAllProducts() {

        List<Product> products = new ArrayList<>();

        String query = "SELECT * FROM products";

        try {

            Connection conn = DatabaseConnection.getConnection();

            PreparedStatement ps = conn.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Product product = new Product();
                product.setId(rs.getInt("product_id"));
                product.setName(rs.getString("product_name"));  
                product.setPrice(rs.getDouble("price"));
                product.setCategory(rs.getString("category"));
                product.setStock(rs.getInt("stock"));

                products.add(product);
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }
}
