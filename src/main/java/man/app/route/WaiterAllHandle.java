package man.app.route;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import man.app.entity.Waiter;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WaiterAllHandle implements HttpHandler {

    /**
     * The format of information send to client is:
     * <p>
     * Array of object JSON, each object JSON had the follow properties:
     * <p>
     * dui: Interger
     * nit: Integer
     * salary: Float
     * name: String
     * department: String
     * marital-status: String
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("GET")) {
            List<Waiter> employees = new ArrayList<>();

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/PlayTech", "root", "HDgtDVi5");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("Select * From Waiter");

                while (resultSet.next()) {
                    int identification = resultSet.getInt(1);
                    String firstName = resultSet.getString(2);
                    String lastName = resultSet.getString(3);

                    employees.add(new Waiter(identification, firstName, lastName));
                }

                connection.close();
            } catch (SQLException ignored) {
                System.err.println("Error MySQL Connection, please change the user || password in source code.");
            } catch (ClassNotFoundException ignored) {
                System.err.println("The Driver hasn't been loaded");
            }

            StringBuilder contentRequestBuild = new StringBuilder(1000);
            contentRequestBuild.append('[');

            for (Waiter employee : employees) {
                contentRequestBuild.append(employee.getJSON());
            }

            contentRequestBuild.append(']');
            // Deleted the last comma character of string for avoid conversion problems with JSON
            String contentRequest = contentRequestBuild.toString().replace(",]", "]");

            exchange.sendResponseHeaders(200, contentRequest.getBytes().length);

            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(contentRequest.getBytes());
            outputStream.flush();
            outputStream.close();
        }
    }
}
