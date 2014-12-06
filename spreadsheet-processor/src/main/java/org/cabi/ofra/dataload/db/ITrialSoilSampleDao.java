package org.cabi.ofra.dataload.db;

import org.cabi.ofra.dataload.model.TrialSoilSample;

/**
 * DAO Object to manipulate trial soil sample data
 */
public interface ITrialSoilSampleDao extends IDao {
  public boolean existsTrialSoilSample(TrialSoilSample sample);
  public boolean existsTrialSoilSampleById(String trialUid, int sampleId);
  public TrialSoilSample findTrialSoilSampleById(String trialUid, int sampleId);
  public TrialSoilSample findTrialSoilSampleByCode(String trialUid, String sampleCode);
  public void createTrialSoilSample(TrialSoilSample sample);
  public void updateTrialSoilSample(TrialSoilSample sample);
}
