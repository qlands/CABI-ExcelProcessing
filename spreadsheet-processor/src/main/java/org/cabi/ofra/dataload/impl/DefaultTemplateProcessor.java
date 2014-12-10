package org.cabi.ofra.dataload.impl;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.configuration.SheetConfiguration;
import org.cabi.ofra.dataload.configuration.TemplateConfiguration;
import org.cabi.ofra.dataload.db.DatabaseService;
import org.cabi.ofra.dataload.event.Event;
import org.cabi.ofra.dataload.event.EventBuilder;
import org.cabi.ofra.dataload.event.IEventCollector;
import org.cabi.ofra.dataload.model.IProcessingContext;
import org.cabi.ofra.dataload.model.ISheetProcessor;
import org.cabi.ofra.dataload.model.ITemplateProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Default implementation of a Template Processor. Provides the basic logic for creating a new DatabaseService, initialize it,
 * create a {@link org.cabi.ofra.dataload.model.IProcessingContext} via the default implementation, and iterate through all
 * the sheets in a workbook.
 * For each sheet, tries to locate a {@link org.cabi.ofra.dataload.configuration.TemplateConfiguration} which configures
 * processing logic, and calls the appropriate {@link org.cabi.ofra.dataload.model.ISheetProcessor} implementation class
 */
public class DefaultTemplateProcessor implements ITemplateProcessor {
  private static Logger logger = LoggerFactory.getLogger(DefaultTemplateProcessor.class);

  public IProcessingContext processTemplate(Workbook workbook, TemplateConfiguration configuration, IEventCollector eventCollector, String databasePropertiesFile, String user, String ckanorg, String trialPublic) throws ProcessorException {
    try {
      // first, initialize the database service, potentially from an externalized properties file
      DatabaseService databaseService = new DatabaseService();
      databaseService.initialize(databasePropertiesFile);
      IProcessingContext context = new DefaultProcessingContext(configuration.getProcessorConfiguration().getCellProcessors(), configuration.getProcessorConfiguration().getRangeProcessors(), databaseService, user, ckanorg, trialPublic);
      for (int s = 0; s < workbook.getNumberOfSheets(); s++) {
        Sheet sheet = workbook.getSheetAt(s);
        // Check if there is a configured SheetConfiguration object
        SheetConfiguration sheetConfiguration = configuration.getSheet(sheet.getSheetName());
        if (sheetConfiguration != null) {
          try {
            // try to create the Sheet Processor
            ISheetProcessor sheetProcessor = createSheetProcessor(sheetConfiguration.getImplementationClass());
            // and call it to process the sheet
            sheetProcessor.processSheet(sheet, sheetConfiguration, eventCollector, context);
          }
          catch (Exception e) {
            databaseService.rollback();
            throw new ProcessorException(String.format("Error while processing sheet '%s' : %s", sheet.getSheetName(), e.getMessage()));
          }
        }
        else {
          String msg = String.format("Warning: sheet configuration not found for sheet '%1$s' in template '%2$s'", sheet.getSheetName(), configuration.getName());
          eventCollector.addEvent(EventBuilder.createBuilder().withMessage(msg).withType(Event.EVENT_TYPE.WARNING).build());
          //throw new ProcessorException(msg);
        }
      }
      databaseService.commit();
      return context;
    }
    catch (IOException | SQLException  e) {
      throw new ProcessorException(e);
    }
  }

  private ISheetProcessor createSheetProcessor(String clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    Class cls = DefaultTemplateProcessor.class.getClassLoader().loadClass(clazz);
    return (ISheetProcessor) cls.newInstance();
  }
}
