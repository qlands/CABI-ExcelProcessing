package org.cabi.ofra.dataload.db;

import org.cabi.ofra.dataload.model.PlotPlantSample;

/**
 * DAO object to manipulate plot plant sample data
 */
public interface IPlotPlantSampleDao extends IDao {
  public boolean existsPlotPlantSampleById(String trialUid, int blockId, int plotId, int sampleId);
  public PlotPlantSample findPlotPlantSampleById(String trialUid, int blockId, int plotId, int sampleId);
  public PlotPlantSample findPlotPlantSampleByCode(String trialUid, int blockId, int plotId, String sampleCode);
  public void createPlotPlantSample(PlotPlantSample plotPlantSample);
  public void updatePlotPlantSample(PlotPlantSample plotPlantSample);
}
