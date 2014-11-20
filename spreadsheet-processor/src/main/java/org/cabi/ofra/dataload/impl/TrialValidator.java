package org.cabi.ofra.dataload.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.db.DatabaseService;
import org.cabi.ofra.dataload.event.IEventCollector;
import org.cabi.ofra.dataload.model.ICellProcessor;
import org.cabi.ofra.dataload.model.IProcessingContext;
import org.cabi.ofra.dataload.util.Pair;
import org.cabi.ofra.dataload.util.Utilities;

/**
 * Created by equiros on 11/13/2014.
 */
public class TrialValidator extends AbstractProcessor implements ICellProcessor {
  private static final String KEY_MESSAGE = "message";

  @Override
  public void processCell(IProcessingContext context, Cell cell, IEventCollector eventCollector) throws ProcessorException {
    String uid = Utilities.getStringCellValue(cell);
    Pair<String, String> pair = Utilities.splitUid(uid);
    DatabaseService databaseService = context.getDatabaseService();
    if (!databaseService.existsTrialByUniqueId(pair.car())) {
      throw new ProcessorException(getMessage(KEY_MESSAGE, "Cell value '%s' references trial unique identifier '%s', which does not exist", uid, pair.car()));
    }
  }
}
