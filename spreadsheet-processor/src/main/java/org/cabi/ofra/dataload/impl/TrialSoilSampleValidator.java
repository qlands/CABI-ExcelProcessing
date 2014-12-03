package org.cabi.ofra.dataload.impl;

import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.model.TrialSoilSample;

/**
 * Created by equiros on 11/28/2014.
 */
public class TrialSoilSampleValidator extends TrialValidator {
  private static final String KEY_SAMPLENOTFOUND = "sampleNotFoundMessage";

  @Override
  protected void processPostTrialSegment(String trialUid, String segment) throws ProcessorException {
    TrialSoilSample sample = databaseService.findTrialSoilSampleByCode(trialUid, segment);
    if (sample == null) {
      throw new ProcessorException(String.format(getMessage(KEY_SAMPLENOTFOUND, "Invalid trial soil sample referenced for Trial UID %s and Sample Code %s", trialUid, segment)));
    }
  }
}
