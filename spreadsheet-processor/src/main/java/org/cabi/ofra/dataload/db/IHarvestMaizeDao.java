package org.cabi.ofra.dataload.db;

import org.cabi.ofra.dataload.model.HarvestMaize;

import java.util.Date;

/**
 * Created by equiros on 11/30/2014.
 */
public interface IHarvestMaizeDao extends IDao {
  public boolean existsHavestMaize(HarvestMaize harvestMaize);
  public HarvestMaize findHarvestMaizeById(String trialUid, int blockId, int plotId, Date harvestDate);
  public void createHarvestMaize(HarvestMaize harvestMaize);
  public void updateHarvestMaize(HarvestMaize harvestMaize);
}
