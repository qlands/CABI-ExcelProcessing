package org.cabi.ofra.dataload.db;

import org.cabi.ofra.dataload.model.BlockObservation;

/**
 * DAO object to manipulate block observation data
 */
public interface IBlockObservationDao extends IDao {
  public BlockObservation findBlockObservationById(String trialUid, int blockNumber, int observationId);
  public void createBlockObservation(BlockObservation blockObservation);
  public void updateBlockObservation(BlockObservation blockObservation);
}
