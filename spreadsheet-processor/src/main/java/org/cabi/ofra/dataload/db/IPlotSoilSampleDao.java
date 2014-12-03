package org.cabi.ofra.dataload.db;

import org.cabi.ofra.dataload.model.PlotSoilSample;

/**
 * Created by equiros on 11/28/2014.
 */
public interface IPlotSoilSampleDao extends IDao {
  public boolean existsPlotSoilSample(PlotSoilSample sample);
  public PlotSoilSample findPlotSoilSampleByCode(String trialUid, int blockId, int plotId, String sampleCode);
  public void createPlotSoilSample(PlotSoilSample sample);
  public void updatePlotSoilSample(PlotSoilSample sample);
}
