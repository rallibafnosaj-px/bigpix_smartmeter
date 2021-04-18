package Model;

public class RetrieveReports {

    String transNo;
    String dateProcessed;
    String image;

    public RetrieveReports(String transNo, String dateProcessed, String image) {
        this.transNo = transNo;
        this.dateProcessed = dateProcessed;
        this.image = image;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public String getDateProcessed() {
        return dateProcessed;
    }

    public void setDateProcessed(String dateProcessed) {
        this.dateProcessed = dateProcessed;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
