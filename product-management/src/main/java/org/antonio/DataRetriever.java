package org.antonio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {
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
}
