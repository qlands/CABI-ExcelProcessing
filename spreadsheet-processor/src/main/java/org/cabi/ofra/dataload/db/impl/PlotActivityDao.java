package org.cabi.ofra.dataload.db.impl;

import org.cabi.ofra.dataload.db.IPlotActivityDao;
import org.cabi.ofra.dataload.model.PlotActivity;

/**
 * Created by equiros on 11/26/2014.
 */
public class PlotActivityDao extends BaseDao implements IPlotActivityDao {
  @Override
  public boolean existsActivityById(String trialUid, int blockId, int plotId, int activityId) {
    int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ofrafertrials.plotactivity" +
            " WHERE trial_id = ? AND block_id = ? AND plot_id = ? AND acty_id = ?", Integer.class,
            new Object[] {trialUid, blockId, plotId, activityId});
    return count > 0;
  }

  @Override
  public void createActivity(PlotActivity activity) {
    jdbcTemplate.update("INSERT INTO ofrafertrials.plotactivity (trial_id, block_id, plot_id, acty_id, actytype_id, pacty_date, pacty_notes) VALUES (?, ?, ?, ?, ?, ?, ?)",
            activity.getTrialUniqueId(), activity.getBlockId(), activity.getPlotId(), activity.getActivityId(), activity.getActivityType(),
            activity.getActivityDate(), activity.getActivityNotes());
  }

  @Override
  public void updateActivity(PlotActivity activity) {
      jdbcTemplate.update("UPDATE ofrafertrials.plotactivity SET actytype_id = ?, pacty_date = ?, pacty_notes = ?" +
              "WHERE trial_id = ? AND block_id = ? AND plot_id = ? AND acty_id = ?",
              activity.getActivityType(), activity.getActivityDate(), activity.getActivityNotes(),
              activity.getTrialUniqueId(), activity.getBlockId(), activity.getPlotId(), activity.getActivityId());
  }
}
