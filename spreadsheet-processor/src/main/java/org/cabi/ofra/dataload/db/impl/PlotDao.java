package org.cabi.ofra.dataload.db.impl;

import org.cabi.ofra.dataload.db.IPlotDao;
import org.cabi.ofra.dataload.model.Plot;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 * Created by equiros on 10/11/14.
 */
public class PlotDao extends BaseDao implements IPlotDao {
  @Override
  public boolean existsPlot(Plot plot) {
    return existsPlotById(plot.getTrialUniqueId(), plot.getBlockNumber(), plot.getPlotId());
  }

  @Override
  public boolean existsPlotById(String trialUid, int blockId, int plotId) {
    int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ofrafertrials.plot WHERE trial_id = ? AND block_id = ? AND plot_id = ?", Integer.class,
            trialUid, String.valueOf(blockId), plotId);
    return count > 0;
  }

  @Override
  public Plot findPlotById(String trialUid, int blockId, int plotId) {
    try {
      return jdbcTemplate.queryForObject("SELECT trial_id, block_id, plot_id, plot_obs, plot_area, plot_crop1, plot_crop2, plot_crop3, plot_nitrogen, " +
                      " plot_phosphorus, plot_potassium, plot_sulphur, plot_zink, plot_magnesium, plot_boron, plot_manure," +
                      " plot_bootingdate, plot_silkdate, plot_flowerdate, plot_panicledate" +
                      " FROM ofrafertrials.plot" +
                      " WHERE trial_id = ? AND block_id = ? AND plot_id = ?",
              new Object[]{trialUid, blockId, plotId},
              (resultSet, i) -> {
                Plot plot = new Plot();
                plot.setTrialUniqueId(resultSet.getString(1));
                plot.setBlockNumber(resultSet.getInt(2));
                plot.setPlotId(resultSet.getInt(3));
                plot.setObservations(resultSet.getString(4));
                plot.setArea(resultSet.getDouble(5));
                plot.setCropOne(resultSet.getString(6));
                plot.setCropTwo(resultSet.getString(7));
                plot.setCropThree(resultSet.getString(8));
                plot.setNitrogen(resultSet.getDouble(9));
                plot.setPhosphorus(resultSet.getDouble(10));
                plot.setPotassium(resultSet.getDouble(11));
                plot.setSulphur(resultSet.getDouble(12));
                plot.setZinc(resultSet.getDouble(13));
                plot.setMagnesium(resultSet.getDouble(14));
                plot.setBoron(resultSet.getDouble(15));
                plot.setManure(resultSet.getDouble(16));
                plot.setBootingDate(resultSet.getDate(17));
                plot.setSilkDate(resultSet.getDate(18));
                plot.setFlowerDate(resultSet.getDate(19));
                plot.setPanicleDate(resultSet.getDate(20));
                return plot;
              });
    }
    catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Override
  public void createPlot(Plot plot) {
    jdbcTemplate.update("INSERT INTO ofrafertrials.plot(trial_id, block_id, plot_id, plot_obs, plot_area, plot_crop1, plot_crop2, plot_crop3, plot_nitrogen," +
                        " plot_phosphorus, plot_potassium, plot_sulphur, plot_zink, plot_magnesium, plot_boron, plot_manure, plot_bootingdate, plot_silkdate," +
                    "     plot_flowerdate, plot_panicledate)" +
                        " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            nvl(plot.getTrialUniqueId()), nvl(String.valueOf(plot.getBlockNumber())), nvl(plot.getPlotId()), nvl(plot.getObservations()), nvl(plot.getArea()), nvl(plot.getCropOne()),
            nvl(plot.getCropTwo()), nvl(plot.getCropThree()), nvl(plot.getNitrogen()), nvl(plot.getPhosphorus()), nvl(plot.getPotassium()), nvl(plot.getSulphur()), nvl(plot.getZinc()),
            nvl(plot.getMagnesium()), nvl(plot.getBoron()), nvl(plot.getManure()), plot.getBootingDate(), plot.getSilkDate(), plot.getFlowerDate(), plot.getPanicleDate());
  }

  @Override
  public void updatePlot(Plot plot) {
    jdbcTemplate.update("UPDATE ofrafertrials.plot SET plot_obs = ?, plot_area = ?, plot_crop1 = ?, plot_crop2 = ?, plot_crop3 = ?, plot_nitrogen = ?," +
                    " plot_phosphorus = ?, plot_potassium = ?, plot_sulphur = ?, plot_zink = ?, plot_magnesium = ?, plot_boron = ?, plot_manure = ?," +
                    " plot_bootingdate = ?, plot_silkdate = ?, plot_flowerdate = ?, plot_panicledate = ?" +
                    " WHERE trial_id = ? AND block_id = ? AND plot_id = ?",
            nvl(plot.getObservations()), nvl(plot.getArea()), nvl(plot.getCropOne()), nvl(plot.getCropTwo()), nvl(plot.getCropThree()), nvl(plot.getNitrogen()),
            nvl(plot.getPhosphorus()), nvl(plot.getPotassium()), nvl(plot.getSulphur()), nvl(plot.getZinc()), nvl(plot.getMagnesium()), nvl(plot.getBoron()),
            nvl(plot.getManure()), plot.getBootingDate(), plot.getSilkDate(), plot.getFlowerDate(), plot.getPanicleDate(),
            nvl(plot.getTrialUniqueId()), nvl(String.valueOf(plot.getBlockNumber())), nvl(plot.getPlotId()));
  }
}
