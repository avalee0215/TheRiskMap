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

    // Save information from adjacencies file: neighbour countries
    for (String a : adjacencies) {
      String[] line = a.split(","); // split as the file has information  with a line
      String countryName = line[0];
      Country country = graph.get(countryName);
      for (int i = 1; i < line.length; i++) {
        country.addAdjencies(line[i]); // Add all the neighbour countries for each country
      }
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    MessageCli.INSERT_COUNTRY.printMessage();
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}

  /**
   * this method is invoked when the user call nameExceptionHandle to test the countryname is valid.
   */
  public void nameExceptionHandle(String countryName) {
    countryName =
        Utils.capitalizeFirstLetterOfEachWord(
            countryName); // Capitalise only the first letter of each word of the country.
    if (graph.containsKey(countryName)) {
      Country country = graph.get(countryName);
      String tax = String.valueOf(country.getTax());
      MessageCli.COUNTRY_INFO.printMessage(
          countryName,
          country.getContinent(),
          tax); // Print the information of the country (continent, tax fees).
    } else {
      throw new CountryNameException(
          countryName); // If it is invalid, throw the exception with the country name to print the
      // error message.
    }
  }
}
