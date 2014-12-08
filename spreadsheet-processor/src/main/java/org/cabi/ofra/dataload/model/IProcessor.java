package org.cabi.ofra.dataload.model;

/**
 * Common interface ancestor for all processors (either Cell or Range).
 * Allows processors to have a name and to allow arguments
 */
public interface IProcessor {
  /**
   * Sets the processor's name
   * @param name The name for the processor
   */
  public void setName(String name);

  /**
   * Obtains the name of the processor
   * @return The name of the processor
   */
  public String getName();

  /**
   * Sets a named argument for processing logic
   * @param name The name of the argument
   * @param value The value of the argument
   */
  public void setArgument(String name, Object value);

  /**
   * Resets the processor internal state for another processing run
   */
  public void reset();
}
