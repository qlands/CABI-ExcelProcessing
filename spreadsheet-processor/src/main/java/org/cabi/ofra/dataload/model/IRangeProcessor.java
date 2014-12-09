package org.cabi.ofra.dataload.model;

import org.apache.poi.ss.usermodel.Cell;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.configuration.SheetRangeConfiguration;
import org.cabi.ofra.dataload.event.IEventCollector;

import java.util.List;

/**
 * Models logic to process {@link org.apache.poi.ss.usermodel.Sheet} ranges inside a {@link org.apache.poi.ss.usermodel.Workbook}
 */
public interface IRangeProcessor extends IProcessor {
  /**
   * Processes a row in a range (modeled as a {@link java.util.List} of {@link org.apache.poi.ss.usermodel.Cell} objects)
   * @param context The current {@link IProcessingContext} during the processing run
   * @param row The row to process
   * @param rowIndex
   *@param eventCollector The {@link org.cabi.ofra.dataload.event.IEventCollector} for collecting processing events
   * @param rangeConfiguration The {@link org.cabi.ofra.dataload.configuration.SheetRangeConfiguration} object corresponding to the {@link org.apache.poi.ss.usermodel.Sheet} containing the range   @throws ProcessorException
   */
  public void processRow(IProcessingContext context, List<Cell> row, int rowIndex, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration) throws ProcessorException;
}
