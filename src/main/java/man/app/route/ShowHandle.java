package man.app.route;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.sql.*;

public class ShowHandle implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("GET")) {
            File page = new File(ClassLoader.getSystemResource("html/show.html").getFile());

            String contentPage = new String(Files.readAllBytes(page.toPath()));

            exchange.sendResponseHeaders(200, contentPage.length());

            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(contentPage.getBytes());
            outputStream.flush();
            outputStream.close();
        }

        selectAll();
    }

    private void selectAll() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Mandomedia", "root", "HDgtDVi5");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * From Employee");
            while (resultSet.next()) {
                int dui = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String department = resultSet.getString(3);
                float salary = resultSet.getFloat(4);
                String maritalStatus = resultSet.getString(5);
                int nit = resultSet.getInt(6);

                System.out.println("DUI: " + dui);
                System.out.println("Name: " + name);
                System.out.println("Department: " + department);
                System.out.println("Salary: " + salary);
                System.out.println("Marital Status: " + maritalStatus);
                System.out.println("NIT: " + nit);
            }
            connection.close();
        } catch (SQLException ignored) {
            System.err.println("Error MySQL Connection");
        } catch (ClassNotFoundException ignored) {
            System.err.println("The Driver hasn't been loaded");
        }
    }
}
