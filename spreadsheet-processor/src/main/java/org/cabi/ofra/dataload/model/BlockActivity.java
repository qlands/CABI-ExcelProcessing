package org.cabi.ofra.dataload.model;

import java.util.Date;

/**
 * Created by equiros on 11/18/2014.
 */
public class BlockActivity {
  private String trialUniqueId;
  private int blockNumber;
  private int activityNumber;
  private Date activityDate;
  private String acivityType;
  private String activityNotes;

  public String getTrialUniqueId() {
    return trialUniqueId;
  }

  public void setTrialUniqueId(String trialUniqueId) {
    this.trialUniqueId = trialUniqueId;
  }

  public int getBlockNumber() {
    return blockNumber;
  }

  public void setBlockNumber(int blockNumber) {
    this.blockNumber = blockNumber;
  }

  public int getActivityNumber() {
    return activityNumber;
  }

  public void setActivityNumber(int activityNumber) {
    this.activityNumber = activityNumber;
  }

  public Date getActivityDate() {
    return activityDate;
  }

  public void setActivityDate(Date activityDate) {
    this.activityDate = activityDate;
  }

  public String getAcivityType() {
    return acivityType;
  }

  public void setAcivityType(String acivityType) {
    this.acivityType = acivityType;
  }

  public String getActivityNotes() {
    return activityNotes;
  }

  public void setActivityNotes(String activityNotes) {
    this.activityNotes = activityNotes;
  }
}
