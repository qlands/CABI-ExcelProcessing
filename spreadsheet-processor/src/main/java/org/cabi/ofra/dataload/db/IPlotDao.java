package org.cabi.ofra.dataload.db;

import org.cabi.ofra.dataload.model.Plot;

/**
 * Created by equiros on 11/9/2014.
 */
public interface IPlotDao extends IDao {
    public boolean existsPlot(Plot plot);
    public boolean existsPlotById(String trialUid, int blockId, int plotId);
    public void createPlot(Plot plot);
    public void updatePlot(Plot plot);
}
