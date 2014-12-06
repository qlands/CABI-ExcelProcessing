package org.cabi.ofra.dataload.db;

import org.cabi.ofra.dataload.model.PlotSoilSample;

/**
 * DAO object to manipulate plot soil sample data
 */
public interface IPlotSoilSampleDao extends IDao {
  public boolean existsPlotSoilSample(PlotSoilSample sample);
  public PlotSoilSample findPlotSoilSampleByCode(String trialUid, int blockId, int plotId, String sampleCode);
  public void createPlotSoilSample(PlotSoilSample sample);
  public void updatePlotSoilSample(PlotSoilSample sample);
}
