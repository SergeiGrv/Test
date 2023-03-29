
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;


public class Main {
    public static void main(String[] args) {
        HashMap<String, Integer> categories = new HashMap<>();
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = new JSONObject();
        String product = (String) jsonObject.get("title");
        Integer price = (Integer) jsonObject.get("sum");

        try (ServerSocket serverSocket = new ServerSocket(8989);) {
            while (true) {
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream());
                ) {
                    System.out.println("New connection accepted");
                    jsonObject = (JSONObject) parser.parse(in);
                }
            }
        } catch (IOException | ParseException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
        System.out.println(jsonObject);

        try (FileReader reader = new FileReader("categories.tsv")) {
            BufferedReader reader2 = new BufferedReader(reader);
            String line = reader2.readLine();
            while (line != null) {
                System.out.println(line);
                String[] parts = line.split("\t");
                String productName = parts[0];
                String productType = parts[1];
                if (product.equals(productName) && !categories.containsKey(productType)) {
                    categories.put(productType, price);
                } else if (product.equals(productName) && categories.containsKey(productType)) {
                    Integer midPrice = categories.get(productType);
                    Integer newPrice = midPrice + price;
                    categories.put(productType, newPrice);
                }
                line = reader2.readLine();
            }
            reader2.close();
            System.out.println(categories);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}