package com.mechart.catalog;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ProductCatalogController {
    private final ProductCatalogService catalogService;

    public ProductCatalogController(ProductCatalogService catalogService) {
        this.catalogService = catalogService;
    }

    public ApiResponse handle(String method, String path, String rawQuery) {
        if (!"GET".equalsIgnoreCase(method)) {
            return jsonResponse(405, "{\"error\":\"Method not allowed. Only GET is supported.\"}");
        }

        try {
            if ("/products".equals(path)) {
                return listProducts(rawQuery);
            }

            if (path != null && path.startsWith("/products/")) {
                return getProduct(path.substring("/products/".length()));
            }

            return jsonResponse(404, "{\"error\":\"Resource not found.\"}");
        } catch (IllegalArgumentException exception) {
            return jsonResponse(400, errorJson(exception.getMessage()));
        }
    }

    private ApiResponse listProducts(String rawQuery) {
        final Map<String, String> queryParameters = parseQuery(rawQuery);
        final List<Product> products = catalogService.listProducts(
            queryParameters.get("merchantId"),
            queryParameters.get("category")
        );

        final StringBuilder body = new StringBuilder();
        body.append("{\"count\":").append(products.size()).append(",\"products\":[");

        for (int index = 0; index < products.size(); index++) {
            if (index > 0) {
                body.append(',');
            }
            body.append(productJson(products.get(index)));
        }

        body.append("]}");
        return jsonResponse(200, body.toString());
    }

    private ApiResponse getProduct(String productId) {
        final Product product = catalogService.findProductById(decode(productId));
        if (product == null) {
            return jsonResponse(404, "{\"error\":\"Product not found.\"}");
        }
        return jsonResponse(200, productJson(product));
    }

    private Map<String, String> parseQuery(String rawQuery) {
        final Map<String, String> queryParameters = new LinkedHashMap<>();
        if (rawQuery == null || rawQuery.isBlank()) {
            return queryParameters;
        }

        final String[] pairs = rawQuery.split("&");
        for (String pair : pairs) {
            final String[] parts = pair.split("=", 2);
            final String key = decode(parts[0]);
            final String value = parts.length > 1 ? decode(parts[1]) : "";
            queryParameters.put(key, value);
        }

        return queryParameters;
    }

    private ApiResponse jsonResponse(int statusCode, String body) {
        return new ApiResponse(statusCode, body);
    }

    private String productJson(Product product) {
        return "{"
            + "\"id\":\"" + escapeJson(product.getId()) + "\","
            + "\"merchantId\":\"" + escapeJson(product.getMerchantId()) + "\","
            + "\"name\":\"" + escapeJson(product.getName()) + "\","
            + "\"category\":\"" + escapeJson(product.getCategory()) + "\","
            + "\"price\":" + product.getPrice() + ","
            + "\"available\":" + product.isAvailable()
            + "}";
    }

    private String errorJson(String message) {
        return "{\"error\":\"" + escapeJson(message) + "\"}";
    }

    private String decode(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }

    private String escapeJson(String value) {
        if (value == null) {
            return "";
        }

        final StringBuilder escaped = new StringBuilder(value.length());
        for (int index = 0; index < value.length(); index++) {
            final char character = value.charAt(index);
            switch (character) {
                case '"':
                    escaped.append("\\\"");
                    break;
                case '\\':
                    escaped.append("\\\\");
                    break;
                case '\b':
                    escaped.append("\\b");
                    break;
                case '\f':
                    escaped.append("\\f");
                    break;
                case '\n':
                    escaped.append("\\n");
                    break;
                case '\r':
                    escaped.append("\\r");
                    break;
                case '\t':
                    escaped.append("\\t");
                    break;
                default:
                    if (character < 0x20) {
                        escaped.append(String.format("\\u%04x", (int) character));
                    } else {
                        escaped.append(character);
                    }
                    break;
            }
        }

        return escaped.toString();
    }
}