package samples;

import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;

/**
 * Simple Java application using Vert.x to process HTTP requests.
 */
public class TwitterFeedApplication {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        // 1 - Create a Web client
        WebClient client = WebClient.create(vertx);
        vertx.createHttpServer()
            .requestHandler(req -> {
                // 2 - In the request handler, retrieve a Twitter feed
                client
                    .getAbs("https://twitter.com/vertx_project")
                    .send(res -> {
                        // 3 - Write the response based on the result
                        if (res.failed()) {
                            req.response().end("Cannot access "
                                + "the twitter feed: "
                                + res.cause().getMessage());
                        } else {
                            req.response().end(res.result()
                                .bodyAsString());
                        }
                    });
            })
            .listen(8080);
    }
}
