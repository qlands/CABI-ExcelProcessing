package org.cabi.ofra.dataload.impl;

import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.model.ICellProcessor;
import org.cabi.ofra.dataload.util.Utilities;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by equiros on 12/2/2014.
 */
public class HarvestValidator extends PlotValidator implements ICellProcessor {
  private static final String KEY_HARVESTTYPE = "harvestType";
  @Override
  protected void processPostPlotSegment(String trialUid, int blockId, int plotId, String segment) throws ProcessorException {
    Date harvestDate;
    try {
      harvestDate = Utilities.parseDate(segment);
    }
    catch (ParseException e) {
      throw new ProcessorException(e);
    }
    if (!arguments.containsKey(KEY_HARVESTTYPE)) {
      throw new ProcessorException("The harvest type must be specified in Harvest Validator");
    }
    switch (((String) arguments.get(KEY_HARVESTTYPE)).toLowerCase()) {
      case "legume":
        if (!databaseService.existsHarvestLegumeById(trialUid, blockId, plotId, harvestDate)) {
          throw new ProcessorException(String.format("Legume harvest identified by Trial %s, Block %d, Plot %d and Date %4$tY-%4$tm-%4$td does not exist!", trialUid, blockId, plotId, harvestDate));
        }
        break;
      case "cereal":
        if (!databaseService.existsHarvestCerealById(trialUid, blockId, plotId, harvestDate)) {
          throw new ProcessorException(String.format("Cereal harvest identified by Trial %s, Block %d, Plot %d and Date %4$tY-%4$tm-%4$td does not exist!", trialUid, blockId, plotId, harvestDate));
        }
        break;
      case "grass":
        if (!databaseService.existsHarvestGrassById(trialUid, blockId, plotId, harvestDate)) {
          throw new ProcessorException(String.format("Grass harvest identified by Trial %s, Block %d, Plot %d and Date %4$tY-%4$tm-%4$td does not exist!", trialUid, blockId, plotId, harvestDate));
        }
        break;
      case "maize":
        if (!databaseService.existsHarvestMaizeById(trialUid, blockId, plotId, harvestDate)) {
          throw new ProcessorException(String.format("Maize harvest identified by Trial %s, Block %d, Plot %d and Date %4$tY-%4$tm-%4$td does not exist!", trialUid, blockId, plotId, harvestDate));
        }
        break;
      case "cassava":
        if (!databaseService.existsHarvestCassavaById(trialUid, blockId, plotId, harvestDate)) {
          throw new ProcessorException(String.format("Cassava harvest identified by Trial %s, Block %d, Plot %d and Date %4$tY-%4$tm-%4$td does not exist!", trialUid, blockId, plotId, harvestDate));
        }
        break;
    }
  }
}
