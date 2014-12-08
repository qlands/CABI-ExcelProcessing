package org.cabi.ofra.dataload.model;

import org.apache.poi.ss.usermodel.Cell;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.event.IEventCollector;

/**
 * Models logic that can process {@link org.apache.poi.ss.usermodel.Cell} contents during template processing.
 */
public interface ICellProcessor extends IProcessor {
  /**
   * Processes a cell
   * @param context The current {@link org.cabi.ofra.dataload.model.IProcessingContext} for the template run
   * @param cell The {@link org.apache.poi.ss.usermodel.Cell} to process
   * @param eventCollector The {@link org.cabi.ofra.dataload.event.IEventCollector} for collecting internal events during the
   *                       processing run
   * @throws ProcessorException
   */
  public void processCell(IProcessingContext context, Cell cell, IEventCollector eventCollector) throws ProcessorException;
}
