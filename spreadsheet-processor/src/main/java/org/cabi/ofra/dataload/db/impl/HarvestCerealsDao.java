package org.cabi.ofra.dataload.db.impl;

import org.cabi.ofra.dataload.db.IHarvestCerealsDao;
import org.cabi.ofra.dataload.model.HarvestCereal;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by equiros on 11/29/2014.
 */
public class HarvestCerealsDao extends BaseDao implements IHarvestCerealsDao {
  @Override
  public boolean existsHarvestCereal(HarvestCereal harvestCereal) {
    int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ofrafertrials.hrvstcereals" +
            " WHERE trial_id = ? AND block_id = ? AND plot_id = ? AND hrvst_date = ?", Integer.class,
            harvestCereal.getTrialUid(), harvestCereal.getBlockId(), harvestCereal.getPlotId(), harvestCereal.getHarvestDate());
    return count > 0;
  }

  @Override
  public HarvestCereal findHarvestCerealById(String trialUid, int blockId, int plotId, Date harvestDate) {
    try {
      return jdbcTemplate.queryForObject("SELECT trial_id, block_id, plot_id, hrvst_date, hrvst_headweight, hrvst_grainyield, hrvst_stoveryield, " +
                      " hrvst_stoversample, hrvst_grainmoist, hrvst_stoverdryyield" +
                      " FROM ofrafertrials.hrvstcereals " +
                      " WHERE trial_id = ? AND block_id = ? AND plot_id = ? AND hrvst_date = ?",
              new Object[]{trialUid, blockId, plotId, harvestDate},
              (resultSet, i) -> {
                HarvestCereal harvestCereal = new HarvestCereal();
                harvestCereal.setTrialUid(resultSet.getString(1));
                harvestCereal.setBlockId(resultSet.getInt(2));
                harvestCereal.setPlotId(resultSet.getInt(3));
                harvestCereal.setHarvestDate(resultSet.getDate(4));
                harvestCereal.setHeadWeight(resultSet.getDouble(5));
                harvestCereal.setGrainYield(resultSet.getDouble(6));
                harvestCereal.setStoverYield(resultSet.getDouble(7));
                harvestCereal.setStoverSample(resultSet.getDouble(8));
                harvestCereal.setGrainMoist(resultSet.getDouble(9));
                harvestCereal.setStoverDryYield(resultSet.getDouble(10));
                return harvestCereal;
              });
    }
    catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Override
  public void createHarvestCereal(HarvestCereal harvestCereal) {
    jdbcTemplate.update("INSERT INTO ofrafertrials.hrvstcereals (trial_id, block_id, plot_id, hrvst_date, hrvst_headweight, hrvst_grainyield, " +
            "hrvst_stoveryield, hrvst_stoversample, hrvst_grainmoist, hrvst_stoverdryyield) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            harvestCereal.getTrialUid(), harvestCereal.getBlockId(), harvestCereal.getPlotId(), harvestCereal.getHarvestDate(), harvestCereal.getHeadWeight(),
            harvestCereal.getGrainYield(), harvestCereal.getStoverYield(), harvestCereal.getStoverSample(), harvestCereal.getGrainMoist(),
            harvestCereal.getStoverDryYield());
  }

  @Override
  public void updateHarvestCereal(HarvestCereal harvestCereal) {
    jdbcTemplate.update("UPDATE ofrafertrials.hrvstcereals SET hrvst_headweight = ?, hrvst_grainyield = ?, hrvst_stoveryield = ?, hrvst_stoversample = ?, " +
            "hrvst_grainmoist = ?, hrvst_stoverdryyield = ?" +
            " WHERE trial_id = ? AND block_id = ? AND plot_id = ? AND hrvst_date = ?",
            harvestCereal.getHeadWeight(), harvestCereal.getGrainYield(), harvestCereal.getStoverYield(), harvestCereal.getStoverSample(),
            harvestCereal.getGrainMoist(), harvestCereal.getStoverDryYield(),
            harvestCereal.getTrialUid(), harvestCereal.getBlockId(), harvestCereal.getPlotId(), harvestCereal.getHarvestDate());
  }
}
