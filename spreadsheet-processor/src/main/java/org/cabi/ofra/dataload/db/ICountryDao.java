package org.cabi.ofra.dataload.db;

/**
 * DAO object to retrieve country data
 */
public interface ICountryDao extends IDao {
  public boolean existsCountry(String countryCode);
}
