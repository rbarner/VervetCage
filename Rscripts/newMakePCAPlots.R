
DietPCAPlots = function(dataFrameComp, dataFrameVar, newfile, title, location="bottomright")
{
	comp1<-as.character(paste("PC1"," ",(round(dataFrameVar$loading[1],3)),"%"));
	comp2<-as.character(paste("PC2"," ",(round(pcaGenusVar$loading[2],3)),"%"));

	pdf(newfile);
	plot(dataFrameComp$axis1,dataFrameComp$axis2,col=ifelse(dataFrameComp$Diet=='Chow','red','blue'),xlab=comp1,ylab=comp2,main=title,las=1);
	points(dataFrameComp$axis1,dataFrameComp$axis2,cex=2,pch=19,col=ifelse(dataFrameComp$Diet=='Chow','red','blue'));
	legend(location,pch=c(19,19),col=c('red','blue'),legend=c("Chow","High-Fructose"),cex=.5);
	dev.off()
}

DatePCAPlots = function(dataFrameComp, dataFrameVar, newfile, title, location="bottomright")
{
	comp1<-as.character(paste("PC1"," ",(round(dataFrameVar$loading[1],3)),"%"));
	comp2<-as.character(paste("PC2"," ",(round(pcaGenusVar$loading[2],3)),"%"));
	
	pdf(newfile);
	plot(dataFrameComp$axis1,dataFrameComp$axis2,pch=19, col=as.character(dataFrameComp$DateColor),xlab=comp1, ylab=comp2, main=title,las=1);
	points(dataFrameComp$axis1,dataFrameComp$axis2,cex=2,pch=19,col=as.character(dataFrameComp$DateColor));
legend(location,pch=c(rep(19,times=6)),col=c('red','blue','green4','yellow','black','cyan'),legend=c("baseline","12/21/11","12/30/11","1/5/12","1/12/12","biopsy"),cex=.5);
	dev.off()
}

AnimalPCAPlots = function(dataFrameComp, dataFrameVar, newfile, title, location="bottomright")
{
	comp1<-as.character(paste("PC1"," ",(round(dataFrameVar$loading[1],3)),"%"));
	comp2<-as.character(paste("PC2"," ",(round(pcaGenusVar$loading[2],3)),"%"));
	
	pdf(newfile);
	plot(dataFrameComp$axis1,dataFrameComp$axis2,pch=19, col=as.character(dataFrameComp$AnimalColor),xlab=comp1, ylab=comp2, main=title,las=1);
	points(dataFrameComp$axis1,dataFrameComp$axis2,cex=2,pch=19,col=as.character(dataFrameComp$AnimalColor));
	legend(location,pch=c(rep(19,times=10)),col=c('plum3','green','black','green4','yellow','orange','cyan','grey50','red','blue'),legend=c("1030","1211","1238","1245","1248","1254","1291","1347","1448","1467"),cex=.5);
	dev.off()
}

CagePCAPlots = function(dataFrameComp, dataFrameVar, newfile, title, location="bottomright")
{
	comp1<-as.character(paste("PC1"," ",(round(dataFrameVar$loading[1],3)),"%"));
	comp2<-as.character(paste("PC2"," ",(round(pcaGenusVar$loading[2],3)),"%"));
	
	pdf(newfile);
	plot(dataFrameComp$axis1,dataFrameComp$axis2,pch=19, col=as.character(dataFrameComp$CageColor),xlab=comp1, ylab=comp2, main=title,las=1);
	points(dataFrameComp$axis1,dataFrameComp$axis2,pch=19,col=as.character(dataFrameComp$CageColor));
	legend(location,pch=c(19,19,19,19,19,19),col=c("cyan","yellow","green","blue","black","red"),legend=c("Cage A","Cage B","Cage C","Cage D","Cage E","Cage F"),cex=.5);
	dev.off()
}

AgePCAPlots = function(dataFrameComp, dataFrameVar, newfile, title, location="bottomright")
{
	comp1<-as.character(paste("PC1"," ",(round(dataFrameVar$loading[1],3)),"%"));
	comp2<-as.character(paste("PC2"," ",(round(pcaGenusVar$loading[2],3)),"%"));
	
	pdf(newfile);
	plot(dataFrameComp$axis1,dataFrameComp$axis2,pch=19,col=as.character(dataFrameComp$AgeColor),xlab=comp1,ylab=comp2,main=title,las=1);
	points(dataFrameComp$axis1,dataFrameComp$axis2,cex=2,pch=19,col=as.character(dataFrameComp$AgeColor));
	legend(location,pch=c(19,19),col=c('red','blue'),legend=c("Old","Young"),cex=.5);
	dev.off()
}

AllPCAPlots = function(dataFrameComp, dataFrameVar, newfile, location="bottomright")
{
	pdf(newfile,width=9,height=9);
	par(mfrow=c(2,2))

	comp1<-as.character(paste("PC1"," ",(round(dataFrameVar$loading[1],3)),"%"));
	comp2<-as.character(paste("PC2"," ",(round(pcaGenusVar$loading[2],3)),"%"));
	
	plot(dataFrameComp$axis1,dataFrameComp$axis2,col=ifelse(dataFrameComp$Diet=='Chow','red','blue'),xlab=comp1,ylab=comp2,main="Genus Level PCA (Colored by Diet)",las=1);
	points(dataFrameComp$axis1,dataFrameComp$axis2,cex=1,pch=19,col=ifelse(dataFrameComp$Diet=='Chow','red','blue'));
	legend(location,pch=c(19,19),col=c('red','blue'),legend=c("Chow","High-Fructose"),cex=.5);
	
	plot(dataFrameComp$axis1,dataFrameComp$axis2,pch=19,xlab=comp1,ylab=comp2,main="Colored by Age",las=1);
	points(dataFrameComp$axis1,dataFrameComp$axis2,cex=1,pch=19,col=as.character(dataFrameComp$AgeColor));
	legend(location,pch=c(19,19),col=c('red','blue'),legend=c("Old","Young"),cex=.5);
	
	plot(dataFrameComp$axis1,dataFrameComp$axis2,pch=19, xlab=comp1, ylab=comp2, main="Colored by Date",las=1);
	points(dataFrameComp$axis1,dataFrameComp$axis2,cex=1,pch=19,col=as.character(dataFrameComp$DateColor));
legend(location,pch=c(rep(19,times=6)),col=c('red','blue','green4','yellow','black','cyan'),legend=c("baseline","12/21/11","12/30/11","1/5/12","1/12/12","biopsy"),cex=.5);
	
	plot(dataFrameComp$axis1,dataFrameComp$axis2,pch=19, xlab=comp1, ylab=comp2, main="Colored by Animal",las=1);
	points(dataFrameComp$axis1,dataFrameComp$axis2,cex=1,pch=19,col=as.character(dataFrameComp$AnimalColor));
legend(location,pch=c(rep(19,times=10)),col=c('plum3','green','black','green4','yellow','orange','cyan','grey50','red','blue'),legend=c("1030","1211","1238","1245","1248","1254","1291","1347","1448","1467"),cex=.5)
	dev.off()
}
#######################################################################################################################################
sampleData=read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/key/vervet/WholeGenomeKey.txt");

pcaPhylum=read.delim("/Applications/mothur/pcoas/brayCurtForMothur_phylumLogged.pcoa.axes");
pcaPhylumVar=read.delim("/Applications/mothur/pcoas/brayCurtForMothur_phylumLogged.pcoa.loadings");
pcaPhylum=cbind(sampleData,pcaPhylum);
pcaPhylum$Date=factor(pcaPhylum$Date,levels=c("baseline","12/21/11","12/30/11","1/5/12","1/12/12","biopsy"));

pcaClass=read.delim("/Applications/mothur/pcoas/brayCurtForMothur_classLogged.pcoa.axes");
pcaClassVar=read.delim("/Applications/mothur/pcoas/brayCurtForMothur_classLogged.pcoa.loadings");
pcaClass=cbind(sampleData,pcaClass);
pcaClass$Date=factor(pcaClass$Date,levels=c("baseline","12/21/11","12/30/11","1/5/12","1/12/12","biopsy"));

pcaOrder=read.delim("/Applications/mothur/pcoas/brayCurtForMothur_orderLogged.pcoa.axes");
pcaOrderVar=read.delim("/Applications/mothur/pcoas/brayCurtForMothur_orderLogged.pcoa.loadings");
pcaOrder=cbind(sampleData,pcaOrder);
pcaOrder$Date=factor(pcaOrder$Date,levels=c("baseline","12/21/11","12/30/11","1/5/12","1/12/12","biopsy"));


pcaFamily=read.delim("/Applications/mothur/pcoas/brayCurtForMothur_familyLogged.pcoa.axes");
pcaFamilyVar=read.delim("/Applications/mothur/pcoas/brayCurtForMothur_familyLogged.pcoa.loadings");
pcaFamily=cbind(sampleData,pcaFamily);
pcaFamily$Date=factor(pcaFamily$Date,levels=c("baseline","12/21/11","12/30/11","1/5/12","1/12/12","biopsy"));


pcaGenus=read.delim("/Applications/mothur/pcoas/brayCurtForMothur_genusLogged.pcoa.axes");
pcaGenusVar=read.delim("/Applications/mothur/pcoas/brayCurtForMothur_genusLogged.pcoa.loadings");
pcaGenus=cbind(sampleData,pcaGenus);
pcaGenus$Date=factor(pcaGenus$Date,levels=c("baseline","12/21/11","12/30/11","1/5/12","1/12/12","biopsy"));


AgePCAPlots(pcaGenus, pcaGenusVar,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/Age/AgepcaGenus2qiime.pdf","Genus level pca plot", location="topright");
AgePCAPlots(pcaFamily, pcaFamilyVar, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/Age/AgepcaFamilyqiime.pdf","Family level pca plot", location="bottomleft");
AgePCAPlots(pcaOrder, pcaOrderVar,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/Age/AgepcaOrderqiime.pdf","Order level pca plot", location="bottomright");
AgePCAPlots(pcaClass, pcaClassVar, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/Age/AgepcaClassqiime.pdf","Class level pca plot", location="bottomright");
AgePCAPlots(pcaPhylum, pcaPhylumVar, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/Age/AgepcaPhylumqiime.pdf","Phylum level pca plot", location="bottomright");

DatePCAPlots(pcaGenus, pcaGenusVar, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/Date/DatepcaGenusqiime.pdf","Genus level pca plot","topright");
DatePCAPlots(pcaFamily, pcaFamilyVar,  "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/Date/DatepcaFamilyqiime.pdf","Family level pca plot","bottomleft");
DatePCAPlots(pcaOrder, pcaOrderVar, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/Date/DatepcaOrderqiime.pdf","Order level pca plot");
DatePCAPlots(pcaClass, pcaClassVar, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/Date/DatepcaClassqiime.pdf","Class level pca plot");
DatePCAPlots(pcaPhylum, pcaPhylumVar, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/Date/DatepcaPhylumqiime.pdf","Phylum level pca plot");

DietPCAPlots(pcaGenus, pcaGenusVar,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/Diet/DietpcaGenus2qiime.pdf","Genus level pca plot", "topright");
DietPCAPlots(pcaFamily, pcaFamilyVar, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/Diet/DietpcaFamilyqiime.pdf","Family level pca plot", "bottomleft");
DietPCAPlots(pcaOrder, pcaOrderVar,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/Diet/DietpcaOrderqiime.pdf","Order level pca plot", "bottomright");
DietPCAPlots(pcaClass, pcaClassVar, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/Diet/DietpcaClassqiime.pdf","Class level pca plot", "bottomright");
DietPCAPlots(pcaPhylum, pcaPhylumVar, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/Diet/DietpcaPhylumqiime.pdf","Phylum level pca plot", "bottomright");

AnimalPCAPlots(pcaGenus, pcaGenusVar, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/Animal/AnimalpcaGenus2qiime.pdf","Genus level pca plot", location="topright");
AnimalPCAPlots(pcaFamily, pcaFamilyVar, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/Animal/AnimalpcaFamilyqiime.pdf","Family level pca plot", location="bottomleft");
AnimalPCAPlots(pcaOrder, pcaOrderVar, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/Animal/AnimalpcaOrderqiime.pdf","Order level pca plot", location="bottomright");
AnimalPCAPlots(pcaClass, pcaClassVar, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/Animal/AnimalpcaClassqiime.pdf","Class level pca plot", location="bottomleft");
AnimalPCAPlots(pcaPhylum, pcaPhylumVar, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/Animal/AnimalpcaPhylumqiime.pdf","Phylum level pca plot", location="bottomright");

CagePCAPlots(pcaGenus, pcaGenusVar,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/Cage/CagepcaGenus2qiime.pdf","Genus level pca plot", location="topright");
CagePCAPlots(pcaFamily, pcaFamilyVar,  "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/Cage/CagepcaFamilyqiime.pdf","Family level pca plot", location="bottomleft");
CagePCAPlots(pcaOrder, pcaOrderVar, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/Cage/CagepcaOrderqiime.pdf","Order level pca plot", location="bottomright");
CagePCAPlots(pcaClass, pcaClassVar, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/Cage/CagepcaClassqiime.pdf","Class level pca plot", location="bottomright");
CagePCAPlots(pcaPhylum, pcaPhylumVar, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/Cage/CagepcaPhylumqiime.pdf","Phylum level pca plot", location="bottomright");

AllPCAPlots(pcaGenus, pcaGenusVar,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/pcaGenus2qiime.pdf", location="topright");
