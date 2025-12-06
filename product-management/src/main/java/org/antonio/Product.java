package org.antonio;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@AllArgsConstructor
@Getter
public class Product {
  private int id;
  private String name;
  private Instant creationDatetime;
  private Category category;

  // methods to get category name
  public String getCategoryName() {
    return category.getName();
  }
}