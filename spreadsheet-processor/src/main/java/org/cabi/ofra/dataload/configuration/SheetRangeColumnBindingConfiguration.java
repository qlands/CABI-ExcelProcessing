package org.cabi.ofra.dataload.configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration object for column bindings associated with a range. Ranges can have column-bound configuration information
 * for cell processors that will be called on each cell for the configured range-column binding
*/
public class SheetRangeColumnBindingConfiguration {
  // column within the range to which the binding is associated
  private int column;
  // cell processor associated with the column
  private String processorReference;
  // optional arguments to call the cell processor
  private Map<String, String> arguments = new HashMap<>();
  // parent range configuration
  private SheetRangeConfiguration parentRangeConfiguration;

  public SheetRangeColumnBindingConfiguration() {
  }

  public int getColumn() {
    return column;
  }

  public void setColumn(int column) {
    this.column = column;
  }

  public String getProcessorReference() {
    return processorReference;
  }

  public void setProcessorReference(String processorReference) {
    this.processorReference = processorReference;
  }

  public Map<String, String> getArguments() {
    return arguments;
  }

  public void addArgument(String name, String value) {
    arguments.put(name, value);
  }

  public SheetRangeConfiguration getParentRangeConfiguration() {
    return parentRangeConfiguration;
  }

  public void setParentRangeConfiguration(SheetRangeConfiguration parentRangeConfiguration) {
    this.parentRangeConfiguration = parentRangeConfiguration;
  }
}
