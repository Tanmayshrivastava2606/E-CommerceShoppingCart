package com.tanmay.ecommerce.view;

import com.tanmay.ecommerce.dao.ProductDAO;
import com.tanmay.ecommerce.model.Product;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ShoppingCartUI extends Application {

    private ListView<Product> cartList = new ListView<>();
    private Label totalLabel = new Label("💰 Total : ₹0");
    private double total = 0;

    @Override
    public void start(Stage stage) {

        totalLabel.setId("totalLabel");
        ProductDAO dao = new ProductDAO();
        cartList.setPrefHeight(430);

        TableView<Product> table = new TableView<>();

        // ID Column
        TableColumn<Product, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cell ->
                new SimpleIntegerProperty(cell.getValue().getId()).asObject());

        // Product Name Column
        TableColumn<Product, String> nameCol = new TableColumn<>("Product");
        nameCol.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getName()));

        // Price Column
        TableColumn<Product, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(cell ->
                new SimpleDoubleProperty(cell.getValue().getPrice()).asObject());

        // Category Column
        TableColumn<Product, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getCategory()));

        // Stock Column
        TableColumn<Product, Integer> stockCol = new TableColumn<>("Stock");
        stockCol.setCellValueFactory(cell ->
                new SimpleIntegerProperty(cell.getValue().getStock()).asObject());

        table.getColumns().addAll(idCol, nameCol, priceCol, categoryCol, stockCol);

        table.setItems(FXCollections.observableArrayList(dao.getAllProducts()));

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefHeight(620);

        // Title
        Label title = new Label("🛒 E-Commerce Shopping Cart");
        title.setStyle("-fx-font-size:22px; -fx-font-weight:bold;");

        VBox top = new VBox(title);
        top.setPadding(new Insets(15));

        // Add To Cart Button
        Button addButton = new Button("🛒 Add To Cart");
        addButton.setPrefWidth(220);
        addButton.setPrefHeight(45);
        addButton.setId("addButton");

        addButton.setOnAction(e -> {

            Product selected = table.getSelectionModel().getSelectedItem();

            if (selected != null) {

                cartList.getItems().add(selected);

                total += selected.getPrice();

                totalLabel.setText("Total : ₹" + total);

            } else {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setContentText("Please select a product.");
                alert.showAndWait();

            }

        });

        // Left Panel
        VBox left = new VBox(10);
        left.getChildren().addAll(table, addButton);
        left.setPadding(new Insets(10));

        // Right Panel
        Label cartTitle = new Label("🛍 Shopping Cart");
        cartTitle.setStyle("-fx-font-size:18px; -fx-font-weight:bold;");

        Button removeButton = new Button("❌ Remove");
        removeButton.setPrefWidth(220);
        removeButton.setPrefHeight(45);
        removeButton.setId("removeButton");
        removeButton.setOnAction(e -> {

    Product selected = cartList.getSelectionModel().getSelectedItem();

    if (selected != null) {

        cartList.getItems().remove(selected);

        total -= selected.getPrice();

        totalLabel.setText("💰 Total : ₹" + total);

    } else {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText("Please select a product to remove.");
        alert.showAndWait();

    }

});
        Button checkoutButton = new Button("💳 Checkout");
        checkoutButton.setPrefWidth(220);
        checkoutButton.setPrefHeight(45);
        checkoutButton.setId("checkoutButton");
        checkoutButton.setOnAction(e -> {

    if (cartList.getItems().isEmpty()) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Cart Empty");
        alert.setHeaderText(null);
        alert.setContentText("Please add products before checkout.");
        alert.showAndWait();

        return;
    }

    StringBuilder bill = new StringBuilder();

    bill.append("========== ORDER SUMMARY ==========\n\n");

    for (Product p : cartList.getItems()) {

        bill.append(p.getName())
            .append("   ₹")
            .append(p.getPrice())
            .append("\n");

    }

    bill.append("\n---------------------------------\n");
    bill.append("Total : ₹").append(total);
    bill.append("\n\nThank You For Shopping ❤️");

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Checkout");
    alert.setHeaderText("Order Placed Successfully!");
    alert.setContentText(bill.toString());

    alert.showAndWait();

    // Clear Cart
    cartList.getItems().clear();
    total = 0;
    totalLabel.setText("💰 Total : ₹0");

});

        VBox right = new VBox(15);
        right.setPadding(new Insets(10));
        right.setPrefWidth(250);

        right.getChildren().addAll(
                cartTitle,
                cartList,
                totalLabel,
                removeButton,
                checkoutButton
        );

        BorderPane root = new BorderPane();

        root.setTop(top);
        root.setCenter(left);
        root.setRight(right);

        Scene scene = new Scene(root, 1200, 700);
        scene.getStylesheets().add(
        getClass().getResource("/style.css").toExternalForm()
);

        stage.setTitle("E-Commerce Shopping Cart");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}