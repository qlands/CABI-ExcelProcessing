package org.cabi.ofra.dataload.db;

import org.apache.commons.dbcp.BasicDataSource;
import org.cabi.ofra.dataload.db.impl.*;
import org.cabi.ofra.dataload.model.*;
import org.cabi.ofra.dataload.util.Utilities;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

/**
 * Provides access to the database layer, providing multiple database-related operations for the business logic layer
 * Acts a facade to isolate the business layer from the underlying implementation of operations that require
 * database access
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
  private IPlotPlantSampleDao plotPlantSampleDao;
  private IPlotSoilSampleDao plotSoilSampleDao;
  private IHarvestCerealsDao harvestCerealsDao;
  private IHarvestLegumesDao harvestLegumesDao;
  private IHarvestMaizeDao harvestMaizeDao;
  private IHarvestGrassesDao harvestGrassesDao;
  private IHarvestCassavaDao harvestCassavaDao;

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
    plotPlantSampleDao = new PlotPlantSampleDao();
    plotPlantSampleDao.setDataSource(dataSource);
    plotSoilSampleDao = new PlotSoilSampleDao();
    plotSoilSampleDao.setDataSource(dataSource);
    harvestCerealsDao = new HarvestCerealsDao();
    harvestCerealsDao.setDataSource(dataSource);
    harvestLegumesDao = new HarvestLegumesDao();
    harvestLegumesDao.setDataSource(dataSource);
    harvestMaizeDao = new HarvestMaizeDao();
    harvestMaizeDao.setDataSource(dataSource);
    harvestGrassesDao = new HarvestGrassesDao();
    harvestGrassesDao.setDataSource(dataSource);
    harvestCassavaDao = new HarvestCassavaDao();
    harvestCassavaDao.setDataSource(dataSource);
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
    if (!trialDao.existsTrial(t.getTrialUniqueId()))
    {
      trialDao.createTrial(t);
      System.out.println("{}");
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

  public TrialSoilSample findTrialSoilSampleByCode(String trialUid, String sampleCode) {
    return trialSoilSampleDao.findTrialSoilSampleByCode(trialUid, sampleCode);
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

  public BlockSoilSample findBlockSoilSampleByCode(String trialUid, int blockId, String sampleCode) {
    return blockSoilSampleDao.findBlockSoilSampleByCode(trialUid, blockId, sampleCode);
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
    return blockWeatherDao.existsBlockWeatherById(trialUid, blockId, collectionDate);
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

  public PlotPlantSample findPlotPlantSampleByCode(String trialUid, int blockId, int plotId, String sampleCode) {
    return plotPlantSampleDao.findPlotPlantSampleByCode(trialUid, blockId, plotId, sampleCode);
  }

  public void updatePlotPlantSample(PlotPlantSample plotPlantSample) {
    plotPlantSampleDao.updatePlotPlantSample(plotPlantSample);
  }

  public void createOrUpdatePlotPlantSample(PlotPlantSample plotPlantSample) {
    if (plotPlantSampleDao.existsPlotPlantSampleById(plotPlantSample.getTrialUniqueId(), plotPlantSample.getBlockId(), plotPlantSample.getPlotId(), plotPlantSample.getSampleId())) {
      plotPlantSampleDao.updatePlotPlantSample(plotPlantSample);
    }
    else {
      plotPlantSampleDao.createPlotPlantSample(plotPlantSample);
    }
  }

  // Plot Soil Sample
  public PlotSoilSample findPlotSoilSampleByCode(String trialUid, int blockId, int plotId, String sampleCode) {
    return plotSoilSampleDao.findPlotSoilSampleByCode(trialUid, blockId, plotId, sampleCode);
  }

  public void updatePlotSoilSample(PlotSoilSample plotSoilSample) {
    plotSoilSampleDao.updatePlotSoilSample(plotSoilSample);
  }

  public void createOrUpdatePlotSoilSample(PlotSoilSample plotSoilSample) {
    if (plotSoilSampleDao.existsPlotSoilSample(plotSoilSample)) {
      plotSoilSampleDao.updatePlotSoilSample(plotSoilSample);
    }
    else {
      plotSoilSampleDao.createPlotSoilSample(plotSoilSample);
    }
  }

  // Harvest Legumes

  public boolean existsHarvestLegumeById(String trialUid, int blockId, int plotId, Date harvestDate) {
    HarvestLegume harvestLegume = new HarvestLegume();
    harvestLegume.setTrialUid(trialUid);
    harvestLegume.setBlockId(blockId);
    harvestLegume.setPlotId(plotId);
    harvestLegume.setHarvestDate(harvestDate);
    return harvestLegumesDao.existsHarvestLegume(harvestLegume);
  }

  public HarvestLegume findHarvestLegumeById(String trialUid, int blockId, int plotId, Date harvestDate) {
    return harvestLegumesDao.findHarvestLegumeById(trialUid, blockId, plotId, harvestDate);
  }

  public void updateHarvestLegume(HarvestLegume harvestLegume) {
    harvestLegumesDao.updateHarvestLegume(harvestLegume);
  }

  public void createOrUpdateHarvestLegume(HarvestLegume harvestLegume) {
    if (harvestLegumesDao.existsHarvestLegume(harvestLegume)) {
      harvestLegumesDao.updateHarvestLegume(harvestLegume);
    }
    else {
      harvestLegumesDao.createHarvestLegume(harvestLegume);
    }
  }

  // Harvest Cereals

  public boolean existsHarvestCerealById(String trialUid, int blockId, int plotId, Date harvestDate) {
    HarvestCereal harvestCereal = new HarvestCereal();
    harvestCereal.setTrialUid(trialUid);
    harvestCereal.setBlockId(blockId);
    harvestCereal.setPlotId(plotId);
    harvestCereal.setHarvestDate(harvestDate);
    return harvestCerealsDao.existsHarvestCereal(harvestCereal);
  }

  public HarvestCereal findHarvestCerealById(String trialUid, int blockId, int plotId, Date harvestDate) {
    return harvestCerealsDao.findHarvestCerealById(trialUid, blockId, plotId, harvestDate);
  }

  public void updateHarvestCereal(HarvestCereal harvestCereal) {
    harvestCerealsDao.updateHarvestCereal(harvestCereal);
  }

  public void createOrUpdateHarvestCereal(HarvestCereal harvestCereal) {
    if (harvestCerealsDao.existsHarvestCereal(harvestCereal)) {
      harvestCerealsDao.updateHarvestCereal(harvestCereal);
    }
    else {
      harvestCerealsDao.createHarvestCereal(harvestCereal);
    }
  }

  // Harvest Maize

  public boolean existsHarvestMaizeById(String trialUid, int blockId, int plotId, Date harvestDate) {
    HarvestMaize harvestMaize = new HarvestMaize();
    harvestMaize.setTrialUid(trialUid);
    harvestMaize.setBlockId(blockId);
    harvestMaize.setPlotId(plotId);
    harvestMaize.setHarvestDate(harvestDate);
    return harvestMaizeDao.existsHavestMaize(harvestMaize);
  }

  public HarvestMaize findHarvestMaizeById(String trialUid, int blockId, int plotId, Date harvestDate) {
    return harvestMaizeDao.findHarvestMaizeById(trialUid, blockId, plotId, harvestDate);
  }

  public void updateHarvestMaize(HarvestMaize harvestMaize) {
    harvestMaizeDao.updateHarvestMaize(harvestMaize);
  }

  public void createOrUpdateHarvestMaize(HarvestMaize harvestMaize) {
    if (harvestMaizeDao.existsHavestMaize(harvestMaize)) {
      harvestMaizeDao.updateHarvestMaize(harvestMaize);
    }
    else {
      harvestMaizeDao.createHarvestMaize(harvestMaize);
    }
  }

  // Harvest Grasses

  public boolean existsHarvestGrassById(String trialUid, int blockId, int plotId, Date harvestDate) {
    HarvestGrass harvestGrass = new HarvestGrass();
    harvestGrass.setTrialUid(trialUid);
    harvestGrass.setBlockId(blockId);
    harvestGrass.setPlotId(plotId);
    harvestGrass.setHarvestDate(harvestDate);
    return harvestGrassesDao.existsHarvestGrass(harvestGrass);
  }

  public HarvestGrass findHarvestGrassById(String trialUid, int blockId, int plotId, Date harvestDate) {
    return harvestGrassesDao.findHarvestGrassById(trialUid, blockId, plotId, harvestDate);
  }

  public void updateHarvestGrass(HarvestGrass harvestGrass) {
    harvestGrassesDao.updateHarvestGrass(harvestGrass);
  }

  public void createOrUpdateHarvestGrass(HarvestGrass harvestGrass) {
    if (harvestGrassesDao.existsHarvestGrass(harvestGrass)) {
      harvestGrassesDao.updateHarvestGrass(harvestGrass);
    }
    else {
      harvestGrassesDao.createHarvestGrass(harvestGrass);
    }
  }

  // Harvest Cassava

  public boolean existsHarvestCassavaById(String trialUid, int blockId, int plotId, Date harvestDate) {
    HarvestCassava harvestCassava = new HarvestCassava();
    harvestCassava.setTrialUid(trialUid);
    harvestCassava.setBlockId(blockId);
    harvestCassava.setPlotId(plotId);
    harvestCassava.setHarvestDate(harvestDate);
    return harvestCassavaDao.existsHarvestCassava(harvestCassava);
  }

  public HarvestCassava findHarvestCassavaById(String trialUid, int blockId, int plotId, Date harvestDate) {
    return harvestCassavaDao.findHarvestCassavaById(trialUid, blockId, plotId, harvestDate);
  }

  public void updateHarvestCassava(HarvestCassava harvestCassava) {
    harvestCassavaDao.updateHarvestCassava(harvestCassava);
  }

  public void createOrUpdateHarvestCassava(HarvestCassava harvestCassava) {
    if (harvestCassavaDao.existsHarvestCassava(harvestCassava)) {
      harvestCassavaDao.updateHarvestCassava(harvestCassava);
    }
    else {
      harvestCassavaDao.createHarvestCassava(harvestCassava);
    }
  }
}
