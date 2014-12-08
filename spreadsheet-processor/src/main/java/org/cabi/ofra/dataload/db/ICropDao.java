package org.cabi.ofra.dataload.db;

/**
 * DAO object to retrieve crop data
 */
public interface ICropDao extends IDao {
  public boolean existsCrop(String cropId);
}
