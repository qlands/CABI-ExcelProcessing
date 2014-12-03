package org.cabi.ofra.dataload.db.impl;

import org.cabi.ofra.dataload.db.IBlockActivityDao;
import org.cabi.ofra.dataload.model.BlockActivity;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by equiros on 11/19/2014.
 */
public class BlockActivityDao extends BaseDao implements IBlockActivityDao {
  @Override
  public BlockActivity findBlockActivityById(String trialId, String blockId, int activityId) {
    try {
      return jdbcTemplate.queryForObject("SELECT trial_id, block_id, acty_id, actytype_id, pacty_date, pacty_notes " +
                      " FROM ofrafertrials.blockactivity" +
                      " WHERE trial_id = ? AND " +
                      "block_id = ? AND " +
                      "acty_id = ?", new Object[]{trialId, blockId, activityId},
              (resultSet, i) -> {
                BlockActivity blockActivity = new BlockActivity();
                blockActivity.setTrialUniqueId(resultSet.getString(1));
                blockActivity.setBlockNumber(Integer.valueOf(resultSet.getString(2)));
                blockActivity.setActivityNumber(resultSet.getInt(3));
                blockActivity.setAcivityType(resultSet.getString(4));
                blockActivity.setActivityDate(resultSet.getDate(5));
                blockActivity.setActivityNotes(resultSet.getString(6));
                return blockActivity;
              });
    }
    catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Override
  public void createBlockActivity(BlockActivity blockActivity) {
    jdbcTemplate.update("INSERT INTO ofrafertrials.blockactivity(trial_id, block_id, acty_id, actytype_id, pacty_date, pacty_notes ) " +
            "VALUES ( ?,  ?,  ?,  ?,  ?,  ?)", blockActivity.getTrialUniqueId(), blockActivity.getBlockNumber(), String.valueOf(blockActivity.getActivityNumber()),
            blockActivity.getAcivityType(), blockActivity.getActivityDate(), blockActivity.getActivityNotes());
  }

  @Override
  public void updateBlockActivity(BlockActivity blockActivity) {
    jdbcTemplate.update("UPDATE ofrafertrials.blockactivity SET actytype_id = ?, pacty_date = ?, pacty_notes = ? " +
            "WHERE trial_id = ? AND block_id = ? AND actytype_id = ?", blockActivity.getTrialUniqueId(), String.valueOf(blockActivity.getBlockNumber()),
            blockActivity.getActivityNumber());
  }
}
