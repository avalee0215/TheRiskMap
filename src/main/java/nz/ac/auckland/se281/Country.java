package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.List;

public class Country {

  private String countryName;
  private String continentName;
  private int taxFees;
  private List<String> adjacentCountries;

  public Country(String countryName, String continentName, int taxFees) {
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
}
