package org.cabi.ofra.dataload.db.impl;

import org.cabi.ofra.dataload.db.IActivityDao;

/**
 * Created by equiros on 12/10/2014.
 */
public class ActivityDao extends BaseDao implements IActivityDao {
  @Override
  public boolean existsActivity(String activityCode) {
    int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ofrafertrials.lkpactytype WHERE actytype_id = ?", Integer.class, activityCode);
    return count > 0;
  }
}
