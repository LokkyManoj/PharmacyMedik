package pharmacy.model;

import java.sql.Blob;

public class Product {
    private int productId;
    private String productName;
    private Blob productImage;
    private int productQuantity;
    private int productPrice;
    private String description;
    private String uses;
    private String contains;
    private String category;
    private String mfdDate;
    private String expDate;

    public Product(int productId, String productName, Blob productImage, int productQuantity, int productPrice,
                   String description, String uses, String contains, String category, String mfdDate, String expDate) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.description = description;
        this.uses = uses;
        this.contains = contains;
        this.category = category;
        this.mfdDate = mfdDate;
        this.expDate = expDate;
    }

    // Getters
    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Blob getProductImage() {
        return productImage;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public String getDescription() {
        return description;
    }

    public String getUses() {
        return uses;
    }

    public String getContains() {
        return contains;
    }

    public String getCategory() {
        return category;
    }

    public String getMfdDate() {
        return mfdDate;
    }

    public String getExpDate() {
        return expDate;
    }
}
