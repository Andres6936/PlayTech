package man.app.route;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import java.util.Map;
import java.util.Collections;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class TableViewHandle implements HttpHandler {
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
