package man.app.route;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Optional;

public class StaticHandle implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("GET")) {
            String staticFile = exchange.getRequestURI().getPath().substring(1);
            String mimeType = getMimeType(getExtensionFilename(staticFile).get());

            System.out.println("GET: " + staticFile + " | MimeType: " + mimeType);
            File page = new File(ClassLoader.getSystemResource(staticFile).getFile());

            String contentPage = new String(Files.readAllBytes(page.toPath()));

            exchange.getResponseHeaders().set("Content-Type", mimeType);
            exchange.sendResponseHeaders(200, contentPage.getBytes().length);

            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(contentPage.getBytes());
            outputStream.flush();
            outputStream.close();
        }
    }

    /**
     * This method will check for the dot ‘.' occurrence in the given filename.
     * <p>
     * If it exists, then it will find the last position of the dot ‘.' and
     * return the characters after that, the characters after the last dot ‘.'
     * known as the file extension.
     * <p>
     * Special Cases:
     * <p>
     * - No extension – this method will return an empty String.
     * - Only extension – this method will return the String after the dot,
     * e.g. “gitignore”.
     *
     * @param filename The filename of file.
     * @return The extension of file, empty string if not had.
     */
    private Optional<String> getExtensionFilename(String filename) {
        return Optional.ofNullable(filename)
                .filter(file -> file.contains("."))
                .map(file -> file.substring(filename.lastIndexOf(".") + 1));
    }

    /**
     * @param extension The extension of file.
     * @return The mime type.
     */
    private String getMimeType(String extension) {
        if (extension.equals("css")) {
            return "text/css";
        } else if (extension.equals("js")) {
            return "text/javascript";
        }

        return "text";
    }
}
