package org.cabi.ofra.dataload.specific;

import org.apache.poi.ss.usermodel.Cell;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.configuration.SheetRangeConfiguration;
import org.cabi.ofra.dataload.db.DatabaseService;
import org.cabi.ofra.dataload.event.IEventCollector;
import org.cabi.ofra.dataload.impl.AbstractRangeProcessor;
import org.cabi.ofra.dataload.model.*;
import org.cabi.ofra.dataload.util.Pair;
import org.cabi.ofra.dataload.util.Utilities;

import javax.rmi.CORBA.Util;
import java.text.ParseException;
import java.util.List;
import java.util.regex.Matcher;

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

  public static class BlockObservationsRangeProcessor extends AbstractRangeProcessor {
    DatabaseService databaseService;

    @Override
    protected void process(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration) throws ProcessorException {
      databaseService = context.getDatabaseService();
      BlockObservation blockObservation = buildBlockObservation(row);
      databaseService.createOrUpdateBlockObservation(blockObservation);
    }

    private BlockObservation buildBlockObservation(List<Cell> row) throws ProcessorException {
      try {
        BlockObservation observation = new BlockObservation();
        String blockUid = Utilities.getStringCellValue(row.get(0));
        Pair<String, Integer> pair = Utilities.splitBlockUid(blockUid);
        observation.setTrialUniqueId(pair.car());
        observation.setBlockNumber(pair.cdr());
        observation.setObservationId(Utilities.getIntegerCellValue(row.get(1)));
        observation.setObservationDate(Utilities.getDateCellValue(row.get(2)));
        observation.setObservationNotes(Utilities.getStringCellValue(row.get(3)));
        observation.setObservationDiseaseRelated(Utilities.getBooleanCellValue(row.get(4)));
        return observation;
      }
      catch (ParseException e) {
        throw new ProcessorException(e);
      }
    }
  }

  public static class BlockSoilSampleRangeProcessor extends AbstractRangeProcessor {
    private DatabaseService databaseService;

    @Override
    protected void process(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration) throws ProcessorException {
      databaseService = context.getDatabaseService();
      String blockUid = Utilities.getStringCellValue(row.get(0));
      Pair<String, Integer> pair = Utilities.splitBlockUid(blockUid);
      if (pair != null) {
        Utilities.validateBlock(databaseService, blockUid);
        String sampleCode = Utilities.getStringCellValue(row.get(1));
        BlockSoilSample sample = databaseService.findBlockSoilSampleByCode(pair.car(), pair.cdr(), sampleCode);
        if (sample == null) {
          sample = new BlockSoilSample();
          sample.setTrialUniqueId(pair.car());
          sample.setBlockId(pair.cdr());
          sample.setSampleId(Utilities.extractSampleId(sampleCode));
        }
        sample.setCode(sampleCode);
        try {
          sample.setCdate(Utilities.getDateCellValue(row.get(2)));
        }
        catch (ParseException e) {
          throw new ProcessorException(e);
        }
        sample.setTrt(Utilities.getStringCellValue(row.get(3)));
        sample.setDepth(Utilities.getStringCellValue(row.get(4)));
        databaseService.createOrUpdateBlockSoilSample(sample);
      }
    }
  }

  public static class BlockSoilResultRangeProcessor extends AbstractRangeProcessor {
    private DatabaseService databaseService;

    @Override
    protected void process(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration) throws ProcessorException {
      databaseService = context.getDatabaseService();
      String sampleUid = Utilities.getStringCellValue(row.get(0));
      StringBuffer sampleCode = new StringBuffer();
      Pair<String, Integer> pair = Utilities.splitBlockUid(sampleUid, (segment, matcher) -> sampleCode.append(segment));
      if (pair != null) {
        BlockSoilSample sample = databaseService.findBlockSoilSampleByCode(pair.car(), pair.cdr(), sampleCode.toString());
        if (sample != null) {
          sample.setSsn(Utilities.getStringCellValue(row.get(1)));
          try {
            sample.setAdate(Utilities.getDateCellValue(row.get(2)));
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
          databaseService.updateBlockSoilSample(sample);
        }
      }
    }
  }

  public static class BlockWeatherRangeProcessor extends AbstractRangeProcessor {
    @Override
    protected void process(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration) throws ProcessorException {
      DatabaseService databaseService = context.getDatabaseService();
      String blockUid = Utilities.getStringCellValue(row.get(0));
      Utilities.validateBlock(databaseService, blockUid);
      Pair<String, Integer> pair = Utilities.splitBlockUid(blockUid);
      BlockWeather blockWeather = new BlockWeather();
      blockWeather.setTrialUniqueId(pair.car());
      blockWeather.setBlockId(Integer.valueOf(pair.cdr()));
      try {
        blockWeather.setCollectionDate(Utilities.getDateCellValue(row.get(1)));
      }
      catch (ParseException e) {
        throw new ProcessorException(e);
      }
      blockWeather.setRainfall(Utilities.getDoubleCellValue(row.get(2)));
      blockWeather.setMaxtemp(Utilities.getDoubleCellValue(row.get(3)));
      blockWeather.setMintemp(Utilities.getDoubleCellValue(row.get(4)));
      blockWeather.setRadiation(Utilities.getDoubleCellValue(row.get(5)));
      blockWeather.setWind(Utilities.getDoubleCellValue(row.get(6)));
      blockWeather.setRelhumid(Utilities.getDoubleCellValue(row.get(7)));
      databaseService.createOrUpdateBlockWeather(blockWeather);
    }
  }
}
