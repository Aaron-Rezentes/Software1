package sample;

/**
 * @author Aaron Rezentes
 * purpose: A product that can be associated with 0 or more parts
 */

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class Product
{
    private ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * @param id the ID for this product
     * @param name the name for this product
     * @param price the price for this product
     * @param stock the stock for this product
     * @param min the min for this product
     * @param max the max for this product
     */
    public Product(int id, String name, double price, int stock, int min, int max)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        associatedParts = FXCollections.observableArrayList();
    }

    /**
     * @return the id
     */
    public int getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice()
    {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price)
    {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock()
    {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock)
    {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin()
    {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min)
    {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax()
    {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max)
    {
        this.max = max;
    }

    /**
     * @param part the part to be associated
     */
    public void addAssociatedPart(Part part)
    {
        associatedParts.add(part);
    }

    /**
     * @param selectedAssociatedPart the selected part
     * @return whether the part was successfully dissociated
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart)
    {
        if (associatedParts.contains(selectedAssociatedPart))
        {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * @return the full list of associated parts
     */
    public ObservableList<Part> getAllAssociatedParts()
    {
        return associatedParts;
    }
}
