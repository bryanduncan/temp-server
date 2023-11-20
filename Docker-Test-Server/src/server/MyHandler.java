package server;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;

public class MyHandler implements HttpHandler {

  public void handle(HttpExchange httpExchange) throws IOException {
    String response = "Request Received";
    String method = httpExchange.getRequestMethod();
    try {
      if (method.equals("GET")) {
        response = handleGet(httpExchange);
      } else {
        throw new Exception("Not Valid Request Method");
      }
    } catch (Exception e) {
      System.out.println("An erroneous request");
      response = e.toString();
      e.printStackTrace();
    }

    OutputStream outputStream = httpExchange.getResponseBody();

    // this line is a must
    httpExchange.sendResponseHeaders(200, response.length());

    outputStream.write(response.getBytes());
    outputStream.flush();
    outputStream.close();
  }

  private String handleGet(HttpExchange httpExchange) {
    String response = "Invalid GET request";
    URI uri = httpExchange.getRequestURI();
    String query = uri.getRawQuery();
    if (query != null) {
      String name = query.substring(query.indexOf("=") + 1);

      StringBuilder htmlBuilder = new StringBuilder();
      htmlBuilder
        .append("<html>")
        .append("<body>")
        .append("<h1>")
        .append("Hello ")
        .append(name)
        .append("</h1>")
        .append("</body>")
        .append("</html>");

      // encode HTML content
      response = htmlBuilder.toString();
    }
    return response;
  }
}