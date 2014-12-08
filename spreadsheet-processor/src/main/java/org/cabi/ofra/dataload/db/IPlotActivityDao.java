package org.cabi.ofra.dataload.db;

import org.cabi.ofra.dataload.model.PlotActivity;

/**
 * DAO Object to manipulate plot activity data
 */
public interface IPlotActivityDao extends IDao {
  public boolean existsActivityById(String trialUid, int blockId, int plotId, int activityId);
  public void createActivity(PlotActivity activity);
  public void updateActivity(PlotActivity activity);
}
