package se.gu.ait.sbserver.storage;

import se.gu.ait.sbserver.domain.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <p>An implementation of ProuctLine which reads products from the database.
 * </p>
 */
public class SQLBasedProductLine implements ProductLine {

  private List<Product> products;

  // Prevent instantiation from outside this package
  SQLBasedProductLine() { }
  
  public List<Product> getProductsFilteredBy(Predicate<Product> predicate) {
    readProductsFromDatabase();
    return products.stream().filter(predicate).collect(Collectors.toList());
  }
  
  public List<Product> getAllProducts() {
    readProductsFromDatabase();
    return products;
  }

  private void readProductsFromDatabase() {
    System.out.println("Reading products from database.");
    products = new ArrayList<>();
    try {
      ResultSet rs = DBHelper.productsResultSet();
      while (rs.next()) {
        String name;
        double alcohol;
        int volume;
        double price;
        int nr;
        String productGroup;
        String type;
        String added;
        int dropped;
        name = rs.getString(DBHelper.ColumnId.NAME);
        alcohol = rs.getDouble(DBHelper.ColumnId.ALCOHOL);
        volume = rs.getInt(DBHelper.ColumnId.VOLUME);
        price = rs.getDouble(DBHelper.ColumnId.PRICE);
        nr = rs.getInt(DBHelper.ColumnId.PRODUCT_NR);
        type = rs.getString(DBHelper.ColumnId.TYPE);
        added = rs.getString(DBHelper.ColumnId.ADDED);
        dropped = rs.getInt(DBHelper.ColumnId.DROPPED);
        productGroup = rs.getString(DBHelper.ColumnId.PRODUCT_GROUP);
        // System.out.println("\nprice: " + price + "\nalcohol: " + alcohol + "\nvolume: " + volume + "\nnr: " + nr + "\nproductGroup: " + productGroup + 
        //               "\ntype: " + type + "\nadded: " + added + "\ndropped: " + dropped);
        products.add(new Product.Builder()
                     .name(name)
                     .price(price)
                     .alcohol(alcohol)
                     .volume(volume)
                     .nr(nr)
                     .productGroup(productGroup)
                     .type(type)
                     .added(added)
                     .dropped(dropped)
                     .build());
      }
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
  }

  @Override
  public List<Product> getProductsByAddedHistory(String start, String end) {
    DBHelper dbHelper = new DBHelper();
    System.out.println("Reading history products from database.");
    products = new ArrayList<>();
    try {
      ResultSet rs = dbHelper.addedHistoryResultSet(start, end);
      while (rs.next()) {
        String name;
        double alcohol;
        int volume;
        double price;
        int nr;
        String productGroup;
        String type;
        String added;
        int dropped;
        name = rs.getString(DBHelper.ColumnId.NAME);
        alcohol = rs.getDouble(DBHelper.ColumnId.ALCOHOL);
        volume = rs.getInt(DBHelper.ColumnId.VOLUME);
        price = rs.getDouble(DBHelper.ColumnId.PRICE);
        nr = rs.getInt(DBHelper.ColumnId.PRODUCT_NR);
        type = rs.getString(DBHelper.ColumnId.TYPE);
        productGroup = rs.getString(DBHelper.ColumnId.PRODUCT_GROUP);
        added = rs.getString(DBHelper.ColumnId.ADDED);
        dropped = rs.getInt(DBHelper.ColumnId.DROPPED);
        // System.out.println("\nprice: " + price + "\nalcohol: " + alcohol + "\nvolume: " + volume + "\nnr: " + nr + "\nproductGroup: " + productGroup + 
        //               "\ntype: " + type + "\nadded: " + added + "\ndropped: " + dropped);
        products.add(new Product.Builder()
                     .name(name)
                     .price(price)
                     .alcohol(alcohol)
                     .volume(volume)
                     .nr(nr)
                     .productGroup(productGroup)
                     .type(type)
                     .added(added)
                     .dropped(dropped)
                     .build());
      }
      dbHelper = null;
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
    return products;
  }

  @Override
  public List<Product> getProductByPriceHistory(int reqNr, String start, String end) {
    DBHelper dbHelper = new DBHelper();
    System.out.println("Reading products from database by price history.");
    products = new ArrayList<>();
    try {
      ResultSet rs = dbHelper.priceHistoryResultSet(reqNr, start, end);
      while (rs.next()) {
        String name;
        double alcohol;
        int volume;
        double price;
        int nr;
        String productGroup;
        String type;
        String added;
        int dropped;
        name = rs.getString(DBHelper.ColumnId.NAME);
        alcohol = rs.getDouble(DBHelper.ColumnId.ALCOHOL);
        volume = rs.getInt(DBHelper.ColumnId.VOLUME);
        price = rs.getDouble(DBHelper.ColumnId.PRICE);
        nr = rs.getInt(DBHelper.ColumnId.PRODUCT_NR);
        type = rs.getString(DBHelper.ColumnId.TYPE);
        productGroup = rs.getString(DBHelper.ColumnId.PRODUCT_GROUP);
        added = rs.getString(DBHelper.ColumnId.ADDED);
        dropped = rs.getInt(DBHelper.ColumnId.DROPPED);
        products.add(new Product.Builder()
                     .name(name)
                     .price(price)
                     .alcohol(alcohol)
                     .volume(volume)
                     .nr(nr)
                     .productGroup(productGroup)
                     .type(type)
                     .added(added)
                     .dropped(dropped)
                     .build());
                    //  System.out.println("\nprice: " + price + "\nalcohol: " + alcohol + "\nvolume: " + volume + "\nnr: " + nr + "\nproductGroup: " + productGroup + 
                    //   "\ntype: " + type + "\nadded: " + added + "\ndropped: " + dropped);
      }
      dbHelper = null;
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
    return products;
  }
}
