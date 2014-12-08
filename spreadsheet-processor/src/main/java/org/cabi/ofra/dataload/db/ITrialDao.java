package org.cabi.ofra.dataload.db;

import org.cabi.ofra.dataload.model.Trial;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * DAO object to manipulate trial data
 */
public interface ITrialDao extends IDao {
  public void createTrial(Trial trial);
  public Trial getTrialById(String trialUniqueId);
  public void updateTrial(Trial trial);
  public boolean existsTrial(String trialUniqueId);
}
