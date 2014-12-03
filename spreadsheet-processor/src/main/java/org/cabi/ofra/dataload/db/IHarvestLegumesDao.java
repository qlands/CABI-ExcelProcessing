package org.cabi.ofra.dataload.db;

import org.cabi.ofra.dataload.model.HarvestLegume;

import java.util.Date;

/**
 * Created by equiros on 11/29/2014.
 */
public interface IHarvestLegumesDao extends IDao {
  public boolean existsHarvestLegume(HarvestLegume harvestLegume);
  public HarvestLegume findHarvestLegumeById(String trialUid, int blockId, int plotId, Date harvestDate);
  public void createHarvestLegume(HarvestLegume harvestLegume);
  public void updateHarvestLegume(HarvestLegume harvestLegume);
}
