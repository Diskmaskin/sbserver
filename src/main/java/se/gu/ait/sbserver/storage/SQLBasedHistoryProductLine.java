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
public class SQLBasedHistoryProductLine implements ProductLine {

  private List<Product> products;

  // Prevent instantiation from outside this package
  SQLBasedHistoryProductLine() { }
  
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
        name = rs.getString(DBHelper.ColumnId.NAME);
        alcohol = rs.getDouble(DBHelper.ColumnId.ALCOHOL);
        volume = rs.getInt(DBHelper.ColumnId.VOLUME);
        price = rs.getDouble(DBHelper.ColumnId.PRICE);
        nr = rs.getInt(DBHelper.ColumnId.PRODUCT_NR);
        type = rs.getString(DBHelper.ColumnId.TYPE);
        productGroup = rs.getString(DBHelper.ColumnId.PRODUCT_GROUP);
        products.add(new Product.Builder()
                     .name(name)
                     .price(price)
                     .alcohol(alcohol)
                     .volume(volume)
                     .nr(nr)
                     .productGroup(productGroup)
                     .type(type)
                     .build());
      }
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
  }  
}
