package org.cabi.ofra.dataload.db;

import org.cabi.ofra.dataload.model.HarvestCereal;

import java.util.Date;

/**
 * Created by equiros on 11/29/2014.
 */
public interface IHarvestCerealsDao extends IDao {
  public boolean existsHarvestCereal(HarvestCereal harvestCereal);
  public HarvestCereal findHarvestCerealById(String trialUid, int blockId, int plotId, Date harvestDate);
  public void createHarvestCereal(HarvestCereal harvestCereal);
  public void updateHarvestCereal(HarvestCereal harvestCereal);
}
