package man.app.route;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

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
            List<Employee> employees = new ArrayList<>();

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/Mandomedia", "root", "root");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("Select * From Employee");

                while (resultSet.next()) {
                    int dui = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    String department = resultSet.getString(3);
                    float salary = resultSet.getFloat(4);
                    String maritalStatus = resultSet.getString(5);
                    int nit = resultSet.getInt(6);

                    Employee employee = new Employee(dui, name, salary);
                    employee.setNit(nit);
                    employee.setDepartment(department);
                    employee.setMaritalStatus(MaritalStatus.valueOf(maritalStatus));

                    employees.add(employee);
                }

                connection.close();
            } catch (SQLException ignored) {
                System.err.println("Error MySQL Connection, please change the user || password in source code.");
            } catch (ClassNotFoundException ignored) {
                System.err.println("The Driver hasn't been loaded");
            }

            StringBuilder contentRequestBuild = new StringBuilder(1000);
            contentRequestBuild.append('[');

            for (Employee employee : employees) {
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
