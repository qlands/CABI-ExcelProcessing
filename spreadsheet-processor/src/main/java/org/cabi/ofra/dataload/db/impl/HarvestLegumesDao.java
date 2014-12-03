package org.cabi.ofra.dataload.db.impl;

import org.cabi.ofra.dataload.db.IHarvestLegumesDao;
import org.cabi.ofra.dataload.model.HarvestLegume;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Date;

/**
 * Created by equiros on 11/29/2014.
 */
public class HarvestLegumesDao extends BaseDao implements IHarvestLegumesDao {
  @Override
  public boolean existsHarvestLegume(HarvestLegume harvestLegume) {
    int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ofrafertrials.hrvstlegumes" +
            " WHERE trial_id = ? AND block_id = ? AND plot_id = ? AND hrvst_date = ?", Integer.class,
            harvestLegume.getTrialUid(), harvestLegume.getBlockId(), harvestLegume.getPlotId(), harvestLegume.getHarvestDate());
    return count > 0;
  }

  @Override
  public HarvestLegume findHarvestLegumeById(String trialUid, int blockId, int plotId, Date harvestDate) {
    try {
      return jdbcTemplate.queryForObject("SELECT trial_id, block_id, plot_id, hrvst_date, hrvst_plantcount, hrvst_biomass, hrvst_yield, hrvst_flowerdate, " +
                      " hrvst_grainmoist " +
                      " FROM ofrafertrials.hrvstlegumes" +
                      " WHERE trial_id = ? AND block_id = ? AND plot_id = ? AND hrvst_date = ?",
              new Object[]{trialUid, blockId, plotId, harvestDate},
              (resultSet, i) -> {
                HarvestLegume harvestLegume = new HarvestLegume();
                harvestLegume.setTrialUid(resultSet.getString(1));
                harvestLegume.setBlockId(resultSet.getInt(2));
                harvestLegume.setPlotId(resultSet.getInt(3));
                harvestLegume.setHarvestDate(resultSet.getDate(4));
                harvestLegume.setPlantCount(resultSet.getInt(5));
                harvestLegume.setBioMass(resultSet.getDouble(6));
                harvestLegume.setYield(resultSet.getDouble(7));
                harvestLegume.setFlowerDate(resultSet.getDate(8));
                harvestLegume.setGrainMoisture(resultSet.getDouble(9));
                return harvestLegume;
              });
    }
    catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Override
  public void createHarvestLegume(HarvestLegume harvestLegume) {
    jdbcTemplate.update("INSERT INTO ofrafertrials.hrvstlegumes (trial_id, block_id, plot_id, hrvst_date, hrvst_plantcount, hrvst_biomass, " +
            " hrvst_yield, hrvst_flowerdate, hrvst_grainmoist) " +
            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
            harvestLegume.getTrialUid(), harvestLegume.getBlockId(), harvestLegume.getPlotId(), harvestLegume.getHarvestDate(), harvestLegume.getPlantCount(),
            harvestLegume.getBioMass(), harvestLegume.getYield(), harvestLegume.getFlowerDate(), harvestLegume.getGrainMoisture());
  }

  @Override
  public void updateHarvestLegume(HarvestLegume harvestLegume) {
    jdbcTemplate.update("UPDATE ofrafertrials.hrvstlegumes SET hrvst_plantcount = ?, hrvst_biomass = ?, hrvst_yield = ?, hrvst_flowerdate = ?, hrvst_grainmoist = ?" +
            " WHERE trial_id = ? AND block_id = ? AND plot_id = ? AND hrvst_date = ?",
            harvestLegume.getPlantCount(), harvestLegume.getBioMass(), harvestLegume.getYield(), harvestLegume.getFlowerDate(), harvestLegume.getGrainMoisture(),
            harvestLegume.getTrialUid(), harvestLegume.getBlockId(), harvestLegume.getPlotId(), harvestLegume.getHarvestDate());
  }
}
