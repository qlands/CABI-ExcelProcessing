package org.cabi.ofra.dataload.db;

import org.cabi.ofra.dataload.model.HarvestGrass;

import java.util.Date;

/**
 * DAO object to manipulate grasses harvest data
 */
public interface IHarvestGrassesDao extends IDao {
  public boolean existsHarvestGrass(HarvestGrass harvestGrass);
  public HarvestGrass findHarvestGrassById(String trialUid, int blockId, int plotId, Date harvestDate);
  public void createHarvestGrass(HarvestGrass harvestGrass);
  public void updateHarvestGrass(HarvestGrass harvestGrass);
}
