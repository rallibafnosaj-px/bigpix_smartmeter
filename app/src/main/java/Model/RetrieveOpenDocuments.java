package Model;

public class RetrieveOpenDocuments {

    String transNo;
    String dueDate;
    String unitPrice;
    String itemCode;
    String id;



    public RetrieveOpenDocuments(String transNo, String dueDate, String unitPrice, String itemCode, String id) {
        this.transNo = transNo;
        this.dueDate = dueDate;
        this.unitPrice = unitPrice;
        this.itemCode = itemCode;
        this.id = id;
    }


    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




}
