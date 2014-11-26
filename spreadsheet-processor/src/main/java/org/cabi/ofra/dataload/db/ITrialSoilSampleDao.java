package org.cabi.ofra.dataload.db;

import org.cabi.ofra.dataload.model.TrialSoilSample;

/**
 * Created by equiros on 11/14/2014.
 */
public interface ITrialSoilSampleDao extends IDao {
  public boolean existsTrialSoilSample(TrialSoilSample sample);
  public boolean existsTrialSoilSampleById(String trialUid, int sampleId);
  public TrialSoilSample findTrialSoilSampleById(String trialUid, int sampleId);
  public void createTrialSoilSample(TrialSoilSample sample);
  public void updateTrialSoilSample(TrialSoilSample sample);
}
