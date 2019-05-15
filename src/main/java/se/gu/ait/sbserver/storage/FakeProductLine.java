package se.gu.ait.sbserver.storage;

import se.gu.ait.sbserver.domain.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <p>An implementation of ProuctLine which fakes some hard coded products.</p>
 */
public class FakeProductLine implements ProductLine {

  private List<Product> products;

  // Prevent instantiation from outside this package
  FakeProductLine() { }
  
  public List<Product> getProductsFilteredBy(Predicate<Product> predicate) {
    if (products == null) {      
      //createFakeProducts();
      createFakeProductsFromCSV();
    }
    return products.stream().filter(predicate).collect(Collectors.toList());
  }
  
  public List<Product> getAllProducts() {
    if (products == null) {
      //createFakeProducts();
      createFakeProductsFromCSV();
    }
    return products;
  }

  private Product getFakeProduct(String name, String price,
                                 String alcohol,
                                 String volume,
                                 String nr,
                                 String productGroup,
                                 String type) {
    return new Product.Builder()
      .name(name)
      .price(Double.parseDouble(price))
      .alcohol(Double.parseDouble(alcohol))
      .volume((int)Double.parseDouble(volume))
      .nr(Integer.parseInt(nr))
      .productGroup(productGroup)
      .type(type)
      .build();
    
  }

  // Set the products list by calling the helper
  // method in CSVHelper
  private void createFakeProductsFromCSV() {
    if (products == null) {
      products = CSVHelper.productsFromCSV();
    }
    System.out.println("Read " + products.size() + " products from csv.");
  }

  private void createFakeProducts() {
    System.out.println("Creating products from CSV");
    products = new ArrayList<>();
    products.add(getFakeProduct("Johanneshof Reinisch Pinot Noir","143.0","13.0","750","7440201","Rött vin",""));
    products.add(getFakeProduct("Dobogó Tokaji Furmint","187.0","13.5","750","7598701","Vitt vin",""));
    products.add(getFakeProduct("Château de Cazeneuve Carignan","250.0","13.5","750","7105201","Rött vin",""));
    products.add(getFakeProduct("Engelholms Pacific Pilsner","21.0","5.0","330","3067603","Öl","Ljus lager"));
    products.add(getFakeProduct("Speyside Sherry Cask 21 Years","1537.0","55.1","700","8591801","Whisky","Malt"));
    products.add(getFakeProduct("Giró i Giró Montaner Brut Nature Gran Reserva","151.0","11.5","750","7723101","Mousserande vin","Vitt Torrt"));
    products.add(getFakeProduct("Clynelish No 4051 17 Years","1583.0","55.0","700","8515601","Whisky","Malt"));
    products.add(getFakeProduct("Albarossa","252.0","14.0","750","7321901","Rött vin",""));
    products.add(getFakeProduct("Los Frailes Monastrell Garnacha","115.0","13.5","750","7444301","Rött vin",""));
    products.add(getFakeProduct("Auchentoshan Maltbarn Bourbon Cask 23 Years","1735.0","52.0","700","8537101","Whisky","Malt"));
    products.add(getFakeProduct("Albin Jacumin Châteauneuf-du-Pape","246.0","14.5","750","7551101","Rött vin",""));
    products.add(getFakeProduct("Bibbiano Chianti Classico","147.0","13.5","750","7539001","Rött vin",""));
    products.add(getFakeProduct("Chapter 7 Irish Whiskey Bourbon Hogshead 14 Years","1113.0","56.7","700","8771001","Whisky","Malt"));
    products.add(getFakeProduct("Amour de Deutz","796.0","12.0","375","9602502","Mousserande vin","Vitt Torrt"));
    products.add(getFakeProduct("Deutz Exclusive Gift Box","2397.0","12.0","1125","9696209","Mousserande vin",""));
    products.add(getFakeProduct("Villa Spinosa Valpolicella Classico","130.0","12.0","750","7249001","Rött vin",""));
    products.add(getFakeProduct("D'Aria Sauvignon Blanc","146.0","13.0","750","7337501","Vitt vin",""));
    products.add(getFakeProduct("Fairtransport Tres Hombres 21 Años","885.0","41.6","700","8712101","Rom","Mörk"));
    products.add(getFakeProduct("Donatella Cinelli Colombini","1104.0","14.0","4500","7475209","Rött vin",""));
    products.add(getFakeProduct("Abrigo Giovanni Piemonte Mix 1","732.0","13.5","4500","7096809","Rött vin",""));
    //products.add(getFakeProduct("", "", "", "", "", "", "", "");
  }

  @Override
  public List<Product> getProductsByAddedHistory(String start, String end) {
    return null;
  }

  @Override
  public List<Product> getProductByPriceHistory(int nr, String start, String end) {
    return null;
  }
  
}
