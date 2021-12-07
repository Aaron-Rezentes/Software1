package sample;

/**
 * @author Aaron Rezentes
 * purpose: A Part that has been specified as made in house thus having a machine ID
 */

public class InHouse extends Part
{
    private int machineId;

    /**
     * @param id the ID for this part
     * @param name the name for this part
     * @param price the price for this part
     * @param stock the stock for this part
     * @param min the min for this part
     * @param max the max for this part
     * @param machineId the machine ID for this part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId)
    {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @param machineId the machine ID to set
     */
    public void setMachineId(int machineId)
    {
        this.machineId = machineId;
    }

    /**
     * @return the machineId
     */
    public int getMachineId()
    {
        return machineId;
    }
}