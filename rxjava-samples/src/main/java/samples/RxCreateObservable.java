package samples;

import io.reactivex.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author <a href="https://julien.ponge.org/">Julien Ponge</a>
 */
public class RxCreateObservable {

  private static final Logger logger = LoggerFactory.getLogger(RxCreateObservable.class);

  public static void main(String[] args) {

    List<String> data = Arrays.asList("foo", "bar", "baz");
    Random random = new Random();
    Observable<String> source = Observable.create(subscriber -> {
      for (String s : data) {
        if (random.nextInt(6) == 0) {
          subscriber.onError(new RuntimeException("Bad luck for you..."));
        }
        subscriber.onNext(s);
      }
      subscriber.onComplete();
    });

    for (int i = 0; i < 10; i++) {
      logger.info("=======================================");
      source.subscribe(next -> logger.info("Next: {}", next),
        error -> logger.error("Woops"),
        () -> logger.info("Done"));
    }

    logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    source
      .retry(5)
      .subscribe(next -> logger.info("Next: {}", next),
        error -> logger.error("Woops"),
        () -> logger.info("Done"));
  }
}
