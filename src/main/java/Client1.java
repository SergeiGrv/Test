import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client1 {
    public static void main(String[] args) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        String time = simpleDateFormat.format(date);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", "булка");
        jsonObject.put("date", time);
        jsonObject.put("sum", 200);

        final String HOST = "localhost";
        final int PORT = 8989;
        try (Socket clientSocket = new Socket(HOST, PORT);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            out.println(jsonObject);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
