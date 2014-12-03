package org.cabi.ofra.dataload.db.impl;

import org.cabi.ofra.dataload.db.IHarvestCassavaDao;
import org.cabi.ofra.dataload.model.HarvestCassava;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Date;

/**
 * Created by equiros on 11/30/2014.
 */
public class HarvestCassavaDao extends BaseDao implements IHarvestCassavaDao {
  @Override
  public boolean existsHarvestCassava(HarvestCassava harvestCassava) {
    int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ofrafertrials.hrvstcassava" +
            " WHERE trial_id = ? AND block_id = ? AND plot_id = ? AND hrvst_date = ?", Integer.class,
            harvestCassava.getTrialUid(), harvestCassava.getBlockId(), harvestCassava.getPlotId(), harvestCassava.getHarvestDate());
    return count > 0;
  }

  @Override
  public HarvestCassava findHarvestCassavaById(String trialUid, int blockId, int plotId, Date harvestDate) {
    try {
      return jdbcTemplate.queryForObject("SELECT trial_id, block_id, plot_id, hrvst_date, hrvst_plantcount, hrvst_tubercount, hrvst_yield, hrvst_sample, " +
                      " hrvst_sampledry, hrvst_drytuber" +
                      " FROM ofrafertrials.hrvstcassava" +
                      " WHERE trial_id = ? AND block_id = ? AND plot_id = ? AND hrvst_date = ?",
              new Object[]{trialUid, blockId, plotId, harvestDate},
              (resultSet, i) -> {
                HarvestCassava harvestCassava = new HarvestCassava();
                harvestCassava.setTrialUid(resultSet.getString(1));
                harvestCassava.setBlockId(resultSet.getInt(2));
                harvestCassava.setPlotId(resultSet.getInt(3));
                harvestCassava.setHarvestDate(resultSet.getDate(4));
                harvestCassava.setPlantCount(resultSet.getInt(5));
                harvestCassava.setTuberCount(resultSet.getInt(6));
                harvestCassava.setYield(resultSet.getDouble(7));
                harvestCassava.setSample(resultSet.getDouble(8));
                harvestCassava.setSampleDry(resultSet.getDouble(9));
                harvestCassava.setDryTuber(resultSet.getDouble(10));
                return harvestCassava;
              });
    }
    catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Override
  public void createHarvestCassava(HarvestCassava harvestCassava) {
    jdbcTemplate.update("INSERT INTO ofrafertrials.hrvstcassava (trial_id, block_id, plot_id, hrvst_date, hrvst_plantcount, hrvst_tubercount, hrvst_yield, " +
            " hrvst_sample, hrvst_sampledry, hrvst_drytuber) " +
            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            harvestCassava.getTrialUid(), harvestCassava.getBlockId(), harvestCassava.getPlotId(), harvestCassava.getHarvestDate(), harvestCassava.getPlantCount(),
            harvestCassava.getTuberCount(), harvestCassava.getYield(), harvestCassava.getSample(), harvestCassava.getSampleDry(), harvestCassava.getDryTuber());
  }

  @Override
  public void updateHarvestCassava(HarvestCassava harvestCassava) {
    jdbcTemplate.update("UPDATE ofrafertrials.hrvstcassava SET hrvst_plantcount = ?, hrvst_tubercount = ?, hrvst_yield = ?, hrvst_sample = ?, " +
            " hrvst_sampledry = ?, hrvst_drytuber = ?" +
            " WHERE trial_id = ? AND block_id = ? AND plot_id = ? AND hrvst_date = ?",
            harvestCassava.getPlantCount(), harvestCassava.getTuberCount(), harvestCassava.getYield(), harvestCassava.getSample(), harvestCassava.getSampleDry(),
            harvestCassava.getDryTuber(), harvestCassava.getTrialUid(), harvestCassava.getBlockId(), harvestCassava.getPlotId(), harvestCassava.getHarvestDate());
  }
}
