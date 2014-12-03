package org.cabi.ofra.dataload.db.impl;

import org.cabi.ofra.dataload.db.IBlockWeatherDao;
import org.cabi.ofra.dataload.model.BlockWeather;

import java.util.Date;

/**
 * Created by equiros on 11/24/2014.
 */
public class BlockWeatherDao extends BaseDao implements IBlockWeatherDao {
  @Override
  public boolean existsBlockWeatherById(String trialUid, int blockId, Date collectionDate) {
    int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ofrafertrials.blockweather " +
            "WHERE trial_id = ? AND block_id = ? AND colle_date = ?", Integer.class,
            new Object[] {trialUid, blockId, collectionDate});
    return count > 0;
  }

  @Override
  public void updateBlockWeather(BlockWeather blockWeather) {
    jdbcTemplate.update("UPDATE ofrafertrials.blockweather SET rainfall = ?, maxtemp = ?, mintemp = ?, radiation = ?, wind = ?, relhumid = ?" +
            " WHERE trial_id = ? AND block_id = ? AND colle_date = ?",
            blockWeather.getRainfall(), blockWeather.getMaxtemp(), blockWeather.getMintemp(), blockWeather.getRadiation(), blockWeather.getWind(), blockWeather.getRelhumid(),
            blockWeather.getTrialUniqueId(), blockWeather.getBlockId(), blockWeather.getCollectionDate());
  }

  @Override
  public void createBlockWeather(BlockWeather blockWeather) {
    jdbcTemplate.update("INSERT INTO ofrafertrials.blockweather (trial_id, block_id, colle_date, rainfall, maxtemp, mintemp, radiation, wind, relhumid) " +
            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
            blockWeather.getTrialUniqueId(), blockWeather.getBlockId(), blockWeather.getCollectionDate(), blockWeather.getRainfall(), blockWeather.getMaxtemp(),
            blockWeather.getMintemp(), blockWeather.getRadiation(), blockWeather.getWind(), blockWeather.getRelhumid());
  }
}
