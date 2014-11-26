package org.cabi.ofra.dataload.db;

import org.apache.commons.dbcp.BasicDataSource;
import org.cabi.ofra.dataload.db.impl.*;
import org.cabi.ofra.dataload.model.*;
import org.cabi.ofra.dataload.util.Utilities;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public class DatabaseService {
  private BasicDataSource dataSource;
  private ITrialDao trialDao;
  private IBlockDao blockDao;
  private ICropDao cropDao;
  private ICountryDao countryDao;
  private IPlotDao plotDao;
  private ITrialSoilSampleDao trialSoilSampleDao;
  private IBlockActivityDao blockActivityDao;
  private IBlockObservationDao blockObservationDao;
  private IBlockSoilSampleDao blockSoilSampleDao;
  private IBlockWeatherDao blockWeatherDao;
  private IPlotActivityDao plotActivityDao;
  private IPlotObservationDao plotObservationDao;

  public DatabaseService() {
    dataSource = new BasicDataSource();
  }

  public void initialize(String propertiesFile) throws IOException {
    initializeDataSource(propertiesFile);
    initializeDaos();
  }

  private void initializeDaos() {
    trialDao = new TrialDao();
    trialDao.setDataSource(dataSource);
    blockDao = new BlockDao();
    blockDao.setDataSource(dataSource);
    cropDao = new CropDao();
    cropDao.setDataSource(dataSource);
    countryDao = new CountryDao();
    countryDao.setDataSource(dataSource);
    plotDao = new PlotDao();
    plotDao.setDataSource(dataSource);
    trialSoilSampleDao = new TrialSoilSampleDao();
    trialSoilSampleDao.setDataSource(dataSource);
    blockActivityDao = new BlockActivityDao();
    blockActivityDao.setDataSource(dataSource);
    blockObservationDao = new BlockObservationDao();
    blockObservationDao.setDataSource(dataSource);
    blockSoilSampleDao = new BlockSoilSampleDao();
    blockSoilSampleDao.setDataSource(dataSource);
    blockWeatherDao = new BlockWeatherDao();
    blockWeatherDao.setDataSource(dataSource);
    plotActivityDao = new PlotActivityDao();
    plotActivityDao.setDataSource(dataSource);
    plotObservationDao = new PlotObservationDao();
    plotObservationDao.setDataSource(dataSource);
  }

  private void initializeDataSource(String propertiesFile) throws IOException {
    Properties props = Utilities.loadDatabaseProperties(propertiesFile);
    dataSource.setDriverClassName(props.getProperty("database.driver"));
    dataSource.setUrl(props.getProperty("database.url"));
    dataSource.setUsername(props.getProperty("database.username"));
    dataSource.setPassword(props.getProperty("database.password"));
  }

  // Trials

  public boolean existsTrialByUniqueId(String uid) {
    return trialDao.existsTrial(uid);
  }

  public void createOrUpdateTrial(Trial t) {
    if (!trialDao.existsTrial(t.getTrialUniqueId())) {
      trialDao.createTrial(t);
    }
    else {
      trialDao.updateTrial(t);
    }
  }

  // Blocks

  public boolean existsBlockById(String trialUid, int blockId) {
    return blockDao.existsBlockById(trialUid, blockId);
  }

  public void createOrUpdateBlock(Block b) {
    if (!blockDao.existsBlock(b)) {
      blockDao.createBlock(b);
    }
    else {
      blockDao.updateBlock(b);
    }
  }

  // Utility routines for Crops and Countries catalogs

  public boolean existsCrop(String cropId) {
    return cropDao.existsCrop(cropId);
  }

  public boolean existsCountry(String countryCode) {
    return countryDao.existsCountry(countryCode);
  }

  // Plots

  public boolean existsPlotById(String trialUid, int blockId, int plotId) {
    return plotDao.existsPlotById(trialUid, blockId, plotId);
  }

  public void createOrUpdatePlot(Plot p) {
    if (!plotDao.existsPlot(p)) {
      plotDao.createPlot(p);
    }
    else {
      plotDao.updatePlot(p);
    }
  }

  // Soil Samples

  public TrialSoilSample findTrialSoilSampleById(String trialUid, int sampleId) {
    return trialSoilSampleDao.findTrialSoilSampleById(trialUid, sampleId);
  }

  public void updateTrialSoilSample(TrialSoilSample trialSoilSample) {
    trialSoilSampleDao.updateTrialSoilSample(trialSoilSample);
  }

  public void createOrUpdateTrialSoilSample(TrialSoilSample trialSoilSample) {
    if (trialSoilSampleDao.existsTrialSoilSample(trialSoilSample)) {
      trialSoilSampleDao.updateTrialSoilSample(trialSoilSample);
    }
    else {
      trialSoilSampleDao.createTrialSoilSample(trialSoilSample);
    }
  }

  // Block Activities

  public void createOrUpdateBlockActivity(BlockActivity blockActivity) {
    BlockActivity activity = blockActivityDao.findBlockActivityById(blockActivity.getTrialUniqueId(), String.valueOf(blockActivity.getBlockNumber()), blockActivity.getActivityNumber());
    if (activity == null) {
      blockActivityDao.createBlockActivity(blockActivity);
    }
    else {
      activity.setAcivityType(blockActivity.getAcivityType());
      activity.setActivityDate(blockActivity.getActivityDate());
      activity.setActivityNotes(blockActivity.getActivityNotes());
      blockActivityDao.updateBlockActivity(activity);
    }
  }

  // Block Observations

  public void createOrUpdateBlockObservation(BlockObservation blockObservation) {
    BlockObservation observation = blockObservationDao.findBlockObservationById(blockObservation.getTrialUniqueId(), blockObservation.getBlockNumber(),
            blockObservation.getObservationId());
    if (observation == null) {
      blockObservationDao.createBlockObservation(blockObservation);
    }
    else {
      observation.setObservationDate(blockObservation.getObservationDate());
      observation.setObservationNotes(blockObservation.getObservationNotes());
      observation.setObservationDiseaseRelated(blockObservation.isObservationDiseaseRelated());
      blockObservationDao.updateBlockObservation(observation);
    }
  }

  // Block Soil Samples

  public BlockSoilSample findBlockSoilSampleById(String trialUid, int blockId, int sampleId) {
    return blockSoilSampleDao.findBlockSoilSampleById(trialUid, blockId, sampleId);
  }

  public void updateBlockSoilSample(BlockSoilSample blockSoilSample) {
    blockSoilSampleDao.updateBlockSoilSample(blockSoilSample);
  }

  public void createOrUpdateBlockSoilSample(BlockSoilSample blockSoilSample) {
    if (blockSoilSampleDao.existsBlockSoilSample(blockSoilSample)) {
      blockSoilSampleDao.updateBlockSoilSample(blockSoilSample);
    }
    else {
      blockSoilSampleDao.createBlockSoilSample(blockSoilSample);
    }
  }

  // Block Weather Data
  public boolean findBlockWeatherById(String trialUid, int blockId, Date collectionDate) {
    return blockWeatherDao.findBlockWeatherById(trialUid, blockId, collectionDate);
  }

  public void createOrUpdateBlockWeather(BlockWeather blockWeather) {
    if (findBlockWeatherById(blockWeather.getTrialUniqueId(), blockWeather.getBlockId(), blockWeather.getCollectionDate())) {
      blockWeatherDao.updateBlockWeather(blockWeather);
    }
    else {
      blockWeatherDao.createBlockWeather(blockWeather);
    }
  }

  // Plot Activity
  public void createOrUpdatePlotActivity(PlotActivity activity) {
    if (plotActivityDao.existsActivityById(activity.getTrialUniqueId(), activity.getBlockId(), activity.getPlotId(), activity.getActivityId())) {
      plotActivityDao.updateActivity(activity);
    }
    else {
      plotActivityDao.createActivity(activity);
    }
  }

  // Plot Observations
  public void createOrUpdatePlotObservation(PlotObservation observation) {
    if (plotObservationDao.existsObservationById(observation.getTrialUniqueId(), observation.getBlockId(), observation.getPlotId(), observation.getObservationId())) {
      plotObservationDao.updateObservation(observation);
    }
    else {
      plotObservationDao.createObservation(observation);
    }
  }
}
