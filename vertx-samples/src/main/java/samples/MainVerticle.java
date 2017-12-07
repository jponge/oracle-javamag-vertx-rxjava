package samples;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() {
        vertx.deployVerticle(new BiddingServiceVerticle());

        vertx.deployVerticle(new BiddingServiceVerticle(),
            new DeploymentOptions()
                .setConfig(new JsonObject().put("port", 3001)));

        vertx.deployVerticle(new BiddingServiceVerticle(),
            new DeploymentOptions()
                .setConfig(new JsonObject().put("port", 3002)));

        vertx.deployVerticle("samples.BestOfferServiceVerticle",
            new DeploymentOptions().setInstances(2));
    }
}
