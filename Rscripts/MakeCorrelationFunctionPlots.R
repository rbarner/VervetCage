
#Load in Whole-Genome Metagenome Functions
WGSData1=read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/naiveWGSblast/WGSLevel1.txt");
WGSData2=read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/naiveWGSblast/WGSLevel2.txt");

#Load in Picrust (Unnormalized) Predicted Functions
picrustData1=read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/picrust/parsedTables/picrustLevel1.txt");
picrustData2=read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/picrust/parsedTables/picrustLevel2.txt");

#Load in Picrust (Normalized) Prediction Functions
picrustDataNorm1=read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/picrust/parsedTables/normalized/picrustLevel1.txt");
picrustDataNorm2=read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/picrust/parsedTables/normalized/picrustLevel2.txt");

#Reorder Functions in Alphebetical Order (Optional Step; this just makes decoding bugs easier for me)
WGSData1=WGSData1[order(WGSData1[,1]),];
WGSData2=WGSData2[order(WGSData2[,1]),];

picrustData1=picrustData1[order(picrustData1[,1]),];
picrustData2=picrustData2[order(picrustData2[,1]),];

picrustDataNorm1=picrustDataNorm1[order(picrustDataNorm1[,1]),];
picrustDataNorm2=picrustDataNorm2[order(picrustDataNorm2[,1]),];

#Find the picrust functions in common with whole genome metagenome functions (at Level1 and Level2 they should all be in common)
commonFunctionsPicrust1=intersect(picrustData1$Function,WGSData1$Function);
commonFunctionsPicrust2=intersect(picrustData2$Function,WGSData2$Function);

commonFunctionsPicrustNorm1=intersect(picrustDataNorm1$Function,WGSData1$Function);
commonFunctionsPicrustNorm2=intersect(picrustDataNorm2$Function,WGSData2$Function);

#Subset Whole Genome Metagenome functions so that only functions in common with picrust(raw data is ) 
subWGSData1=subset(WGSData1, Function %in% commonFunctionsPicrust1);
subWGSData2=subset(WGSData2, Function %in% commonFunctionsPicrust2);

subPicrustData1=subset(picrustData1, Function %in% commonFunctionsPicrust1);
subPicrustData2=subset(picrustData2, Function %in% commonFunctionsPicrust2);

subPicrustDataNorm1=subset(picrustDataNorm1, Function %in% commonFunctionsPicrustNorm1);
subPicrustDataNorm2=subset(picrustDataNorm2, Function %in% commonFunctionsPicrustNorm2);

#Create vectors that will be populated with each samples correlation of WGS function counts and Picrust function counts

makeCorrelationTables = function(plotFile, wgsData, picrustData, picrustNormData,sampleSize)
{
  correlationsPicrust=numeric(sampleSize);
  correlationsPicrustNorm=numeric(sampleSize);
  correlationsRandomPicrust=numeric(sampleSize);
  randomPicrustData=picrustNormData[sample(nrow(picrustNormData)),]
  
	pdf(plotFile);
	for(i in 2: sampleSize+1)
	{
		corPicrust=cor(wgsData[,i],picrustData[,i],method="spearman");
		corPicrustNorm=cor(wgsData[,i],picrustNormData[,i],method="spearman");
    corPicrustRandom=cor(wgsData[,i],randomPicrustData[,i],method="spearman");
      
		correlationsPicrust[i]= corPicrust;
		correlationsPicrustNorm[i]= corPicrustNorm;
    correlationsRandomPicrust[i]=corPicrustRandom;
	}
	boxplot(correlationsPicrust,correlationsPicrustNorm,correlationsRandomPicrust,names=c("Picrust (Raw)","Picrust (Normalized)","Random"),main="Raw Picrust Predictions vs Picrust Predictions Normalized", ylab="Accuracy");
	dev.off();	
}

makeCorrelationTables("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/randomtTestLevel2.pdf",subWGSData2,subPicrustData2, subPicrustDataNorm2,52);
