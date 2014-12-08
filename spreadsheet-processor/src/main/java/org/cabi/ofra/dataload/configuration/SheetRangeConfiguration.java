package org.cabi.ofra.dataload.configuration;

import java.util.*;

/**
* Object to hold cofiguration information for a range within a {@link org.cabi.ofra.dataload.configuration.SheetConfiguration}
*/
public class SheetRangeConfiguration {
  // range processor reference
  String processorReference;
  // named cell (e.g. A0) on which the range starts
  String start;
  // range width
  int width;
  // optional arguments for range processor call
  Map<String, String> arguments;
  // optional column bindings
  Map<Integer, SheetRangeColumnBindingConfiguration> columnBindings;
  // indicates whether all columns in the range are required
  boolean requireAll = false;

  public SheetRangeConfiguration() {
    columnBindings = new HashMap<>();
  }

  public String getProcessorReference() {
    return processorReference;
  }

  public void setProcessorReference(String processorReference) {
    this.processorReference = processorReference;
  }

  public String getStart() {
    return start;
  }

  public void setStart(String start) {
    this.start = start;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public void addArgument(String name, String value) {
    if (arguments == null) {
      arguments = new HashMap<String, String>();
    }
    arguments.put(name, value);
  }

  public void addColumnBindingConfiguration(SheetRangeColumnBindingConfiguration bindingConfiguration) {
    columnBindings.put(bindingConfiguration.getColumn(), bindingConfiguration);
  }

  public Map<String, String> getArguments() {
    if (arguments == null) return Collections.emptyMap();
    return arguments;
  }

  public Map<Integer, SheetRangeColumnBindingConfiguration> getColumnBindings() {
    return columnBindings;
  }

  public boolean isRequireAll() {
    return requireAll;
  }

  public void setRequireAll(boolean requireAll) {
    this.requireAll = requireAll;
  }
}
