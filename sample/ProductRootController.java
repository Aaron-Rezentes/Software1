package sample;

/**
 * @author Aaron Rezentes
 * purpose: The controller for the product add and modify forms
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProductRootController
{

    @FXML
    private Label ProductLabel;

    @FXML
    private TextField ProductIDField;

    @FXML
    private TextField ProductNameField;

    @FXML
    private TextField ProductInvField;

    @FXML
    private TextField ProductPriceField;

    @FXML
    private TextField ProductMaxField;

    @FXML
    private TextField ProductMinField;

    @FXML
    private Button ProductSaveButton;

    @FXML
    private Button ProductCancelButton;

    @FXML
    private Label ProductErrorName;

    @FXML
    private Label ProductErrorInv;

    @FXML
    private Label ProductErrorPrice;

    @FXML
    private Label ProductErrorMax;

    @FXML
    private Label ProductErrorMin;

    @FXML
    private Label ProductErrorSize;

    @FXML
    private Label ProductErrorInvVal;

    @FXML
    private Button ProductRemoveButton;

    @FXML
    private TableView<Part> AvailablePartsTable;

    @FXML
    private TableColumn<Part, Integer> PartIDColumn;

    @FXML
    private TableColumn<Part, String> PartNameColumn;

    @FXML
    private TableColumn<Part, Integer> PartInventoryColumn;

    @FXML
    private TableColumn<Part, Double> PartPriceColumn;

    @FXML
    private TableView<Part> AssociatedPartsTable;

    @FXML
    private TableColumn<Part, Integer> PartIDColumn1;

    @FXML
    private TableColumn<Part, String> PartNameColumn1;

    @FXML
    private TableColumn<Part, Integer> PartInventoryColumn1;

    @FXML
    private TableColumn<Part, Double> PartPriceColumn1;

    @FXML
    private Button ProductAddButton;

    @FXML
    private TextField AvailablePartSearchField;

    private ObservableList<Part> partObservableList = FXCollections.observableArrayList();

    /**
     * initializes the product form by loading it as a modify or add form depending on which the user chose
     * if it's a modify form it loads the values in to their fields
     */
    @FXML
    public void initialize()
    {
        Product product;
        PartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        PartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AvailablePartsTable.setItems(Inventory.getAllParts());

        PartIDColumn1.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartPriceColumn1.setCellValueFactory(new PropertyValueFactory<>("price"));
        PartInventoryColumn1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        if(MainRootController.getIsModifyingProduct())
        {
            product = MainRootController.getSelectedProduct();

            partObservableList.addAll(product.getAllAssociatedParts());
            ProductLabel.setText("Modify Product");
            ProductIDField.setText(String.valueOf(product.getId()));
            ProductNameField.setText(product.getName());
            ProductInvField.setText(String.valueOf(product.getStock()));
            ProductPriceField.setText(String.valueOf(product.getPrice()));
            ProductMaxField.setText(String.valueOf(product.getMax()));
            ProductMinField.setText(String.valueOf(product.getMin()));
            AssociatedPartsTable.setItems(product.getAllAssociatedParts());


        }
        else
        {
            ProductLabel.setText("Add Part");
        }
    }

    /**
     * runs the search field for available parts
     */
    @FXML
    public void searchAvailableParts()
    {
        int id = 0;
        String searchText;
        ObservableList<Part> foundParts = FXCollections.observableArrayList();

        if (AvailablePartSearchField.getText() == null)
        {
            AvailablePartsTable.setItems(Inventory.getAllParts());
            return;
        }
        try
        {
            id = Integer.parseInt(AvailablePartSearchField.getText());
            foundParts.add(Inventory.lookupPart(id));
            AvailablePartsTable.setItems(foundParts);
        }
        catch(NumberFormatException e)
        {
            searchText = AvailablePartSearchField.getText();
            foundParts = Inventory.lookupPart(searchText);
            AvailablePartsTable.setItems(foundParts);
        }
    }

    /**
     * @throws Exception
     * returns to the main menu
     */
    @FXML
    public void cancelButton() throws Exception
    {
        showMainRoot();
    }

    /**
     * adds a part from the available table to the associated table
     */
    @FXML
    public void productAddButton()
    {
        Part part = AvailablePartsTable.getSelectionModel().getSelectedItem();
        if(part == null)
        {
            return;
        }
        partObservableList.add(part);
        AssociatedPartsTable.setItems(partObservableList);
    }

    /**
     * prompts a confirmation for the removal and if confirmed removes a part from the associated table
     */
    @FXML
    public void productRemoveButton()
    {
        Part part = AssociatedPartsTable.getSelectionModel().getSelectedItem();

        if(part == null)
        {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove " + part.getName() + "?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES)
        {
            partObservableList.remove(part);
            AssociatedPartsTable.setItems(partObservableList);
        }
    }

    /**
     * @throws Exception
     * saves the given information in to a new product if add was selected or modifies an old product if modify was selected
     */
    @FXML
    public void saveButton() throws Exception
    {
        boolean valid = true;
        boolean minMaxValid = true;
        int inv = 0;
        double price = 0.0;
        int max = 0;
        int min = 0;
        boolean modify = false;
        int index = 0;

        //the following series of try/catch and if/else statements are to validate input and display error messages accordingly
        try
        {
            inv = Integer.parseInt(ProductInvField.getText());
            ProductErrorInv.setVisible(false);
        }
        catch(NumberFormatException e)
        {
            ProductErrorInv.setVisible(true);
            valid = false;
        }
        try
        {
            max = Integer.parseInt(ProductMaxField.getText());
            ProductErrorMax.setVisible(false);
        }
        catch(NumberFormatException e)
        {
            ProductErrorMax.setVisible(true);
            valid = false;
            minMaxValid = false;
        }
        try
        {
            min = Integer.parseInt(ProductMinField.getText());
            ProductErrorMin.setVisible(false);
        }
        catch(NumberFormatException e)
        {
            ProductErrorMin.setVisible(true);
            valid = false;
            minMaxValid = false;
        }
        if(valid && (inv > max || inv < min))
        {
            ProductErrorInvVal.setVisible(true);
            valid = false;
        }
        else
        {
            ProductErrorInvVal.setVisible(false);
        }
        if(minMaxValid && min > max)
        {
            ProductErrorSize.setVisible(true);
            valid = false;
        }
        else
        {
            ProductErrorSize.setVisible(false);
        }
        if(ProductNameField.getText().equals(""))
        {
            ProductErrorName.setVisible(true);
            valid = false;
        }
        else
        {
            ProductErrorName.setVisible(false);
        }
        try
        {
            price = Double.parseDouble(ProductPriceField.getText());
            ProductErrorPrice.setVisible(false);
        }
        catch(NumberFormatException e)
        {
            ProductErrorPrice.setVisible(true);
            valid = false;
        }
        try
        {
            Integer.parseInt(ProductIDField.getText());
            modify = true;
        }
        catch(NumberFormatException e)
        {
            modify = false;
        }
        if(valid) //if all of the information is valid save/modify the product
        {
            if(modify)
            {
                for(Product product: Inventory.getAllProducts()) //finds the index of the product to be modified
                {
                    if(product.getId() == Integer.parseInt(ProductIDField.getText()))
                    {
                        index = Inventory.getAllProducts().indexOf(product);
                    }
                }
                Product newProduct = new Product(Integer.parseInt(ProductIDField.getText()), ProductNameField.getText(), price, inv, min, max);
                if(partObservableList != null)
                {
                    for(Part part: partObservableList)
                    {
                        newProduct.addAssociatedPart(part);
                    }
                }
                Inventory.updateProduct(index, newProduct);
            }
            else //else save it as a new product
            {
                Product newProduct = new Product(Inventory.getNewProductID(), ProductNameField.getText(), price, inv, min, max);
                if(partObservableList != null)
                {
                    for(Part part: partObservableList)
                    {
                        newProduct.addAssociatedPart(part);
                    }
                }
                Inventory.addProduct(newProduct);
            }
            showMainRoot();
        }
    }

    /**
     * @throws Exception
     * returns to the main screen
     */
    private void showMainRoot() throws Exception
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("MainRoot.fxml"));
            Stage partStage = (Stage)ProductIDField.getScene().getWindow();
            Scene scene = new Scene(root);
            partStage.setScene(scene);
            partStage.show();
        }
        catch(Exception e)
        {
            System.out.println("Error, fxml file not found");
        }
    }

}

