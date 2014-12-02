package org.cabi.ofra.dataload.model;

import java.util.Date;

/**
 * Created by equiros on 11/30/2014.
 */
public class HarvestCassava {
  private String trialUid;
  private int blockId;
  private int plotId;
  private Date harvestDate;
  private int plantCount;
  private int tuberCount;
  private double yield;
  private double sample;
  private double sampleDry;
  private double dryTuber;

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

  public int getTuberCount() {
    return tuberCount;
  }

  public void setTuberCount(int tuberCount) {
    this.tuberCount = tuberCount;
  }

  public double getYield() {
    return yield;
  }

  public void setYield(double yield) {
    this.yield = yield;
  }

  public double getSample() {
    return sample;
  }

  public void setSample(double sample) {
    this.sample = sample;
  }

  public double getSampleDry() {
    return sampleDry;
  }

  public void setSampleDry(double sampleDry) {
    this.sampleDry = sampleDry;
  }

  public double getDryTuber() {
    return dryTuber;
  }

  public void setDryTuber(double dryTuber) {
    this.dryTuber = dryTuber;
  }
}
