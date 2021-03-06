package org.cabi.ofra.dataload.impl;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.event.IEventCollector;
import org.cabi.ofra.dataload.model.ICellProcessor;
import org.cabi.ofra.dataload.model.IProcessingContext;
import org.cabi.ofra.dataload.util.Utilities;

/**
 * Checks if the cell has a value; otherwise, issues a message
 * This processor supports the following arguments:
 * - message: the message to issue if the cell is blank
 */
public class CheckPresent extends AbstractProcessor implements ICellProcessor {
  private static final String KEY_MESSAGE = "message";
  @Override
  public void processCell(IProcessingContext context, CellReference cellReference, Cell cell, IEventCollector eventCollector) throws ProcessorException {
    if (cell == null || Utilities.getStringCellValue(cell).isEmpty()) {
      throw new ProcessorException(getMessage(KEY_MESSAGE, String.format("Cell '%s' is required for processing, but is blank in the spreadsheet", cellReference.formatAsString())));
    }
  }
}
