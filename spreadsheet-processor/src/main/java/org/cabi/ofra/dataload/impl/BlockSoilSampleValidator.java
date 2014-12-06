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
 * Validates a Soil Sample associated to a block.
 * Block data is validated using logic inherited from {@link org.cabi.ofra.dataload.impl.BlockValidator}
 * Supports the following arguments:
 * - sampleNotFoundMessage: message to issue when the sample is not found. Supports 4 placeholders for Trial ID, Block ID, and Sample ID (in this order)
 */
public class BlockSoilSampleValidator extends BlockValidator {
  private static final String KEY_SAMPLENOTFOUND = "sampleNotFoundMessage";

  @Override
  protected void processPostBlockSegment(String trialUid, int blockId, String segment) throws ProcessorException {
    BlockSoilSample sample = databaseService.findBlockSoilSampleByCode(trialUid, blockId, segment);
    if (sample == null) {
      throw new ProcessorException(String.format(getMessage(KEY_SAMPLENOTFOUND, "Invalid block soil sample referenced for Trial UID %s, Block ID %d and Sample Code %s", trialUid, blockId, segment)));
    }
  }
}
