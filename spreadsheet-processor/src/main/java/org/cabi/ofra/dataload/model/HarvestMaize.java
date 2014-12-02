package org.cabi.ofra.dataload.model;

import java.util.Date;

/**
 * Created by equiros on 11/30/2014.
 */
public class HarvestMaize {
  private String trialUid;
  private int blockId;
  private int plotId;
  private Date harvestDate;
  private int plantCount;
  private int earCount;
  private double earWeight;
  private double grainYield;
  private double stoverYield;
  private double stoverSample;
  private double grainMoisture;
  private Date silkDate;
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

  public int getPlantCount() {
    return plantCount;
  }

  public void setPlantCount(int plantCount) {
    this.plantCount = plantCount;
  }

  public int getEarCount() {
    return earCount;
  }

  public void setEarCount(int earCount) {
    this.earCount = earCount;
  }

  public double getEarWeight() {
    return earWeight;
  }

  public void setEarWeight(double earWeight) {
    this.earWeight = earWeight;
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

  public double getGrainMoisture() {
    return grainMoisture;
  }

  public void setGrainMoisture(double grainMoisture) {
    this.grainMoisture = grainMoisture;
  }

  public Date getSilkDate() {
    return silkDate;
  }

  public void setSilkDate(Date silkDate) {
    this.silkDate = silkDate;
  }

  public double getStoverDryYield() {
    return stoverDryYield;
  }

  public void setStoverDryYield(double stoverDryYield) {
    this.stoverDryYield = stoverDryYield;
  }
}
