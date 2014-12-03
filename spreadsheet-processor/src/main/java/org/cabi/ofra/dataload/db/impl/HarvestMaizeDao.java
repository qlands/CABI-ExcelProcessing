package org.cabi.ofra.dataload.db.impl;

import org.cabi.ofra.dataload.db.IHarvestMaizeDao;
import org.cabi.ofra.dataload.model.HarvestMaize;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import java.util.Date;

/**
 * Created by equiros on 11/30/2014.
 */
public class HarvestMaizeDao extends BaseDao implements IHarvestMaizeDao {
  @Override
  public boolean existsHavestMaize(HarvestMaize harvestMaize) {
    int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ofrafertrials.hrvstmaize" +
            " WHERE trial_id = ? AND block_id = ? AND plot_id = ? AND hrvst_date = ?", Integer.class,
            harvestMaize.getTrialUid(), harvestMaize.getBlockId(), harvestMaize.getPlotId(), harvestMaize.getHarvestDate());
    return count > 0;
  }

  @Override
  public HarvestMaize findHarvestMaizeById(String trialUid, int blockId, int plotId, Date harvestDate) {
    try {
      return jdbcTemplate.queryForObject("SELECT trial_id, block_id, plot_id, hrvst_date, hrvst_plantcount, hrvst_earcount, hrvst_earweight, " +
                      "hrvst_grainyield, hrvst_stoveryield, hrvst_stoversample, hrvst_grainmoist, hrvst_silkdate, hrvst_stoverdryyield" +
                      " FROM ofrafertrials.hrvstmaize" +
                      " WHERE trial_id = ? AND block_id = ? AND plot_id = ? AND hrvst_date = ?",
              new Object[]{trialUid, blockId, plotId, harvestDate},
              (RowMapper<HarvestMaize>) (resultSet, i) -> {
                HarvestMaize harvestMaize = new HarvestMaize();
                harvestMaize.setTrialUid(resultSet.getString(1));
                harvestMaize.setBlockId(resultSet.getInt(2));
                harvestMaize.setPlotId(resultSet.getInt(3));
                harvestMaize.setHarvestDate(resultSet.getDate(4));
                harvestMaize.setPlantCount(resultSet.getInt(5));
                harvestMaize.setEarCount(resultSet.getInt(6));
                harvestMaize.setEarWeight(resultSet.getDouble(7));
                harvestMaize.setGrainYield(resultSet.getDouble(8));
                harvestMaize.setStoverYield(resultSet.getDouble(9));
                harvestMaize.setStoverSample(resultSet.getDouble(10));
                harvestMaize.setGrainMoisture(resultSet.getDouble(11));
                harvestMaize.setSilkDate(resultSet.getDate(12));
                harvestMaize.setStoverDryYield(resultSet.getDouble(13));
                return harvestMaize;
              });
    }
    catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Override
  public void createHarvestMaize(HarvestMaize harvestMaize) {
    jdbcTemplate.update("INSERT INTO ofrafertrials.hrvstmaize (trial_id, block_id, plot_id, hrvst_date, hrvst_plantcount, hrvst_earcount, " +
            " hrvst_earweight, hrvst_grainyield, hrvst_stoveryield, hrvst_stoversample, hrvst_grainmoist, hrvst_silkdate, hrvst_stoverdryyield) " +
            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            harvestMaize.getTrialUid(), harvestMaize.getBlockId(), harvestMaize.getPlotId(), harvestMaize.getHarvestDate(), harvestMaize.getPlantCount(),
            harvestMaize.getEarCount(), harvestMaize.getEarWeight(), harvestMaize.getGrainYield(), harvestMaize.getStoverYield(), harvestMaize.getStoverSample(),
            harvestMaize.getGrainMoisture(), harvestMaize.getSilkDate(), harvestMaize.getStoverDryYield());
  }

  @Override
  public void updateHarvestMaize(HarvestMaize harvestMaize) {
    jdbcTemplate.update("UPDATE ofrafertrials.hrvstmaize SET hrvst_plantcount = ?, hrvst_earcount = ?, hrvst_earweight = ?, hrvst_grainyield = ?, " +
            " hrvst_stoveryield = ?, hrvst_stoversample = ?, hrvst_grainmoist = ?, hrvst_silkdate = ?, hrvst_stoverdryyield = ?" +
            " WHERE trial_id = ? AND block_id = ? AND plot_id = ? AND hrvst_date = ?",
            harvestMaize.getPlantCount(), harvestMaize.getEarCount(), harvestMaize.getEarWeight(), harvestMaize.getGrainYield(),
            harvestMaize.getStoverYield(), harvestMaize.getStoverSample(), harvestMaize.getGrainMoisture(), harvestMaize.getSilkDate(), harvestMaize.getStoverDryYield(),
            harvestMaize.getTrialUid(), harvestMaize.getBlockId(), harvestMaize.getPlotId(), harvestMaize.getHarvestDate());
  }
}
