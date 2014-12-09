package org.cabi.ofra.dataload.model;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.event.IEventCollector;

/**
 * Models logic that can process {@link org.apache.poi.ss.usermodel.Cell} contents during template processing.
 */
public interface ICellProcessor extends IProcessor {
  /**
   * Processes a cell
   * @param context The current {@link IProcessingContext} for the template run
   * @param cellReference
   *@param cell The {@link org.apache.poi.ss.usermodel.Cell} to process
   * @param eventCollector The {@link org.cabi.ofra.dataload.event.IEventCollector} for collecting internal events during the
 *                       processing run   @throws ProcessorException
   */
  public void processCell(IProcessingContext context, CellReference cellReference, Cell cell, IEventCollector eventCollector) throws ProcessorException;
}
