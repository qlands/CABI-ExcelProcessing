package org.cabi.ofra.dataload.model;

import java.util.Date;

/**
 * Created by equiros on 11/26/2014.
 */
public class PlotActivity {
  private String trialUniqueId;
  private int blockId;
  private int plotId;
  private int activityId;
  private String activityType;
  private Date activityDate;
  private String activityNotes;

  public String getTrialUniqueId() {
    return trialUniqueId;
  }

  public void setTrialUniqueId(String trialUniqueId) {
    this.trialUniqueId = trialUniqueId;
  }

  public int getBlockId() {
    return blockId;
  }

  public void setBlockId(int blockId) {
    this.blockId = blockId;
  }

  public int getPlotId() {
    return plotId;
  }

  public void setPlotId(int plotId) {
    this.plotId = plotId;
  }

  public int getActivityId() {
    return activityId;
  }

  public void setActivityId(int activityId) {
    this.activityId = activityId;
  }

  public String getActivityType() {
    return activityType;
  }

  public void setActivityType(String activityType) {
    this.activityType = activityType;
  }

  public Date getActivityDate() {
    return activityDate;
  }

  public void setActivityDate(Date activityDate) {
    this.activityDate = activityDate;
  }

  public String getActivityNotes() {
    return activityNotes;
  }

  public void setActivityNotes(String activityNotes) {
    this.activityNotes = activityNotes;
  }
}
