package WebService;

public class URL {
    public static String IP = "192.168.1.89:8080";
    public static String ROOT_URL ="http://" +  IP + "/bigpix_smartmeter/";
    public static String LOGINACCOUNT = ROOT_URL + "LoginAccount.php";
    public static String RETRIEVEOPENDOCUMENTS = ROOT_URL + "RetrieveOpenDocuments.php";
    public static String PROCESSTRANSACTION = ROOT_URL + "ProcessTransaction.php";
    public static String UPLOADPROOFOFPAYMENT = ROOT_URL + "UploadProofOfPayment.php";

}
