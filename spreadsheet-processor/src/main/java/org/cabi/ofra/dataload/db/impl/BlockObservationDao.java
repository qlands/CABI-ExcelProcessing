package org.cabi.ofra.dataload.db.impl;

import org.cabi.ofra.dataload.db.IBlockObservationDao;
import org.cabi.ofra.dataload.model.BlockObservation;

/**
 * Created by equiros on 11/19/2014.
 */
public class BlockObservationDao extends BaseDao implements IBlockObservationDao {
  @Override
  public BlockObservation findBlockObservationById(String trialUid, int blockNumber, int observationId) {
    return jdbcTemplate.queryForObject("SELECT trial_id, block_id, obs_id, obs_date, obs_notes, obs_disea " +
                    " FROM ofrafertrials.blockobservation" +
                    " WHERE trial_id = ? AND block_id = ? AND obs_id = ?", new Object[]{trialUid, blockNumber, observationId},
            (resultSet, i) -> {
              BlockObservation blockObservation = new BlockObservation();
              blockObservation.setTrialUniqueId(resultSet.getString(1));
              blockObservation.setBlockNumber(resultSet.getInt(2));
              blockObservation.setObservationId(resultSet.getInt(3));
              blockObservation.setObservationDate(resultSet.getDate(4));
              blockObservation.setObservationNotes(resultSet.getString(5));
              blockObservation.setObservationDiseaseRelated(resultSet.getBoolean(6));
              return blockObservation;
            });
  }

  @Override
  public void createBlockObservation(BlockObservation blockObservation) {
    jdbcTemplate.update("INSERT INTO ofrafertrials.blockobservation (trial_id, block_id, obs_id, obs_date, obs_notes, obs_disea) VALUES (?, ?, ?, ?, ?, ?)",
            blockObservation.getTrialUniqueId(), blockObservation.getBlockNumber(), blockObservation.getObservationId(), blockObservation.getObservationDate(),
            blockObservation.getObservationNotes(), blockObservation.isObservationDiseaseRelated());
  }

  @Override
  public void updateBlockObservation(BlockObservation blockObservation) {
    jdbcTemplate.update("UPDATE ofrafertrials.blockobservation" +
            " SET obs_date = ?, obs_notes = ?, obs_disea = ?" +
            " WHERE trial_id = ? AND block_id = ? AND obs_id = ?",
            blockObservation.getObservationDate(), blockObservation.getObservationNotes(), blockObservation.isObservationDiseaseRelated(),
            blockObservation.getTrialUniqueId(), blockObservation.getBlockNumber(), blockObservation.getObservationId());
  }
}
