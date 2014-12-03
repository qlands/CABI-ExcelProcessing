package org.cabi.ofra.dataload.model;

import java.util.Date;

/**
 * Created by equiros on 11/26/2014.
 */
public class PlotObservation {
  private String trialUniqueId;
  private int blockId;
  private int plotId;
  private int observationId;
  private Date observationDate;
  private String observationNotes;
  private boolean observationDiseaseRelated;

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

  public int getObservationId() {
    return observationId;
  }

  public void setObservationId(int observationId) {
    this.observationId = observationId;
  }

  public Date getObservationDate() {
    return observationDate;
  }

  public void setObservationDate(Date observationDate) {
    this.observationDate = observationDate;
  }

  public String getObservationNotes() {
    return observationNotes;
  }

  public void setObservationNotes(String observationNotes) {
    this.observationNotes = observationNotes;
  }

  public boolean isObservationDiseaseRelated() {
    return observationDiseaseRelated;
  }

  public void setObservationDiseaseRelated(boolean observationDiseaseRelated) {
    this.observationDiseaseRelated = observationDiseaseRelated;
  }
}
