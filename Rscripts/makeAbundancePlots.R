#Read in vervet sample data (includes cage ID, animal ID, date of sample, etc.)
sampleData=read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/key/vervet/WholeGenomeKey.txt");

#Define an order for the sample dates
sampleData$Date=factor(sampleData$Date,levels=c("baseline","12/21/11","12/30/11","1/5/12","1/12/12","biopsy"));

#Read in Whole genome functions, picrust functions and phylum level classifications
function16SLevel2=read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/picrust/parsedTables/picrustLevel2Abundances.txt",row.names=1)
functionWGSLevel2=read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/naiveWGSblast/level2/Level2PivotAbundances.txt",row.names=1)
classificationLevelPhylum=read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/sixteenSseqs/transposed/orderedPivots/kylieVervet_abundance_phy_transposed.txt",row.names=1)

#transpose matrices
transposed16S=t(function16SLevel2);
transposedWGS=t(functionWGSLevel2);
transposedPhylum=t(classificationLevelPhylum);

#Add sample data to matrices
funct16s=cbind(sampleData,transposed16S);
functWGS=cbind(sampleData,transposedWGS);
classPhy=cbind(sampleData,transposedPhylum);

#Reorder samples by animal ID then by date
funct16s=funct16s[with(funct16s,order(Sample.ID,Date)),];
functWGS=functWGS[with(functWGS,order(Sample.ID,Date)),];
classPhy=classPhy[with(classPhy,order(Sample.ID,Date)),];

#transpose matrices again (so that they look like original matrices just ordered by animal and date)
funct16S_transposed=t(funct16s[,12:dim(funct16s)[2]]);
functWGS_transposed=t(functWGS[,12:dim(functWGS)[2]]);
classPhy_transposed=t(classPhy[,12:dim(classPhy)[2]]);



pdf("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/sampleAbundancesPlots.pdf",width=14,height=8)
par(mfrow=c(3,1))
barplot(as.matrix(classPhy_transposed),col=rainbow(16),border=NA,axisnames=FALSE, main="Abundances of Microbiota Per Sample (Phylum Level)")
barplot(as.matrix(functWGS_transposed),col=rainbow(24),border=NA,axisnames=FALSE, main="Abundances of Functions Per Sample (Level 2)")
barplot(as.matrix(funct16S_transposed),col=rainbow(24),border=NA,axisnames=FALSE, main="Abundances of Predicted Functions Per Sample (Level 2)")
dev.off()
