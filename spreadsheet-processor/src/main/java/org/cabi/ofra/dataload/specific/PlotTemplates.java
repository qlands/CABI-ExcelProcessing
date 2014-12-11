package org.cabi.ofra.dataload.specific;

import org.apache.poi.ss.usermodel.Cell;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.configuration.SheetRangeConfiguration;
import org.cabi.ofra.dataload.db.DatabaseService;
import org.cabi.ofra.dataload.event.IEventCollector;
import org.cabi.ofra.dataload.impl.AbstractProcessor;
import org.cabi.ofra.dataload.impl.AbstractRangeProcessor;
import org.cabi.ofra.dataload.model.*;
import org.cabi.ofra.dataload.util.Triplet;
import org.cabi.ofra.dataload.util.Utilities;

import java.text.ParseException;
import java.util.List;

/**
 * Created by equiros on 11/26/2014.
 */
public class PlotTemplates {
  private static abstract class AbstractPlotPortionRangeProcessor extends AbstractRangeProcessor {
    protected DatabaseService databaseService;

    @Override
    protected void process(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration) throws ProcessorException {
      databaseService = context.getDatabaseService();
      String plotUid = Utilities.getStringCellValue(row.get(0));
      Triplet<String, Integer, Integer> triplet = Utilities.splitPlotUid(plotUid);
      Plot plot = databaseService.findPlotById(triplet.getFirst(), triplet.getSecond(), triplet.getThird());
      if (plot != null) {
        updatePlot(row, plot, eventCollector, rangeConfiguration);
        databaseService.updatePlot(plot);
      }
    }

    protected abstract void updatePlot(List<Cell> row, Plot plot, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration) throws ProcessorException;
  }

  public static class PlotFloweringDateRangeProcessor extends AbstractPlotPortionRangeProcessor {
    @Override
    protected void updatePlot(List<Cell> row, Plot plot, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration) throws ProcessorException {
      try {
        plot.setFlowerDate(Utilities.getDateCellValue(row.get(1)));
      }
      catch (ParseException e) {
        throw new ProcessorException(e);
      }
    }
  }

  public static class PlotBootingDateRangeProcessor extends AbstractPlotPortionRangeProcessor {
    @Override
    protected void updatePlot(List<Cell> row, Plot plot, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration) throws ProcessorException {
      try {
        plot.setBootingDate(Utilities.getDateCellValue(row.get(1)));
      }
      catch (ParseException e) {
        throw new ProcessorException(e);
      }
    }
  }

  public static class PlotPanicleDateRangeProcessor extends AbstractPlotPortionRangeProcessor {
    @Override
    protected void updatePlot(List<Cell> row, Plot plot, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration) throws ProcessorException {
      try {
        plot.setPanicleDate(Utilities.getDateCellValue(row.get(1)));
      }
      catch (ParseException e) {
        throw new ProcessorException(e);
      }
    }
  }

  public static class PlotSilkDateRangeProcessor extends AbstractPlotPortionRangeProcessor {
    @Override
    protected void updatePlot(List<Cell> row, Plot plot, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration) throws ProcessorException {
      try {
        plot.setSilkDate(Utilities.getDateCellValue(row.get(1)));
      }
      catch (ParseException e) {
        throw new ProcessorException(e);
      }
    }
  }

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

  public static class PlotPlantSampleRangeProcessor extends AbstractRangeProcessor {
    @Override
    protected void process(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration) throws ProcessorException {
      DatabaseService databaseService = context.getDatabaseService();
      String plotUid = Utilities.getStringCellValue(row.get(0));
      Triplet<String, Integer, Integer> triplet = Utilities.splitPlotUid(plotUid);
      String sampleCode = Utilities.getStringCellValue(row.get(1));
      PlotPlantSample sample = databaseService.findPlotPlantSampleByCode(triplet.getFirst(), triplet.getSecond(), triplet.getThird(), sampleCode);
      if (sample == null) {
        sample = new PlotPlantSample();
        sample.setTrialUniqueId(triplet.getFirst());
        sample.setBlockId(triplet.getSecond());
        sample.setPlotId(triplet.getThird());
        sample.setSampleId(Utilities.extractSampleId(sampleCode));
      }
      sample.setSampleCode(sampleCode);
      try {
        sample.setCollectionDate(Utilities.getDateCellValue(row.get(2)));
      }
      catch (ParseException e) {
        throw new ProcessorException(e);
      }
      databaseService.createOrUpdatePlotPlantSample(sample);
    }
  }

  public static class PlotPlantSampleResultsRangeProcessor extends AbstractRangeProcessor {
    @Override
    protected void process(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration) throws ProcessorException {
      DatabaseService databaseService = context.getDatabaseService();
      String sampleUid = Utilities.getStringCellValue(row.get(0));
      final StringBuffer sampleCode = new StringBuffer();
      Triplet<String, Integer, Integer> triplet = Utilities.splitPlotUid(sampleUid, (segment, matcher) -> sampleCode.append(segment));
      if (sampleCode.length() > 0) {
        PlotPlantSample sample = databaseService.findPlotPlantSampleByCode(triplet.getFirst(), triplet.getSecond(), triplet.getThird(), sampleCode.toString());
        if (sample != null) {
          sample.setSsn(Utilities.getStringCellValue(row.get(1)));
          try {
            sample.setAnalysisDate(Utilities.getDateCellValue(row.get(2)));
          }
          catch (ParseException e) {
            throw new ProcessorException(e);
          }
          sample.setPn(Utilities.getDoubleCellValue(row.get(3)));
          sample.setPp(Utilities.getDoubleCellValue(row.get(4)));
          sample.setPk(Utilities.getDoubleCellValue(row.get(5)));
          sample.setPca(Utilities.getDoubleCellValue(row.get(6)));
          sample.setPmg(Utilities.getDoubleCellValue(row.get(7)));
          sample.setPfe(Utilities.getDoubleCellValue(row.get(8)));
          sample.setPzn(Utilities.getDoubleCellValue(row.get(9)));
          sample.setPmn(Utilities.getDoubleCellValue(row.get(10)));
          sample.setPcu(Utilities.getDoubleCellValue(row.get(11)));
          sample.setPb(Utilities.getDoubleCellValue(row.get(12)));
          sample.setWave(Utilities.getDoubleCellValue(row.get(13)));
          databaseService.updatePlotPlantSample(sample);
        }
      }
    }
  }

  public static class PlotSoilSampleRangeProcessor extends AbstractRangeProcessor {
    @Override
    protected void process(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration) throws ProcessorException {
      DatabaseService databaseService = context.getDatabaseService();
      String plotUid = Utilities.getStringCellValue(row.get(0));
      Triplet<String, Integer, Integer> triplet = Utilities.splitPlotUid(plotUid);
      if (triplet != null) {
        String sampleCode = Utilities.getStringCellValue(row.get(1));
        PlotSoilSample sample = databaseService.findPlotSoilSampleByCode(triplet.getFirst(), triplet.getSecond(), triplet.getThird(), sampleCode);
        if (sample == null) {
          sample = new PlotSoilSample();
          sample.setTrialUid(triplet.getFirst());
          sample.setBlockId(triplet.getSecond());
          sample.setPlotId(triplet.getThird());
          sample.setSampleId(Utilities.extractSampleId(sampleCode));
          sample.setSampleCode(sampleCode);
        }
        try {
          sample.setCollectionDate(Utilities.getDateCellValue(row.get(2)));
        }
        catch (ParseException e) {
          throw new ProcessorException(e);
        }
        sample.setTrt(Utilities.getStringCellValue(row.get(3)));
        sample.setDepth(Utilities.getStringCellValue(row.get(4)));
        databaseService.createOrUpdatePlotSoilSample(sample);
      }
    }
  }

  public static class PlotSoilSampleResultsRangeProcessor extends AbstractRangeProcessor {
    @Override
    protected void process(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration) throws ProcessorException {
      DatabaseService databaseService = context.getDatabaseService();
      String sampleUid = Utilities.getStringCellValue(row.get(0));
      StringBuffer sampleCode = new StringBuffer();
      Triplet<String, Integer, Integer> triplet = Utilities.splitPlotUid(sampleUid, (segment, matcher) -> sampleCode.append(segment));
      if (triplet != null) {
        PlotSoilSample sample = databaseService.findPlotSoilSampleByCode(triplet.getFirst(), triplet.getSecond(), triplet.getThird(), sampleCode.toString());
        if (sample != null) {
          sample.setSsn(Utilities.getStringCellValue(row.get(1)));
          try {
            sample.setAnalysisDate(Utilities.getDateCellValue(row.get(2)));
          }
          catch (ParseException e) {
            throw new ProcessorException(e);
          }
          sample.setPh(Utilities.getDoubleCellValue(row.get(3)));
          sample.setEc(Utilities.getDoubleCellValue(row.get(4)));
          sample.setM3ai(Utilities.getDoubleCellValue(row.get(5)));
          sample.setM3b(Utilities.getDoubleCellValue(row.get(6)));
          sample.setM3ca(Utilities.getDoubleCellValue(row.get(7)));
          sample.setM3cu(Utilities.getDoubleCellValue(row.get(8)));
          sample.setM3fe(Utilities.getDoubleCellValue(row.get(9)));
          sample.setM3k(Utilities.getDoubleCellValue(row.get(10)));
          sample.setM3mg(Utilities.getDoubleCellValue(row.get(11)));
          sample.setM3mn(Utilities.getDoubleCellValue(row.get(12)));
          sample.setM3na(Utilities.getDoubleCellValue(row.get(13)));
          sample.setM3p(Utilities.getDoubleCellValue(row.get(14)));
          sample.setM3s(Utilities.getDoubleCellValue(row.get(15)));
          sample.setM3zn(Utilities.getDoubleCellValue(row.get(16)));
          sample.setHp(Utilities.getDoubleCellValue(row.get(17)));
          databaseService.updatePlotSoilSample(sample);
        }
      }
    }
  }
}
