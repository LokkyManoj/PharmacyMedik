package pharmacy.model;

import com.mysql.cj.jdbc.Blob;

public class CartItem {
    private int productId;
    private String productName;
    private int productPrice;
    private Blob productImage;
    private int quantity;

    public CartItem(int productId, String productName, int productPrice, Blob productImage, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public Blob getProductImage() {
        return productImage;
    }

    public int getQuantity() {
        return quantity;
    }
}
