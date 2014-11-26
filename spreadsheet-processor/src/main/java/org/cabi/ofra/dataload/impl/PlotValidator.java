package org.cabi.ofra.dataload.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.db.DatabaseService;
import org.cabi.ofra.dataload.event.IEventCollector;
import org.cabi.ofra.dataload.model.ICellProcessor;
import org.cabi.ofra.dataload.model.IProcessingContext;
import org.cabi.ofra.dataload.util.Triplet;
import org.cabi.ofra.dataload.util.Utilities;

/**
 * Created by equiros on 11/26/2014.
 */
public class PlotValidator extends AbstractProcessor implements ICellProcessor {
  private static final String KEY_TRIALMESSAGE = "trialMessage";
  private static final String KEY_BLOCKMESSAGE = "blockMessage";
  private static final String KEY_PLOTMESSAGE = "plotMessage";
  @Override
  public void processCell(IProcessingContext context, Cell cell, IEventCollector eventCollector) throws ProcessorException {
    DatabaseService databaseService = context.getDatabaseService();
    String plotUid = Utilities.getStringCellValue(cell);
    Triplet<String, Integer, Integer> triplet = Utilities.splitPlotUid(plotUid);
    if (!databaseService.existsTrialByUniqueId(triplet.getFirst())) {
      throw new ProcessorException(getMessage(KEY_TRIALMESSAGE, "Cell value %s references trial UID %s, which does not exist", plotUid, triplet.getFirst()));
    }
    if (!databaseService.existsBlockById(triplet.getFirst(),triplet.getSecond())) {
      throw new ProcessorException(getMessage(KEY_BLOCKMESSAGE, "Cell value %s references block %d for trial UID %s, which does not exist", plotUid, triplet.getSecond(), triplet.getFirst()));
    }
    if (!databaseService.existsPlotById(triplet.getFirst(), triplet.getSecond(), triplet.getThird())) {
      throw new ProcessorException(getMessage(KEY_BLOCKMESSAGE, "Cell value %s references plot %d for block %d for trial UID %s, which does not exist", plotUid, triplet.getThird(), triplet.getSecond(), triplet.getFirst()));
    }
  }
}
