package nz.ac.auckland.se281;

/**
 * This is the nonchecked custom Exception. When the invalid(non-exist) country name is typed, the
 * code will use this exception.
 */
public class CountryNameException extends RuntimeException {
  public CountryNameException(String countryName) {
    super(countryName);
  }
}
