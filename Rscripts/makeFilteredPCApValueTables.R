sampleData=read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/key/vervet/WholeGenomeKey.txt");
sampleData2=read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/key/vervet/kylieVervet_finalMap.txt");

pcaPhylum=read.delim("/Applications/mothur/pcoas/brayCurtForMothur_phylumLogged.pcoa.axes");
pcaPhylumPercent=read.delim("/Applications/mothur/pcoas/brayCurtForMothur_phylumLogged.pcoa.loadings");
pcaPhylum=cbind(sampleData,pcaPhylum);
pcaPhylum$Date=factor(pcaPhylum$Date,levels=c("baseline","12/21/11","12/30/11","1/5/12","1/12/12","biopsy"));

pcaClass=read.delim("/Applications/mothur/pcoas/brayCurtForMothur_classLogged.pcoa.axes");
pcaClassPercent=read.delim("/Applications/mothur/pcoas/brayCurtForMothur_classLogged.pcoa.loadings");
pcaClass=cbind(sampleData,pcaClass)
pcaClass$Date=factor(pcaClass$Date,levels=c("baseline","12/21/11","12/30/11","1/5/12","1/12/12","biopsy"));

pcaOrder=read.delim("/Applications/mothur/pcoas/brayCurtForMothur_orderLogged.pcoa.axes");
pcaOrderPercent=read.delim("/Applications/mothur/pcoas/brayCurtForMothur_orderLogged.pcoa.loadings");
pcaOrder=cbind(sampleData,pcaOrder)
pcaOrder$Date=factor(pcaOrder$Date,levels=c("baseline","12/21/11","12/30/11","1/5/12","1/12/12","biopsy"));

pcaFamily=read.delim("/Applications/mothur/pcoas/brayCurtForMothur_familyLogged.pcoa.axes");
pcaFamilyPercent=read.delim("/Applications/mothur/pcoas/brayCurtForMothur_familyLogged.pcoa.loadings");
pcaFamily=cbind(sampleData,pcaFamily)
pcaFamily$Date=factor(pcaFamily$Date,levels=c("baseline","12/21/11","12/30/11","1/5/12","1/12/12","biopsy"));

pcaGenus=read.delim("/Applications/mothur/pcoas/brayCurtForMothur_genusLogged.pcoa.axes");
pcaGenusPercent=read.delim("/Applications/mothur/pcoas/brayCurtForMothur_genusLogged.pcoa.loadings");
pcaGenus=cbind(sampleData,pcaGenus)
pcaGenus$Date=factor(pcaGenus$Date,levels=c("baseline","12/21/11","12/30/11","1/5/12","1/12/12","biopsy"));

anovaTableCage = function(dataFrame, componentPercentage, newfile)
{
	fValList=numeric(0);
	pValList=numeric(0);
	for(i in (dim(sampleData)[2]+2): length(names(dataFrame)))
	{
		f<-as.formula(paste(names(dataFrame)[i],"~","Cage"));
		anova=aov(f,dataFrame);
		fVal=summary(anova)[[1]][["F value"]][1];
		pVal=summary(anova)[[1]][["Pr(>F)"]][1];
		fValList[[length(fValList)+1]]= fVal;
		pValList[[length(pValList)+1]] = pVal;	
	}
	adjustedPvalues=p.adjust(pValList,"fdr");
	makeTable=data.frame(fValList,pValList,adjustedPvalues,componentPercentage$loading);
	names(makeTable)=cbind("F value","p-value","adjusted p-value");
	write("Component\tt score\tp-value\tadjusted p-value\tCumulative % explained",newfile);
	write.table(makeTable,newfile,quote=FALSE, sep="\t",append=TRUE,col.names=FALSE);
}

anovaTableAnimal = function(dataFrame, componentPercentage, newfile)
{
	fValList=numeric(0);
	pValList=numeric(0);
	for(i in (dim(sampleData)[2]+2): length(names(dataFrame)))
	{
		f<-as.formula(paste(names(dataFrame)[i],"~","Sample.ID"));
		anova=aov(f,dataFrame);
		fVal=summary(anova)[[1]][["F value"]][1];
		pVal=summary(anova)[[1]][["Pr(>F)"]][1];
		fValList[[length(fValList)+1]]= fVal;
		pValList[[length(pValList)+1]] = pVal;	
	}
	adjustedPvalues=p.adjust(pValList,"fdr");
	makeTable=data.frame(fValList,pValList,adjustedPvalues,componentPercentage$loading);
	names(makeTable)=cbind("F value","p-value","adjusted p-value");
	write("Component\tt score\tp-value\tadjusted p-value\tCumulative % explained",newfile);
	write.table(makeTable,newfile,quote=FALSE, sep="\t",append=TRUE,col.names=FALSE);
}

anovaTableDate = function(dataFrame, componentPercentage, newfile)
{
	fValList=numeric(0);
	pValList=numeric(0);
	for(i in (dim(sampleData)[2]+2): length(names(dataFrame)))
	{
		f<-as.formula(paste(names(dataFrame)[i],"~","Date"));
		anova=aov(f,dataFrame);
		fVal=summary(anova)[[1]][["F value"]][1];
		pVal=summary(anova)[[1]][["Pr(>F)"]][1];
		fValList[[length(fValList)+1]]= fVal;
		pValList[[length(pValList)+1]] = pVal;	
	}
	adjustedPvalues=p.adjust(pValList,"fdr");
	makeTable=data.frame(fValList,pValList,adjustedPvalues,componentPercentage$loading);
	names(makeTable)=cbind("F value","p-value","adjusted p-value");
	write("Component\tt score\tp-value\tadjusted p-value\tCumulative % explained",newfile);
	write.table(makeTable,newfile,quote=FALSE, sep="\t",append=TRUE,col.names=FALSE);
}

tTestTable = function(dataFrame, componentPercentage, newfile)
{
	tValList=numeric(0);
	pValList=numeric(0);
	for(i in (dim(sampleData)[2]+2): length(names(dataFrame)))
	{
		f<-as.formula(paste(names(dataFrame)[i],"~","Diet"));
		ttest=t.test(f,dataFrame);
		tVal=ttest$statistic[[1]];
		pVal=ttest$p.value[[1]];
		tValList[[length(tValList)+1]]= tVal;
		pValList[[length(pValList)+1]] = pVal;	
	}
	adjustedPvalues=p.adjust(pValList,"fdr");
	makeTable=data.frame(tValList,pValList,adjustedPvalues,componentPercentage$loading);
	names(makeTable)=cbind("t score","p-value","adjusted p-value");
	write("Component\tt score\tp-value\tadjusted p-value\tCumulative % explained",newfile);
	write.table(makeTable,newfile,quote=FALSE, sep="\t",append=TRUE, col.names=FALSE);
}


tTestTableAge = function(dataFrame, componentPercentage, newfile)
{
	tValList=numeric(0);
	pValList=numeric(0);
	for(i in (dim(sampleData)[2]+2): length(names(dataFrame)))
	{
		f<-as.formula(paste(names(dataFrame)[i],"~","AgeCat"));
		ttest=t.test(f,dataFrame);
		tVal=ttest$statistic[[1]];
		pVal=ttest$p.value[[1]];
		tValList[[length(tValList)+1]]= tVal;
		pValList[[length(pValList)+1]] = pVal;	
	}
	adjustedPvalues=p.adjust(pValList,"fdr");
	makeTable=data.frame(tValList,pValList,adjustedPvalues,componentPercentage$loading);
	names(makeTable)=cbind("t score","p-value","adjusted p-value");
	write("Component\tt score\tp-value\tadjusted p-value\tCumulative % explained",newfile);
	write.table(makeTable,newfile,quote=FALSE, sep="\t",append=TRUE, col.names=FALSE);
}


anovaTableCage(pcaPhylum,pcaPhylumPercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/cage/AnovaCageqiime_Phylum.txt");
anovaTableCage(pcaClass,pcaClassPercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/cage/AnovaCageqiime_Class.txt");
anovaTableCage(pcaOrder,pcaOrderPercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/cage/AnovaCageqiime_Order.txt");
anovaTableCage(pcaFamily,pcaFamilyPercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/cage/AnovaCageqiime_Family.txt");
anovaTableCage(pcaGenus,pcaGenusPercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/cage/AnovaCageqiime_Genus.txt");


anovaTableAnimal(pcaPhylum,pcaPhylumPercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/animal/AnovaAnimalqiime_Phylum.txt");
anovaTableAnimal(pcaClass,pcaClassPercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/animal/AnovaAnimalqiime_Class.txt");
anovaTableAnimal(pcaOrder,pcaOrderPercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/animal/AnovaAnimalqiime_Order.txt");
anovaTableAnimal(pcaFamily,pcaFamilyPercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/animal/AnovaAnimalqiime_Family.txt");
anovaTableAnimal(pcaGenus,pcaGenusPercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/animal/AnovaAnimalqiime_Genus.txt");


anovaTableDate(pcaPhylum,pcaPhylumPercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/date/AnovaDateqiime_Phylum.txt");
anovaTableDate(pcaClass,pcaClassPercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/date/AnovaDateqiime_Class.txt");
anovaTableDate(pcaOrder,pcaOrderPercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/date/AnovaDateqiime_Order.txt");
anovaTableDate(pcaFamily,pcaFamilyPercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/date/AnovaDateqiime_Family.txt");
anovaTableDate(pcaGenus,pcaGenusPercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/date/AnovaDateqiime_Genus.txt");


tTestTable(pcaPhylum,pcaPhylumPercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/diet/ttestDietqiime_Phylum.txt");
tTestTable(pcaClass,pcaClassPercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/diet/ttestDietqiime_Class.txt");
tTestTable(pcaOrder,pcaOrderPercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/diet/ttestDietqiime_Order.txt");
tTestTable(pcaFamily,pcaFamilyPercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/diet/ttestDietqiime_Family.txt");
tTestTable(pcaGenus,pcaGenusPercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/diet/ttestDietqiime_Genus.txt");


tTestTableAge(pcaPhylum,pcaPhylumPercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/age/ttestAgeqiime_Phylum.txt");
tTestTableAge(pcaClass,pcaClassPercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/age/ttestAgeqiime_Class.txt");
tTestTableAge(pcaOrder,pcaOrderPercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/age/ttestAgeqiime_Order.txt");
tTestTableAge(pcaFamily,pcaFamilyPercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/age/ttestAgeqiime_Family.txt");
tTestTableAge(pcaGenus,pcaGenusPercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/age/ttestAgeqiime_Genus.txt");
#############################################
