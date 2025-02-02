package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

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
    boolean inputValid = false; // Repeat the process until the input is valid country name

    while (!inputValid) {
      try {
        String countryName = Utils.scanner.nextLine();
        countryName =
            Utils.capitalizeFirstLetterOfEachWord(
                countryName); // Capitalise only the first letter of each word of the country.
        nameExceptionHandle(
            countryName,
            "information"); // Check that the country name from the user is valid. Add the type of
        // the method to reuse the nameExceptionHandle.
        inputValid = true;
      } catch (CountryNameException e) {
        MessageCli.INVALID_COUNTRY.printMessage(
            e.getMessage()); // Error message with the invalid input string.
      }
    }
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
    // Initially, get two countries (source and destination) by using the same method in
    // showInfoCountry
    MessageCli.INSERT_SOURCE.printMessage();
    boolean startValid = false;
    String startCountry = null; // save the source country as start country
    while (!startValid) {
      try {
        String countryName = Utils.scanner.nextLine();
        countryName =
            Utils.capitalizeFirstLetterOfEachWord(
                countryName); // Capitalise only the first letter of each word of the country.
        nameExceptionHandle(
            countryName, "route"); // Check that the country name from the user is valid.
        startCountry = countryName;
        startValid = true;
      } catch (CountryNameException e) {
        MessageCli.INVALID_COUNTRY.printMessage(
            e.getMessage()); // Error message with the invalid input string.
      }
    }

    MessageCli.INSERT_DESTINATION.printMessage();
    boolean endValid = false;
    String endCountry = null; // save the destination country as end country
    while (!endValid) {
      try {
        String countryName = Utils.scanner.nextLine();
        countryName =
            Utils.capitalizeFirstLetterOfEachWord(
                countryName); // Capitalise only the first letter of each word of the country.
        nameExceptionHandle(
            countryName, "route"); // Check that the country name from the user is valid.
        endValid = true;
        endCountry = countryName;
      } catch (CountryNameException e) {
        MessageCli.INVALID_COUNTRY.printMessage(
            e.getMessage()); // Error message with the invalid input string.
      }
    }

    // Find the fastest route
    List<String> route =
        findRoute(
            startCountry, endCountry); // Save the history of the route and use findRoute method
    if (startCountry.equals(endCountry)) {
      MessageCli.NO_CROSSBORDER_TRAVEL
          .printMessage(); // print the cross border message. It means the start and end country are
      // the same.
    } else {
      // Otherwise, print the shortest route, continents, and total taxes.
      Set<String> visitedContinents = new LinkedHashSet<>();
      int taxTotal = 0;

      for (int i = 0; i < route.size(); i++) {
        Country country = graph.get(route.get(i));
        String continent = country.getContinent();
        visitedContinents.add(
            continent); // add the continent of countries that are passed. By using the set, the
        // continent will not be repeated.

        if (i > 0) {
          taxTotal = country.getTax() + taxTotal; // Calculate the total tax fees except itself.
        }
      }
      List<String> orderedContinents =
          new ArrayList<>(visitedContinents); // Convert the set to the list

      // Print the messages of the shortest route, continents, and total tax fees.
      MessageCli.ROUTE_INFO.printMessage(route.toString());
      MessageCli.CONTINENT_INFO.printMessage(orderedContinents.toString());
      MessageCli.TAX_INFO.printMessage(String.valueOf(taxTotal));
    }
  }

  /**
   * this method is invoked when the user call nameExceptionHandle to test the countryname is valid.
   */
  public void nameExceptionHandle(String countryName, String method) {

    if (graph.containsKey(countryName)) {
      if (method == "information") {
        // Print the information if the type is information (infoCountry)
        Country country = graph.get(countryName);
        String tax = String.valueOf(country.getTax());
        MessageCli.COUNTRY_INFO.printMessage(
            countryName,
            country.getContinent(),
            tax); // Print the information of the country (continent, tax fees).
      }

    } else {
      throw new CountryNameException(
          countryName); // If it is invalid, throw the exception with the country name.
    }
  }

  /**
   * this method is to find the fastest route from the source to destination country by using BFS.
   */
  public List<String> findRoute(String startCountry, String endCountry) {
    Set<String> track = new HashSet<>(); // To keep track of visited countries, use hashset
    Queue<List<String>> queue = new LinkedList<>(); // Use to hold paths

    // start tracking from the start country
    queue.add(Arrays.asList(startCountry));
    track.add(startCountry);

    // Repeat tracking until the queue is empty
    while (!queue.isEmpty()) {
      List<String> path = queue.poll();
      String pathLastCountry = path.get(path.size() - 1);

      if (pathLastCountry.equals(endCountry)) {
        // When the last country in the path is same as the end country, it means the path is over
        // so return the path
        return path;
      }

      // Otherwise, add the neighbour countries to path
      for (String neighbourCountry : graph.get(pathLastCountry).getAdjacencies()) {
        // BFS method: moving on to the next node(country) at the next level.
        if (!track.contains(neighbourCountry)) {
          track.add(neighbourCountry);
          List<String> newPath =
              new ArrayList<>(
                  path); // Add a new path which has the current path and add the neighbour country
          // at the end.
          newPath.add(neighbourCountry);
          queue.add(newPath);
        }
      }
    }
    return null; //  If there is no destination country we want to find, return null.
  }
}
