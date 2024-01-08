package lk.ijse.Util;

import java.io.InputStream;
import java.util.regex.Pattern;



public class Validation {

    private static boolean isValidAddress(String address) {
        return address != null && address.matches("[A-Z][a-zA-Z]*$");
    }

    private static boolean isValidName(String field) {
        String regex = "^[a-zA-Z]+(\\s[a-zA-Z]+)?(-[a-zA-Z]+)?$";
        // Check if the input matches the regex pattern
        return Pattern.matches(regex, field);
    }

    private static boolean isValidCount(String count) {
        return count != null && count.matches("\\d+");
    }

    private static boolean isValidItemCode(String code){
        return code != null && code.matches("^I\\d{1,4}$");
    }

    private static boolean isValidPrice(String price){
        return price != null && price.matches("^\\d+(\\.\\d{1,2})?$");
    }

    public static boolean ItemValidate(String text, String txtUnitPrice, String txtStockPrice, String txtQtyOnHand) {
        return isValidItemCode(text) &&
                isValidPrice(txtUnitPrice) &&
                isValidPrice(txtStockPrice) &&
                isValidCount(txtQtyOnHand);

    }
    private static boolean isValidCustomerId(String Id){
        return Id != null && Id.matches("^C\\d{1,4}$");
    }

    private static boolean isValidEmployeeId(String Id){
        return Id != null && Id.matches("^E\\d{1,4}$");
    }

    public static boolean BiteValidate(String ID, String Price) {
        return isValidItemCode(ID) &&
                isValidPrice(Price);
    }
    public static boolean isValidNumber(String num){
        return num != null && num.matches("^\\d{10}$");
    }

    private static boolean isValidSupplierId(String Id){
        return Id != null && Id.matches("^S\\d{1,4}$");
    }
    public static boolean CustomerValidate(String Id, String Name, String Number) {
        return isValidCustomerId(Id) &&
                isValidName(Name) &&
                isValidNumber(Number);
    }

    public static boolean SupplierValidate(String Id, String name, String number, String Address) {
        return isValidSupplierId(Id) &&
                isValidName(name) &&
                isValidNumber(number) &&
                isValidAddress(Address);
    }


    public static boolean EmployeeValidate(String Id, String Name, String Num, String Address) {
        return isValidEmployeeId(Id) &&
                isValidName(Name) &&
                isValidNumber(Num) &&
                isValidAddress(Address);
    }

    public static boolean salaryValidate(String text) {
        return isValidPrice(text);
    }
}
