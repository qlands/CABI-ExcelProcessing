package org.cabi.ofra.dataload.db;

import org.cabi.ofra.dataload.model.BlockActivity;

/**
 * DAO Object to manipulate data associated with block activities
 */
public interface IBlockActivityDao extends IDao {
  public BlockActivity findBlockActivityById(String trialId, String blockId, int activityId);
  public void createBlockActivity(BlockActivity blockActivity);
  public void updateBlockActivity(BlockActivity blockActivity);
}
