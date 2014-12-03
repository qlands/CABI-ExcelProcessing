package org.cabi.ofra.dataload.db;

import org.cabi.ofra.dataload.model.BlockSoilSample;

/**
 * Created by equiros on 11/23/2014.
 */
public interface IBlockSoilSampleDao extends IDao {
  public boolean existsBlockSoilSample(BlockSoilSample sample);
  public boolean existsBlockSoilSampleById(String trialUid, int blockId, int sampleId);
  public BlockSoilSample findBlockSoilSampleById(String trialUid, int blockId, int sampleId);
  public BlockSoilSample findBlockSoilSampleByCode(String trialUid, int blockId, String sampleCode);
  public void createBlockSoilSample(BlockSoilSample sample);
  public void updateBlockSoilSample(BlockSoilSample sample);
}
