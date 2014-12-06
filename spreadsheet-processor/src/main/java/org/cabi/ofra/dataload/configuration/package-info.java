/**
 * This package holds the configuration information for the template processor. The following is a sample XML from the default
 * (out of the box) template configuration:
 * <pre>
 * {@code
 * <processor-configuration>
<cell-processors>
<cell-processor name="valset" class="org.cabi.ofra.dataload.impl.ValidateAndSet"/>
<cell-processor name="checkPresent" class="org.cabi.ofra.dataload.impl.CheckPresent"/>
<cell-processor name="trialValidator" class="org.cabi.ofra.dataload.impl.TrialValidator"/>
<cell-processor name="trialSoilSampleValidator" class="org.cabi.ofra.dataload.impl.TrialSoilSampleValidator"/>
<cell-processor name="blockValidator" class="org.cabi.ofra.dataload.impl.BlockValidator"/>
<cell-processor name="blockSoilSampleValidator" class="org.cabi.ofra.dataload.impl.BlockSoilSampleValidator"/>
<cell-processor name="plotValidator" class="org.cabi.ofra.dataload.impl.PlotValidator"/>
<cell-processor name="plotPlantSampleValidator" class="org.cabi.ofra.dataload.impl.PlotPlantSampleValidator"/>
<cell-processor name="plotSoilSampleValidator" class="org.cabi.ofra.dataload.impl.PlotSoilSampleValidator"/>
<cell-processor name="harvestValidator" class="org.cabi.ofra.dataload.impl.HarvestValidator"/>
</cell-processors>
<range-processors>
<!-- Trial Processors -->
<range-processor name="trial-blocks-processor" class="org.cabi.ofra.dataload.specific.TrialDefinitionTemplate$BlockRangeProcessor"/>
<!-- Block Processors -->
<range-processor name="block-activities-processor" class="org.cabi.ofra.dataload.specific.BlockTemplates$BlockActivitiesRangeProcessor"/>
<!-- Plot Processors -->
<range-processor name="plot-activities-processor" class="org.cabi.ofra.dataload.specific.PlotTemplates$PlotActivitiesRangeProcessor"/>
<!-- Sample harvest processor -->
<range-processor name="harvest-grasses-dryweight-processor" class="org.cabi.ofra.dataload.specific.HarvestTemplates$PlotHarvestGrassStoverDryRangeProcessor"/>
</range-processors>
<templates>
<!-- Main Trial Definition Template -->
<template name="trial-definition">
<sheets>
<sheet name="Trial" required="true" implementationClass="org.cabi.ofra.dataload.specific.TrialDefinitionTemplate$TrialSheetProcessor">
<cells>
<cell processorReference="valset" location="B23">
<args>
<arg name="variableName" value="trialUniqueId"/>
</args>
</cell>
<cell processorReference="valset" location="B2">
<args>
<arg name="variableName" value="countryCode"/>
</args>
</cell>
</cells>
</sheet>
<sheet name="Blocks" required="true" implementationClass="org.cabi.ofra.dataload.specific.TrialDefinitionTemplate$BlocksSheetProcessor">
<cells>
<cell processorReference="valset" location="B1">
<args>
<arg name="variableName" value="blocksTrialUID"/>
</args>
</cell>
</cells>
<ranges>
<range start="A3" width="12" processorReference="trial-blocks-processor">
<column-bindings>
<column-binding column="0" processorReference="checkPresent">
<args>
<arg name="message" value="Block number must be specified in 'Blocks' sheet"/>
</args>
</column-binding>
<column-binding column="1" processorReference="checkPresent">
<args>
<arg name="message" value="Latitude Point #1 must be specified in 'Blocks' sheet"/>
</args>
</column-binding>
<column-binding column="2" processorReference="checkPresent">
<args>
<arg name="message" value="Longitude Point #1 must be specified in 'Blocks' sheet"/>
</args>
</column-binding>
<column-binding column="9" processorReference="checkPresent">
<args>
<arg name="message" value="Elevation value must be specified in 'Blocks' sheet"/>
</args>
</column-binding>
</column-bindings>
</range>
</ranges>
</sheet>
<sheet name="Plots" required="true" implementationClass="org.cabi.ofra.dataload.specific.TrialDefinitionTemplate$PlotsSheetProcessor">
<cells>
<cell processorReference="valset" location="B1">
<args>
<arg name="variableName" value="plotsTrialUID"/>
</args>
</cell>
</cells>
<ranges>
<range start="A3" width="15" processorReference="trial-plots-processor">
<column-bindings>
<column-binding column="0" processorReference="checkPresent">
<args>
<arg name="message" value="Block number must be specified in 'Plots' sheet"/>
</args>
</column-binding>
</column-bindings>
</range>
</ranges>
</sheet>
</sheets>
</template>
<!-- Block Observations Template -->
<template name="block-observations">
<sheets>
<sheet name="Block-Observations" required="true" implementationClass="org.cabi.ofra.dataload.impl.BaseSheetProcessor">
<ranges>
<range start="A2" width="5" processorReference="block-observations-processor">
<column-bindings>
<column-binding column="0" processorReference="blockValidator">
<args>
<arg name="trialMessage" value="Cell value %s references trial UID %s, which does not exist"/>
<arg name="blockMessage" value="Cell value %s references block %d for trial UID %s, which does not exist"/>
</args>
</column-binding>
</column-bindings>
</range>
</ranges>
</sheet>
</sheets>
</template>
<!-- Block Soil Sample Template -->
<template name="block-soil-sample">
<sheets>
<sheet name="Block Soil Sample" required="true" implementationClass="org.cabi.ofra.dataload.impl.BaseSheetProcessor">
<ranges>
<range start="A2" width="5" processorReference="block-soil-sample-processor">
<column-bindings>
<column-binding column="0" processorReference="blockValidator">
<args>
<arg name="trialMessage" value="Cell value %s references trial UID %s, which does not exist"/>
<arg name="blockMessage" value="Cell value %s references block %d for trial UID %s, which does not exist"/>
</args>
</column-binding>
</column-bindings>
</range>
</ranges>
</sheet>
</sheets>
</template>
<!-- Plot Activities Template -->
<template name="plot-activities">
<sheets>
<sheet name="Plot-Activities" required="true" implementationClass="org.cabi.ofra.dataload.impl.BaseSheetProcessor">
<ranges>
<range start="A2" width="5" processorReference="plot-activities-processor">
<column-bindings>
<column-binding column="0" processorReference="plotValidator">
</column-binding>
</column-bindings>
</range>
</ranges>
</sheet>
</sheets>
</template>
<!-- Plot Plant Sample Result Template -->
<template name="plot-plant-sample-result">
<sheets>
<sheet name="Plot Plant Result" required="true" implementationClass="org.cabi.ofra.dataload.impl.BaseSheetProcessor">
<ranges>
<range start="A2" width="14" processorReference="plot-plant-sample-results-processor">
<column-bindings>
<column-binding column="0" processorReference="plotPlantSampleValidator">
</column-binding>
</column-bindings>
</range>
</ranges>
</sheet>
</sheets>
</template>
<!-- Plot Soil Sample Result Template -->
<template name="plot-soil-sample-result">
<sheets>
<sheet name="Plot Soil Result" required="true" implementationClass="org.cabi.ofra.dataload.impl.BaseSheetProcessor">
<ranges>
<range start="A2" width="14" processorReference="plot-soil-sample-results-processor">
<column-bindings>
<column-binding column="0" processorReference="plotSoilSampleValidator"/>
</column-bindings>
</range>
</ranges>
</sheet>
</sheets>
</template>
<!-- Beans Harvesting Template -->
<template name="plot-harvest-beans">
<sheets>
<sheet name="Beans Harvest" required="true" implementationClass="org.cabi.ofra.dataload.impl.BaseSheetProcessor">
<ranges>
<range start="A2" width="5" processorReference="harvest-legumes-processor" requireAll="true">
<column-bindings>
<column-binding column="0" processorReference="plotValidator"/>
</column-bindings>
</range>
</ranges>
</sheet>
</sheets>
</template>
</templates>
</processor-configuration>
 * }
 * </pre>
 */
package org.cabi.ofra.dataload.configuration;