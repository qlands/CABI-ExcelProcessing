package org.cabi.ofra.dataload.db.impl;

import org.cabi.ofra.dataload.db.IBlockSoilSampleDao;
import org.cabi.ofra.dataload.model.BlockSoilSample;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by equiros on 11/23/2014.
 */
public class BlockSoilSampleDao extends BaseDao implements IBlockSoilSampleDao {
  @Override
  public boolean existsBlockSoilSample(BlockSoilSample sample) {
    return existsBlockSoilSampleById(sample.getTrialUniqueId(), sample.getBlockId(), sample.getSampleId());
  }

  @Override
  public boolean existsBlockSoilSampleById(String trialUid, int blockId, int sampleId) {
    int count = jdbcTemplate.queryForObject("SELECT trial_id, block_id, ssample_id FROM ofrafertrials.blocksoilsample" +
            " WHERE trial_id = ? AND block_id = ? AND ssample_id = ?", Integer.class,
            trialUid, String.valueOf(blockId), sampleId);
    return count > 0;
  }

  @Override
  public BlockSoilSample findBlockSoilSampleById(String trialUid, int blockId, int sampleId) {
    return jdbcTemplate.queryForObject("SELECT trial_id, block_id, ssample_id, ssample_code, ssample_cdate, ssample_trt, ssample_depth, ssample_adate, " +
                    "ssample_ssn, ssample_ph, ssample_ec, ssample_m3ai, ssample_m3b, ssample_m3ca, ssample_m3cu, ssample_m3fe, ssample_m3k, ssample_m3mg, " +
                    "ssample_m3mn, ssample_m3na, ssample_m3p, ssample_m3s, ssample_m3zn, ssmaple_hp" +
                    " FROM ofrafertrials.blocksoilsample" +
                    " WHERE trial_id = ? AND block_id = ? AND ssample_id = ?",
            new Object[]{trialUid, blockId, sampleId},
            new RowMapper<BlockSoilSample>() {
              @Override
              public BlockSoilSample mapRow(ResultSet resultSet, int i) throws SQLException {
                BlockSoilSample sample = new BlockSoilSample();
                sample.setTrialUniqueId(resultSet.getString(1));
                sample.setBlockId(Integer.valueOf(resultSet.getString(2)));
                sample.setSampleId(resultSet.getInt(3));
                sample.setCode(resultSet.getString(4));
                sample.setCdate(resultSet.getDate(5));
                sample.setTrt(resultSet.getString(6));
                sample.setDepth(resultSet.getString(7));
                sample.setAdate(resultSet.getDate(8));
                sample.setSsn(resultSet.getString(9));
                sample.setPh(resultSet.getDouble(10));
                sample.setEc(resultSet.getDouble(11));
                sample.setM3ai(resultSet.getDouble(12));
                sample.setM3b(resultSet.getDouble(13));
                sample.setM3ca(resultSet.getDouble(14));
                sample.setM3cu(resultSet.getDouble(15));
                sample.setM3fe(resultSet.getDouble(16));
                sample.setM3k(resultSet.getDouble(17));
                sample.setM3mg(resultSet.getDouble(18));
                sample.setM3mn(resultSet.getDouble(19));
                sample.setM3na(resultSet.getDouble(20));
                sample.setM3p(resultSet.getDouble(21));
                sample.setM3s(resultSet.getDouble(22));
                sample.setM3zn(resultSet.getDouble(23));
                sample.setHp(resultSet.getDouble(24));
                return sample;
              }
            });
  }

  @Override
  public void createBlockSoilSample(BlockSoilSample sample) {
    jdbcTemplate.update("INSERT INTO ofrafertrials.blocksoilsample (trial_id, block_id, ssample_id, ssample_code, ssample_cdate, ssample_trt, ssample_depth, " +
            " ssample_adate, ssample_ssn, ssample_ph, ssample_ec, ssample_m3ai, ssample_m3b, ssample_m3ca, ssample_m3cu, ssample_m3fe, " +
            "ssample_m3k, ssample_m3mg, ssample_m3mn, ssample_m3na, ssample_m3p, ssample_m3s, ssample_m3zn, ssmaple_hp) " +
            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            sample.getTrialUniqueId(), String.valueOf(sample.getBlockId()), sample.getSampleId(), sample.getCode(), sample.getCdate(), sample.getTrt(), sample.getDepth(),
            sample.getAdate(), sample.getSsn(), sample.getPh(), sample.getEc(), sample.getM3ai(), sample.getM3b(), sample.getM3ca(), sample.getM3cu(), sample.getM3fe(),
            sample.getM3k(), sample.getM3mg(), sample.getM3mn(), sample.getM3na(), sample.getM3p(), sample.getM3s(), sample.getM3zn(), sample.getHp());
  }

  @Override
  public void updateBlockSoilSample(BlockSoilSample sample) {
    jdbcTemplate.update("UPDATE ofrafertrials.blocksoilsample SET ssample_code = ?, ssample_cdate = ?, ssample_trt = ?, ssample_depth = ?, ssample_adate = ?, " +
            " ssample_ssn = ?, ssample_ph = ?, ssample_ec = ?, ssample_m3ai = ?, ssample_m3b = ?, ssample_m3ca = ?, ssample_m3cu = ?, ssample_m3fe = ?, " +
            " ssample_m3k = ?, ssample_m3mg = ?, ssample_m3mn = ?, ssample_m3na = ?, ssample_m3p = ?, ssample_m3s = ?, ssample_m3zn = ?, ssmaple_hp = ?" +
            "  WHERE trial_id = ? AND block_id = ? AND ssample_id = ?",
            sample.getCode(), sample.getCdate(), sample.getTrt(), sample.getDepth(),
            sample.getAdate(), sample.getSsn(), sample.getPh(), sample.getEc(), sample.getM3ai(), sample.getM3b(), sample.getM3ca(), sample.getM3cu(), sample.getM3fe(),
            sample.getM3k(), sample.getM3mg(), sample.getM3mn(), sample.getM3na(), sample.getM3p(), sample.getM3s(), sample.getM3zn(), sample.getHp(),
            sample.getTrialUniqueId(), String.valueOf(sample.getBlockId()), sample.getSampleId());
  }
}
