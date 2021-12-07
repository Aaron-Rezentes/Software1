package sample;

/**
 * @author Aaron Rezentes
 * purpose: The controller for the part add and modify forms
 */

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class PartRootController
{

    @FXML
    private Label PartLabel;

    @FXML
    private Label SwitchLabel;

    @FXML
    private RadioButton PartHouseRadio;

    @FXML
    private ToggleGroup Part;

    @FXML
    private TextField PartIDField;

    @FXML
    private TextField PartNameField;

    @FXML
    private TextField PartInvField;

    @FXML
    private TextField PartPriceField;

    @FXML
    private TextField PartMaxField;

    @FXML
    private TextField PartMinField;

    @FXML
    private RadioButton PartOutsourcedRadio;

    @FXML
    private TextField PartMachineField;

    @FXML
    private Button PartSaveButton;

    @FXML
    private Button PartCancelButton;

    @FXML
    private Label PartErrorName;

    @FXML
    private Label PartErrorInv;

    @FXML
    private Label PartErrorPrice;

    @FXML
    private Label PartErrorMax;

    @FXML
    private Label PartErrorMin;

    @FXML
    private Label PartErrorSize;

    @FXML
    private Label PartErrorInvVal;

    @FXML
    private Label PartErrorRadio;

    /**
     * initializes the part form by loading it as a modify or add form depending on which the user chose
     * if it's a modify form it loads the values in to their fields
     */
    @FXML
    public void initialize()
    {
        Part part = MainRootController.getSelectedPart();
        if(MainRootController.getIsModifyingPart())
        {
            SwitchLabel.setText("Modify Part");

            if(part.getClass().toString().equals("class sample.InHouse"))//decides which radio button to select based on the class of the part
            {
                PartHouseRadio.setSelected(true);
                PartMachineField.setText(String.valueOf(((InHouse)part).getMachineId()));
            }
            else
            {
                PartOutsourcedRadio.setSelected(true);
                PartMachineField.setText(String.valueOf(((Outsourced)part).getCompanyName()));
            }
            PartIDField.setText(String.valueOf(part.getId()));
            PartNameField.setText(part.getName());
            PartInvField.setText(String.valueOf(part.getStock()));
            PartPriceField.setText(String.valueOf(part.getPrice()));
            PartMaxField.setText(String.valueOf(part.getMax()));
            PartMinField.setText(String.valueOf(part.getMin()));
        }
        else
        {
            SwitchLabel.setText("Add Part");
        }
    }

    /**
     * @throws Exception
     * goes back to the main menu
     */
    @FXML
    public void cancelButton() throws Exception
    {
        showMainRoot();
    }

    /**
     * switches the text label to correctly identify the text field
     */
    @FXML
    public void radioButton()
    {
        if(PartHouseRadio.isSelected())
        {
            PartLabel.setText("Machine ID");
            PartErrorRadio.setText("Machine ID is not an integer");
        }
        else
        {
            PartLabel.setText("Company Name");
            PartErrorRadio.setText("Exception: No data in Company name field");
        }
    }

    /**
     * @throws Exception
     * saves the given information in to a new part if add was selected or modifies an old part if modify was selected
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
        int machineID = 0;
        String companyName;
        boolean modify = false;
        int index = 0;

        //the following series of try/catch and if/else statements are to validate input and display error messages accordingly
        try
        {
            inv = Integer.parseInt(PartInvField.getText());
            PartErrorInv.setVisible(false);
        }
        catch(NumberFormatException e)
        {
            PartErrorInv.setVisible(true);
            valid = false;
        }
        try
        {
            max = Integer.parseInt(PartMaxField.getText());
            PartErrorMax.setVisible(false);
        }
        catch(NumberFormatException e)
        {
            PartErrorMax.setVisible(true);
            valid = false;
            minMaxValid = false;
        }
        try
        {
            min = Integer.parseInt(PartMinField.getText());
            PartErrorMin.setVisible(false);
        }
        catch(NumberFormatException e)
        {
            PartErrorMin.setVisible(true);
            valid = false;
            minMaxValid = false;
        }
        if(valid && (inv > max || inv < min))
        {
            PartErrorInvVal.setVisible(true);
            valid = false;
        }
        else
        {
            PartErrorInvVal.setVisible(false);
        }
        if(minMaxValid && min > max)
        {
            PartErrorSize.setVisible(true);
            valid = false;
        }
        else
        {
            PartErrorSize.setVisible(false);
        }
        if(PartNameField.getText().equals(""))
        {
            PartErrorName.setVisible(true);
            valid = false;
        }
        else
        {
            PartErrorName.setVisible(false);
        }
        if(PartHouseRadio.isSelected())
        {
            try
            {
                machineID = Integer.parseInt(PartMachineField.getText());
                PartErrorRadio.setVisible(false);
            }
            catch(NumberFormatException e)
            {
                PartErrorRadio.setVisible(true);
                valid = false;
            }
        }
        else
        {
            if(PartMachineField.getText().equals(""))
            {
                PartErrorRadio.setVisible(true);
                valid = false;
            }
            else
            {
                PartErrorRadio.setVisible(false);
            }
        }
        try
        {
            price = Double.parseDouble(PartPriceField.getText());
            PartErrorPrice.setVisible(false);
        }
        catch(NumberFormatException e)
        {
            PartErrorPrice.setVisible(true);
            valid = false;
        }
        try
        {
            Integer.parseInt(PartIDField.getText());
            modify = true;
        }
        catch(NumberFormatException e)
        {
            modify = false;
        }
        if(valid) //if all of the information is valid save/modify the part
        {
            if(modify)
            {
                for(Part part: Inventory.getAllParts()) //finds the index of the part to be modified
                {
                    if(part.getId() == Integer.parseInt(PartIDField.getText()))
                    {
                        index = Inventory.getAllParts().indexOf(part);
                    }
                }
                if(PartHouseRadio.isSelected())
                {
                    InHouse newInHouse = new InHouse(Integer.parseInt(PartIDField.getText()), PartNameField.getText(), price, inv, min, max, machineID);
                    Inventory.updatePart(index, newInHouse);
                }
                else
                {
                    Outsourced newOutsourced = new Outsourced(Integer.parseInt(PartIDField.getText()), PartNameField.getText(), price, inv, min, max, PartMachineField.getText());
                    Inventory.updatePart(index, newOutsourced);
                }
            }
            else //else save it as a new part
            {
                if(PartHouseRadio.isSelected())
                {
                    InHouse newInHouse = new InHouse(Inventory.getNewPartID(), PartNameField.getText(), price, inv, min, max, machineID);
                    Inventory.addPart(newInHouse);
                }
                else
                {
                    Outsourced newOutsourced = new Outsourced(Inventory.getNewPartID(), PartNameField.getText(), price, inv, min, max, PartMachineField.getText());
                    Inventory.addPart(newOutsourced);
                }
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
            Stage partStage = (Stage)PartIDField.getScene().getWindow();
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

