package org.cabi.ofra.dataload.db.impl;

import org.cabi.ofra.dataload.db.IDao;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Base object for all DAO Objects in the database layer
 * This object provides the main link between the data layer and Spring's {@link org.springframework.jdbc.core.JdbcTemplate}
 */
public class BaseDao implements IDao {
  // Jdbc template object for data access and manipulation using Spring's abstractions
  protected JdbcTemplate jdbcTemplate;

  @Override
  /**
   * Sets the datasource to use with the Jdbc Temmplate object
   */
  public void setDataSource(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
  }

  protected Object nvl(double d) {
    return (d == Double.MIN_VALUE) ? null : d;
  }

  protected Object nvl(int i) {
    return (i == Integer.MIN_VALUE) ? null : i;
  }

  protected Object nvl(String s) {
    return "".equals(s) ? null : s;
  }
}
