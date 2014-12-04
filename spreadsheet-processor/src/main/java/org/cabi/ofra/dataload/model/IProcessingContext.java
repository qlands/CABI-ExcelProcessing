package org.cabi.ofra.dataload.model;

import org.cabi.ofra.dataload.db.DatabaseService;

import java.io.Serializable;
import java.util.Map;

/**
 * Represents globally-accessible information during a template processing run.
 * Includes an environment of name->value mappings, all registered cell and range processors,
 * the database service in use and other contextual information such as the provided user name
 * and CKAN organization name
 */
public interface IProcessingContext {
  /**
   * Sets a name->value mapping in the context
   * @param name The name key
   * @param value The value. Must be {@link java.io.Serializable}
   */
  public void set(String name, Serializable value);

  /**
   * Gets the value corresponding to the specified key
   * @param name The key to look for in the mapping
   * @return The corresponding value
   */
  public Serializable get(String name);

  /**
   * Obtains the configured instances of {@link org.cabi.ofra.dataload.model.ICellProcessor}
   * @return A {@link java.util.Map} with the named cell processor instances
   */
  public Map<String, ICellProcessor> getCellProcessors();

  /**
   * Obtains the configured instances of {@link org.cabi.ofra.dataload.model.IRangeProcessor}
   * @return A {@link java.util.Map} with the named cell processor instances
   */
  public Map<String, IRangeProcessor> getRangeProcessors();

  /**
   * Obtains the configured {@link org.cabi.ofra.dataload.db.DatabaseService} instance
   * @return
   */
  public DatabaseService getDatabaseService();

  /**
   * Obtains the configured user name
   * @return The configured user name
   */
  public String getUser();

  /**
   * Obtains the configured CKAN organization name
   * @return the configured CKAN organization name
   */
  public String getCkanOrganization();

  /**
   * Convenience generic default method for obtaining key->value mappings without resorting to
   * type-casting from {@link java.io.Serializable}
   * @param name The key for the value to obtain
   * @param <T> Class specification of the desired value stored in the map (must extend {@link java.io.Serializable})
   * @return The mapping value
   */
  public default <T extends Serializable> T getv(String name) {
    return (T) get(name);
  }
}
