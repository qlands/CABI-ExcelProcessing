package org.cabi.ofra.dataload.impl;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.db.DatabaseService;
import org.cabi.ofra.dataload.event.IEventCollector;
import org.cabi.ofra.dataload.model.ICellProcessor;
import org.cabi.ofra.dataload.model.IProcessingContext;
import org.cabi.ofra.dataload.util.Utilities;

/**
 * Validates an activity code against the database. Optionally checks for null activity code and issues an error message if not present
 * Accepts the following arguments:
 * + required: boolean to indicate if the activity code is required or not (default true)
 * + absentMessage: message to issue if the activity code is required and not present
 * + validationMessage: message to issue if the activity code is not valid against the database. Placeholder: activity code
 */
public class ActivityValidator extends AbstractProcessor implements ICellProcessor {
  private static String KEY_REQUIRED = "required";
  private static String KEY_ABSENTMESSAGE = "absentMessage";
  private static String KEY_VALIDATIONMESSAGE = "validationMessage";

  @Override
  public void processCell(IProcessingContext context, CellReference cellReference, Cell cell, IEventCollector eventCollector) throws ProcessorException {
    if (required() && (cell == null || Utilities.getStringCellValue(cell).isEmpty())) {
      throw new ProcessorException(getMessage(KEY_ABSENTMESSAGE, "Activity code required but not present"));
    }
    if (cell != null && !Utilities.getStringCellValue(cell).isEmpty()) {
      String activityCode = Utilities.getStringCellValue(cell);
      DatabaseService databaseService = context.getDatabaseService();
      if (!databaseService.existsActivity(activityCode)) {
        throw new ProcessorException(getMessage(KEY_VALIDATIONMESSAGE, "Activity code '%s' is not valid", activityCode));
      }
    }
  }

  private boolean required() {
    return !arguments.containsKey(KEY_REQUIRED) || Boolean.valueOf(arguments.get(KEY_REQUIRED).toString());
  }
}
