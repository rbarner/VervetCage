#############################################
library(calibrate);
CagePCAPlots = function(dataFrameComp, dataFrameVar, dataFramePval, newfile, title, location="bottomright")
{
	dataFrameVar$sum[dataFrameVar$sum<0.0]<-0
	dataFrameVar$cumulativeSum=dataFrameVar$sum/sum(dataFrameVar$sum)
	#comp1<-as.character(paste("Component 1"," ",(round(dataFrameVar[1,3],3)*100),"%, P-value= ",(round(dataFramePval[1,4],3))));
	#comp2<-as.character(paste("Component 2"," ",(round(dataFrameVar[2,3],3)*100),"%, P-value= ",(round(dataFramePval[2,4],3))));
	
	comp1<-as.character(paste("Component 1"," ",(round(dataFrameVar[1,3],3)*100),"%"));
	comp2<-as.character(paste("Component 2"," ",(round(dataFrameVar[2,3],3)*100),"%"));
	
	pdf(newfile);
	plot(dataFrameComp$Comp0,dataFrameComp$Comp1,pch=19, xlab=comp1, ylab=comp2, main=title);
	points(dataFrameComp$Comp0,cex=2,dataFrameComp$Comp1,pch=19,col=as.character(dataFrameComp$CageColor));
#textxy(dataFrameComp$Comp0,dataFrameComp$Comp1,dataFrameComp$Cage);
	#legend(location,pch=c(19,19,19,19,19,19),col=c("cyan","yellow","green","blue","black","red"),legend=c("Cage A","Cage B","Cage C","Cage D","Cage E","Cage F"));
	dev.off()
}

#######################################################################################################################################
sampleData=read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/key/vervet/WholeGenomeKey.txt");
out=split(sampleData, f=sampleData$Date)
jan12sampleData=out$`1/12/12`
jan5sampleData=out$`1/5/12`
dec21sampleData=out$`12/21/11`
dec30sampleData=out$`12/30/11`
baselinesampleData=out$baseline
biopsysampleData=out$biopsy

pcaGenusBaseline=read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaTables/_dateSeparated/baseline/baselineGenusLevelNormPivot.txt");
pcaGenusBaselinePercent=read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaTables/_dateSeparated/baseline/baselineGenusLevelNormPivot.txt_COMPONENENTS.txt");
genusBaselineCagePvalues=read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/baseline/AnovaCageqiime_GenusBaseline.txt");
pcaGenusBaseline=cbind(baselinesampleData,pcaGenusBaseline);

pcaGenusBiopsy=read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaTables/_dateSeparated/biopsy/biopsyGenusLevelNormPivot.txt");
pcaGenusBiopsyPercent=read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaTables/_dateSeparated/biopsy/biopsyGenusLevelNormPivot.txt_COMPONENENTS.txt");
genusBiopsyCagePvalues=read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/biopsy/AnovaCageqiime_GenusBiopsy.txt");
pcaGenusBiopsy=cbind(biopsysampleData,pcaGenusBiopsy);

pcaGenusDec21=read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaTables/_dateSeparated/dec21/dec21GenusLevelNormPivot.txt");
pcaGenusDec21Percent=read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaTables/_dateSeparated/dec21/dec21GenusLevelNormPivot.txt_COMPONENENTS.txt");
genusDec21CagePvalues=read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/dec21/AnovaCageqiime_GenusDec21.txt");
pcaGenusDec21=cbind(dec21sampleData,pcaGenusDec21);

pcaGenusDec30=read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaTables/_dateSeparated/dec30/dec30GenusLevelNormPivot.txt");
pcaGenusDec30Percent=read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaTables/_dateSeparated/dec30/dec30GenusLevelNormPivot.txt_COMPONENENTS.txt");
genusDec30CagePvalues=read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/dec30/AnovaCageqiime_GenusDec30.txt");
pcaGenusDec30=cbind(dec30sampleData,pcaGenusDec30);

pcaGenusJan5=read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaTables/_dateSeparated/jan5/jan5GenusLevelNormPivot.txt");
pcaGenusJan5Percent=read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaTables/_dateSeparated/jan5/jan5GenusLevelNormPivot.txt_COMPONENENTS.txt");
genusJan5CagePvalues=read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/jan5/AnovaCageqiime_GenusJan5.txt");
pcaGenusJan5=cbind(jan5sampleData,pcaGenusJan5);

pcaGenusJan12=read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaTables/_dateSeparated/jan12/jan12GenusLevelNormPivot.txt");
pcaGenusJan12Percent=read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaTables/_dateSeparated/jan12/jan12GenusLevelNormPivot.txt_COMPONENENTS.txt");
genusJan12CagePvalues=read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/jan12/AnovaCageqiime_GenusJan12.txt");
pcaGenusJan12=cbind(jan12sampleData,pcaGenusJan12);



CagePCAPlots(pcaGenusBaseline, pcaGenusBaselinePercent, genusBaselineCagePvalues,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/_dateSeparated/baseline/baselineCagePCA.pdf","Genus level PCOA plot at Baseline (Colored by Cage)", location="topright");
CagePCAPlots(pcaGenusBiopsy, pcaGenusBiopsyPercent, genusBiopsyCagePvalues,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/_dateSeparated/biopsy/biopsyCagePCA.pdf","Genus level PCOA plot of Biopsy samples (Colored by Cage)", location="bottomright");
CagePCAPlots(pcaGenusDec21, pcaGenusDec21Percent, genusDec21CagePvalues, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/_dateSeparated/dec21/dec21CagePCA.pdf","Genus level PCOA plot at Dec 21 (Colored by Cage)", location="bottomleft");
CagePCAPlots(pcaGenusDec30, pcaGenusDec30Percent, genusDec30CagePvalues, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/_dateSeparated/dec30/dec30CagePCA.pdf","Genus level PCOA plot at Dec 30 (Colored by Cage)", location="bottomright");
CagePCAPlots(pcaGenusJan5, pcaGenusJan5Percent, genusJan5CagePvalues, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/_dateSeparated/jan5/jan5CagePCA.pdf","Genus level PCOA plot at Jan 5 (Colored by Cage)", location="bottomright");
CagePCAPlots(pcaGenusJan12, pcaGenusJan12Percent, genusJan12CagePvalues, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/_dateSeparated/jan12/jan12CagePCA.pdf","Genus level PCOA plot at Jan 12 (Colored by Cage)", location="bottomright");


