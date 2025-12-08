package org.antonio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {
  // code to get all categories
  public List<Category> getAllCategories() throws SQLException {
    List<Category> categories = new ArrayList<>();
    String sql = "SELECT id, name FROM Product_category";

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql);
         ResultSet resultSet = statement.executeQuery()) {

      while(resultSet.next()) {
        Category category = new Category(
            resultSet.getInt("id"),
          resultSet.getString("name")
        );
        categories.add(category);
      }
    }
    return categories;
  }

  // code to get product list
  List<Product> getProductList(int page, int size) throws SQLException {
    List<Product> products = new ArrayList<>();
    int offset = (page - 1) * size;
    String sql = """
        SELECT  p.id AS product_id,
            p.name AS product_name,
            p.creation_datetime,
            c.id AS category_id,
            c.name AS category_name
        FROM Product p
        LEFT JOIN Product_category c ON c.product_id = p.id
        ORDER BY p.id
        LIMIT ? OFFSET ?;
        """;

    try (Connection connection = DBConnection.getConnection();
      PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, size);
      statement.setInt(2, offset);

      ResultSet resultSet = statement.executeQuery(); {
        while(resultSet.next()) {
          Category category = null;
          int categoryId = resultSet.getInt("category_id");
          String categoryName = resultSet.getString("category_name");

          if (categoryName != null) {
            category = new Category(categoryId, categoryName);
          }

          Product product = new Product (
              resultSet.getInt("product_id"),
              resultSet.getString("product_name"),
              resultSet.getTimestamp("creation_datetime").toInstant(),
              category
          );

          products.add(product);
        }
      }
    }
    return products;
  }
}
