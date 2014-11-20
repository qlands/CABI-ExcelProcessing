package org.cabi.ofra.dataload.specific;

import org.apache.poi.ss.usermodel.Cell;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.configuration.SheetRangeConfiguration;
import org.cabi.ofra.dataload.db.DatabaseService;
import org.cabi.ofra.dataload.event.IEventCollector;
import org.cabi.ofra.dataload.impl.AbstractRangeProcessor;
import org.cabi.ofra.dataload.model.BlockActivity;
import org.cabi.ofra.dataload.model.IProcessingContext;
import org.cabi.ofra.dataload.util.Pair;
import org.cabi.ofra.dataload.util.Utilities;

import java.text.ParseException;
import java.util.List;

/**
 * Created by equiros on 11/18/2014.
 */
public class BlockTemplates {
  /**
   * Processes a range composed of block activity data
   * This class assumes the block UID has already been validated
   */
  public static class BlockActivitiesRangeProcessor extends AbstractRangeProcessor {
    DatabaseService databaseService;
    @Override
    protected void process(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration) throws ProcessorException {
      databaseService = context.getDatabaseService();
      BlockActivity activity = buildBlockActivity(row);
      databaseService.createOrUpdateBlockActivity(activity);
    }

    private BlockActivity buildBlockActivity(List<Cell> row) throws ProcessorException {
      try {
        BlockActivity activity = new BlockActivity();
        // process row id
        String blockUid = Utilities.getStringCellValue(row.get(0));
        Pair<String, Integer> pair = Utilities.splitBlockUid(blockUid);
        activity.setTrialUniqueId(pair.car());
        activity.setBlockNumber(pair.cdr());
        activity.setActivityNumber(Utilities.getIntegerCellValue(row.get(1)));
        activity.setActivityDate(Utilities.getDateCellValue(row.get(2)));
        activity.setAcivityType(Utilities.getStringCellValue(row.get(3)));
        activity.setActivityNotes(Utilities.getStringCellValue(row.get(4)));
        return activity;
      }
      catch (ParseException e) {
        throw new ProcessorException(e);
      }
    }
  }

  public static class BlockObservationsProcessor extends AbstractRangeProcessor {
    @Override
    protected void process(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration) throws ProcessorException {

    }
  }
}
