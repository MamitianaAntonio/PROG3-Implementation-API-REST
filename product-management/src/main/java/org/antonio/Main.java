package org.antonio;

import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    DataRetriever dataRetriever = new DataRetriever();

    try{
      //testGetAllCategories(dataRetriever);
      //testGetProductList(dataRetriever);
      //testCriteriaNoPagination(dataRetriever);
      testCriteriaWithPagination(dataRetriever);
    } catch (SQLException e) {
      handleError(e);
    }
  }

  // Test : get all categories
  private static void testGetAllCategories(DataRetriever dataRetriever) throws SQLException {
    System.out.println(" -- Get all Categories Test -- ");
    List<Category> categories = dataRetriever.getAllCategories();
    for (Category category : categories) {
      System.out.println("ID : " + category.getId() + ", Name : " + category.getName());
    }
  }

  // Test : get all product list
  private static void testGetProductList(DataRetriever dataRetriever) throws SQLException {
    System.out.println(" -- Get all Products List -- ");
    int[][] testValue = {{1, 10}, {1, 5}, {1, 3}, {2, 2}};

    for (int [] test : testValue) {
      int page = test[0];
      int size = test[1];

      System.out.println("\nPage: " + page + ", Size: " + size);
      List<Product> products = dataRetriever.getProductList(page, size);
      for (Product product : products) {
        System.out.println("ID : " + product.getId() + ", Name : " + product.getName() + ", Creation date : " +
            product.getCreationDatetime() + ", Category : " + product.getCategory());
      }
    }
  }

  // Test : criteria with no pagination
  private static void testCriteriaNoPagination (DataRetriever dataRetriever) throws SQLException {
    String[][] tests = {
        {"Dell", null, null, null},
        {null, "info", null, null},
        {"iPhone", "mobile", null, null},
        {null, null, "2024-02-01", "2024-03-01"},
        {"Samsung", "bureau", null, null},
        {"Sony", "informatique", null, null},
        {null, "audio", "2024-01-01", "2024-12-01"},
        {null, null, null, null}
    };

    for (String[] test : tests) {
      String productName = test[0];
      String categoryName = test[1];
      String minDate = test[2];
      String maxDate = test[3];

      Instant creationMin = minDate != null ? Instant.parse(minDate + "T00:00:00Z") : null;
      Instant creationMax = maxDate != null ? Instant.parse(maxDate + "T23:59:59Z") : null;

      List<Product> results = dataRetriever.getProductsByCriteria(
          productName, categoryName, creationMin, creationMax
      );

      System.out.println("Results: " + results.size());
      if (results.size() <= 5) {
        for (Product p : results) {
          String category = p.getCategory() != null ? p.getCategory().getName() : "-";
          System.out.println(" - " + p.getName() + " (" + category + ")");
        }
      }
    }
  }

  // Test : Criteria with pagination
  private static void testCriteriaWithPagination(DataRetriever dataRetriever) throws SQLException {
    Object[][] tests = {
        {null, null, null, null, 1, 10},
        {"Dell", null, null, null, 1, 5},
        {null, "Informatique", null, null, 1, 10}
    };

    for (Object[] test : tests) {
      String productName = (String) test[0];
      String categoryName = (String) test[1];
      String minDate = (String) test[2];
      String maxDate = (String) test[3];
      int page = (int) test[4];
      int size = (int) test[5];

      Instant creationMin = minDate != null ? Instant.parse(minDate + "T00:00:00Z") : null;
      Instant creationMax = maxDate != null ? Instant.parse(maxDate + "T23:59:59Z") : null;

      System.out.println("\nTest: product='" + productName +
          "', category='" + categoryName +
          "', min=" + minDate + ", max=" + maxDate +
          ", page=" + page + ", size=" + size);

      List<Product> results = dataRetriever.getProductsByCriteria(
          productName, categoryName, creationMin, creationMax, page, size
      );

      System.out.println("Results: " + results.size() + " (max " + size + ")");
      for (Product p : results) {
        String category = p.getCategory() != null ? p.getCategory().getName() : "-";
        System.out.println(" - ID: " + p.getId() +
            ", Name: " + p.getName() +
            ", Category: " + category);
      }
    }
  }

  private static void handleError (SQLException e) {
    System.out.println("Error" + e.getMessage());
    e.printStackTrace();
  }
}