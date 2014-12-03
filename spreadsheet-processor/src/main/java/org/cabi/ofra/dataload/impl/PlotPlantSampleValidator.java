package org.cabi.ofra.dataload.impl;


import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.model.PlotPlantSample;

/**
 * Created by equiros on 11/27/2014.
 */
public class PlotPlantSampleValidator extends PlotValidator {
  private final String KEY_SAMPLENOTFOUND = "sampleNotFoundMessage";
  @Override
  protected void processPostPlotSegment(String trialUid, int blockId, int plotId, String segment) throws ProcessorException {
    PlotPlantSample sample = databaseService.findPlotPlantSampleByCode(trialUid, blockId, plotId, segment);
    if (sample == null) {
      throw new ProcessorException(getMessage(KEY_SAMPLENOTFOUND, "Invalid plot plant sample referenced for Trial UID %s, Block %d, Plot %d and Sample Code %s", trialUid, blockId, plotId, segment));
    }
  }
}
