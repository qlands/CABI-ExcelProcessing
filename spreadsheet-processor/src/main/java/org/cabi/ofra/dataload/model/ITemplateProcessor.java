package org.cabi.ofra.dataload.model;

import org.apache.poi.ss.usermodel.Workbook;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.configuration.TemplateConfiguration;
import org.cabi.ofra.dataload.event.Event;
import org.cabi.ofra.dataload.event.IEventCollector;

import java.util.List;

/**
 * Interface for main processing logic. Although there is a single implementation, the interface could allow for multiple implementations
 * to be provided by the SpreadSheet Processor via configuration
 */
public interface ITemplateProcessor {
  /**
   * Processes a given template
   * @param workbook The {@link org.apache.poi.ss.usermodel.Workbook} instance that corresponds to the
   * @param configuration The {@link org.cabi.ofra.dataload.configuration.TemplateConfiguration} instance corresponding to the configured template mapping
   * @param eventCollector The {@link org.cabi.ofra.dataload.event.IEventCollector} instance to collect events inside the processor
   * @param databasePropertiesFile The name of the database properties file to use, if specified in the command line arguments
   * @param user The user name for creating/updating Trial information
   * @param ckanorg The CKAN organization name to use for creating/updating Trial information
   * @return The resulting {@link org.cabi.ofra.dataload.model.IProcessingContext} after processing the template
   * @throws ProcessorException
   */
  public IProcessingContext processTemplate(Workbook workbook, TemplateConfiguration configuration, IEventCollector eventCollector, String databasePropertiesFile, String user, String ckanorg) throws ProcessorException;
}
