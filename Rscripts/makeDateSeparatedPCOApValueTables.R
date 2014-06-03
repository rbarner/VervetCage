anovaTableCage = function(dataFrame, componentPercentage, newfile)
{
	fValList=numeric(0);
	pValList=numeric(0);
	for(i in 13: length(names(dataFrame)))
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

sampleData=read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/key/vervet/WholeGenomeKey.txt");
out=split(sampleData, f=sampleData$Date)

jan12sampleData=out$`1/12/12`
jan5sampleData=out$`1/5/12`
dec21sampleData=out$`12/21/11`
dec30sampleData=out$`12/30/11`
baselinesampleData=out$baseline
biopsysampleData=out$biopsy


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


anovaTableCage(pcaGenusBaseline,pcaGenusBaselinePercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/baseline/AnovaCageqiime_GenusBaseline.txt");
anovaTableCage(pcaGenusBiopsy,pcaGenusBiopsyPercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/biopsy/AnovaCageqiime_GenusBiopsy.txt");
anovaTableCage(pcaGenusDec21,pcaGenusDec21Percent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/dec21/AnovaCageqiime_GenusDec21.txt");
anovaTableCage(pcaGenusDec30,pcaGenusDec30Percent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/dec30/AnovaCageqiime_GenusDec30.txt");
anovaTableCage(pcaGenusJan5,pcaGenusJan5Percent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/jan5/AnovaCageqiime_GenusJan5.txt");
anovaTableCage(pcaGenusJan12,pcaGenusJan12Percent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/jan12/AnovaCageqiime_GenusJan12.txt");



pcaFamilyBaseline=read.delim("/Applications/mothur/dateBrayCurt/baseline/brayCurtForMothur_family.pcoa.axes");
pcaFamilyBaselinePercent=read.delim("/Applications/mothur/dateBrayCurt/baseline/brayCurtForMothur_family.pcoa.loadings");
pcaFamilyBaseline=cbind(baselinesampleData,pcaFamilyBaseline);

pcaFamilyBiopsy=read.delim("/Applications/mothur/dateBrayCurt/biopsy/brayCurtForMothur_family.txt.pcoa.axes");
pcaFamilyBiopsyPercent=read.delim("/Applications/mothur/dateBrayCurt/biopsy/brayCurtForMothur_family.txt.pcoa.loadings");
pcaFamilyBiopsy=cbind(biopsysampleData,pcaFamilyBiopsy);

pcaFamilyDec21=read.delim("/Applications/mothur/dateBrayCurt/dec21/brayCurtForMothur_family.txt.pcoa.axes");
pcaFamilyDec21Percent=read.delim("/Applications/mothur/dateBrayCurt/dec21/brayCurtForMothur_family.txt.pcoa.loadings");
pcaFamilyDec21=cbind(dec21sampleData,pcaFamilyDec21);

pcaFamilyDec30=read.delim("/Applications/mothur/dateBrayCurt/dec30/brayCurtForMothur_family.txt.pcoa.axes");
pcaFamilyDec30Percent=read.delim("/Applications/mothur/dateBrayCurt/dec30/brayCurtForMothur_family.txt.pcoa.loadings");
pcaFamilyDec30=cbind(dec30sampleData,pcaFamilyDec30);

pcaFamilyJan5=read.delim("/Applications/mothur/dateBrayCurt/jan5/brayCurtForMothur_family.txt.pcoa.axes");
pcaFamilyJan5Percent=read.delim("/Applications/mothur/dateBrayCurt/jan5/brayCurtForMothur_family.txt.pcoa.loadings");
pcaFamilyJan5=cbind(jan5sampleData,pcaFamilyJan5);

pcaFamilyJan12=read.delim("/Applications/mothur/dateBrayCurt/jan12/brayCurtForMothur_family.txt.pcoa.axes");
pcaFamilyJan12Percent=read.delim("/Applications/mothur/dateBrayCurt/jan12/brayCurtForMothur_family.txt.pcoa.loadings");
pcaFamilyJan12=cbind(jan12sampleData,pcaFamilyJan12);


anovaTableCage(pcaFamilyBaseline,pcaFamilyBaselinePercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/baseline/AnovaCageqiime_FamilyBaseline.txt");
anovaTableCage(pcaFamilyBiopsy,pcaFamilyBiopsyPercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/biopsy/AnovaCageqiime_FamilyBiopsy.txt");
anovaTableCage(pcaFamilyDec21,pcaFamilyDec21Percent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/dec21/AnovaCageqiime_FamilyDec21.txt");
anovaTableCage(pcaFamilyDec30,pcaFamilyDec30Percent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/dec30/AnovaCageqiime_FamilyDec30.txt");
anovaTableCage(pcaFamilyJan5,pcaFamilyJan5Percent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/jan5/AnovaCageqiime_FamilyJan5.txt");
anovaTableCage(pcaFamilyJan12,pcaFamilyJan12Percent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/jan12/AnovaCageqiime_FamilyJan12.txt");


pcaOrderBaseline=read.delim("/Applications/mothur/dateBrayCurt/baseline/brayCurtForMothur_order.pcoa.axes");
pcaOrderBaselinePercent=read.delim("/Applications/mothur/dateBrayCurt/baseline/brayCurtForMothur_order.pcoa.loadings");
pcaOrderBaseline=cbind(baselinesampleData,pcaOrderBaseline);

pcaOrderBiopsy=read.delim("/Applications/mothur/dateBrayCurt/biopsy/brayCurtForMothur_order.txt.pcoa.axes");
pcaOrderBiopsyPercent=read.delim("/Applications/mothur/dateBrayCurt/biopsy/brayCurtForMothur_order.txt.pcoa.loadings");
pcaOrderBiopsy=cbind(biopsysampleData,pcaOrderBiopsy);

pcaOrderDec21=read.delim("/Applications/mothur/dateBrayCurt/dec21/brayCurtForMothur_order.txt.pcoa.axes");
pcaOrderDec21Percent=read.delim("/Applications/mothur/dateBrayCurt/dec21/brayCurtForMothur_order.txt.pcoa.loadings");
pcaOrderDec21=cbind(dec21sampleData,pcaOrderDec21);

pcaOrderDec30=read.delim("/Applications/mothur/dateBrayCurt/dec30/brayCurtForMothur_order.txt.pcoa.axes");
pcaOrderDec30Percent=read.delim("/Applications/mothur/dateBrayCurt/dec30/brayCurtForMothur_order.txt.pcoa.loadings");
pcaOrderDec30=cbind(dec30sampleData,pcaOrderDec30);

pcaOrderJan5=read.delim("/Applications/mothur/dateBrayCurt/jan5/brayCurtForMothur_order.txt.pcoa.axes");
pcaOrderJan5Percent=read.delim("/Applications/mothur/dateBrayCurt/jan5/brayCurtForMothur_order.txt.pcoa.loadings");
pcaOrderJan5=cbind(jan5sampleData,pcaOrderJan5);

pcaOrderJan12=read.delim("/Applications/mothur/dateBrayCurt/jan12/brayCurtForMothur_order.txt.pcoa.axes");
pcaOrderJan12Percent=read.delim("/Applications/mothur/dateBrayCurt/jan12/brayCurtForMothur_order.txt.pcoa.loadings");
pcaOrderJan12=cbind(jan12sampleData,pcaOrderJan12);


anovaTableCage(pcaOrderBaseline,pcaOrderBaselinePercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/baseline/AnovaCageqiime_OrderBaseline.txt");
anovaTableCage(pcaOrderBiopsy,pcaOrderBiopsyPercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/biopsy/AnovaCageqiime_OrderBiopsy.txt");
anovaTableCage(pcaOrderDec21,pcaOrderDec21Percent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/dec21/AnovaCageqiime_OrderDec21.txt");
anovaTableCage(pcaOrderDec30,pcaOrderDec30Percent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/dec30/AnovaCageqiime_OrderDec30.txt");
anovaTableCage(pcaOrderJan5,pcaOrderJan5Percent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/jan5/AnovaCageqiime_OrderJan5.txt");
anovaTableCage(pcaOrderJan12,pcaOrderJan12Percent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/jan12/AnovaCageqiime_OrderJan12.txt");

pcaClassBaseline=read.delim("/Applications/mothur/dateBrayCurt/baseline/brayCurtForMothur_class.txt.pcoa.axes");
pcaClassBaselinePercent=read.delim("/Applications/mothur/dateBrayCurt/baseline/brayCurtForMothur_class.txt.pcoa.loadings");
pcaClassBaseline=cbind(baselinesampleData,pcaClassBaseline);

pcaClassBiopsy=read.delim("/Applications/mothur/dateBrayCurt/biopsy/brayCurtForMothur_class.txt.pcoa.axes");
pcaClassBiopsyPercent=read.delim("/Applications/mothur/dateBrayCurt/biopsy/brayCurtForMothur_class.txt.pcoa.loadings");
pcaClassBiopsy=cbind(biopsysampleData,pcaClassBiopsy);

pcaClassDec21=read.delim("/Applications/mothur/dateBrayCurt/dec21/brayCurtForMothur_class.txt.pcoa.axes");
pcaClassDec21Percent=read.delim("/Applications/mothur/dateBrayCurt/dec21/brayCurtForMothur_class.txt.pcoa.loadings");
pcaClassDec21=cbind(dec21sampleData,pcaClassDec21);

pcaClassDec30=read.delim("/Applications/mothur/dateBrayCurt/dec30/brayCurtForMothur_class.txt.pcoa.axes");
pcaClassDec30Percent=read.delim("/Applications/mothur/dateBrayCurt/dec30/brayCurtForMothur_class.txt.pcoa.loadings");
pcaClassDec30=cbind(dec30sampleData,pcaClassDec30);

pcaClassJan5=read.delim("/Applications/mothur/dateBrayCurt/jan5/brayCurtForMothur_class.txt.pcoa.axes");
pcaClassJan5Percent=read.delim("/Applications/mothur/dateBrayCurt/jan5/brayCurtForMothur_class.txt.pcoa.loadings");
pcaClassJan5=cbind(jan5sampleData,pcaClassJan5);

pcaClassJan12=read.delim("/Applications/mothur/dateBrayCurt/jan12/brayCurtForMothur_class.txt.pcoa.axes");
pcaClassJan12Percent=read.delim("/Applications/mothur/dateBrayCurt/jan12/brayCurtForMothur_class.txt.pcoa.loadings");
pcaClassJan12=cbind(jan12sampleData,pcaClassJan12);


anovaTableCage(pcaClassBaseline,pcaClassBaselinePercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/baseline/AnovaCageqiime_ClassBaseline.txt");
anovaTableCage(pcaClassBiopsy,pcaClassBiopsyPercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/biopsy/AnovaCageqiime_ClassBiopsy.txt");
anovaTableCage(pcaClassDec21,pcaClassDec21Percent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/dec21/AnovaCageqiime_ClassDec21.txt");
anovaTableCage(pcaClassDec30,pcaClassDec30Percent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/dec30/AnovaCageqiime_ClassDec30.txt");
anovaTableCage(pcaClassJan5,pcaClassJan5Percent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/jan5/AnovaCageqiime_ClassJan5.txt");
anovaTableCage(pcaClassJan12,pcaClassJan12Percent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/jan12/AnovaCageqiime_ClassJan12.txt");


pcaPhylumBaseline=read.delim("/Applications/mothur/dateBrayCurt/baseline/brayCurtForMothur_phylum.txt.pcoa.axes");
pcaPhylumBaselinePercent=read.delim("/Applications/mothur/dateBrayCurt/baseline/brayCurtForMothur_phylum.txt.pcoa.loadings");
pcaPhylumBaseline=cbind(baselinesampleData,pcaPhylumBaseline);

pcaPhylumBiopsy=read.delim("/Applications/mothur/dateBrayCurt/biopsy/brayCurtForMothur_phylum.txt.pcoa.axes");
pcaPhylumBiopsyPercent=read.delim("/Applications/mothur/dateBrayCurt/biopsy/brayCurtForMothur_phylum.txt.pcoa.loadings");
pcaPhylumBiopsy=cbind(biopsysampleData,pcaPhylumBiopsy);

pcaPhylumDec21=read.delim("/Applications/mothur/dateBrayCurt/dec21/brayCurtForMothur_phylum.txt.pcoa.axes");
pcaPhylumDec21Percent=read.delim("/Applications/mothur/dateBrayCurt/dec21/brayCurtForMothur_phylum.txt.pcoa.loadings");
pcaPhylumDec21=cbind(dec21sampleData,pcaPhylumDec21);

pcaPhylumDec30=read.delim("/Applications/mothur/dateBrayCurt/dec30/brayCurtForMothur_phylum.txt.pcoa.axes");
pcaPhylumDec30Percent=read.delim("/Applications/mothur/dateBrayCurt/dec30/brayCurtForMothur_phylum.txt.pcoa.loadings");
pcaPhylumDec30=cbind(dec30sampleData,pcaPhylumDec30);

pcaPhylumJan5=read.delim("/Applications/mothur/dateBrayCurt/jan5/brayCurtForMothur_phylum.txt.pcoa.axes");
pcaPhylumJan5Percent=read.delim("/Applications/mothur/dateBrayCurt/jan5/brayCurtForMothur_phylum.txt.pcoa.loadings");
pcaPhylumJan5=cbind(jan5sampleData,pcaPhylumJan5);

pcaPhylumJan12=read.delim("/Applications/mothur/dateBrayCurt/jan12/brayCurtForMothur_phylum.txt.pcoa.axes");
pcaPhylumJan12Percent=read.delim("/Applications/mothur/dateBrayCurt/jan12/brayCurtForMothur_phylum.txt.pcoa.loadings");
pcaPhylumJan12=cbind(jan12sampleData,pcaPhylumJan12);


anovaTableCage(pcaPhylumBaseline,pcaPhylumBaselinePercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/baseline/AnovaCageqiime_PhylumBaseline.txt");
anovaTableCage(pcaPhylumBiopsy,pcaPhylumBiopsyPercent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/biopsy/AnovaCageqiime_PhylumBiopsy.txt");
anovaTableCage(pcaPhylumDec21,pcaPhylumDec21Percent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/dec21/AnovaCageqiime_PhylumDec21.txt");
anovaTableCage(pcaPhylumDec30,pcaPhylumDec30Percent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/dec30/AnovaCageqiime_PhylumDec30.txt");
anovaTableCage(pcaPhylumJan5,pcaPhylumJan5Percent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/jan5/AnovaCageqiime_PhylumJan5.txt");
anovaTableCage(pcaPhylumJan12,pcaPhylumJan12Percent,"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/_dateSeparated/jan12/AnovaCageqiime_PhylumJan12.txt");