package org.cabi.ofra.dataload.impl;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.configuration.SheetRangeColumnBindingConfiguration;
import org.cabi.ofra.dataload.configuration.SheetRangeConfiguration;
import org.cabi.ofra.dataload.event.IEventCollector;
import org.cabi.ofra.dataload.model.ICellProcessor;
import org.cabi.ofra.dataload.model.IProcessingContext;
import org.cabi.ofra.dataload.model.IRangeProcessor;
import org.cabi.ofra.dataload.util.Utilities;

import java.util.List;
import java.util.Map;

/**
 * Abstract base class for range processors. Provides generic null-checking logic, and prepares and calls cell processors
 * associated to columns (via {@link org.cabi.ofra.dataload.configuration.SheetRangeColumnBindingConfiguration}
 */
public abstract class AbstractRangeProcessor extends AbstractProcessor implements IRangeProcessor {
  @Override
  public void processRow(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration) throws ProcessorException {
    int i = 0;
    Map<Integer, SheetRangeColumnBindingConfiguration> bindings = rangeConfiguration.getColumnBindings();
    for (Cell cell : row) {
      // validates that for 'requireAll' ranges, there are no blank cells
      if (Utilities.isBlank(cell) && rangeConfiguration.isRequireAll()) {
        throw new ProcessorException(String.format("Cell at position %d is null in range ", i));
      }
      // obtains the associated column binding for the column (index based)
      SheetRangeColumnBindingConfiguration binding = bindings.get(i);
      if (binding != null) {
        // if found, processes the binding and calls the associated processor
        processBinding(context, binding, cell, i, eventCollector);
      }
      i++;
    }
    process(context, row, eventCollector, rangeConfiguration);
  }

  /**
   * This method must be provided by implementing classes to do actual range processing
   * @param context The current {@link org.cabi.ofra.dataload.model.IProcessingContext} during the processing run
   * @param row The row to process
   * @param eventCollector The {@link org.cabi.ofra.dataload.event.IEventCollector} for collecting processing events
   * @param rangeConfiguration The {@link org.cabi.ofra.dataload.configuration.SheetRangeConfiguration} object corresponding to the {@link org.apache.poi.ss.usermodel.Sheet} containing the range
   * @throws ProcessorException
   */
  protected abstract void process(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration) throws ProcessorException;

  // Processes the column binding, resetting the processor, setting the arguments and calling the appropriate method
  private void processBinding(IProcessingContext context, SheetRangeColumnBindingConfiguration binding, Cell cell, int columnIndex, IEventCollector eventCollector) throws ProcessorException {
    // obtains the associated Cell Processor
    ICellProcessor processor = context.getCellProcessors().get(binding.getProcessorReference());
    if (processor != null) {
      // if found, reset its internal state
      processor.reset();
      // set the arguments according to configuration
      for (Map.Entry<String, String> e : binding.getArguments().entrySet()) {
        processor.setArgument(e.getKey(), e.getValue());
      }
      // and call the method to process the cell
      processor.processCell(context, new CellReference(cell.getRowIndex(), cell.getColumnIndex()), cell, eventCollector);
    }
    else {
      throw new ProcessorException(String.format("Processor reference %s not found for binding on column number %d", binding.getProcessorReference(), columnIndex));
    }
  }
}
