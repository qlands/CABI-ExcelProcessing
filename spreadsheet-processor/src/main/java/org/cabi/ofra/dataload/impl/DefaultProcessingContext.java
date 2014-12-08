package org.cabi.ofra.dataload.impl;

import org.cabi.ofra.dataload.db.DatabaseService;
import org.cabi.ofra.dataload.model.ICellProcessor;
import org.cabi.ofra.dataload.model.IProcessingContext;
import org.cabi.ofra.dataload.model.IRangeProcessor;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Default implementation of the {@link org.cabi.ofra.dataload.model.IProcessingContext}
 */
public class DefaultProcessingContext implements IProcessingContext {
  private Map<String, Serializable> context;
  private Map<String, ICellProcessor> cellProcessors = Collections.EMPTY_MAP;
  private Map<String, IRangeProcessor> rangeProcessors = Collections.EMPTY_MAP;
  private DatabaseService databaseService;
  private String user;
  private String ckanorg;
  private String trialPublic;

  public DefaultProcessingContext(Map<String, ICellProcessor> cellProcessors, Map<String, IRangeProcessor> rangeProcessors, DatabaseService databaseService, String user, String ckanorg, String trialPublic) {
    this.cellProcessors = cellProcessors;
    this.rangeProcessors = rangeProcessors;
    this.databaseService = databaseService;
    context = new HashMap<>();
    this.user = user;
    this.ckanorg = ckanorg;
    this.trialPublic = trialPublic;
  }

  @Override
  public void set(String name, Serializable value) {
    context.put(name, value);
  }

  @Override
  public Serializable get(String name) {
    return context.get(name);
  }

  @Override
  public Map<String, ICellProcessor> getCellProcessors() {
    return cellProcessors;
  }

  @Override
  public Map<String, IRangeProcessor> getRangeProcessors() {
    return rangeProcessors;
  }

  @Override
  public DatabaseService getDatabaseService() {
    return databaseService;
  }

  @Override
  public String getUser() {
    return user;
  }

  @Override
  public String getCkanOrganization() {
      return ckanorg;
  }

  @Override
  public String getTrialPublic() {return trialPublic;}

}
