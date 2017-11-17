package samples;

import io.vertx.core.Vertx;

/**
 * Simple Java application using Vert.x to process HTTP requests.
 */
public class HttpApplication {

  public static void main(String[] args) {
    // 1 - Create a Vert.x instance
    Vertx vertx = Vertx.vertx();

    // 2 - Create the HTTP server
    vertx.createHttpServer()
      // 3 - Attach a request handler processing the requests
      .requestHandler(req -> req.response().end("Hello, request handled from "
        + Thread.currentThread().getName()))
      // 4 - Start the server on the port 8080
      .listen(8080);
  }

}
