package sample;

/**
 * @author Aaron Rezentes
 * course: C482
 * purpose: A series of GUI menus to keep track of Proucts as well as their related and unrelated parts
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * @param primaryStage the primary stage used
     */
    @Override
    public void start(Stage primaryStage)
    {
        try //loads and shows the main menu
        {
            Parent root = FXMLLoader.load(getClass().getResource("MainRoot.fxml"));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch(Exception e)
        {
            System.out.println("Error, fxml file not found");
        }
    }


    /**
     * @param args the args to be launched? lol
     * java doc is in a folder titled "JavaDoc" in the path "C482 Final\src"
     * FUTURE ENHANCEMENT: The program would be a bit more filled out if it had a search bar for searching the associated parts
     */
    public static void main(String[] args)
    {
        Part Cheese = new InHouse(Inventory.getNewPartID(), "Cheese", 1.99, 10, 1, 25,12);
        Part Bread = new Outsourced(Inventory.getNewPartID(), "Bread", 0.99, 40, 1, 100, "Bread Shop");
        Part Meat = new Outsourced(Inventory.getNewPartID(), "Meat", 3.99, 20, 1, 50, "Meat Shop");
        Product Hamburger = new Product(Inventory.getNewProductID(),"Hamburger", 6.99, 5, 1, 10);
        Product Cheeseburger = new Product(Inventory.getNewProductID(),"Cheeseburger", 8.99, 4, 1, 10);

        //adds some parts and products I made to the inventory
        Inventory.addPart(Cheese);
        Inventory.addPart(Bread);
        Inventory.addPart(Meat);
        Hamburger.addAssociatedPart(Bread);
        Hamburger.addAssociatedPart(Meat);
        Cheeseburger.addAssociatedPart(Cheese);
        Cheeseburger.addAssociatedPart(Bread);
        Cheeseburger.addAssociatedPart(Meat);
        Inventory.addProduct(Hamburger);
        Inventory.addProduct(Cheeseburger);

        launch(args);
    }
}
