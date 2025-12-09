package org.antonio;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataRetrieverTest {
  private DataRetriever dataRetriever = new DataRetriever();

  @Test
  void testGetAllCategories() throws SQLException {
    List<Category> categories = dataRetriever.getAllCategories();

    assertNotNull(categories);
    assertFalse(categories.isEmpty());
  }

  @Test
  void testGetProductListPage1Size10() throws SQLException {
    List<Product> products = dataRetriever.getProductList(1, 10);
    assertNotNull(products);
    assertTrue(products.size() <= 10);
  }

  @Test
  void testGetProductListPage1Size5() throws SQLException {
    List<Product> products = dataRetriever.getProductList(1, 5);
    assertNotNull(products);
    assertTrue(products.size() <= 5);
  }

  @Test
  void testGetProductListPage1Size3() throws SQLException {
    List<Product> products = dataRetriever.getProductList(1, 3);
    assertNotNull(products);
    assertTrue(products.size() <= 3);
  }

  @Test
  void testGetProductListPage2Size2() throws SQLException {
    List<Product> products = dataRetriever.getProductList(2, 2);
    assertNotNull(products);
    assertTrue(products.size() <= 2);
  }

  @Test
  void testGetProductsByCriteriaDell() throws SQLException {
    List<Product> results = dataRetriever.getProductsByCriteria(
        "Dell", null, null, null, 1, 10
    );
    assertNotNull(results);
  }

  @Test
  void testGetProductsByCriteriaInfo() throws SQLException {
    List<Product> results = dataRetriever.getProductsByCriteria(
        null, "info", null, null, 1, 10
    );
    assertNotNull(results);
  }

  @Test
  void testGetProductsByCriteriaiPhoneMobile() throws SQLException {
    List<Product> results = dataRetriever.getProductsByCriteria(
        "iPhone", "mobile", null, null, 1, 10
    );
    assertNotNull(results);
  }

  @Test
  void testGetProductsByCriteriaDateRange() throws SQLException {
    Instant min = Instant.parse("2024-02-01T00:00:00Z");
    Instant max = Instant.parse("2024-03-01T23:59:59Z");

    List<Product> results = dataRetriever.getProductsByCriteria(
        null, null, min, max, 1, 10
    );
    assertNotNull(results);
  }

  @Test
  void testGetProductsByCriteriaSamsungBureau() throws SQLException {
    List<Product> results = dataRetriever.getProductsByCriteria(
        "Samsung", "bureau", null, null, 1, 10
    );
    assertNotNull(results);
  }

  @Test
  void testGetProductsByCriteriaSonyInfo() throws SQLException {
    List<Product> results = dataRetriever.getProductsByCriteria(
        "Sony", "informatique", null, null, 1, 10
    );
    assertNotNull(results);
  }

  @Test
  void testGetProductsByCriteriaAudioDateRange() throws SQLException {
    Instant min = Instant.parse("2024-01-01T00:00:00Z");
    Instant max = Instant.parse("2024-12-01T23:59:59Z");

    List<Product> results = dataRetriever.getProductsByCriteria(
        null, "audio", min, max, 1, 10
    );
    assertNotNull(results);
  }

  @Test
  void testGetProductsByCriteriaNoFilters() throws SQLException {
    List<Product> results = dataRetriever.getProductsByCriteria(
        null, null, null, null, 1, 10
    );
    assertNotNull(results);
  }

  @Test
  void testGetProductsByCriteriaWithPagination1() throws SQLException {
    List<Product> results = dataRetriever.getProductsByCriteria(
        null, null, null, null, 1, 10
    );
    assertNotNull(results);
    assertTrue(results.size() <= 10);
  }

  @Test
  void testGetProductsByCriteriaWithPagination2() throws SQLException {
    List<Product> results = dataRetriever.getProductsByCriteria(
        "Dell", null, null, null, 1, 5
    );
    assertNotNull(results);
    assertTrue(results.size() <= 5);
  }

  @Test
  void testGetProductsByCriteriaWithPagination3() throws SQLException {
    List<Product> results = dataRetriever.getProductsByCriteria(
        null, "Informatique", null, null, 1, 10
    );
    assertNotNull(results);
    assertTrue(results.size() <= 10);
  }

  @Test
  void testConsistencyBetweenMethods() throws SQLException {
    List<Product> allProducts = dataRetriever.getProductList(1, 100);
    List<Product> allViaCriteria = dataRetriever.getProductsByCriteria(
        null, null, null, null, 1, 100
    );

    assertEquals(allProducts.size(), allViaCriteria.size());
  }
}