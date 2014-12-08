package org.cabi.ofra.dataload.impl;

import org.cabi.ofra.dataload.model.IProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class that provides basic functionality common to all processors (Cell or Range)
 * Provides internal variables for storing the processor's name, as well as a backing store
 * for the arguments ({@link java.util.Map}
 */
public abstract class AbstractProcessor implements IProcessor {
  protected String name;
  // SLF4J Logger
  protected Logger logger = LoggerFactory.getLogger(this.getClass());

  /**
   * Backing store for processor arguments. Arguments are set before each processing call.
   */
  protected Map<String, Object> arguments = new HashMap<>();

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setArgument(String name, Object value) {
    arguments.put(name, value);
  }

  @Override
  public void reset() {
    // clears the arguments
    arguments.clear();
  }

  /**
   * Obtains an argument, assumes it is a formattable string and passes a set of arguments to the
   * standard String formatting routine, for usage as a message in logic
   * @param key The argument to treat as a message string
   * @param defaultMessage Fallback message string if the key is not found as argument
   * @param args Variable arguments to pass to the resulting message
   * @return The formatted message string
   */
  protected String getMessage(String key, String defaultMessage, Object ... args) {
    String msg = defaultMessage;
    Object m = arguments.get(key);
    if (m != null && m.getClass().equals(String.class)) {
      msg = (String) m;
    }
    return String.format(msg, args);
  }
}
