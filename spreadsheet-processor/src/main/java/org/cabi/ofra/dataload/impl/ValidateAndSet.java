package org.cabi.ofra.dataload.impl;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.event.Event;
import org.cabi.ofra.dataload.event.EventBuilder;
import org.cabi.ofra.dataload.event.IEventCollector;
import org.cabi.ofra.dataload.model.ICellProcessor;
import org.cabi.ofra.dataload.model.IProcessingContext;
import org.cabi.ofra.dataload.util.Utilities;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * Cell processor that validates that the contents of a cell is not blank, potentially checks this content against
 * a regular expression, and sets a variable value in the current processing context, for further use down the
 * template processing chain
 * This validator accepts the following arguments:
 * + regex: optional regular expression to validate the cell value. If the argument is present, the regex is applied against
 *          the string value of the cell contents. If the value of the cell does not match against the regular expression,
 *          the variable name (next argument) will not be set
 * + variableName: name of the variable to set if the content of the cell is non blank, and if matches against the regex
 * + toString: boolean to determine if the value to store in the variable will be the {@code toString} value of the cell
 * + required: boolean to determine if the cell value is required. If not, and the cell value is not present, the validation passes
 */
public class ValidateAndSet extends AbstractProcessor implements ICellProcessor {
  private static final String KEY_REQUIRED = "required";
  private static final String KEY_REGEX = "regex";
  private static final String KEY_VARIABLENAME = "variableName";
  private static final String KEY_TOSTRING = "toString";

  @Override
  public void processCell(IProcessingContext context, CellReference cellReference, Cell cell, IEventCollector eventCollector) throws ProcessorException {
    if (checkRequired()) {
      if (cell == null) {
        String msg = String.format("Cell '%s' is null. Processing aborted on ValidateAndSet processor", cellReference.formatAsString());
        eventCollector.addEvent(EventBuilder.createBuilder().withMessage(msg).withType(Event.EVENT_TYPE.WARNING).build());
        throw new ProcessorException(msg);
      }
      if (!arguments.containsKey(KEY_VARIABLENAME)) {
        throw new ProcessorException("ValidateAndSet processor requires 'variableName' argument");
      }
      String variableName = arguments.get(KEY_VARIABLENAME).toString();
      if (arguments.containsKey(KEY_REGEX)) {
        if (!Pattern.matches(arguments.get(KEY_REGEX).toString(), cell.getStringCellValue())) {
          String msg = String.format("Value '%s' does not match pattern '%s'. Variable '%s' will not be set", cell.getStringCellValue(), arguments.get(KEY_REGEX).toString(),
                  variableName);
          eventCollector.addEvent(EventBuilder.createBuilder().withMessage(msg).withType(Event.EVENT_TYPE.WARNING).build());
        }
      }
      Serializable val = Utilities.getCellValue(cell);
      if (arguments.containsKey(KEY_TOSTRING)) {
        val = String.valueOf(val);
      }
      context.set(variableName, val);
    }
  }

  private boolean checkRequired() {
    return !arguments.containsKey(KEY_REQUIRED) || Boolean.valueOf(arguments.get(KEY_REQUIRED).toString());
  }
}
