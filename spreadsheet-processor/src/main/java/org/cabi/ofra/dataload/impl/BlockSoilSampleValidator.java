package org.cabi.ofra.dataload.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.db.DatabaseService;
import org.cabi.ofra.dataload.event.IEventCollector;
import org.cabi.ofra.dataload.model.BlockSoilSample;
import org.cabi.ofra.dataload.model.ICellProcessor;
import org.cabi.ofra.dataload.model.IProcessingContext;
import org.cabi.ofra.dataload.util.Pair;
import org.cabi.ofra.dataload.util.Utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by equiros on 11/24/2014.
 */
public class BlockSoilSampleValidator extends AbstractProcessor implements ICellProcessor {
  private static final String KEY_MALFORMED_UID = "malformedUidMessage";
  private static final String KEY_TRIALNOTFOUND = "trialNotFoundMessage";
  private static final String KEY_BLOCKNOTFOUND = "blockNotFoundMessage";
  private static final String KEY_SAMPLENOTFOUND = "sampleNotFoundMessage";

  @Override
  public void processCell(IProcessingContext context, Cell cell, IEventCollector eventCollector) throws ProcessorException {
    DatabaseService databaseService = context.getDatabaseService();
    String uid = Utilities.getStringCellValue(cell);
    Pair<String, String> pair = Utilities.splitUid(uid);
    if (pair == null) {
      throw new ProcessorException(String.format(getMessage(KEY_MALFORMED_UID, "Block soil identifier '%s' is malformed. Please check template", uid)));
    }
    String trialUid = pair.car();
    if (!databaseService.existsTrialByUniqueId(trialUid)) {
      throw new ProcessorException(String.format(getMessage(KEY_TRIALNOTFOUND, "Trial identified by UID %s does not exist", trialUid)));
    }
    Matcher matcher = Utilities.BlockSoilSamplePattern.matcher(pair.cdr());
    if (matcher.matches()) {
      int blockId = Integer.valueOf(matcher.group(1));
      int sampleId = Integer.valueOf(matcher.group(2));
      if (!databaseService.existsBlockById(trialUid, blockId)) {
        throw new ProcessorException(String.format(getMessage(KEY_BLOCKNOTFOUND, "Block identified by Trial UID %s and Block ID %d does not exist", trialUid, blockId)));
      }
      BlockSoilSample sample = databaseService.findBlockSoilSampleById(trialUid, blockId, sampleId);
      if (sample == null) {
        throw new ProcessorException(String.format(getMessage(KEY_SAMPLENOTFOUND, "Block soil sample identified by Trial UID %s, Block ID %d and Sample ID %d does not exist", trialUid, blockId, sampleId)));
      }
    }
    else {
      throw new ProcessorException(String.format(getMessage(KEY_MALFORMED_UID, "Block soil identifier '%s' is malformed. Please check template", uid)));
    }
  }
}
