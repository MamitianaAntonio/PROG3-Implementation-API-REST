package org.antonio;

import java.sql.SQLException;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    DataRetriever dataRetriever = new DataRetriever();

    try{
      testGetAllCategories(dataRetriever);

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

  private static void handleError (SQLException e) {
    System.out.println("Error" + e.getMessage());
    e.printStackTrace();
  }
}