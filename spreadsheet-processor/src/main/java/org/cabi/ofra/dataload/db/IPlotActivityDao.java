package org.cabi.ofra.dataload.db;

import org.cabi.ofra.dataload.model.PlotActivity;

/**
 * Created by equiros on 11/26/2014.
 */
public interface IPlotActivityDao extends IDao {
  public boolean existsActivityById(String trialUid, int blockId, int plotId, int activityId);
  public void createActivity(PlotActivity activity);
  public void updateActivity(PlotActivity activity);
}
