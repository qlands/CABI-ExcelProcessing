package org.cabi.ofra.dataload.db.impl;

import org.cabi.ofra.dataload.db.IPlotObservationDao;
import org.cabi.ofra.dataload.model.PlotObservation;

/**
 * Created by equiros on 11/26/2014.
 */
public class PlotObservationDao extends BaseDao implements IPlotObservationDao {
  @Override
  public boolean existsObservationById(String trialUid, int blockId, int plotId, int observationId) {
    int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ofrafertrials.plotobservation " +
            " WHERE trial_id = ? AND block_id = ? AND plot_id = ? AND obs_id = ?", Integer.class,
            new Object[] {trialUid, blockId, plotId, observationId});
    return count > 0;
  }

  @Override
  public void createObservation(PlotObservation observation) {
    jdbcTemplate.update("INSERT INTO ofrafertrials.plotobservation (trial_id, block_id, plot_id, obs_id, obs_date, obs_notes, obs_disea) VALUES (?, ?, ?, ?, ?, ?, ?)",
            observation.getTrialUniqueId(), observation.getBlockId(), observation.getPlotId(), observation.getObservationId(), observation.getObservationDate(),
            observation.getObservationNotes(), observation.isObservationDiseaseRelated());
  }

  @Override
  public void updateObservation(PlotObservation observation) {
    jdbcTemplate.update("UPDATE ofrafertrials.plotobservation SET obs_date = ?, obs_notes = ?, obs_disea = ?" +
            " WHERE trial_id = ? AND block_id = ? AND plot_id = ? AND obs_id = ?",
            observation.getObservationDate(), observation.getObservationNotes(), observation.isObservationDiseaseRelated(),
            observation.getTrialUniqueId(), observation.getBlockId(), observation.getPlotId(), observation.getObservationId());
  }
}
