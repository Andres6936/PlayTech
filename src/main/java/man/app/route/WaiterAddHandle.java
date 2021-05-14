package man.app.route;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WaiterAddHandle implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("POST")) {
            try {

                // REQUEST Headers
                Headers requestHeaders = exchange.getRequestHeaders();

                int contentLength = Integer.parseInt(requestHeaders.getFirst("Content-length"));

                // REQUEST Body
                InputStream is = exchange.getRequestBody();

                byte[] data = new byte[contentLength];
                int length = is.read(data);

                // Currently, the form had three (3) fields.
                String contentForm = new String(data, StandardCharsets.UTF_8);
                // Split the form in a array of length three (3).
                String[] keyValue = contentForm.split("&");

                // The current convention is: DUI, NAME and SALARY.
                // The index are: DUI (0), NAME (1) and SALARY (1).

                // Get the value of this field. Again, we divided the String and get the value in the position 1.
                String name = keyValue[1].split("=")[1];
                // Same process here, divide and get the value of field.
                float salary = Float.parseFloat(keyValue[2].split("=")[1]);

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/Mandomedia", "root", "HDgtDVi5");
                    PreparedStatement statement = connection.prepareStatement("Insert INTO Employee (Name, Salary) VALUES (?, ?)");
                    statement.setString(1, name);
                    statement.setFloat(2, salary);

                    statement.execute();

                    connection.close();
                } catch (SQLException ignored) {
                    System.err.println("Error MySQL Connection");
                } catch (ClassNotFoundException ignored) {
                    System.err.println("The Driver hasn't been loaded");
                }

                File page = new File(ClassLoader.getSystemResource("html/successful.html").getFile());

                String contentPage = new String(Files.readAllBytes(page.toPath()));

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, contentPage.length());

                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(contentPage.getBytes());
                outputStream.flush();
                outputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
