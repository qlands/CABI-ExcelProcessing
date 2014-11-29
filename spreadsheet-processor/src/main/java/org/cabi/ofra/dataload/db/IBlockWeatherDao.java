package org.cabi.ofra.dataload.db;

import org.cabi.ofra.dataload.model.BlockWeather;

import java.util.Date;

/**
 * Created by equiros on 11/24/2014.
 */
public interface IBlockWeatherDao extends IDao {
  public boolean existsBlockWeatherById(String trialUid, int blockId, Date collectionDate);
  public void updateBlockWeather(BlockWeather blockWeather);
  public void createBlockWeather(BlockWeather blockWeather);
}
