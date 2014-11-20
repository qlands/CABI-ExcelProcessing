package org.cabi.ofra.dataload.model;

import java.util.Date;

/**
 * Created by equiros on 11/19/2014.
 */
public class BlockObservation {
  private String trialUniqueId;
  private int blockNumber;
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

  public int getBlockNumber() {
    return blockNumber;
  }

  public void setBlockNumber(int blockNumber) {
    this.blockNumber = blockNumber;
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
