package com.mechart.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public final class ProductCatalogService {
    private final List<Product> products;

    public ProductCatalogService(List<Product> products) {
        Objects.requireNonNull(products, "products must not be null");
        this.products = List.copyOf(products);
    }

    public static ProductCatalogService withSampleData() {
        final List<Product> sampleProducts = new ArrayList<>();
        sampleProducts.add(new Product("P-100", "M-100", "Trail Running Shoes", "footwear", 129.99, true));
        sampleProducts.add(new Product("P-200", "M-100", "Weatherproof Jacket", "apparel", 189.50, true));
        sampleProducts.add(new Product("P-300", "M-200", "Travel Backpack", "accessories", 89.00, false));
        sampleProducts.add(new Product("P-400", "M-300", "Stainless Bottle", "accessories", 24.99, true));
        return new ProductCatalogService(sampleProducts);
    }

    public List<Product> listProducts(String merchantId, String category, String available) {
        final String normalizedMerchantId = normalizeFilter(merchantId);
        final String normalizedCategory = normalizeFilter(category);
        final Boolean availabilityFilter = normalizeAvailabilityFilter(available);
        final List<Product> filteredProducts = new ArrayList<>();

        for (Product product : products) {
            if (matchesMerchant(product, normalizedMerchantId)
                && matchesCategory(product, normalizedCategory)
                && matchesAvailability(product, availabilityFilter)) {
                filteredProducts.add(product);
            }
        }

        return filteredProducts;
    }

    public Product findProductById(String productId) {
        final String normalizedProductId = normalizeRequired(productId, "productId");

        for (Product product : products) {
            if (product.getId().equalsIgnoreCase(normalizedProductId)) {
                return product;
            }
        }

        return null;
    }

    private boolean matchesMerchant(Product product, String merchantId) {
        return merchantId == null || product.getMerchantId().equalsIgnoreCase(merchantId);
    }

    private boolean matchesCategory(Product product, String category) {
        return category == null || product.getCategory().toLowerCase(Locale.ROOT).equals(category);
    }

    private boolean matchesAvailability(Product product, Boolean available) {
        return available == null || product.isAvailable() == available.booleanValue();
    }

    private String normalizeFilter(String value) {
        if (value == null) {
            return null;
        }

        final String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Filter values must not be empty.");
        }
        return trimmed.toLowerCase(Locale.ROOT);
    }

    private String normalizeRequired(String value, String fieldName) {
        Objects.requireNonNull(value, fieldName + " must not be null.");

        final String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be empty.");
        }
        return trimmed;
    }

    private Boolean normalizeAvailabilityFilter(String value) {
        if (value == null) {
            return null;
        }

        final String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Availability filter must not be empty.");
        }

        if ("true".equalsIgnoreCase(trimmed)) {
            return Boolean.TRUE;
        }

        if ("false".equalsIgnoreCase(trimmed)) {
            return Boolean.FALSE;
        }

        throw new IllegalArgumentException("Availability filter must be true or false.");
    }
}