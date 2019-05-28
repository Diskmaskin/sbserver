package se.gu.ait.sbserver.main;

import java.util.List;
import java.util.ArrayList;

import se.gu.ait.sbserver.domain.Product;
import se.gu.ait.sbserver.storage.DBHelper;
import se.gu.ait.sbserver.storage.ProductLineFactory;
import se.gu.ait.sbserver.storage.XMLBasedProductLine;
import se.gu.ait.sbserver.storage.SQLBasedProductLine;

public class Update {

    private List<Product> newProducts;
    private List<Product> oldProducts;

    public static void main(String[] args) {
        new Update();
    }

    public Update() {
        System.setProperty("PL", "ProductLine");
        DBHelper dbHelper = new DBHelper();

        newProducts = ProductLineFactory.getXMLBasedProductLine().getAllProducts();
        oldProducts = ProductLineFactory.getProductLine().getAllProducts();
        List<Product> changedProducts = new ArrayList<Product>();
        List<Product> newPriceProducts = new ArrayList<Product>();
        List<String> newProductGroups = new ArrayList<String>();

        System.out.println("\n========================================");
        System.out.println("Products from Systembolaget API: " + newProducts.size());
        System.out.println("Existing products in database: " + oldProducts.size());
        System.out.println("========================================");

        System.out.println("\nChecking which products are new or has been changed...");
        for (Product np : newProducts) {
            if (!oldProducts.contains(np)) {
                changedProducts.add(np);
                if (!dbHelper.isProductGroup(np.productGroup())) {
                    System.out.println("New product group encountered.");
                    dbHelper.insertNewProductGroup(np.productGroup());
                    System.out.println("New product group created.");
                }
                for (Product op : oldProducts) {
                    if (op.nr() == np.nr()) {
                        if (op.price() != np.price()) {
                            newPriceProducts.add(np);
                        }
                    }
                }
            }
        }

        if (newPriceProducts.size() > 0) {
            //INSERT NEW priceHistory-PRODUCTS INTO DATABASE
            System.out.println("\nInserting old prices into price history in database...");
            dbHelper.insertPriceHistory(newPriceProducts);
        } else {
            System.out.println("\nNo prices changed.");
        }
        
        if (changedProducts.size() > 0) {
                //REPLACE product-PRODUCTS INTO DATABASE
            System.out.println("\nInserting new products and updating existing product's information in database...");
            if (changedProducts.size() > 500) System.out.println("(This may take a while)");
            dbHelper.updateProducts(changedProducts);
            
            System.out.print("\nUpdate finished.");
        } else {
            System.out.print("\nNo updates required.");
        }
        
        System.out.print(" Your database is up to date.");

        System.out.println("\n========================================");
        System.out.println("Changed products: " + changedProducts.size());
        System.out.println("Changed price products: " + newPriceProducts.size());
        System.out.println("========================================");

    }
}