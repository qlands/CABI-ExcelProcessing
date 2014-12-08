package org.cabi.ofra.dataload.db.impl;

import org.cabi.ofra.dataload.db.ITrialDao;
import org.cabi.ofra.dataload.model.Trial;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public class TrialDao extends BaseDao implements ITrialDao {
  @Override
  public boolean existsTrial(String trialUniqueId) {
    int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ofrafertrials.trial WHERE trial_id = ?", Integer.class, trialUniqueId);
    return count > 0;
  }

  @Override
  public void createTrial(Trial trial) {
    jdbcTemplate.update("INSERT INTO ofrafertrials.trial(trial_id,trial_cnty,trial_region,trial_district,trial_village,trial_frmocentre,trial_lrserch,trial_fldassi,trial_fldassm,trial_crop1," +
                        "                  trial_crop2,trial_crop3,trial_lati,trial_long,trial_user,trial_year,trial_season,trial_date,trial_ckanorg,trial_public) " +
                        "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
            trial.getTrialUniqueId(), trial.getCountry(), trial.getRegionName(), trial.getDistrictName(), trial.getVillageName(), trial.getFarmerOrCentre(),
            trial.getLeadResearcher(), trial.getFieldAssistantName(), trial.getFieldAssistantTelephone(), trial.getCropOne(),
            trial.getCropTwo(), trial.getCropThree(), trial.getLat(), trial.getLng(), trial.getUser(), trial.getYear(),trial.getSeason(),trial.getTrialDate(),trial.getCkanOrganization(),trial.getTrialPublic());
  }

  @Override
  public Trial getTrialById(String trialUniqueId) {
    return jdbcTemplate.queryForObject("select trial_id, trial_cnty, trial_region, trial_district, trial_village, trial_frmocentre, trial_lrserch, trial_fldassi, trial_fldassm, trial_crop1," +
                                       "       trial_crop2, trial_crop3, trial_lati, trial_long, trial_user, trial_year, trial_season, trial_date, trial_ckanorg, trial_public" +
                                       "  from ofrafertrials.trial where trial_id = ?", new Object[] {trialUniqueId},
            (resultSet, i) -> {
              Trial t = new Trial();
              t.setTrialUniqueId(resultSet.getString(1));
              t.setCountry(resultSet.getString(2));
              t.setRegionName(resultSet.getString(3));
              t.setDistrictName(resultSet.getString(4));
              t.setVillageName(resultSet.getString(5));
              t.setFarmerOrCentre(resultSet.getString(6));
              t.setLeadResearcher(resultSet.getString(7));
              t.setFieldAssistantName(resultSet.getString(8));
              t.setFieldAssistantTelephone(resultSet.getString(9));
              t.setCropOne(resultSet.getString(10));
              t.setCropTwo(resultSet.getString(11));
              t.setCropThree(resultSet.getString(12));
              t.setLat(resultSet.getFloat(13));
              t.setLng(resultSet.getFloat(14));
              t.setUser(resultSet.getString(15));
              t.setYear(resultSet.getInt(16));
              t.setSeason(resultSet.getString(17));
              t.setTrialDate(resultSet.getString(18));
              t.setCkanOrganization(resultSet.getString(19));
              t.setTrialPublic(resultSet.getString(20));
              return t;
            });
  }

  @Override
  public void updateTrial(Trial trial) {
    //We modify just variables that are not part of the key. Otherwise we might need to create the key again.
    jdbcTemplate.update("UPDATE ofrafertrials.trial SET trial_frmocentre = ? ,trial_lrserch = ? ," +
                        "trial_fldassi = ? ,trial_fldassm = ? ,trial_lati = ? ,trial_long = ? ," +
                        "trial_date = ?, trial_public = ? WHERE trial_id = ?",
            trial.getFarmerOrCentre(), trial.getLeadResearcher(),
            trial.getFieldAssistantName(), trial.getFieldAssistantTelephone(), trial.getLat(), trial.getLng(),
            trial.getTrialDate(),trial.getTrialPublic(),trial.getTrialUniqueId());
  }
}
