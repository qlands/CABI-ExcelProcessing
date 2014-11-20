package org.cabi.ofra.dataload.db;

import org.cabi.ofra.dataload.model.BlockActivity;

/**
 * Created by equiros on 11/18/2014.
 */
public interface IBlockActivityDao extends IDao {
  public BlockActivity findBlockActivityById(String trialId, String blockId, int activityId);
  public void createBlockActivity(BlockActivity blockActivity);
  public void updateBlockActivity(BlockActivity blockActivity);
}
