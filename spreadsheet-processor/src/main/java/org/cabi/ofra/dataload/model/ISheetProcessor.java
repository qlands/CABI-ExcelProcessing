package org.cabi.ofra.dataload.model;

import org.apache.poi.ss.usermodel.Sheet;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.configuration.SheetConfiguration;
import org.cabi.ofra.dataload.event.Event;
import org.cabi.ofra.dataload.event.IEventCollector;

import java.util.List;

/**
 * Contract for an object capable of processing a {@link org.apache.poi.ss.usermodel.Sheet} inside a template
 */
public interface ISheetProcessor {
  /**
   * Processes a {@link org.apache.poi.ss.usermodel.Sheet} during a template run
   * @param sheet The {@link org.apache.poi.ss.usermodel.Sheet} object to process
   * @param sheetConfiguration The {@link org.cabi.ofra.dataload.configuration.SheetConfiguration} object as configured for the run
   * @param eventCollector The {@link org.cabi.ofra.dataload.event.IEventCollector} object to collect events during the run
   * @param context The current {@link org.cabi.ofra.dataload.model.IProcessingContext} configured for the run
   * @throws ProcessorException
   */
  public void processSheet(Sheet sheet, SheetConfiguration sheetConfiguration, IEventCollector eventCollector, IProcessingContext context) throws ProcessorException;
}
