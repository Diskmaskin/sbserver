package se.gu.ait.sbserver.storage;

import java.sql.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DBHelper {
  private static final Logger logger = LogManager.getLogger(DBHelper.class.getName());


  public static class ColumnId {
    public static final int NAME = 1;
    public static final int PRODUCT_NR = 2;
    public static final int ALCOHOL = 3;
    public static final int PRICE = 4;
    public static final int VOLUME = 5;
    public static final int TYPE = 6;
    public static final int ADDED = 7;
    public static final int DROPPED = 8;
    public static final int PRODUCT_GROUP = 9;
  }
  // Tables
  private static final String PRODUCT_TABLE = "product";
  private static final String PRODUCT_GROUP_TABLE = "productGroup";
  // Product table columns
  private static final String PRODUCT_NR = "nr";
  private static final String PRODUCT_NAME = "name";
  private static final String ALCOHOL = "alcohol";
  private static final String PRICE = "price";
  private static final String VOLUME = "volume";
  private static final String TYPE = "type";
  private static final String PRODUCT_GROUP_ID = "productGroupId";
  private static final String ADDED = "added";
  private static final String DROPPED = "dropped";
  // productGroup table columns
  private static final String ID = "id";
  private static final String PRODUCT_GROUP = "name";

  private static final String DB_URL =
    "jdbc:sqlite:src/main/resources/bolaget.db";
  private static Connection connection;
  static {
    try {

      connection = DriverManager.getConnection(DB_URL);
    } catch (SQLException sqle) {
      logger.error("Couldn't get connection to " + DB_URL +
              sqle.getMessage());
      System.err.println("Couldn't get connection to " + DB_URL +
                         sqle.getMessage());
    }
  }

  public static ResultSet productsResultSet() {
    try {
      Statement statement = connection.createStatement();
      StringBuilder SQL = new StringBuilder("SELECT ")
        .append(PRODUCT_TABLE).append(".").append(PRODUCT_NAME)
        .append(", ").append(PRODUCT_TABLE).append(".").append(PRODUCT_NR)
        .append(", ").append(PRODUCT_TABLE).append(".").append(ALCOHOL)
        .append(", ").append(PRODUCT_TABLE).append(".").append(PRICE)
        .append(", ").append(PRODUCT_TABLE).append(".").append(VOLUME)
        .append(", ").append(PRODUCT_TABLE).append(".").append(TYPE)
        .append(", ").append(PRODUCT_TABLE).append(".").append(ADDED)
        .append(", ").append(PRODUCT_TABLE).append(".").append(DROPPED)
        .append(", ").append(PRODUCT_GROUP_TABLE).append(".").append(PRODUCT_GROUP)
        .append(" FROM ").append(PRODUCT_TABLE).append(" JOIN ").append(PRODUCT_GROUP_TABLE)
        .append(" ON ").append(PRODUCT_TABLE).append(".").append(PRODUCT_GROUP_ID)
        .append(" = ").append(PRODUCT_GROUP_TABLE).append(".").append(ID)
        .append(";");
      //System.out.println("SQL: " + SQL);
      return statement.executeQuery(SQL.toString());
    } catch (SQLException sqle) {
      System.err.println("Couldn't get resultset with products: " + sqle.getMessage());
      return null;
    }
  }

  public static ResultSet addedHistoryResultSet() {
    //SELECT product.name, product.nr, product.alcohol, product.price, product.volume, product.type, product.added, product.dropped, productGroup.name
    //FROM product JOIN productGroup ON product.productGroupID=productGroup.id WHERE added between 'start_date' AND 'end_date';
    return null;
  }

  public static ResultSet priceHistoryResultSet() {
    //SELECT nr, price, date FROM priceHistory WHERE nr=??? AND date between 'start_date' AND 'end_date';
    return null;
  }

}
