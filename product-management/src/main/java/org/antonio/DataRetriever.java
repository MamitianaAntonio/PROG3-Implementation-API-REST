package org.antonio;

import java.sql.*;
import java.time.Instant;
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

  // getProduct by criteria methods
  List<Product> getProductsByCriteria (String productName, String categoryName, Instant creationMin, Instant creationMax) throws SQLException {
    List<Product> products = new ArrayList<>();

    StringBuilder sql = new StringBuilder();
    List<Object> params = new ArrayList<>();

    sql.append("""
        SELECT  p.id AS product_id,
          p.name AS product_name,
          p.creation_datetime,
          c.id AS category_id,
          c.name AS category_name
        FROM Product p
        LEFT JOIN Product_category c ON c.product_id = p.id
        WHERE 1 = 1
    """);

    if (productName != null && !productName.isEmpty()) {
      sql.append(" AND p.name ILIKE ?");
      params.add(productName.trim());
    }

    if (categoryName != null && !categoryName.isEmpty()) {
      sql.append(" AND c.name ILIKE ?");
      params.add(categoryName.trim());
    }

    if (creationMin != null) {
      sql.append(" AND p.creation_datetime >= ?");
      params.add(Timestamp.from(creationMin));
    }

    if (creationMax != null) {
      sql.append(" AND p.creation_datetime <= ?");
      params.add(Timestamp.from(creationMax));
    }

    sql.append(" ORDER BY p.creation_datetime DESC;");

    try (Connection connection = DBConnection.getConnection();
      PreparedStatement statement = connection.prepareStatement(sql.toString())) {

      for (int i = 0; i < params.size(); i++) {
        statement.setObject(i + 1, params.get(i));
      }

      try(ResultSet resultSet = statement.executeQuery()) {
        while(resultSet.next()) {
          Category category = null;
          int catId = resultSet.getInt("category_id");
          String catName = resultSet.getString("category_name");

          if (catName != null) {
            category = new Category(catId, catName);
          }

          Product product = new Product(
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

  List<Product> getProductsByCriteria(String productName, String categoryName, Instant creationMin, Instant creationMax, int page, int size) throws SQLException {
    int offset = (page - 1) * size;
    List<Product> products = new ArrayList<>();
    StringBuilder sql = new StringBuilder();
    List<Object> params = new ArrayList<>();

    sql.append("""
            SELECT p.id AS product_id,
                   p.name AS product_name,
                   p.creation_datetime,
                   c.id AS category_id,
                   c.name AS category_name
            FROM Product p
            LEFT JOIN Product_category c ON c.product_id = p.id
            WHERE 1=1
            """);

    // all filters
    if (productName != null && !productName.trim().isEmpty()) {
      sql.append(" AND p.name ILIKE ?");
      params.add("%" + productName.trim() + "%");
    }

    if (categoryName != null && !categoryName.trim().isEmpty()) {
      sql.append(" AND c.name ILIKE ?");
      params.add("%" + categoryName.trim() + "%");
    }

    if (creationMin != null) {
      sql.append(" AND p.creation_datetime >= ?");
      params.add(Timestamp.from(creationMin));
    }

    if (creationMax != null) {
      sql.append(" AND p.creation_datetime <= ?");
      params.add(Timestamp.from(creationMax));
    }

    sql.append(" ORDER BY p.creation_datetime DESC");

    // pagination with limit
    sql.append(" LIMIT ? OFFSET ?");
    params.add(size);
    params.add(offset);

    String finalSql = sql.toString();

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(finalSql)) {

      for (int i = 0; i < params.size(); i++) {
        statement.setObject(i + 1, params.get(i));
      }

      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          Category category = null;
          int categoryId = resultSet.getInt("category_id");
          String catName = resultSet.getString("category_name");

          if (catName != null) {
            category = new Category(categoryId, catName);
          }

          Product product = new Product(
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
