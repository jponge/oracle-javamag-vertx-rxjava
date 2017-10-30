package samples;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="https://julien.ponge.org/">Julien Ponge</a>
 */
public class RxThreading {

  private static final Logger logger = LoggerFactory.getLogger(RxThreading.class);

  public static void main(String[] args) throws InterruptedException {

    Flowable.range(1, 5)
        .map(i -> i * 10)
        .map(i -> {
          logger.info("map({})", i);
          return i.toString();
        })
        .subscribe(logger::info);

    Thread.sleep(1000);
    logger.info("===================================");

    Flowable.range(1, 5)
        .map(i -> i * 10)
        .map(i -> {
          logger.info("map({})", i);
          return i.toString();
        })
        .observeOn(Schedulers.single())
        .subscribe(logger::info);

    Thread.sleep(1000);
    logger.info("===================================");

    Flowable.range(1, 5)
        .map(i -> i * 10)
        .map(i -> {
          logger.info("map({})", i);
          return i.toString();
        })
        .observeOn(Schedulers.single())
        .subscribeOn(Schedulers.computation())
        .subscribe(logger::info);

    Thread.sleep(1000);
    logger.info("===================================");
  }
}
