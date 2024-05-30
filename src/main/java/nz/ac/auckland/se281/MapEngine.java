package nz.ac.auckland.se281;

import java.util.HashMap;
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

    // Save information from countries file: continent, tax fees, and name.
    for (String a : countries) {
      String[] line = a.split(","); // split as the file has information of the country with a line
      Country country = new Country(line[0], line[1], Integer.parseInt(line[2]));
      graph.put(line[0], country); // Save the country(object) in the graph
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    // add code here
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
