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
            " WHERE plot_trial_id = ? AND plot_block_id = ? AND plot_plot_id = ? AND hrvst_date = ?", Integer.class,
            harvestLegume.getTrialUid(), harvestLegume.getBlockId(), harvestLegume.getPlotId(), harvestLegume.getHarvestDate());
    return count > 0;
  }

  @Override
  public HarvestLegume findHarvestLegumeById(String trialUid, int blockId, int plotId, Date harvestDate) {
    try {
      return jdbcTemplate.queryForObject("SELECT plot_trial_id, plot_block_id, plot_plot_id, hrvst_date, hrvst_plantcount, hrvst_biomass, hrvst_yield, hrvst_flowerdate, " +
                      " hrvst_grainmoist " +
                      " FROM ofrafertrials.hrvstlegumes" +
                      " WHERE plot_trial_id = ? AND plot_block_id = ? AND plot_plot_id = ? AND hrvst_date = ?",
              new Object[]{trialUid, blockId, plotId, harvestDate},
              (resultSet, i) -> {
                HarvestLegume harvestLegume = new HarvestLegume();
                return harvestLegume;
              });
    }
    catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Override
  public void createHarvestLegume(HarvestLegume harvestLegume) {
    jdbcTemplate.update("INSERT INTO ofrafertrials.hrvstlegumes (plot_trial_id, plot_block_id, plot_plot_id, hrvst_date, hrvst_plantcount, hrvst_biomass, " +
            " hrvst_yield, hrvst_flowerdate, hrvst_grainmoist) " +
            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
            harvestLegume.getTrialUid(), harvestLegume.getBlockId(), harvestLegume.getPlotId(), harvestLegume.getHarvestDate(), harvestLegume.getPlantCount(),
            harvestLegume.getBioMass(), harvestLegume.getYield(), harvestLegume.getFlowerDate(), harvestLegume.getGrainMoisture());
  }

  @Override
  public void updateHarvestLegume(HarvestLegume harvestLegume) {
    jdbcTemplate.update("UPDATE ofrafertrials.hrvstlegumes SET hrvst_plantcount = ?, hrvst_biomass = ?, hrvst_yield = ?, hrvst_flowerdate = ?, hrvst_grainmoist = ?" +
            " WHERE plot_trial_id = ? AND plot_block_id = ? AND plot_plot_id = ? AND hrvst_date = ?",
            harvestLegume.getPlantCount(), harvestLegume.getBioMass(), harvestLegume.getYield(), harvestLegume.getFlowerDate(), harvestLegume.getGrainMoisture(),
            harvestLegume.getTrialUid(), harvestLegume.getBlockId(), harvestLegume.getPlotId(), harvestLegume.getHarvestDate());
  }
}
