package sample;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 * @author Aaron Rezentes
 * purpose: keeps track of all the parts and products aswell as producing unique ID's for each
 */

public class Inventory
{
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int partID = 0;
    private static int productID = 0;

    /**
     * @param newPart the part to be added
     */
    public static void addPart (Part newPart)
    {
        allParts.add(newPart);
    }

    /**
     * @param newProduct the product to be added
     */
    public static void addProduct(Product newProduct)
    {
        allProducts.add(newProduct);
    }

    /**
     * @return the part found by the search
     * @param partId the ID to be searched for
     */
    public static Part lookupPart(int partId)
    {
        Part searchedPart = null;

        for (Part part : allParts)
        {
            if (part.getId() == partId)
            {
                searchedPart = part;
            }
        }

        return searchedPart;
    }

    /**
     * @return the product found by the search
     * @param productId the ID to be searched for
     */
    public static Product lookupProduct(int productId)
    {
        Product searchedProduct = null;

        for (Product product : allProducts)
        {
            if (product.getId() == productId)
            {
                searchedProduct = product;
            }
        }

        return searchedProduct;
    }

    /**
     * @return the parts found by the search
     * @param partName the string to be searched for
     */
    public static ObservableList<Part> lookupPart(String partName)
    {
        ObservableList<Part> searchedParts = FXCollections.observableArrayList();

        for(Part part: allParts)
        {
            if(part.getName().toUpperCase().contains(partName.toUpperCase()))
            {
                searchedParts.add(part);
            }
        }
        return searchedParts;
    }

    /**
     * @return the products found by the search
     * @param productName the name to be searched for
     */
    public static ObservableList<Product> lookupProduct(String productName)
    {
        ObservableList<Product> searchedProducts = FXCollections.observableArrayList();

        for(Product product: allProducts)
        {
            if(product.getName().toUpperCase().contains(productName.toUpperCase()))
            {
                searchedProducts.add(product);
            }
        }
        return searchedProducts;
    }

    /**
     * @param index the index of the part
     * @param selectedPart the new part to replace to the old one
     */
    public static void updatePart(int index, Part selectedPart)
    {
        allParts.set(index, selectedPart);
    }

    /**
     * @param index the index of the product
     * @param newProduct the new product to replace the old one
     */
    public static void updateProduct(int index, Product newProduct)
    {
        allProducts.set(index, newProduct);
    }

    /**
     * @return whether the part was removed successfully
     * @param selectedPart the selected part
     */
    public static boolean deletePart(Part selectedPart)
    {
        if (allParts.contains(selectedPart))
        {
            allParts.remove(selectedPart);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * @return whether the product was removed successfully
     * @param selectedProduct the selected product
     */
    public static boolean deleteProduct(Product selectedProduct)
    {
        if (allProducts.contains(selectedProduct))
        {
            allProducts.remove(selectedProduct);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * @return the full list of parts
     */
    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }

    /**
     * @return the full list of products
     */
    public static ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }

    /**
     * @return the new unique part ID
     */
    public static int getNewPartID()
    {
        partID++;
        return partID;
    }

    /**
     * @return the new unique product ID
     */
    public static int getNewProductID()
    {
        productID++;
        return productID;
    }
}
