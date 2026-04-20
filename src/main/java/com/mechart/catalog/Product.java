package com.mechart.catalog;

public final class Product {
    private final String id;
    private final String merchantId;
    private final String name;
    private final String category;
    private final double price;
    private final boolean available;

    public Product(String id, String merchantId, String name, String category, double price, boolean available) {
        this.id = id;
        this.merchantId = merchantId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.available = available;
    }

    public String getId() {
        return id;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }
}