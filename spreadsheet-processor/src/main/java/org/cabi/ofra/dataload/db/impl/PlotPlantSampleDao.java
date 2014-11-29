package org.cabi.ofra.dataload.db.impl;

import org.cabi.ofra.dataload.db.IPlotPlantSampleDao;
import org.cabi.ofra.dataload.model.PlotPlantSample;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by equiros on 11/26/2014.
 */
public class PlotPlantSampleDao extends BaseDao implements IPlotPlantSampleDao {
  @Override
  public boolean existsPlotPlantSampleById(String trialUid, int blockId, int plotId, int sampleId) {
    int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ofrafertrials.plantsample WHERE trial_id = ? AND block_id = ? AND plot_id = ? AND psample_id = ?", Integer.class,
            trialUid, blockId, plotId, sampleId);
    return count > 0;
  }

  @Override
  public PlotPlantSample findPlotPlantSampleByCode(String trialUid, int blockId, int plotId, String sampleCode) {
    try {
      return jdbcTemplate.queryForObject("SELECT trial_id, block_id, plot_id, psample_id, psample_code, psample_cdate, psample_adate, " +
                      "psample_ssn, psample_pn, psample_pp, psample_pk, psample_pca, psample_pmg, psample_pfe, psample_pzn, psample_pmn, " +
                      "psample_pcu, psample_pb, psample_wave" +
                      " FROM ofrafertrials.plantsample" +
                      " WHERE trial_id = ? AND block_id = ? AND plot_id = ? AND psample_code = ?",
              new Object[]{trialUid, blockId, plotId, sampleCode},
              (resultSet, i) -> {
                return buildPlotPlantSample(resultSet);
              });
    }
    catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  private PlotPlantSample buildPlotPlantSample(ResultSet resultSet) throws SQLException {
    PlotPlantSample sample = new PlotPlantSample();
    sample.setTrialUniqueId(resultSet.getString(1));
    sample.setBlockId(resultSet.getInt(2));
    sample.setPlotId(resultSet.getInt(3));
    sample.setSampleId(resultSet.getInt(4));
    sample.setSampleCode(resultSet.getString(5));
    sample.setCollectionDate(resultSet.getDate(6));
    sample.setAnalysisDate(resultSet.getDate(7));
    sample.setSsn(resultSet.getString(8));
    sample.setPn(resultSet.getDouble(9));
    sample.setPp(resultSet.getDouble(10));
    sample.setPk(resultSet.getDouble(11));
    sample.setPca(resultSet.getDouble(12));
    sample.setPmg(resultSet.getDouble(13));
    sample.setPfe(resultSet.getDouble(14));
    sample.setPzn(resultSet.getDouble(15));
    sample.setPmn(resultSet.getDouble(16));
    sample.setPcu(resultSet.getDouble(17));
    sample.setPb(resultSet.getDouble(18));
    sample.setWave(resultSet.getDouble(19));
    return sample;
  }

  @Override
  public PlotPlantSample findPlotPlantSampleById(String trialUid, int blockId, int plotId, int sampleId) {
    try {
      return jdbcTemplate.queryForObject("SELECT trial_id, block_id, plot_id, psample_id, psample_code, psample_cdate, psample_adate, " +
                      "psample_ssn, psample_pn, psample_pp, psample_pk, psample_pca, psample_pmg, psample_pfe, psample_pzn, psample_pmn, " +
                      "psample_pcu, psample_pb, psample_wave" +
                      " FROM ofrafertrials.plantsample" +
                      " WHERE trial_id = ? AND block_id = ? AND plot_id = ? AND psample_id = ?",
              new Object[]{trialUid, blockId, plotId, sampleId},
              (resultSet, i) -> {
                return buildPlotPlantSample(resultSet);
              });
    }
    catch (EmptyResultDataAccessException  e) {
      return null;
    }
  }

  @Override
  public void createPlotPlantSample(PlotPlantSample plotPlantSample) {
    jdbcTemplate.update("INSERT INTO ofrafertrials.plantsample (trial_id, block_id, plot_id, psample_id, psample_code, psample_cdate, psample_adate, " +
            "psample_ssn, psample_pn, psample_pp, psample_pk, psample_pca, psample_pmg, psample_pfe, psample_pzn, " +
            "psample_pmn, psample_pcu, psample_pb, psample_wave) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            plotPlantSample.getTrialUniqueId(), plotPlantSample.getBlockId(), plotPlantSample.getPlotId(), plotPlantSample.getSampleId(), plotPlantSample.getSampleCode(),
            plotPlantSample.getCollectionDate(), plotPlantSample.getAnalysisDate(), plotPlantSample.getSsn(), plotPlantSample.getPn(), plotPlantSample.getPp(),
            plotPlantSample.getPk(), plotPlantSample.getPca(), plotPlantSample.getPmg(), plotPlantSample.getPfe(), plotPlantSample.getPzn(),
            plotPlantSample.getPmn(), plotPlantSample.getPcu(), plotPlantSample.getPb(), plotPlantSample.getWave());
  }

  @Override
  public void updatePlotPlantSample(PlotPlantSample plotPlantSample) {
    jdbcTemplate.update("UPDATE ofrafertrials.plantsample SET psample_code = ?, psample_cdate = ?, psample_adate = ?, psample_ssn = ?, psample_pn = ?, " +
            "psample_pp = ?, psample_pk = ?, psample_pca = ?, psample_pmg = ?, psample_pfe = ?, psample_pzn = ?, psample_pmn = ?, " +
            "psample_pcu = ?, psample_pb = ?, psample_wave = ?" +
            " WHERE trial_id = ? AND block_id = ? AND plot_id = ? AND psample_id = ?",
            plotPlantSample.getSampleCode(), plotPlantSample.getCollectionDate(), plotPlantSample.getAnalysisDate(), plotPlantSample.getSsn(), plotPlantSample.getPn(),
            plotPlantSample.getPp(), plotPlantSample.getPk(), plotPlantSample.getPca(), plotPlantSample.getPmg(), plotPlantSample.getPfe(), plotPlantSample.getPzn(),
            plotPlantSample.getPmn(), plotPlantSample.getPcu(), plotPlantSample.getPb(), plotPlantSample.getWave(),
            plotPlantSample.getTrialUniqueId(), plotPlantSample.getBlockId(), plotPlantSample.getPlotId(), plotPlantSample.getSampleId());
  }
}
