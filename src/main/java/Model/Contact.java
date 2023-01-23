package Model;
/**
 * @Author Ryan Wilkinson
 * C195 - Software II
 */

/**
 * Creating class model Contact
 */
public class Contact {


    public int contactID;
    public String contactName;
    public String contactEmail;

    /**
     * @param contactID Contstructor for the contact model class
     * @param contactName
     * @param contactEmail
     */
    public Contact(int contactID, String contactName, String contactEmail){
        this.contactEmail = contactEmail;
        this.contactName = contactName;
        this.contactID = contactID;
    }

    /**
     * @return gets contactId
     */
    public int getContactID(){
        return contactID;
    }

    /**
     * @return gets contact name
     */
    public String getContactName(){
        return contactName;
    }

    public String getContactEmail(){
       return getContactEmail();
    }

}
