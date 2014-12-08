package org.cabi.ofra.dataload.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Configuration holder for template processing
 */
public class TemplateConfiguration {
  // name of the template
  private String name;
  // Collection of Sheet objects inside the template
  private Map<String, SheetConfiguration> sheets;
  // Parent Processor Configuration object
  private ProcessorConfiguration processorConfiguration;

  public TemplateConfiguration() {
    sheets = new HashMap<>();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void addSheetConfiguration(SheetConfiguration def) {
    sheets.put(def.getName(), def);
  }

  public Map<String, SheetConfiguration> getSheets() {
    return sheets;
  }

  public SheetConfiguration getSheet(String sheetName) {
    return sheets.get(sheetName);
  }

  public ProcessorConfiguration getProcessorConfiguration() {
    return processorConfiguration;
  }

  public void setProcessorConfiguration(ProcessorConfiguration processorConfiguration) {
    this.processorConfiguration = processorConfiguration;
  }
}
