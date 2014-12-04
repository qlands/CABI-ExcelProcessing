package org.cabi.ofra.dataload.impl;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.configuration.SheetCellProcessorConfiguration;
import org.cabi.ofra.dataload.configuration.SheetConfiguration;
import org.cabi.ofra.dataload.configuration.SheetRangeConfiguration;
import org.cabi.ofra.dataload.event.Event;
import org.cabi.ofra.dataload.event.EventBuilder;
import org.cabi.ofra.dataload.event.IEventCollector;
import org.cabi.ofra.dataload.model.ICellProcessor;
import org.cabi.ofra.dataload.model.IProcessingContext;
import org.cabi.ofra.dataload.model.IRangeProcessor;
import org.cabi.ofra.dataload.model.ISheetProcessor;
import org.cabi.ofra.dataload.util.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Abstract class that provides method placeholders for pre- and post-range processing logic and cell processing logic
 */
public abstract class AbstractSheetProcessor implements ISheetProcessor {
  protected static Logger logger = LoggerFactory.getLogger(AbstractSheetProcessor.class);


  public void processSheet(Sheet sheet, SheetConfiguration sheetConfiguration, IEventCollector eventCollector, IProcessingContext context) throws ProcessorException {
    // first, let's fire the cell processors
    beforeFireCellProcessors(sheet, sheetConfiguration, eventCollector, context);
    fireCellProcessors(sheet, sheetConfiguration, eventCollector, context);
    afterFireCellProcessors(sheet, sheetConfiguration, eventCollector, context);
    // then, fire range processors
    beforeFireRangeProcessors(sheet, sheetConfiguration, eventCollector, context);
    fireRangeProcessors(sheet, sheetConfiguration, eventCollector, context);
    afterFireRangeProcessors(sheet, sheetConfiguration, eventCollector, context);
    // fire the main process on the sheet
    process(sheet, sheetConfiguration, eventCollector, context);
  }

  /**
   * This method is fired before calling the range processors in the {@link org.apache.poi.ss.usermodel.Sheet}
   * @param sheet The {@link org.apache.poi.ss.usermodel.Sheet} where ranges are being processed
   * @param sheetConfiguration The {@link org.cabi.ofra.dataload.configuration.SheetConfiguration} object associated with the Sheet
   * @param eventCollector The {@link org.cabi.ofra.dataload.event.IEventCollector} to collect processing events
   * @param context The {@link org.cabi.ofra.dataload.model.IProcessingContext} object configured for the run
   * @throws ProcessorException
   */
  protected abstract void afterFireRangeProcessors(Sheet sheet, SheetConfiguration sheetConfiguration, IEventCollector eventCollector, IProcessingContext context) throws ProcessorException;

  /**
   * This method is fired after calling the range processors in the {@link org.apache.poi.ss.usermodel.Sheet}
   * @param sheet The {@link org.apache.poi.ss.usermodel.Sheet} where ranges are being processed
   * @param sheetConfiguration The {@link org.cabi.ofra.dataload.configuration.SheetConfiguration} object associated with the Sheet
   * @param eventCollector The {@link org.cabi.ofra.dataload.event.IEventCollector} to collect processing events
   * @param context The {@link org.cabi.ofra.dataload.model.IProcessingContext} object configured for the run
   * @throws ProcessorException
   */
  protected abstract void beforeFireRangeProcessors(Sheet sheet, SheetConfiguration sheetConfiguration, IEventCollector eventCollector, IProcessingContext context) throws ProcessorException;

  /**
   * Fires the range processors associated with the {@link org.apache.poi.ss.usermodel.Sheet}
   * @param sheet The {@link org.apache.poi.ss.usermodel.Sheet} where ranges are being processed
   * @param sheetConfiguration The {@link org.cabi.ofra.dataload.configuration.SheetConfiguration} object associated with the Sheet
   * @param eventCollector The {@link org.cabi.ofra.dataload.event.IEventCollector} to collect processing events
   * @param context The {@link org.cabi.ofra.dataload.model.IProcessingContext} object configured for the run
   * @throws ProcessorException
   */
  private void fireRangeProcessors(Sheet sheet, SheetConfiguration sheetConfiguration, IEventCollector eventCollector, IProcessingContext context) throws ProcessorException {
    // get all configured ranges
    for (SheetRangeConfiguration rangeConfiguration : sheetConfiguration.getRangeConfigurations()) {
      // obtains the associated Range Processor
      IRangeProcessor rangeProcessor = getRangeProcessor(rangeConfiguration.getProcessorReference(), context);
      if (rangeProcessor != null) {
        // gets the first cell in the range as configured
        CellReference ref = new CellReference(rangeConfiguration.getStart());
        // if the cell is valid
        if (!Utilities.validateCellReference(ref, sheet)) {
          // if not, issue a message and skip to the next configured range
          String msg = String.format("Warning: Range processor '%s' references cell %s as range start, which is invalid in sheet '%s'", rangeProcessor.getName(), ref.toString(), sheet.getSheetName());
          eventCollector.addEvent(EventBuilder.createBuilder().withMessage(msg).withType(Event.EVENT_TYPE.WARNING).build());
          logger.warn(msg);
          continue;
        }
        // process the range
        for (int i = ref.getRow(); i <= sheet.getLastRowNum(); i++) {
          // get the row
          Row row = sheet.getRow(i);
          // check if the first cell in the row is not null (this is the stop criteria)
          Cell left = row.getCell(ref.getCol(), Row.RETURN_BLANK_AS_NULL);
          if (left != null) {
            // this means there is a valid row, then create the backing store
            List<Cell> r = new ArrayList<>(rangeConfiguration.getWidth());
            // calculate the last column in the range
            int lastColumn = Math.min(row.getLastCellNum(), ref.getCol() + rangeConfiguration.getWidth() - 1);
            // fill the row object
            for (int j = ref.getCol(); j <= lastColumn; j++) {
              r.add(row.getCell(j, Row.CREATE_NULL_AS_BLANK));
            }
            // and call the processor
            try {
              rangeProcessor.processRow(context, r, eventCollector, rangeConfiguration);
            }
            catch (Exception e) {
              String msg = String.format("Error processing row #%d on RangeProcessor '%s'", i, rangeProcessor.getName());
              logger.warn(msg, e);
              eventCollector.addEvent(EventBuilder.createBuilder().withMessage(msg).withException(e).withType(Event.EVENT_TYPE.WARNING).build());
            }
          }
        }
      }
      else {
        String msg = String.format("Warning: range processor reference %s not found while processing sheet '%s'", rangeConfiguration.getProcessorReference(), sheet.getSheetName());
        eventCollector.addEvent(EventBuilder.createBuilder().withMessage(msg).withType(Event.EVENT_TYPE.WARNING).build());
        logger.warn(msg);
      }
    }
  }

  /**
   * This method gets fired before all cell processors are fired in the {@link org.apache.poi.ss.usermodel.Sheet}
   * @param sheet The {@link org.apache.poi.ss.usermodel.Sheet} where ranges are being processed
   * @param sheetConfiguration The {@link org.cabi.ofra.dataload.configuration.SheetConfiguration} object associated with the Sheet
   * @param eventCollector The {@link org.cabi.ofra.dataload.event.IEventCollector} to collect processing events
   * @param context The {@link org.cabi.ofra.dataload.model.IProcessingContext} object configured for the run
   * @throws ProcessorException
   */
  protected abstract void afterFireCellProcessors(Sheet sheet, SheetConfiguration sheetConfiguration, IEventCollector eventCollector, IProcessingContext context) throws ProcessorException;

  /**
   * This method gets fired after all cell processors are fired in the {@link org.apache.poi.ss.usermodel.Sheet}
   * @param sheet The {@link org.apache.poi.ss.usermodel.Sheet} where ranges are being processed
   * @param sheetConfiguration The {@link org.cabi.ofra.dataload.configuration.SheetConfiguration} object associated with the Sheet
   * @param eventCollector The {@link org.cabi.ofra.dataload.event.IEventCollector} to collect processing events
   * @param context The {@link org.cabi.ofra.dataload.model.IProcessingContext} object configured for the run
   * @throws ProcessorException
   */
  protected abstract void beforeFireCellProcessors(Sheet sheet, SheetConfiguration sheetConfiguration, IEventCollector eventCollector, IProcessingContext context) throws ProcessorException;

  /**
   * Fires the cell processors associated with the {@link org.apache.poi.ss.usermodel.Sheet}
   * @param sheet The {@link org.apache.poi.ss.usermodel.Sheet} where ranges are being processed
   * @param sheetConfiguration The {@link org.cabi.ofra.dataload.configuration.SheetConfiguration} object associated with the Sheet
   * @param eventCollector The {@link org.cabi.ofra.dataload.event.IEventCollector} to collect processing events
   * @param context The {@link org.cabi.ofra.dataload.model.IProcessingContext} object configured for the run
   * @throws ProcessorException
   */
  private void fireCellProcessors(Sheet sheet, SheetConfiguration sheetConfiguration, IEventCollector eventCollector, IProcessingContext context) throws ProcessorException {
    // iterate through all configured cell processors
    for (SheetCellProcessorConfiguration cellProcessorConfiguration : sheetConfiguration.getCellProcessorConfigurations()) {
      // get the associated processor
      ICellProcessor cellProcessor = getCellProcessor(cellProcessorConfiguration.getProcessorReference(), context);
      if (cellProcessor != null) {
        // if the processor is valid, validate if the cell is valid
        CellReference ref = new CellReference(cellProcessorConfiguration.getLocation());
        if (!Utilities.validateCellReference(ref, sheet)) {
          // if not, issue an error
          String msg = String.format("Warning: Cell processor '%s' references cell %s, which is invalid in sheet '%s'", cellProcessor.getName(), ref.toString(), sheet.getSheetName());
          eventCollector.addEvent(EventBuilder.createBuilder().withMessage(msg).withType(Event.EVENT_TYPE.WARNING).build());
          logger.warn(msg);
          continue;
        }
        // get the row object from POI
        Row row = sheet.getRow(ref.getRow());
        // reset the processor's internal state
        cellProcessor.reset();
        // set the arguments
        for (Map.Entry<String, String> e : cellProcessorConfiguration.getArguments().entrySet()) {
          cellProcessor.setArgument(e.getKey(), e.getValue());
        }
        // and call the processor
        cellProcessor.processCell(context, row.getCell(ref.getCol(), Row.RETURN_BLANK_AS_NULL), eventCollector);
      }
      else {
        String msg = String.format("Warning: processor reference %s not found while processing sheet '%s'", cellProcessorConfiguration.getProcessorReference(), sheet.getSheetName());
        eventCollector.addEvent(EventBuilder.createBuilder().withMessage(msg).withType(Event.EVENT_TYPE.WARNING).build());
        logger.warn(msg);
      }
    }
  }

  /**
   * Gets the {@link org.cabi.ofra.dataload.model.ICellProcessor} instance from the context
   * @param processorReference The name of the processor to get
   * @param context The {@link org.cabi.ofra.dataload.model.IProcessingContext} in use
   * @return The {@link org.cabi.ofra.dataload.model.ICellProcessor} referenced by the 'processorReference' argument
   */
  private ICellProcessor getCellProcessor(String processorReference, IProcessingContext context) {
    return context.getCellProcessors().get(processorReference);
  }

  /**
   * Gets the {@link org.cabi.ofra.dataload.model.IRangeProcessor} instance from the context
   * @param processorReference The name of the processor to get
   * @param context The {@link org.cabi.ofra.dataload.model.IProcessingContext} in use
   * @return The {@link org.cabi.ofra.dataload.model.IRangeProcessor} referenced by the 'processorReference' argument
   */
  private IRangeProcessor getRangeProcessor(String processorReference, IProcessingContext context) {
    return context.getRangeProcessors().get(processorReference);
  }

  /**
   * Allows subclasses to process a {@link org.apache.poi.ss.usermodel.Sheet} object and provide custom logic during
   * template processing
   * @param sheet The {@link org.apache.poi.ss.usermodel.Sheet} where ranges are being processed
   * @param sheetConfiguration The {@link org.cabi.ofra.dataload.configuration.SheetConfiguration} object associated with the Sheet
   * @param eventCollector The {@link org.cabi.ofra.dataload.event.IEventCollector} to collect processing events
   * @param context The {@link org.cabi.ofra.dataload.model.IProcessingContext} object configured for the run
   * @throws ProcessorException
   */
  protected abstract void process(Sheet sheet, SheetConfiguration sheetConfiguration, IEventCollector eventCollector, IProcessingContext context) throws ProcessorException;
}
