package org.cabi.ofra.dataload.model;

import java.util.Date;

/**
 * Created by equiros on 11/29/2014.
 */
public class HarvestLegume {
  private String trialUid;
  private int blockId;
  private int plotId;
  private Date harvestDate;
  private int plantCount;
  private double bioMass;
  private double yield;
  private Date flowerDate;
  private double grainMoisture;

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

  public double getBioMass() {
    return bioMass;
  }

  public void setBioMass(double bioMass) {
    this.bioMass = bioMass;
  }

  public double getYield() {
    return yield;
  }

  public void setYield(double yield) {
    this.yield = yield;
  }

  public Date getFlowerDate() {
    return flowerDate;
  }

  public void setFlowerDate(Date flowerDate) {
    this.flowerDate = flowerDate;
  }

  public double getGrainMoisture() {
    return grainMoisture;
  }

  public void setGrainMoisture(double grainMoisture) {
    this.grainMoisture = grainMoisture;
  }
}
