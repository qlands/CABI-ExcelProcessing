package org.cabi.ofra.dataload.db.impl;

import org.cabi.ofra.dataload.db.IPlotSoilSampleDao;
import org.cabi.ofra.dataload.model.PlotSoilSample;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by equiros on 11/28/2014.
 */
public class PlotSoilSampleDao extends BaseDao implements IPlotSoilSampleDao {
  @Override
  public boolean existsPlotSoilSample(PlotSoilSample sample) {
    int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ofrafertrials.plotsoilsample" +
            " WHERE trial_id = ? AND block_id = ? AND plot_id = ? AND ssample_code = ?", Integer.class,
            sample.getTrialUid(), sample.getBlockId(), sample.getPlotId(), sample.getSampleCode());
    return count > 0;
  }

  @Override
  public PlotSoilSample findPlotSoilSampleByCode(String trialUid, int blockId, int plotId, String sampleCode) {
    return jdbcTemplate.queryForObject("SELECT trial_id, block_id, plot_id, ssample_id, ssample_code, ssample_cdate, ssample_trt, ssample_depth, ssample_adate, " +
                    "ssample_ssn, ssample_ph, ssample_ec, ssample_m3ai, ssample_m3b, ssample_m3ca, ssample_m3cu, ssample_m3fe, ssample_m3k, ssample_m3mg, " +
                    "ssample_m3mn, ssample_m3na, ssample_m3p, ssample_m3s, ssample_m3zn, ssmaple_hp" +
                    " FROM ofrafertrials.plotsoilsample " +
                    " WHERE trial_id = ?, block_id = ?, plot_id = ?, ssample_code = ?",
            new Object[]{trialUid, blockId, plotId, sampleCode},
            (resultSet, i) -> {
              PlotSoilSample sample = new PlotSoilSample();
              sample.setTrialUid(resultSet.getString(1));
              sample.setBlockId(resultSet.getInt(2));
              sample.setPlotId(resultSet.getInt(3));
              sample.setSampleId(resultSet.getInt(4));
              sample.setSampleCode(resultSet.getString(5));
              sample.setCollectionDate(resultSet.getDate(6));
              sample.setTrt(resultSet.getString(7));
              sample.setDepth(resultSet.getString(8));
              sample.setAnalysisDate(resultSet.getDate(9));
              sample.setSsn(resultSet.getString(10));
              sample.setPh(resultSet.getDouble(11));
              sample.setEc(resultSet.getDouble(12));
              sample.setM3ai(resultSet.getDouble(13));
              sample.setM3b(resultSet.getDouble(14));
              sample.setM3ca(resultSet.getDouble(15));
              sample.setM3cu(resultSet.getDouble(16));
              sample.setM3fe(resultSet.getDouble(17));
              sample.setM3k(resultSet.getDouble(18));
              sample.setM3mg(resultSet.getDouble(19));
              sample.setM3mn(resultSet.getDouble(20));
              sample.setM3na(resultSet.getDouble(21));
              sample.setM3p(resultSet.getDouble(22));
              sample.setM3s(resultSet.getDouble(23));
              sample.setM3zn(resultSet.getDouble(24));
              sample.setHp(resultSet.getDouble(25));
              return sample;
            });
  }

  @Override
  public void createPlotSoilSample(PlotSoilSample sample) {
    jdbcTemplate.update("INSERT INTO ofrafertrials.plotsoilsample (trial_id, block_id, plot_id, ssample_id, ssample_code, ssample_cdate, ssample_trt, " +
            "ssample_depth, ssample_adate, ssample_ssn, ssample_ph, ssample_ec, ssample_m3ai, ssample_m3b, ssample_m3ca, " +
            "ssample_m3cu, ssample_m3fe, ssample_m3k, ssample_m3mg, ssample_m3mn, ssample_m3na, ssample_m3p, ssample_m3s, " +
            "ssample_m3zn, ssmaple_hp) " +
            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            sample.getTrialUid(), sample.getBlockId(), sample.getPlotId(), sample.getSampleId(), sample.getSampleCode(), sample.getCollectionDate(), sample.getTrt(),
            sample.getDepth(), sample.getAnalysisDate(), sample.getSsn(), sample.getPh(), sample.getEc(), sample.getM3ai(), sample.getM3b(), sample.getM3ca(),
            sample.getM3cu(), sample.getM3fe(), sample.getM3k(), sample.getM3mg(), sample.getM3mn(), sample.getM3na(), sample.getM3p(), sample.getM3s(),
            sample.getM3zn(), sample.getHp());
  }

  @Override
  public void updatePlotSoilSample(PlotSoilSample sample) {
    jdbcTemplate.update("UPDATE ofrafertrials.plotsoilsample SET ssample_code = ?, ssample_cdate = ?, ssample_trt = ?, ssample_depth = ?, ssample_adate = ?, " +
            "ssample_ssn = ?, ssample_ph = ?, ssample_ec = ?, ssample_m3ai = ?, ssample_m3b = ?, ssample_m3ca = ?, ssample_m3cu = ?, ssample_m3fe = ?, " +
            "ssample_m3k = ?, ssample_m3mg = ?, ssample_m3mn = ?, ssample_m3na = ?, ssample_m3p = ?, ssample_m3s = ?, ssample_m3zn = ?, ssmaple_hp = ?" +
            " WHERE trial_id = ? AND block_id = ? AND plot_id = ? AND ssample_id = ?",
            sample.getSampleCode(), sample.getCollectionDate(), sample.getTrt(), sample.getDepth(), sample.getAnalysisDate(),
            sample.getSsn(), sample.getPh(), sample.getEc(), sample.getM3ai(), sample.getM3b(), sample.getM3ca(), sample.getM3cu(), sample.getM3fe(),
            sample.getM3k(), sample.getM3mg(), sample.getM3mn(), sample.getM3na(), sample.getM3p(), sample.getM3s(), sample.getM3zn(), sample.getHp(),
            sample.getTrialUid(), sample.getBlockId(), sample.getPlotId(), sample.getSampleId());
  }
}
