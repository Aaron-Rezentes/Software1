package sample;

/**
 * @author Aaron Rezentes
 * purpose: A Part that has been specified as outsourced thus having a company name
 */

public class Outsourced extends Part
{
    private String companyName;

    /**
     * @param id the ID for this part
     * @param name the name for this part
     * @param price the price for this part
     * @param stock the stock for this part
     * @param min the min for this part
     * @param max the max for this part
     * @param companyName the company name for this part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName)
    {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @param companyName the company name to set
     */
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    /**
     * @return the company name
     */
    public String getCompanyName()
    {
        return companyName;
    }
}
