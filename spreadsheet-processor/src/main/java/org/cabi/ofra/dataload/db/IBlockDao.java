package org.cabi.ofra.dataload.db;

import org.cabi.ofra.dataload.model.Block;

/**
 * DAO Object to manipulate block related data
 */
public interface IBlockDao extends IDao {
  public boolean existsBlockById(String trialUid, int blockId);
  public boolean existsBlock(Block block);
  public void createBlock(Block block);
  public void updateBlock(Block block);
}
