package org.antonio;

import java.sql.SQLException;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    DataRetriever dataRetriever = new DataRetriever();

    try{
      testGetAllCategories(dataRetriever);
      testGetProductList(dataRetriever);
    } catch (SQLException e) {
      handleError(e);
    }
  }

  private static void testGetAllCategories(DataRetriever dataRetriever) throws SQLException {
    System.out.println(" -- Get all Categories Test -- ");
    List<Category> categories = dataRetriever.getAllCategories();
    for (Category category : categories) {
      System.out.println("ID : " + category.getId() + ", Name : " + category.getName());
    }
  }

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

  private static void handleError (SQLException e) {
    System.out.println("Error" + e.getMessage());
    e.printStackTrace();
  }
}