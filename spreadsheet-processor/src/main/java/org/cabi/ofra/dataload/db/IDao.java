package org.cabi.ofra.dataload.db;

import javax.sql.DataSource;

/**
 * Base DAO interface. Allows setting the reference {@link javax.sql.DataSource}
 */
public interface IDao {
  public void setDataSource(DataSource dataSource);
}
