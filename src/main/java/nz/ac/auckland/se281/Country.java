package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is doing the function of the node class. It saves the information of each country:
 * continent, taxfees, and neighbour countries.
 */
public class Country {

  private String countryName;
  private String continentName;
  private int taxFees;
  private List<String> adjacentCountries;

  /** Update the constructor. */
  public Country(String countryName, String continentName, int taxFees) {
    // Initialise the adjacentCountries list
    this.countryName = countryName;
    this.continentName = continentName;
    this.taxFees = taxFees;
    this.adjacentCountries = new ArrayList<>();
  }

  public String getCountry() {
    return countryName;
  }

  public String getContinent() {
    return continentName;
  }

  public int getTax() {
    return taxFees;
  }

  public List<String> getAdjacencies() {
    return adjacentCountries;
  }

  /** add neighbour countries in the list. */
  public void addAdjencies(String adjCountry) {
    adjacentCountries.add(adjCountry); // Add the neighbour countries for each country
  }
}
