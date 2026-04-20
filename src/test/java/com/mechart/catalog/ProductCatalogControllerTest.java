package com.mechart.catalog;

public class ProductCatalogControllerTest {
    public static void main(String[] args) {
        ProductCatalogControllerTest testSuite = new ProductCatalogControllerTest();
        testSuite.listsAllProducts();
        testSuite.filtersProductsByMerchantAndCategory();
        testSuite.returnsSingleProductById();
        testSuite.rejectsUnsupportedMethods();
        testSuite.rejectsEmptyFilterValues();
        System.out.println("All ProductCatalogController tests passed.");
    }

    private final ProductCatalogController controller =
        new ProductCatalogController(ProductCatalogService.withSampleData());

    void listsAllProducts() {
        ApiResponse response = controller.handle("GET", "/products", null);
        assertEquals(200, response.getStatusCode(), "listsAllProducts status");
        assertContains(response.getBody(), "\"count\":4", "listsAllProducts body");
        assertContains(response.getBody(), "Trail Running Shoes", "listsAllProducts names");
    }

    void filtersProductsByMerchantAndCategory() {
        ApiResponse response = controller.handle("GET", "/products", "merchantId=M-100&category=apparel");
        assertEquals(200, response.getStatusCode(), "filtersProductsByMerchantAndCategory status");
        assertContains(response.getBody(), "\"count\":1", "filtersProductsByMerchantAndCategory count");
        assertContains(response.getBody(), "Weatherproof Jacket", "filtersProductsByMerchantAndCategory result");
    }

    void returnsSingleProductById() {
        ApiResponse response = controller.handle("GET", "/products/P-300", null);
        assertEquals(200, response.getStatusCode(), "returnsSingleProductById status");
        assertContains(response.getBody(), "\"id\":\"P-300\"", "returnsSingleProductById id");
        assertContains(response.getBody(), "\"available\":false", "returnsSingleProductById availability");
    }

    void rejectsUnsupportedMethods() {
        ApiResponse response = controller.handle("POST", "/products", null);
        assertEquals(405, response.getStatusCode(), "rejectsUnsupportedMethods status");
        assertContains(response.getBody(), "Method not allowed", "rejectsUnsupportedMethods body");
    }

    void rejectsEmptyFilterValues() {
        ApiResponse response = controller.handle("GET", "/products", "merchantId=");
        assertEquals(400, response.getStatusCode(), "rejectsEmptyFilterValues status");
        assertContains(response.getBody(), "Filter values must not be empty.", "rejectsEmptyFilterValues body");
    }

    private void assertEquals(int expected, int actual, String scenario) {
        if (expected != actual) {
            throw new AssertionError(scenario + " expected " + expected + " but was " + actual);
        }
    }

    private void assertContains(String actual, String expectedSubstring, String scenario) {
        if (!actual.contains(expectedSubstring)) {
            throw new AssertionError(scenario + " expected body to contain: " + expectedSubstring + " but was: " + actual);
        }
    }
}