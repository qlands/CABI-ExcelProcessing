package org.cabi.ofra.dataload.impl;

import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.model.TrialSoilSample;

/**
 * Validates a trial soil sample. This validator extends {@link org.cabi.ofra.dataload.impl.TrialValidator} to check if a
 * trial soil sample is found or not in the database.
 * Supports the following arguments (in addition to those in {@link org.cabi.ofra.dataload.impl.TrialValidator}):
 *  + sampleNotFoundMessage: issued when the trial soil sample ID is not found in the database. Placeholders: Trial ID, Soil Sample ID
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
