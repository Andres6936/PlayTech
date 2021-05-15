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
import java.sql.Date;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderAddHandle implements HttpHandler {
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
                Map<String, String> params = getParamMap(contentForm);


                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/PlayTech", "root", "HDgtDVi5");
                    PreparedStatement statement = connection.prepareStatement("Insert INTO Order " +
                            "(MealIdentification, TableNumber, Turn, Date, WaiterNumber) VALUES (?, ?, ?, ?, ?)");
                    statement.setInt(1, Integer.parseInt(params.get("Meal")));
                    statement.setInt(2, Integer.parseInt(params.get("Table")));
                    statement.setInt(3, Integer.parseInt(params.get("Turn")));
                    statement.setDate(4, Date.valueOf(params.get("Date")));
                    statement.setInt(5, Integer.parseInt(params.get("Waiter")));

                    statement.execute();

                    connection.close();
                } catch (SQLException exception) {
                    System.err.println("Error MySQL Connection: " + exception.getMessage());
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

    /**
     * I've added a filter to avoid processing any empty params. Something
     * that should not happen, but it allows a cleaner implementation vs.
     * not handling the issue (and sending an empty response). An example
     * of this would look like ?param1=value1&param2=
     * <p>
     * Ref: https://stackoverflow.com/a/63976481
     *
     * @param query The GET parameters for a request.
     * @return The value key pair with each param in the request.
     */
    public static Map<String, String> getParamMap(String query) {
        // query is null if not provided (e.g. localhost/path )
        // query is empty if '?' is supplied (e.g. localhost/path? )
        if (query == null || query.isEmpty()) return Collections.emptyMap();

        return Stream.of(query.split("&"))
                .filter(s -> !s.isEmpty())
                .map(kv -> kv.split("=", 2))
                .collect(Collectors.toMap(x -> x[0], x -> x[1]));

    }
}
