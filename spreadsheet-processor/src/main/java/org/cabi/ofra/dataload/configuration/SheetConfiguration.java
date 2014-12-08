package org.cabi.ofra.dataload.configuration;

import java.util.*;

/**
 * Object to hold configuration for a {@link org.apache.poi.ss.usermodel.Sheet} during the processing run.
 */
public class SheetConfiguration {
  // name of the sheet
  private String name;
  // cell processors associated with the sheet
  private List<SheetCellProcessorConfiguration> cellProcessorConfigurations;
  // ranges configured for the sheet
  private List<SheetRangeConfiguration> rangeConfigurations;
  // indicator of whether the sheet is required for processing or not
  private boolean required;
  // implementation class of the ISheetProcessor
  private String implementationClass;
  // parent template configuration
  private TemplateConfiguration parentTemplate;

  public SheetConfiguration() {
    cellProcessorConfigurations = new ArrayList<>();
    rangeConfigurations = new ArrayList<>();
  }

  public void addCellProcessorConfiguration(SheetCellProcessorConfiguration config) {
    cellProcessorConfigurations.add(config);
  }

  public void addRangeConfiguration(SheetRangeConfiguration config) {
    rangeConfigurations.add(config);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setRequired(boolean required) {
    this.required = required;
  }

  public boolean isRequired() {
    return required;
  }

  public String getImplementationClass() {
    return implementationClass;
  }

  public void setImplementationClass(String implementationClass) {
    this.implementationClass = implementationClass;
  }

  public List<SheetCellProcessorConfiguration> getCellProcessorConfigurations() {
    return cellProcessorConfigurations;
  }

  public List<SheetRangeConfiguration> getRangeConfigurations() {
    return rangeConfigurations;
  }

  public TemplateConfiguration getParentTemplate() {
    return parentTemplate;
  }

  public void setParentTemplate(TemplateConfiguration parentTemplate) {
    this.parentTemplate = parentTemplate;
  }
}
