package org.cabi.ofra.dataload.specific;

import org.apache.poi.ss.usermodel.Cell;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.configuration.SheetRangeConfiguration;
import org.cabi.ofra.dataload.db.DatabaseService;
import org.cabi.ofra.dataload.event.IEventCollector;
import org.cabi.ofra.dataload.impl.AbstractRangeProcessor;
import org.cabi.ofra.dataload.model.IProcessingContext;
import org.cabi.ofra.dataload.model.PlotActivity;
import org.cabi.ofra.dataload.model.PlotObservation;
import org.cabi.ofra.dataload.util.Triplet;
import org.cabi.ofra.dataload.util.Utilities;

import java.text.ParseException;
import java.util.List;

/**
 * Created by equiros on 11/26/2014.
 */
public class PlotTemplates {
  public static class PlotActivitiesRangeProcessor extends AbstractRangeProcessor {
    @Override
    protected void process(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration) throws ProcessorException {
      DatabaseService databaseService = context.getDatabaseService();
      String plotUid = Utilities.getStringCellValue(row.get(0));
      Triplet<String, Integer, Integer> triplet = Utilities.validatePlot(databaseService, plotUid);
      PlotActivity activity = new PlotActivity();
      activity.setTrialUniqueId(triplet.getFirst());
      activity.setBlockId(triplet.getSecond());
      activity.setPlotId(triplet.getThird());
      fillPlotActivity(activity, row);
      databaseService.createOrUpdatePlotActivity(activity);
    }

    private void fillPlotActivity(PlotActivity activity, List<Cell> row) throws ProcessorException {
      activity.setActivityId(Utilities.getIntegerCellValue(row.get(1)));
      try {
        activity.setActivityDate(Utilities.getDateCellValue(row.get(2)));
      }
      catch (ParseException e) {
        throw new ProcessorException(e);
      }
      activity.setActivityType(Utilities.getStringCellValue(row.get(3)));
      activity.setActivityNotes(Utilities.getStringCellValue(row.get(4)));
    }
  }

  public static class PlotObservationsRangeProcessor extends AbstractRangeProcessor {
    @Override
    protected void process(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration) throws ProcessorException {
      DatabaseService databaseService = context.getDatabaseService();
      String plotUid = Utilities.getStringCellValue(row.get(0));
      Triplet<String, Integer, Integer> triplet = Utilities.validatePlot(databaseService, plotUid);
      PlotObservation observation = new PlotObservation();
      observation.setTrialUniqueId(triplet.getFirst());
      observation.setBlockId(triplet.getSecond());
      observation.setPlotId(triplet.getThird());
      fillPlotObservation(observation, row);
      databaseService.createOrUpdatePlotObservation(observation);
    }

    private void fillPlotObservation(PlotObservation observation, List<Cell> row) throws ProcessorException {
      observation.setObservationId(Utilities.getIntegerCellValue(row.get(1)));
      try {
        observation.setObservationDate(Utilities.getDateCellValue(row.get(2)));
      }
      catch (ParseException e) {
        throw new ProcessorException(e);
      }
      observation.setObservationNotes(Utilities.getStringCellValue(row.get(3)));
      observation.setObservationDiseaseRelated(Utilities.getBooleanCellValue(row.get(4)));
    }
  }
}
