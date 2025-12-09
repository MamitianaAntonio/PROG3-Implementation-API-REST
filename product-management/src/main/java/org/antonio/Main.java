package org.antonio;

public class Main {
  public static void main(String[] args) {
    DataRetriever dataRetriever = new DataRetriever();

    try{
      System.out.println(" -- Get all Categories -- ");
      System.out.println(dataRetriever.getAllCategories());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}