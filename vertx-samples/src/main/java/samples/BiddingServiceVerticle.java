package samples;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.UUID;

/**
 * @author <a href="https://julien.ponge.org/">Julien Ponge</a>
 */
public class BiddingServiceVerticle extends AbstractVerticle {

  private final Logger logger = LoggerFactory.getLogger(BiddingServiceVerticle.class);

  @Override
  public void start(Future<Void> verticleStartFuture) throws Exception {
    Random random = new Random();
    String myId = UUID.randomUUID().toString();
    int portNumber = config().getInteger("port", 3000);

    Router router = Router.router(vertx);
    router.get("/offer").handler(context -> {
      String clientIdHeader = context.request().getHeader("Client-Request-Id");
      String clientId = (clientIdHeader != null) ? clientIdHeader : "N/A";
      int myBid = 10 + random.nextInt(20);
      JsonObject payload = new JsonObject()
        .put("origin", myId)
        .put("bid", myBid);
      if (clientIdHeader != null) {
        payload.put("clientRequestId", clientId);
      }
      long artificialDelay = random.nextInt(1000);
      vertx.setTimer(artificialDelay, id -> {
        if (random.nextInt(20) == 1) {
          context.response()
            .setStatusCode(500)
            .end();
          logger.error("{} injects an error (client-id={}, artificialDelay={})", myId, myBid, clientId, artificialDelay);
        } else {
          context.response()
            .putHeader("Content-Type", "application/json")
            .end(payload.encode());
          logger.info("{} offers {} (client-id={}, artificialDelay={})", myId, myBid, clientId, artificialDelay);
        }
      });
    });

    vertx.createHttpServer()
      .requestHandler(router::accept)
      .listen(portNumber, ar -> {
        if (ar.succeeded()) {
          logger.info("Bidding service listening on HTTP port {}", portNumber);
          verticleStartFuture.complete();
        } else {
          logger.error("Bidding service failed to start", ar.cause());
          verticleStartFuture.fail(ar.cause());
        }
      });
  }
}
