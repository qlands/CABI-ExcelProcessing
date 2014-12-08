package org.cabi.ofra.dataload.impl;

import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.model.PlotSoilSample;

/**
 * Validates a plot soil sample. This validator extends {@link org.cabi.ofra.dataload.impl.PlotValidator} to check if a
 * plot soil sample is found or not in the database.
 * Supports the following arguments (in addition to those in {@link org.cabi.ofra.dataload.impl.PlotValidator}):
 *  + sampleNotFoundMessage: issued when the plot plant sample ID is not found in the database. Placeholders: Trial ID, Block ID, Plot ID, Soil Sample ID
 */
public class PlotSoilSampleValidator extends PlotValidator {
  private final String KEY_SAMPLENOTFOUND = "sampleNotFoundMessage";

  @Override
  protected void processPostPlotSegment(String trialUid, int blockId, int plotId, String segment) throws ProcessorException {
    PlotSoilSample sample = databaseService.findPlotSoilSampleByCode(trialUid, blockId, plotId, segment);
    if (sample == null) {
      throw new ProcessorException(getMessage(KEY_SAMPLENOTFOUND, "Invalid plot soil sample referenced for Trial UID %s, Block %d, Plot %d and Sample Code %s", trialUid, blockId, plotId, segment));
    }
  }
}
