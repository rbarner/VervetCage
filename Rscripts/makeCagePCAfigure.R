#############################################
library(calibrate);
CagePCAPlots = function(dataFrameComp, dataFrameVar, dataFrameCompBase, dataFrameVarBase, dataFrameCompDec21, dataFrameVarDec21,dataFrameCompDec30, dataFrameVarDec30,dataFrameCompJan5, dataFrameVarJan5, dataFrameCompJan12, dataFrameVarJan12, dataFrameCompBiop, dataFrameVarBiop, newfile, title, location="topleft")
{
	comp1<-as.character(paste("PC1"," ",(round(dataFrameVar$loading[1],3)),"%"));
	comp2<-as.character(paste("PC2"," ",(round(dataFrameVar$loading[2],3)),"%"));
	
	comp1Base<-as.character(paste("PC1"," ",(round(dataFrameVarBase$loading[1],3)),"%"));
	comp2Base<-as.character(paste("PC2"," ",(round(dataFrameVarBase$loading[2],3)),"%"));
	
	comp1Dec21<-as.character(paste("PC1"," ",(round(dataFrameVarDec21$loading[1],3)),"%"));
	comp2Dec21<-as.character(paste("PC2"," ",(round(dataFrameVarDec21$loading[2],3)),"%"));
	
	comp1Dec30<-as.character(paste("PC1"," ",(round(dataFrameVarDec30$loading[1],3)),"%"));
	comp2Dec30<-as.character(paste("PC2"," ",(round(dataFrameVarDec30$loading[2],3)),"%"));
	
	comp1Jan5<-as.character(paste("PC1"," ",(round(dataFrameVarJan5$loading[1],3)),"%"));
	comp2Jan5<-as.character(paste("PC2"," ",(round(dataFrameVarJan5$loading[2],3)),"%"));

	comp1Jan12<-as.character(paste("PC1"," ",(round(dataFrameVarJan12$loading[1],3)),"%"));
	comp2Jan12<-as.character(paste("PC2"," ",(round(dataFrameVarJan12$loading[2],3)),"%"));

	comp1Biop<-as.character(paste("PC1"," ",(round(dataFrameVarBiop$loading[1],3)),"%"));
	comp2Biop<-as.character(paste("PC2"," ",(round(dataFrameVarBiop$loading[2],3)),"%"));
	
	pdf(newfile,width=14,height=8);
	#layout(matrix(c(1,1,2,3),2,2,byrow=TRUE), widths=c(3,1), heights=c(1,2));
	#layout(matrix(c(1,1,1,1,1,1,1,1,1,1,1,1,2,3,4,5,6,7),3,6,byrow=TRUE));
	layout(matrix(c(1,1,2,2,3,3,1,1,2,2,3,3,4,5,6,7,8,9),3,6,byrow=TRUE));
	
	plot.new();
	
	plot(dataFrameComp$axis1,dataFrameComp$axis2,pch=19, xlab=comp1, ylab=comp2,col=as.character(dataFrameComp$CageColor), main=title);
	points(dataFrameComp$axis1,dataFrameComp$axis2,pch=19,cex=2,col=as.character(dataFrameComp$CageColor));
	
	plot.new();
	legend(location,box.col="white",cex=2,pch=c(19,19,19,19,19,19),col=c("cyan","yellow","green","blue","black","red"),legend=c("Cage A","Cage B","Cage C","Cage D","Cage E","Cage F"));
	
	plot(dataFrameCompBase$axis1,dataFrameCompBase$axis2,pch=19, xlab=comp1Base, ylab=comp2Base,col=as.character(dataFrameComp$CageColor), main="At Baseline");
	points(dataFrameCompBase$axis1,dataFrameCompBase$axis2,pch=19,cex=1.5,col=as.character(dataFrameCompBase$CageColor));
	
	plot(dataFrameCompDec21$axis1,dataFrameCompDec21$axis2,pch=19, xlab=comp1Dec21, ylab=comp2Dec21,col=as.character(dataFrameComp$CageColor), main="Dec 21");
	points(dataFrameCompDec21$axis1,dataFrameCompDec21$axis2,pch=19,cex=1.5,col=as.character(dataFrameCompDec21$CageColor));
	
	plot(dataFrameCompDec30$axis1,dataFrameCompDec30$axis2,pch=19, xlab=comp1Dec30, ylab=comp2Dec30,col=as.character(dataFrameComp$CageColor), main="Dec 30");
	points(dataFrameCompDec30$axis1,dataFrameCompDec30$axis2,pch=19,cex=1.5,col=as.character(dataFrameCompDec30$CageColor));
	
	plot(dataFrameCompJan5$axis1,dataFrameCompJan5$axis2,pch=19, xlab=comp1Jan5, ylab=comp2Jan5, col=as.character(dataFrameComp$CageColor), main="Jan 5");
	points(dataFrameCompJan5$axis1,dataFrameCompJan5$axis2,pch=19,cex=1.5,col=as.character(dataFrameCompJan5$CageColor));
	
	plot(dataFrameCompJan12$axis1,dataFrameCompJan12$axis2,pch=19, xlab=comp1Jan12, ylab=comp2Jan12,col=as.character(dataFrameComp$CageColor), main="Jan 12");
	points(dataFrameCompJan12$axis1,dataFrameCompJan12$axis2,pch=19,cex=1.5,col=as.character(dataFrameCompJan12$CageColor));
	
	plot(dataFrameCompBiop$axis1,dataFrameCompBiop$axis2,pch=19, xlab=comp1Biop, ylab=comp2Biop,col=as.character(dataFrameComp$CageColor), main="Biopsy");
	points(dataFrameCompBiop$axis1,dataFrameCompBiop$axis2,pch=19,cex=1.5,col=as.character(dataFrameCompBiop$CageColor));
	
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

pcaGenus=read.delim("/Applications/mothur/pcoas/brayCurtForMothur_genusLogged.pcoa.axes");
pcaGenusVar=read.delim("/Applications/mothur/pcoas/brayCurtForMothur_genusLogged.pcoa.loadings");
pcaGenus=cbind(sampleData,pcaGenus);

pcaGenusBaseline=read.delim("/Applications/mothur/dateBrayCurt/baseline/brayCurtForMothur_genus.pcoa.axes");
pcaGenusBaselinePercent=read.delim("/Applications/mothur/dateBrayCurt/baseline/brayCurtForMothur_genus.pcoa.loadings");
pcaGenusBaseline=cbind(baselinesampleData,pcaGenusBaseline);

pcaGenusBiopsy=read.delim("/Applications/mothur/dateBrayCurt/biopsy/brayCurtForMothur_genus.txt.pcoa.axes");
pcaGenusBiopsyPercent=read.delim("/Applications/mothur/dateBrayCurt/biopsy/brayCurtForMothur_genus.txt.pcoa.loadings");
pcaGenusBiopsy=cbind(biopsysampleData,pcaGenusBiopsy);

pcaGenusDec21=read.delim("/Applications/mothur/dateBrayCurt/dec21/brayCurtForMothur_genus.txt.pcoa.axes");
pcaGenusDec21Percent=read.delim("/Applications/mothur/dateBrayCurt/dec21/brayCurtForMothur_genus.txt.pcoa.loadings");
pcaGenusDec21=cbind(dec21sampleData,pcaGenusDec21);

pcaGenusDec30=read.delim("/Applications/mothur/dateBrayCurt/dec30/brayCurtForMothur_genus.txt.pcoa.axes");
pcaGenusDec30Percent=read.delim("/Applications/mothur/dateBrayCurt/dec30/brayCurtForMothur_genus.txt.pcoa.loadings");
pcaGenusDec30=cbind(dec30sampleData,pcaGenusDec30);

pcaGenusJan5=read.delim("/Applications/mothur/dateBrayCurt/jan5/brayCurtForMothur_genus.txt.pcoa.axes");
pcaGenusJan5Percent=read.delim("/Applications/mothur/dateBrayCurt/jan5/brayCurtForMothur_genus.txt.pcoa.loadings");
pcaGenusJan5=cbind(jan5sampleData,pcaGenusJan5);

pcaGenusJan12=read.delim("/Applications/mothur/dateBrayCurt/jan12/brayCurtForMothur_genus.txt.pcoa.axes");
pcaGenusJan12Percent=read.delim("/Applications/mothur/dateBrayCurt/jan12/brayCurtForMothur_genus.txt.pcoa.loadings");
pcaGenusJan12=cbind(jan12sampleData,pcaGenusJan12);


CagePCAPlots(pcaGenus, pcaGenusVar,pcaGenusBaseline, pcaGenusBaselinePercent,pcaGenusDec21, pcaGenusDec21Percent,pcaGenusDec30, pcaGenusDec30Percent,pcaGenusJan5, pcaGenusJan5Percent,pcaGenusJan12, pcaGenusJan12Percent,pcaGenusBiopsy, pcaGenusBiopsyPercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/_dateSeparated/CagePCA.pdf","Genus level PCOA plot (Colored by Cage)", location="topleft");
###############################################################################################################################################################


