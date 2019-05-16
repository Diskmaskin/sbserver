package se.gu.ait.sbserver.main;

import java.util.List;
import java.util.ArrayList;

import se.gu.ait.sbserver.domain.Product;
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

        newProducts = ProductLineFactory.getXMLBasedProductLine().getAllProducts();
        oldProducts = ProductLineFactory.getProductLine().getAllProducts();
        List<Product> changedProducts = new ArrayList<Product>();
        List<Product> newPriceProducts = new ArrayList<Product>();

        System.out.println("\nBEFORE");
        System.out.println("newProducts.size: " + newProducts.size());
        System.out.println("oldProducts.size: " + oldProducts.size());
        for (Product np : newProducts) {
            if (!oldProducts.contains(np)) {
                changedProducts.add(np);
                for (Product op : oldProducts) {
                    if (op.nr() == np.nr()) {
                        if (op.price() != np.price()) {
                            newPriceProducts.add(np);
                        }
                    }
                }
            }
        }
        System.out.println("\nAFTER");
        System.out.println("newProducts.size: " + newProducts.size());
        System.out.println("changedProducts.size: " + changedProducts.size());
        System.out.println("newPriceProducts.size: " + newPriceProducts.size());
        //REPLACE product-PRODUCTS IN DATABASE
        //INSERT NEW priceHistory-PRODUCTS IN DATABASE
    }
}