package samples;

import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.http.HttpServer;
import io.vertx.reactivex.ext.web.client.HttpResponse;
import io.vertx.reactivex.ext.web.client.WebClient;

/**
 * Simple Java application using Vert.x to process HTTP requests.
 */
public class RXTwitterFeedApplication {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    WebClient client = WebClient.create(vertx);
    HttpServer server = vertx.createHttpServer();
    server
      // 1 - Transform the sequence of request into a stream
      .requestStream().toFlowable()
      // 2 - For each request, call the twitter API
      .flatMapCompletable(req ->
        client.getAbs("https://twitter.com/vertx_project")
          .rxSend()
          // 3 - Extract the body as string
          .map(HttpResponse::bodyAsString)
          // 4 - In case of a failure
          .onErrorReturn(t -> "Cannot access the twitter feed: " + t.getMessage())
          // 5 - Write the response
          .doOnSuccess(res -> req.response().end(res))
          // 6 - Just transform the restul into a completable
          .toCompletable()
      )
      // 7 - Nver forget to subscribe to a reactive type, or nothing happens
      .subscribe();

    server
      .listen(8080);
  }

}
