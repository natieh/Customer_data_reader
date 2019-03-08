import database.BDConnectionProvider;
import reader.FileReader;

public class Customer_data_reader {

    public static void main(String[] args) {
        BDConnectionProvider connection = new BDConnectionProvider();
        connection.createDatabase();
        FileReader reader = new FileReader();
        reader.readFiles();
    }
}