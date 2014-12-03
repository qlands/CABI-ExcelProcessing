package org.cabi.ofra.dataload.specific;

import org.apache.poi.ss.usermodel.Cell;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.configuration.SheetRangeConfiguration;
import org.cabi.ofra.dataload.db.DatabaseService;
import org.cabi.ofra.dataload.event.IEventCollector;
import org.cabi.ofra.dataload.impl.AbstractRangeProcessor;
import org.cabi.ofra.dataload.model.*;
import org.cabi.ofra.dataload.util.Triplet;
import org.cabi.ofra.dataload.util.Utilities;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by equiros on 11/29/2014.
 */
public class HarvestTemplates {
  // Private support classes
  private static abstract class AbstractHarvestRangeProcessor extends AbstractRangeProcessor {
    protected DatabaseService databaseService;
    @Override
    protected void process(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration) throws ProcessorException {
      databaseService = context.getDatabaseService();
      String plotUid = Utilities.getStringCellValue(row.get(0));
      Triplet<String, Integer, Integer> triplet = Utilities.splitPlotUid(plotUid);
      if (triplet != null) {
        Date harvestDate;
        try {
          harvestDate = Utilities.getDateCellValue(row.get(1));
        }
        catch (ParseException e) {
          throw new ProcessorException(e);
        }
        processHarvest(context, row, eventCollector, rangeConfiguration, triplet.getFirst(), triplet.getSecond(), triplet.getThird(), harvestDate);
      }
    }

    protected abstract void processHarvest(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration, String trialUid, int blockId, int plotId, Date harvestDate) throws ProcessorException;
  }

  private static abstract class AbstractHarvestPortionRangeProcessor extends AbstractRangeProcessor {
    DatabaseService databaseService;

    @Override
    protected void process(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration) throws ProcessorException {
      databaseService = context.getDatabaseService();
      String harvestUid = Utilities.getStringCellValue(row.get(0));
      StringBuffer harvestDateSegment = new StringBuffer();
      Triplet<String, Integer, Integer> triplet = Utilities.splitPlotUid(harvestUid, (segment, matcher) -> harvestDateSegment.append(segment));
      Date harvestDate;
      try {
        harvestDate = Utilities.parseDate(harvestDateSegment.toString());
      }
      catch (ParseException e) {
        throw new ProcessorException(e);
      }
      processHarvestPortion(context, row, eventCollector, rangeConfiguration, triplet.getFirst(), triplet.getSecond(), triplet.getThird(), harvestDate);
    }

    protected abstract void processHarvestPortion(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration, String trialUid, int blockId, int plotId, Date harvestDate) throws ProcessorException;
  }

  // Harvest: Legumes

  public static class PlotHarvestLegumeRangeProcessor extends AbstractHarvestRangeProcessor {
    @Override
    protected void processHarvest(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration, String trialUid, int blockId, int plotId, Date harvestDate) throws ProcessorException {
      HarvestLegume legume = databaseService.findHarvestLegumeById(trialUid, blockId, plotId, harvestDate);
      if (legume == null) {
        legume = new HarvestLegume();
        legume.setTrialUid(trialUid);
        legume.setBlockId(blockId);
        legume.setPlotId(plotId);
        legume.setHarvestDate(harvestDate);
      }
      legume.setPlantCount(Utilities.getIntegerCellValue(row.get(2)));
      legume.setBioMass(Utilities.getDoubleCellValue(row.get(3)));
      legume.setYield(Utilities.getDoubleCellValue(row.get(4)));
      databaseService.createOrUpdateHarvestLegume(legume);
    }
  }

  private static abstract class AbstractPlotHarvestLegumePortionRangeProcessor extends AbstractHarvestPortionRangeProcessor {
    @Override
    protected void processHarvestPortion(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration, String trialUid, int blockId, int plotId, Date harvestDate) throws ProcessorException{
      HarvestLegume harvestLegume = databaseService.findHarvestLegumeById(trialUid, blockId, plotId, harvestDate);
      if (harvestLegume != null) {
        updateHarvestLegume(context, row, eventCollector, rangeConfiguration, harvestLegume);
        databaseService.updateHarvestLegume(harvestLegume);
      }
    }

    protected abstract void updateHarvestLegume(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration, HarvestLegume harvestLegume) throws ProcessorException;
  }

  public static class PlotHarvestLegumeMoistureRangeProcessor extends AbstractPlotHarvestLegumePortionRangeProcessor {
    @Override
    protected void updateHarvestLegume(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration, HarvestLegume harvestLegume) throws ProcessorException {
      harvestLegume.setGrainMoisture(Utilities.getDoubleCellValue(row.get(1)));
    }
  }

  public static class PlotHarvestLegumeFloweringRangeProcessor extends AbstractPlotHarvestLegumePortionRangeProcessor {
    @Override
    protected void updateHarvestLegume(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration, HarvestLegume harvestLegume) throws ProcessorException {
      try {
        harvestLegume.setFlowerDate(Utilities.getDateCellValue(row.get(1)));
      }
      catch (ParseException e) {
        throw new ProcessorException(e);
      }
    }
  }

  // Harvest: Cereals

  public static class PlotHarvestCerealRangeProcessor extends AbstractHarvestRangeProcessor {
    @Override
    protected void processHarvest(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration, String trialUid, int blockId, int plotId, Date harvestDate) throws ProcessorException {
      HarvestCereal harvestCereal = databaseService.findHarvestCerealById(trialUid, blockId, plotId, harvestDate);
      if (harvestCereal == null) {
        harvestCereal = new HarvestCereal();
        harvestCereal.setTrialUid(trialUid);
        harvestCereal.setBlockId(blockId);
        harvestCereal.setPlotId(plotId);
        harvestCereal.setHarvestDate(harvestDate);
      }
      harvestCereal.setHeadWeight(Utilities.getDoubleCellValue(row.get(2)));
      harvestCereal.setGrainYield(Utilities.getDoubleCellValue(row.get(3)));
      harvestCereal.setStoverYield(Utilities.getDoubleCellValue(row.get(4)));
      harvestCereal.setStoverSample(Utilities.getDoubleCellValue(row.get(5)));
      databaseService.createOrUpdateHarvestCereal(harvestCereal);
    }
  }

  private static abstract class AbstractPlotHarvestCerealPortionRangeProcessor extends AbstractHarvestPortionRangeProcessor {
    @Override
    protected void processHarvestPortion(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration, String trialUid, int blockId, int plotId, Date harvestDate) throws ProcessorException {
      HarvestCereal harvestCereal = databaseService.findHarvestCerealById(trialUid, blockId, plotId, harvestDate);
      if (harvestCereal != null) {
        updateHarvestCereal(context, row, eventCollector, rangeConfiguration, harvestCereal);
        databaseService.updateHarvestCereal(harvestCereal);
      }
    }

    protected abstract void updateHarvestCereal(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration, HarvestCereal harvestCereal) throws ProcessorException;
  }

  public static class PlotHarvestCerealBootingDateRangeProcessor extends AbstractPlotHarvestCerealPortionRangeProcessor {
    @Override
    protected void updateHarvestCereal(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration, HarvestCereal harvestCereal) throws ProcessorException {
      try {
        harvestCereal.setBootingDate(Utilities.getDateCellValue(row.get(1)));
      }
      catch (ParseException e) {
        throw new ProcessorException(e);
      }
      databaseService.updateHarvestCereal(harvestCereal);
    }
  }

  public static class PlotHarvestCerealMoistureContentRangeProcessor extends AbstractPlotHarvestCerealPortionRangeProcessor {
    @Override
    protected void updateHarvestCereal(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration, HarvestCereal harvestCereal) throws ProcessorException {
      harvestCereal.setGrainMoist(Utilities.getDoubleCellValue(row.get(1)));
    }
  }

  public static class PlotHarvestCerealStoverDryRangeProcessor extends AbstractPlotHarvestCerealPortionRangeProcessor {
    @Override
    protected void updateHarvestCereal(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration, HarvestCereal harvestCereal) throws ProcessorException {
      harvestCereal.setStoverDryYield(Utilities.getDoubleCellValue(row.get(1)));
    }
  }

  // Harvest: Grasses

  public static class PlotHarvestGrassRangeProcessor extends AbstractHarvestRangeProcessor {
    @Override
    protected void processHarvest(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration, String trialUid, int blockId, int plotId, Date harvestDate) throws ProcessorException {
      HarvestGrass harvestGrass = databaseService.findHarvestGrassById(trialUid, blockId, plotId, harvestDate);
      if (harvestGrass == null) {
        harvestGrass = new HarvestGrass();
        harvestGrass.setTrialUid(trialUid);
        harvestGrass.setBlockId(blockId);
        harvestGrass.setPlotId(plotId);
        harvestGrass.setHarvestDate(harvestDate);
      }
      harvestGrass.setPlantCount(Utilities.getIntegerCellValue(row.get(2)));
      harvestGrass.setPanicleCount(Utilities.getIntegerCellValue(row.get(3)));
      harvestGrass.setPanicleWeight(Utilities.getDoubleCellValue(row.get(4)));
      harvestGrass.setGrainYield(Utilities.getDoubleCellValue(row.get(5)));
      harvestGrass.setStoverYield(Utilities.getDoubleCellValue(row.get(6)));
      harvestGrass.setStoverSample(Utilities.getDoubleCellValue(row.get(7)));
      databaseService.createOrUpdateHarvestGrass(harvestGrass);
    }
  }

  private static abstract class AbstractPlotHarvestGrassPortionRangeProcessor extends AbstractHarvestPortionRangeProcessor {
    @Override
    protected void processHarvestPortion(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration, String trialUid, int blockId, int plotId, Date harvestDate) throws ProcessorException {
      HarvestGrass harvestGrass = databaseService.findHarvestGrassById(trialUid, blockId, plotId, harvestDate);
      if (harvestGrass != null) {
        updateHarvestGrass(context, row, eventCollector, rangeConfiguration, harvestGrass);
        databaseService.updateHarvestGrass(harvestGrass);
      }
    }

    protected abstract void updateHarvestGrass(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration, HarvestGrass harvestGrass) throws ProcessorException;
  }

  public static class PlotHarvestGrassMoistureRangeProcessor extends AbstractPlotHarvestGrassPortionRangeProcessor {
    @Override
    protected void updateHarvestGrass(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration, HarvestGrass harvestGrass) throws ProcessorException {
      harvestGrass.setGrainMoisture(Utilities.getDoubleCellValue(row.get(1)));
    }
  }

  public static class PlotHarvestGrassPanicleDateRangeProcessor extends AbstractPlotHarvestGrassPortionRangeProcessor {
    @Override
    protected void updateHarvestGrass(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration, HarvestGrass harvestGrass) throws ProcessorException{
      try {
        harvestGrass.setPanicleDate(Utilities.getDateCellValue(row.get(1)));
      }
      catch (ParseException e) {
        throw new ProcessorException(e);
      }
    }

  }

  public static class PlotHarvestGrassStoverDryRangeProcessor extends AbstractPlotHarvestGrassPortionRangeProcessor {
    @Override
    protected void updateHarvestGrass(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration, HarvestGrass harvestGrass) throws ProcessorException {
      harvestGrass.setStoverDryYield(Utilities.getDoubleCellValue(row.get(1)));
    }

  }

  // Harvest: Maize

  public static class PlotHarvestMaizeRangeProcessor extends AbstractHarvestRangeProcessor {
    @Override
    protected void processHarvest(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration, String trialUid, int blockId, int plotId, Date harvestDate) throws ProcessorException {
      HarvestMaize harvestMaize = databaseService.findHarvestMaizeById(trialUid, blockId, plotId, harvestDate);
      if (harvestMaize == null) {
        harvestMaize = new HarvestMaize();
        harvestMaize.setTrialUid(trialUid);
        harvestMaize.setBlockId(blockId);
        harvestMaize.setPlotId(plotId);
        harvestMaize.setHarvestDate(harvestDate);
      }
      harvestMaize.setPlantCount(Utilities.getIntegerCellValue(row.get(2)));
      harvestMaize.setEarCount(Utilities.getIntegerCellValue(row.get(3)));
      harvestMaize.setEarWeight(Utilities.getDoubleCellValue(row.get(4)));
      harvestMaize.setGrainYield(Utilities.getDoubleCellValue(row.get(5)));
      harvestMaize.setStoverYield(Utilities.getDoubleCellValue(row.get(6)));
      harvestMaize.setStoverSample(Utilities.getDoubleCellValue(row.get(7)));
      databaseService.createOrUpdateHarvestMaize(harvestMaize);
    }
  }

  private static abstract class AbstractPlotHarvestMaizePortionRangeProcessor extends AbstractHarvestPortionRangeProcessor {
    @Override
    protected void processHarvestPortion(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration, String trialUid, int blockId, int plotId, Date harvestDate) throws ProcessorException {
      HarvestMaize harvestMaize = databaseService.findHarvestMaizeById(trialUid, blockId, plotId, harvestDate);
      if (harvestMaize != null) {
        updateHarvestMaize(context, row, eventCollector, rangeConfiguration, harvestMaize);
        databaseService.updateHarvestMaize(harvestMaize);
      }
    }

    protected abstract void updateHarvestMaize(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration, HarvestMaize harvestMaize) throws ProcessorException;
  }

  public static class PlotHarvestMaizeSilkDateRangeProcessor extends AbstractPlotHarvestMaizePortionRangeProcessor {
    @Override
    protected void updateHarvestMaize(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration, HarvestMaize harvestMaize) throws ProcessorException {
      try {
        harvestMaize.setSilkDate(Utilities.getDateCellValue(row.get(1)));
      }
      catch (ParseException e) {
        throw new ProcessorException(e);
      }
    }
  }

  public static class PlotHarvestMaizeMoistureRangeProcessor extends AbstractPlotHarvestMaizePortionRangeProcessor {
    @Override
    protected void updateHarvestMaize(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration, HarvestMaize harvestMaize) throws ProcessorException {
      harvestMaize.setGrainMoisture(Utilities.getDoubleCellValue(row.get(1)));
    }
  }

  public static class PlotHarvestMaizeStoverDryWeightRangeProcessor extends AbstractPlotHarvestMaizePortionRangeProcessor {
    @Override
    protected void updateHarvestMaize(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration, HarvestMaize harvestMaize) throws ProcessorException {
      harvestMaize.setStoverDryYield(Utilities.getDoubleCellValue(row.get(1)));
    }
  }

  // Harvest: Cassava
  public static class PlotHarvestCassavaRangeProcessor extends AbstractHarvestRangeProcessor {
    @Override
    protected void processHarvest(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration, String trialUid, int blockId, int plotId, Date harvestDate) throws ProcessorException {
      HarvestCassava harvestCassava = databaseService.findHarvestCassavaById(trialUid, blockId, plotId, harvestDate);
      if (harvestCassava == null) {
        harvestCassava = new HarvestCassava();
        harvestCassava.setTrialUid(trialUid);
        harvestCassava.setBlockId(blockId);
        harvestCassava.setPlotId(plotId);
        harvestCassava.setHarvestDate(harvestDate);
      }
      harvestCassava.setPlantCount(Utilities.getIntegerCellValue(row.get(2)));
      harvestCassava.setTuberCount(Utilities.getIntegerCellValue(row.get(3)));
      harvestCassava.setYield(Utilities.getDoubleCellValue(row.get(4)));
      harvestCassava.setSample(Utilities.getDoubleCellValue(row.get(5)));
      databaseService.createOrUpdateHarvestCassava(harvestCassava);
    }
  }

  public static class PlotHarvestCassavaDryWeightsRangeProcessor extends AbstractHarvestPortionRangeProcessor {
    @Override
    protected void processHarvestPortion(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration, String trialUid, int blockId, int plotId, Date harvestDate) throws ProcessorException {
      HarvestCassava harvestCassava = databaseService.findHarvestCassavaById(trialUid, blockId, plotId, harvestDate);
      if (harvestCassava != null) {
        harvestCassava.setDryTuber(Utilities.getDoubleCellValue(row.get(1)));
        harvestCassava.setSampleDry(Utilities.getDoubleCellValue(row.get(2)));
        databaseService.updateHarvestCassava(harvestCassava);
      }
    }
  }
}
