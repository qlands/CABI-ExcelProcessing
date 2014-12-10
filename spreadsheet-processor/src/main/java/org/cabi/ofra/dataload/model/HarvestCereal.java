package org.cabi.ofra.dataload.model;

import java.util.Date;

/**
 * Created by equiros on 11/29/2014.
 */
public class HarvestCereal {
  private String trialUid;
  private int blockId;
  private int plotId;
  private Date harvestDate;
  private double headWeight;
  private double grainYield;
  private double stoverYield;
  private double stoverSample;
  private double grainMoist;
  private double stoverDryYield;

  public String getTrialUid() {
    return trialUid;
  }

  public void setTrialUid(String trialUid) {
    this.trialUid = trialUid;
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

  public Date getHarvestDate() {
    return harvestDate;
  }

  public void setHarvestDate(Date harvestDate) {
    this.harvestDate = harvestDate;
  }

  public double getHeadWeight() {
    return headWeight;
  }

  public void setHeadWeight(double headWeight) {
    this.headWeight = headWeight;
  }

  public double getGrainYield() {
    return grainYield;
  }

  public void setGrainYield(double grainYield) {
    this.grainYield = grainYield;
  }

  public double getStoverYield() {
    return stoverYield;
  }

  public void setStoverYield(double stoverYield) {
    this.stoverYield = stoverYield;
  }

  public double getStoverSample() {
    return stoverSample;
  }

  public void setStoverSample(double stoverSample) {
    this.stoverSample = stoverSample;
  }

  public double getGrainMoist() {
    return grainMoist;
  }

  public void setGrainMoist(double grainMoist) {
    this.grainMoist = grainMoist;
  }

  public double getStoverDryYield() {
    return stoverDryYield;
  }

  public void setStoverDryYield(double stoverDryYield) {
    this.stoverDryYield = stoverDryYield;
  }
}
