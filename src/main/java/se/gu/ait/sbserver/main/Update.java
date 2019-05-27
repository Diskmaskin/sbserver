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
        System.out.println("Products in database: " + oldProducts.size());
        System.out.println("========================================");

        for (Product np : newProducts) {
            if (!oldProducts.contains(np)) {
                changedProducts.add(np);
                if (!dbHelper.isProductGroup(np.productGroup())) dbHelper.insertNewProductGroup(np.productGroup());
                for (Product op : oldProducts) {
                    if (op.nr() == np.nr()) {
                        if (op.price() != np.price()) {
                            newPriceProducts.add(np);
                        }
                    }
                }
            }
        }

        //INSERT NEW priceHistory-PRODUCTS INTO DATABASE
        dbHelper.insertPriceHistory(newPriceProducts);

        //REPLACE product-PRODUCTS INTO DATABASE
        dbHelper.updateProducts(changedProducts);
        
        


        System.out.println("\n========================================");
        System.out.println("Changed products: " + changedProducts.size());
        System.out.println("Changed price products: " + newPriceProducts.size());
        System.out.println("========================================");

    }
}