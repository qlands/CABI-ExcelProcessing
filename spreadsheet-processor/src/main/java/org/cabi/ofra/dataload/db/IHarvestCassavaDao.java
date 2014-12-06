package org.cabi.ofra.dataload.db;

import org.cabi.ofra.dataload.model.HarvestCassava;

import java.util.Date;

/**
 * DAO object to manipulate cassava harvest data
 */
public interface IHarvestCassavaDao extends IDao {
  public boolean existsHarvestCassava(HarvestCassava harvestCassava);
  public HarvestCassava findHarvestCassavaById(String trialUid, int blockId, int plotId, Date harvestDate);
  public void createHarvestCassava(HarvestCassava harvestCassava);
  public void updateHarvestCassava(HarvestCassava harvestCassava);
}
