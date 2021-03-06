package io.vertx.workshop.data.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.workshop.data.DataStorageService;
import io.vertx.workshop.data.Place;
import io.vertx.ext.mongo.MongoClient;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link DataStorageService}.
 */
public class DataStorageServiceImpl implements DataStorageService {

  public static final String COLLECTION = "places";
  public static final Logger LOGGER = Logger.getLogger("Data Storage Service");

  private final MongoClient mongo;
  private final Vertx vertx;

  public DataStorageServiceImpl(Vertx vertx, JsonObject config) {
    this.mongo = MongoClient.createShared(vertx, config, "places");
    this.vertx = vertx;
    LOGGER.info("Data Storage Service instantiated");
  }

  @Override
  public void getAllPlaces(Handler<AsyncResult<List<Place>>> resultHandler) {
    /**
     * TODO to implement.
     */
  }

  public void getPlacesForCategory(String category,
                                   Handler<AsyncResult<List<Place>>> resultHandler) {
    mongo.find(COLLECTION,
        new JsonObject().put("category", category),
        ar -> {
          if (ar.failed()) {
            resultHandler.handle(Future.failedFuture(ar.cause()));
          } else {
            List<Place> places = ar.result().stream()
                .map(Place::new).collect(Collectors.toList());
            resultHandler.handle(Future.succeededFuture(places));
          }
        }
    );
  }


  @Override
  public void getPlacesForTag(String tag, Handler<AsyncResult<List<Place>>> resultHandler) {
    /**
     * TODO to implement.
     */
  }

  @Override
  public void addPlace(Place place, Handler<AsyncResult<Void>> resultHandler) {
    /**
     * TODO to implement.
     */
  }

  public void close() {
    mongo.close();
  }


  private void report(long time) {
    vertx.eventBus().send("metrics", new JsonObject().put("source", "mongo.query").put("value", time));
  }
}
