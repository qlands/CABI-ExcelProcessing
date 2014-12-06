package org.cabi.ofra.dataload.db;

import org.cabi.ofra.dataload.model.PlotObservation;

/**
 * DAO object to manipulate plot observation data
 */
public interface IPlotObservationDao extends IDao {
  public boolean existsObservationById(String trialUid, int blockId, int plotId, int observationId);
  public void createObservation(PlotObservation observation);
  public void updateObservation(PlotObservation observation);
}
