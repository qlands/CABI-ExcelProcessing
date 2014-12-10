package org.cabi.ofra.dataload.db.impl;

import org.cabi.ofra.dataload.db.IHarvestGrassesDao;
import org.cabi.ofra.dataload.model.HarvestGrass;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Date;

/**
 * Created by equiros on 11/30/2014.
 */
public class HarvestGrassesDao extends BaseDao implements IHarvestGrassesDao {
  @Override
  public boolean existsHarvestGrass(HarvestGrass harvestGrass) {
    int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ofrafertrials.hrvstgrasses " +
            " WHERE trial_id = ? AND block_id = ? AND plot_id = ? AND hrvst_date = ?", Integer.class,
            harvestGrass.getTrialUid(), harvestGrass.getBlockId(), harvestGrass.getPlotId(), harvestGrass.getHarvestDate());
    return count > 0;
  }

  @Override
  public HarvestGrass findHarvestGrassById(String trialUid, int blockId, int plotId, Date harvestDate) {
    try {
      return jdbcTemplate.queryForObject("SELECT trial_id, block_id, plot_id, hrvst_date, hrvst_plantcount, hrvst_paniclecount, " +
                      "hrvst_panicleweight, hrvst_grainyield, hrvst_stoveryield, hrvst_stoversample, hrvst_grainmoist, hrvst_stoverdryyield" +
                      " FROM ofrafertrials.hrvstgrasses" +
                      " WHERE trial_id = ? AND block_id = ? AND plot_id = ? AND hrvst_date = ?",
              new Object[]{trialUid, blockId, plotId, harvestDate},
              (resultSet, i) -> {
                HarvestGrass harvestGrass = new HarvestGrass();
                harvestGrass.setTrialUid(resultSet.getString(1));
                harvestGrass.setBlockId(resultSet.getInt(2));
                harvestGrass.setPlotId(resultSet.getInt(3));
                harvestGrass.setHarvestDate(resultSet.getDate(4));
                harvestGrass.setPlantCount(resultSet.getInt(5));
                harvestGrass.setPanicleCount(resultSet.getInt(6));
                harvestGrass.setPanicleWeight(resultSet.getDouble(7));
                harvestGrass.setGrainYield(resultSet.getDouble(8));
                harvestGrass.setStoverYield(resultSet.getDouble(9));
                harvestGrass.setStoverSample(resultSet.getDouble(10));
                harvestGrass.setGrainMoisture(resultSet.getDouble(11));
                harvestGrass.setStoverDryYield(resultSet.getDouble(12));
                return harvestGrass;
              });
    }
    catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Override
  public void createHarvestGrass(HarvestGrass harvestGrass) {
    jdbcTemplate.update("INSERT INTO ofrafertrials.hrvstgrasses (trial_id, block_id, plot_id, hrvst_date, hrvst_plantcount, hrvst_paniclecount, " +
            " hrvst_panicleweight, hrvst_grainyield, hrvst_stoveryield, hrvst_stoversample, hrvst_grainmoist, hrvst_stoverdryyield) " +
            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            harvestGrass.getTrialUid(), harvestGrass.getBlockId(), harvestGrass.getPlotId(), harvestGrass.getHarvestDate(), harvestGrass.getPlantCount(),
            harvestGrass.getPanicleCount(), harvestGrass.getPanicleWeight(), harvestGrass.getGrainYield(), harvestGrass.getStoverYield(),
            harvestGrass.getStoverSample(), harvestGrass.getGrainMoisture(), harvestGrass.getStoverDryYield());
  }

  @Override
  public void updateHarvestGrass(HarvestGrass harvestGrass) {
    jdbcTemplate.update("UPDATE ofrafertrials.hrvstgrasses SET hrvst_plantcount = ?, hrvst_paniclecount = ?, hrvst_panicleweight = ?, hrvst_grainyield = ?, " +
            " hrvst_stoveryield = ?, hrvst_stoversample = ?, hrvst_grainmoist = ?, hrvst_stoverdryyield = ?" +
            " WHERE trial_id = ? AND block_id = ? AND plot_id = ? AND hrvst_date = ?",
            harvestGrass.getPlantCount(), harvestGrass.getPanicleCount(), harvestGrass.getPanicleWeight(), harvestGrass.getGrainYield(),
            harvestGrass.getStoverYield(), harvestGrass.getStoverSample(), harvestGrass.getGrainMoisture(), harvestGrass.getStoverDryYield(),
            harvestGrass.getTrialUid(), harvestGrass.getBlockId(), harvestGrass.getPlotId(), harvestGrass.getHarvestDate());
  }
}
