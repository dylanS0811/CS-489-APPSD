package edu.miu.cs.cs489appsd.lab1.productmgmtapp;

import edu.miu.cs.cs489appsd.lab1.productmgmtapp.model.Product;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;

public class ProductMgmtApp {

    public static void main(String[] args) {
        Product[] products = {
                new Product(
                        new BigInteger("31288741190182539912"),
                        "Banana",
                        LocalDate.parse("2026-01-24"),
                        124,
                        new BigDecimal("0.55")
                ),
                new Product(
                        new BigInteger("29274582650152771644"),
                        "Apple",
                        LocalDate.parse("2025-12-09"),
                        18,
                        new BigDecimal("1.09")
                ),
                new Product(
                        new BigInteger("91899274600128155167"),
                        "Carrot",
                        LocalDate.parse("2026-03-31"),
                        89,
                        new BigDecimal("2.99")
                ),
                new Product(
                        new BigInteger("31288741190182539913"),
                        "Banana",
                        LocalDate.parse("2026-02-13"),
                        240,
                        new BigDecimal("0.65")
                )
        };

        printProducts(products);
    }

    public static void printProducts(Product[] products) {
        Product[] sortedProducts = Arrays.stream(products)
                .sorted(Comparator.comparing(Product::getName)
                        .thenComparing(Product::getUnitPrice, Comparator.reverseOrder()))
                .toArray(Product[]::new);

        System.out.println("Printed in JSON Format");
        System.out.println(buildJson(sortedProducts));
        System.out.println();

        System.out.println("Printed in XML Format");
        System.out.println(buildXml(sortedProducts));
        System.out.println();

        System.out.println("Printed in Comma-Separated Values (CSV) Format");
        System.out.println(buildCsv(sortedProducts));
    }

    private static String buildJson(Product[] products) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");

        for (int i = 0; i < products.length; i++) {
            Product product = products[i];
            sb.append("  { ")
                    .append("\"productId\":").append(product.getProductId()).append(", ")
                    .append("\"name\":\"").append(product.getName()).append("\", ")
                    .append("\"dateSupplied\":\"").append(product.getDateSupplied()).append("\", ")
                    .append("\"quantityInStock\":").append(product.getQuantityInStock()).append(", ")
                    .append("\"unitPrice\":").append(formatMoney(product.getUnitPrice()))
                    .append(" }");

            if (i < products.length - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }

        sb.append("]");
        return sb.toString();
    }

    private static String buildXml(Product[] products) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<products>\n");

        for (Product product : products) {
            sb.append("  <product ")
                    .append("productId=\"").append(product.getProductId()).append("\" ")
                    .append("name=\"").append(product.getName()).append("\" ")
                    .append("dateSupplied=\"").append(product.getDateSupplied()).append("\" ")
                    .append("quantityInStock=\"").append(product.getQuantityInStock()).append("\" ")
                    .append("unitPrice=\"").append(formatMoney(product.getUnitPrice())).append("\" />\n");
        }

        sb.append("</products>");
        return sb.toString();
    }

    private static String buildCsv(Product[] products) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < products.length; i++) {
            Product product = products[i];
            sb.append(product.getProductId()).append(", ")
                    .append(product.getName()).append(", ")
                    .append(product.getDateSupplied()).append(", ")
                    .append(product.getQuantityInStock()).append(", ")
                    .append(formatMoney(product.getUnitPrice()));

            if (i < products.length - 1) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    private static String formatMoney(BigDecimal unitPrice) {
        return unitPrice.setScale(2, RoundingMode.HALF_UP).toPlainString();
    }
}
