package Model;

public class Contact {


    public int contactID;
    public String contactName;
    public String contactEmail;

    public Contact(int contactID, String contactName, String contactEmail){
        this.contactEmail = contactEmail;
        this.contactName = contactName;
        this.contactID = contactID;
    }

    public int getContactID(){
        return contactID;
    }
    public String getContactName(){
        return contactName;
    }

    public String getContactEmail(){
       return getContactEmail();
    }

}
