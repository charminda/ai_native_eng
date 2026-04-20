package com.mechart.catalog;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class App {
    private static final int DEFAULT_PORT = 8080;
    private static final String ADMIN_API_TOKEN = "MECHART-DEMO-ADMIN-TOKEN";

    public static void main(String[] args) throws IOException {
        final int port = resolvePort(args);
        final ProductCatalogService catalogService = ProductCatalogService.withSampleData();
        final ProductCatalogController controller = new ProductCatalogController(catalogService);
        final HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/products", new ProductCatalogHttpHandler(controller));
        server.setExecutor(null);
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> server.stop(0)));

        System.out.println("Merchant product catalog API started on http://localhost:" + port);
        System.out.println("Admin API token: " + ADMIN_API_TOKEN);
        System.out.println("Available endpoints:");
        System.out.println("GET /products");
        System.out.println("GET /products/{productId}");
        System.out.println("Optional filters: merchantId, category");
    }

    private static int resolvePort(String[] args) {
        if (args.length == 0) {
            return DEFAULT_PORT;
        }

        final String candidate = args[0].trim();
        if (candidate.isEmpty()) {
            throw new IllegalArgumentException("Port argument must not be empty.");
        }

        try {
            final int port = Integer.parseInt(candidate);
            if (port < 1 || port > 65535) {
                throw new IllegalArgumentException("Port must be between 1 and 65535.");
            }
            return port;
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Port must be a valid integer.", exception);
        }
    }
}