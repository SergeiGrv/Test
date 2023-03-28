
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        String time = simpleDateFormat.format(date);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", "булка");
        jsonObject.put("date", time);
        jsonObject.put("sum", 200);
        System.out.println(jsonObject);

        String product = (String) jsonObject.get("title");
        Integer price = (Integer) jsonObject.get("sum");


        HashMap<String, Integer> categories = new HashMap<>();
        try (FileReader reader = new FileReader("categories.tsv")) {
            BufferedReader reader2 = new BufferedReader(reader);
            String line = reader2.readLine();
            while (line != null) {
                System.out.println(line);
                String[] parts = line.split("\t");
                String productName = parts[0];
                String productType = parts[1];
                if(product.equals(productName) && !categories.containsKey(productType)){
                    categories.put(productType, price);
                }else{
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