package org.cabi.ofra.dataload.configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
* Object to hold configuration for {@link org.cabi.ofra.dataload.model.ICellProcessor} instances associated with a Sheet
*/
public class SheetCellProcessorConfiguration {
  private String processorReference;
  private String location;
  private Map<String, String> arguments = new HashMap<>();
  private SheetConfiguration parentSheet;

  public SheetCellProcessorConfiguration() {
  }

  public String getProcessorReference() {
    return processorReference;
  }

  public void setProcessorReference(String processorReference) {
    this.processorReference = processorReference;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public void addArgument(String name, String value) {
    arguments.put(name, value);
  }

  public Map<String, String> getArguments() {
    if (arguments == null) return Collections.emptyMap();
    return arguments;
  }

  public SheetConfiguration getParentSheet() {
    return parentSheet;
  }

  public void setParentSheet(SheetConfiguration parentSheet) {
    this.parentSheet = parentSheet;
  }
}
