package org.cabi.ofra.dataload.impl;

import org.apache.poi.ss.usermodel.Sheet;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.configuration.SheetConfiguration;
import org.cabi.ofra.dataload.event.IEventCollector;
import org.cabi.ofra.dataload.model.IProcessingContext;

/**
 * Base class for creating Sheet Procesors. Provides empty methods for all abstract methods from
 * {@link org.cabi.ofra.dataload.impl.AbstractSheetProcessor}
 * This class can also serve as main Sheet Processor for templates where only range processing
 * is required.
 */
public class BaseSheetProcessor extends AbstractSheetProcessor {
  @Override
  protected void afterFireCellProcessors(Sheet sheet, SheetConfiguration sheetConfiguration, IEventCollector eventCollector, IProcessingContext context) throws ProcessorException {

  }

  @Override
  protected void beforeFireCellProcessors(Sheet sheet, SheetConfiguration sheetConfiguration, IEventCollector eventCollector, IProcessingContext context) throws ProcessorException {

  }

  @Override
  protected void process(Sheet sheet, SheetConfiguration sheetConfiguration, IEventCollector eventCollector, IProcessingContext context) throws ProcessorException{

  }

  @Override
  protected void afterFireRangeProcessors(Sheet sheet, SheetConfiguration sheetConfiguration, IEventCollector eventCollector, IProcessingContext context) throws ProcessorException  {

  }

  @Override
  protected void beforeFireRangeProcessors(Sheet sheet, SheetConfiguration sheetConfiguration, IEventCollector eventCollector, IProcessingContext context) throws ProcessorException {

  }
}
