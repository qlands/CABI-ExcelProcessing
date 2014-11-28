package org.cabi.ofra.dataload.specific;

import org.apache.poi.ss.usermodel.Cell;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.configuration.SheetRangeConfiguration;
import org.cabi.ofra.dataload.db.DatabaseService;
import org.cabi.ofra.dataload.event.IEventCollector;
import org.cabi.ofra.dataload.impl.AbstractRangeProcessor;
import org.cabi.ofra.dataload.model.IProcessingContext;
import org.cabi.ofra.dataload.model.TrialSoilSample;
import org.cabi.ofra.dataload.util.Pair;
import org.cabi.ofra.dataload.util.Utilities;

import java.text.ParseException;
import java.util.List;

/**
 * Created by equiros on 11/12/2014.
 */
public class TrialSoilTemplates {
  public static class TrialSoilSampleRangeProcessor extends AbstractRangeProcessor {
    private DatabaseService databaseService;

    @Override
    protected void process(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration) throws ProcessorException {
      databaseService = context.getDatabaseService();
      String trialUid = Utilities.getStringCellValue(row.get(0));
      String sampleCode = Utilities.getStringCellValue(row.get(1));
      TrialSoilSample trialSoilSample = databaseService.findTrialSoilSampleByCode(trialUid, sampleCode);
      if (trialSoilSample == null) {
        trialSoilSample = new TrialSoilSample();
        trialSoilSample.setTrialUniqueId(trialUid);
        trialSoilSample.setSampleId(Utilities.extractSampleId(sampleCode));
      }
      trialSoilSample.setCode(sampleCode);
      try {
        trialSoilSample.setCdate(Utilities.getDateCellValue(row.get(2)));
      }
      catch (ParseException e) {
        throw new ProcessorException(e);
      }
      trialSoilSample.setTrt(Utilities.getStringCellValue(row.get(3)));
      trialSoilSample.setDepth(Utilities.getStringCellValue(row.get(4)));
      databaseService.createOrUpdateTrialSoilSample(trialSoilSample);
    }
  }

  public static class TrialSoilResultRangeProcessor extends AbstractRangeProcessor {
    private DatabaseService databaseService;

    protected void process(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration) throws ProcessorException {
      databaseService = context.getDatabaseService();
      String uid = Utilities.getStringCellValue(row.get(0));
      Pair<String, String> pair = Utilities.splitUid(uid);
      if (pair != null) {
        String trialUid = pair.car();
        String sampleCode = pair.cdr();
        TrialSoilSample trialSoilSample = databaseService.findTrialSoilSampleByCode(trialUid, sampleCode);
        if (trialSoilSample != null) {
          fillSoilSample(trialSoilSample, row);
          databaseService.updateTrialSoilSample(trialSoilSample);
        }
      }
    }

    private void fillSoilSample(TrialSoilSample trialSoilSample, List<Cell> row) throws ProcessorException {
      trialSoilSample.setSsn(Utilities.getStringCellValue(row.get(1)));
      try {
        trialSoilSample.setAdate(Utilities.getDateCellValue(row.get(2)));
      }
      catch (ParseException e) {
        throw new ProcessorException(e);
      }
      trialSoilSample.setPh(Utilities.getDoubleCellValue(row.get(3)));
      trialSoilSample.setEc(Utilities.getDoubleCellValue(row.get(4)));
      trialSoilSample.setM3ai(Utilities.getDoubleCellValue(row.get(5)));
      trialSoilSample.setM3b(Utilities.getDoubleCellValue(row.get(6)));
      trialSoilSample.setM3ca(Utilities.getDoubleCellValue(row.get(7)));
      trialSoilSample.setM3cu(Utilities.getDoubleCellValue(row.get(8)));
      trialSoilSample.setM3fe(Utilities.getDoubleCellValue(row.get(9)));
      trialSoilSample.setM3k(Utilities.getDoubleCellValue(row.get(10)));
      trialSoilSample.setM3mg(Utilities.getDoubleCellValue(row.get(11)));
      trialSoilSample.setM3mn(Utilities.getDoubleCellValue(row.get(12)));
      trialSoilSample.setM3na(Utilities.getDoubleCellValue(row.get(13)));
      trialSoilSample.setM3p(Utilities.getDoubleCellValue(row.get(14)));
      trialSoilSample.setM3s(Utilities.getDoubleCellValue(row.get(15)));
      trialSoilSample.setM3zn(Utilities.getDoubleCellValue(row.get(16)));
      trialSoilSample.setHp(Utilities.getDoubleCellValue(row.get(17)));
    }

  }
}
