package org.cabi.ofra.dataload.db;

/**
 * Created by equiros on 12/10/2014.
 */
public interface IActivityDao extends IDao {
  public boolean existsActivity(String activityCode);
}
