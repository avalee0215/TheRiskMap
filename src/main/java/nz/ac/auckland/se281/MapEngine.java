package nz.ac.auckland.se281;

import java.util.HashMap; // Import HashMap
import java.util.List;
import java.util.Map;

/** This class is the main entry point. */
public class MapEngine {
  private Map<String, Country>
      graph; // Use map to make a graph. This Map will save countries as a key and
             // related(neighbour) countries as values

  public MapEngine() {
    graph = new HashMap<>();
    loadMap(); // keep this method invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();
    // add code here to create your data structures
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    // add code here
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
