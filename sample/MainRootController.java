package sample;

/**
 * @author Aaron Rezentes
 * purpose: The controller for the main menu
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class MainRootController {

    @FXML
    private AnchorPane MainRoot;

    @FXML
    private Pane PartPane;

    @FXML
    private Label PartLabel;

    @FXML
    private TextField PartSearch;

    @FXML
    private TableView<Part> PartTable;

    @FXML
    private TableColumn<Part, Integer> PartIDColumn;

    @FXML
    private TableColumn<Part, String> PartNameColumn;

    @FXML
    private TableColumn<Part, Integer> PartInventoryColumn;

    @FXML
    private TableColumn<Part, Double> PartPriceColumn;

    @FXML
    private Button ModifyPart;

    @FXML
    private Button DeletePart;

    @FXML
    private Button AddPart;

    @FXML
    private Label InventoryLabel;

    @FXML
    private Pane ProductPane;

    @FXML
    private Label ProductLabel;

    @FXML
    private TextField ProductSearch;

    @FXML
    private TableView<Product> ProductTable;

    @FXML
    private TableColumn<Product, Integer> ProductIDColumn;

    @FXML
    private TableColumn<Product, String> ProductNameColumn;

    @FXML
    private TableColumn<Product, Integer> ProductInventoryColumn;

    @FXML
    private TableColumn<Product, Double> ProductPriceColumn;

    @FXML
    private Button ModifyProduct;

    @FXML
    private Button DeleteProduct;

    @FXML
    private Button AddProduct;

    @FXML
    private Button Exit;

    private static boolean isModifyingPart;

    private static Part selectedPart;

    private static boolean isModifyingProduct;

    private static Product selectedProduct;

    /**
     * initializes the main menu by filling the part and product lists
     */
    public void initialize()
    {
        PartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        PartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartTable.setItems(Inventory.getAllParts());

        ProductIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        ProductInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductTable.setItems(Inventory.getAllProducts());
    }

    /**
     * calls the correct part search method and sets the table to the returned parts or lack there of
     */
    @FXML
    public void searchParts()
    {
        int id = 0;
        String searchText;
        ObservableList<Part> foundParts = FXCollections.observableArrayList();

        if (PartSearch.getText() == null)
        {
            PartTable.setItems(Inventory.getAllParts());
            return;
        }
        try
        {
            id = Integer.parseInt(PartSearch.getText());
            foundParts.add(Inventory.lookupPart(id));
            PartTable.setItems(foundParts);
        }
        catch(NumberFormatException e)
        {
            searchText = PartSearch.getText();
            foundParts = Inventory.lookupPart(searchText);
            PartTable.setItems(foundParts);
        }
    }

    /**
     * calls the correct product search method and sets the table to the returned products or lack there of
     */
    @FXML
    public void searchProducts()
    {
        int id = 0;
        String searchText;
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();

        if (ProductSearch.getText() == null)
        {
            ProductTable.setItems(Inventory.getAllProducts());
            return;
        }
        try
        {
            id = Integer.parseInt(ProductSearch.getText());
            foundProducts.add(Inventory.lookupProduct(id));
            ProductTable.setItems(foundProducts);
        }
        catch(NumberFormatException e)
        {
            searchText = ProductSearch.getText();
            foundProducts = Inventory.lookupProduct(searchText);
            ProductTable.setItems(foundProducts);
        }
    }

    /**
     * prompts a confirmation for the delete action and if confirmed it removes the selected part
     */
    @FXML
    public void deletePart()
    {
        Part part = PartTable.getSelectionModel().getSelectedItem();
        if (part == null)
        {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + part.getName() + "?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES)
        {
            Inventory.deletePart(part);
        }
    }

    /**
     * @throws Exception
     * loads and shows the part add form
     */
    @FXML
    public void addPart() throws Exception
    {
        isModifyingPart = false;
        Parent root = FXMLLoader.load(getClass().getResource("PartRoot.fxml"));
        Stage partStage = (Stage)AddPart.getScene().getWindow();
        Scene scene = new Scene(root);
        partStage.setScene(scene);
        partStage.show();
    }

    /**
     * @throws Exception
     * loads and shows the modify part form
     * RUNTIME ERROR: Originally I lacked the if statement checking if the selection was null so the button would cause errors when nothing was selected so I added the if statement
     */
    @FXML
    public void modifyPart() throws Exception
    {
        isModifyingPart = true;
        selectedPart = PartTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null)
        {
            return;
        }
        Parent root = FXMLLoader.load(getClass().getResource("PartRoot.fxml"));
        Stage partStage = (Stage)AddPart.getScene().getWindow();
        Scene scene = new Scene(root);
        partStage.setScene(scene);
        partStage.show();
    }

    /**
     * If the product has associated parts it tells the user it can't be deleted otherwise it prompts a confirmation for the delete action and if confirmed it removes the selected product
     */
    @FXML
    public void deleteProduct()
    {
        Product product = ProductTable.getSelectionModel().getSelectedItem();
        if (product == null)
        {
            return;
        }

        if(product.getAllAssociatedParts().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + product.getName() + "?", ButtonType.YES, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES)
            {
                Inventory.deleteProduct(product);
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING, product.getName() + " can't be deleted while it has associated parts", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * @throws Exception
     * loads and shows the product add form
     */
    @FXML
    public void addProduct() throws Exception
    {
        isModifyingProduct = false;
        Parent root = FXMLLoader.load(getClass().getResource("ProductRoot.fxml"));
        Stage productStage = (Stage)AddProduct.getScene().getWindow();
        Scene scene = new Scene(root);
        productStage.setScene(scene);
        productStage.show();
    }

    /**
     * @throws Exception
     * loads and shows the product modify form
     */
    @FXML
    public void modifyProduct() throws Exception
    {
        isModifyingProduct = true;
        selectedProduct = ProductTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null)
        {
            return;
        }
        Parent root = FXMLLoader.load(getClass().getResource("ProductRoot.fxml"));
        Stage productStage = (Stage)AddProduct.getScene().getWindow();
        Scene scene = new Scene(root);
        productStage.setScene(scene);
        productStage.show();
    }

    /**
     * @return the selected part
     */
    public static Part getSelectedPart()
    {
        return selectedPart;
    }

    /**
     * @return whether the part is being modified
     */
    public static boolean getIsModifyingPart()
    {
        return isModifyingPart;
    }

    /**
     * @return the selected product
     */
    public static Product getSelectedProduct()
    {
        return selectedProduct;
    }

    /**
     * @return whether the product is being modified
     */
    public static boolean getIsModifyingProduct()
    {
        return isModifyingProduct;
    }

    /**
     * exits the program
     */
    @FXML
    public void Exit()
    {
        System.exit(0);
    }
}

