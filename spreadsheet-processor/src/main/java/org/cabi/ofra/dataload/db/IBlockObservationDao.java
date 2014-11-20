package org.cabi.ofra.dataload.db;

import org.cabi.ofra.dataload.model.BlockObservation;

/**
 * Created by equiros on 11/19/2014.
 */
public interface IBlockObservationDao extends IDao {
  public BlockObservation findBlockObservationById(String trialUid, int blockNumber, int observationId);
  public void createBlockObservation(BlockObservation blockObservation);
  public void updateBlockObservation(BlockObservation blockObservation);
}
