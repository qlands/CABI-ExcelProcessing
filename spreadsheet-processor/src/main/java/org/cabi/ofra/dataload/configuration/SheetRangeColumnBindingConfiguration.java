package org.cabi.ofra.dataload.configuration;

import java.util.HashMap;
import java.util.Map;

/**
* (c) 2014, Eduardo Quirós-Campos
*/
public class SheetRangeColumnBindingConfiguration {
  private int column;
  private String processorReference;
  private Map<String, String> arguments = new HashMap<>();
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
