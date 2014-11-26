package org.cabi.ofra.dataload.model;

import java.util.Date;

/**
 * Created by equiros on 11/24/2014.
 */
public class BlockWeather {
  private String trialUniqueId;
  private int blockId;
  private Date collectionDate;
  private double rainfall;
  private double maxtemp;
  private double mintemp;
  private double radiation;
  private double wind;
  private double relhumid;

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

  public Date getCollectionDate() {
    return collectionDate;
  }

  public void setCollectionDate(Date collectionDate) {
    this.collectionDate = collectionDate;
  }

  public double getRainfall() {
    return rainfall;
  }

  public void setRainfall(double rainfall) {
    this.rainfall = rainfall;
  }

  public double getMaxtemp() {
    return maxtemp;
  }

  public void setMaxtemp(double maxtemp) {
    this.maxtemp = maxtemp;
  }

  public double getMintemp() {
    return mintemp;
  }

  public void setMintemp(double mintemp) {
    this.mintemp = mintemp;
  }

  public double getRadiation() {
    return radiation;
  }

  public void setRadiation(double radiation) {
    this.radiation = radiation;
  }

  public double getWind() {
    return wind;
  }

  public void setWind(double wind) {
    this.wind = wind;
  }

  public double getRelhumid() {
    return relhumid;
  }

  public void setRelhumid(double relhumid) {
    this.relhumid = relhumid;
  }
}
