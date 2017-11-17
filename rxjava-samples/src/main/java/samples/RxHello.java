package samples;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="https://julien.ponge.org/">Julien Ponge</a>
 */
public class RxHello {

  private static final Logger logger = LoggerFactory.getLogger(RxHello.class);

  public static void main(String[] args) {

    Single.just(1)
      .map(i -> i * 10)
      .map(Object::toString)
      .subscribe((Consumer<String>) logger::info);

    Maybe.just("Something")
      .subscribe(logger::info);

    Maybe.never()
      .subscribe(o -> logger.info("Something is here..."));

    Completable.complete()
      .subscribe(() -> logger.info("Completed"));

    Flowable.just("foo", "bar", "baz")
      .filter(s -> s.startsWith("b"))
      .map(String::toUpperCase)
      .subscribe(logger::info);
  }
}
