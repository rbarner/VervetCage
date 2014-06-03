
#Load in whole genome pcoa pvalues
wgsLevel1Comp = read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/WGSLevel1_CagePvalues.txt")
wgsLevel2Comp = read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/WGSLevel2_CagePvalues.txt")

#Only include the first four components for level1 and first 20 components for level2
wgsLevel1Comp = wgsLevel1Comp[1:4,]
wgsLevel2Comp = wgsLevel2Comp[1:20,]

#Load in picrust predicted functions
picrustLevel1Comp = read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/picrustLevel1_CagePvalues.txt")
picrustLevel2Comp = read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/picrustLevel2_CagePvalues.txt")

#Only inclue the first four components for level1 and fist 20 components for level2
picrustLevel1Comp = picrustLevel1Comp[1:4,]
picrustLevel2Comp = picrustLevel2Comp[1:20,]

#Load in components for the classifications at phylum and class level
phylumLevelComp = read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/cage/AnovaCageqiime_Phylum.txt");
classLevelComp = read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/cage/AnovaCageqiime_Class.txt");
classLevelComp = classLevelComp[1:20,]

#Create plot with component on the x axis and the -log10 of p-value on the y-axis (Function Level 1 and phylum level)
pdf("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/Picrust_ComponentsLevel1.pdf")
plot(wgsLevel1Comp$Component,-log10(wgsLevel1Comp$p.value),xlab="Components",ylab="-Log10(p-value)",col="black",ylim=c(0,15),xlim=c(0,20),pch=19)
lines(wgsLevel1Comp$Component,-log10(wgsLevel1Comp$p.value),col="black")
abline(h=-log10(0.05),col="red")
points(picrustLevel1Comp$Component,-log10(picrustLevel1Comp$p.value),col="green3",pch=19)
lines(picrustLevel1Comp$Component,-log10(picrustLevel1Comp$p.value),col="green3")
points(phylumLevelComp$Component,-log10(phylumLevelComp$p.value),col="purple",pch=19)
lines(phylumLevelComp$Component,-log10(phylumLevelComp$p.value),col="purple")\\
legend("topright",c("Actual Functions","Picrust Predicted Functions","16S phylum level classifications","0.05 p-value"),lty=c(1,1,1,1,1),lwd=c(2,2,2,2,2),col=c("black","green3","purple","red"))
dev.off()

#Create plot with component on the x axis and the -log10 of p-value on the y-axis (Function Level 2 and class level)
pdf("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/Picrust_ComponentsLevel2.pdf")
plot(wgsLevel2Comp$Component,-log10(wgsLevel2Comp$p.value),xlab="Components",ylab="-Log10(p-value)",col="black",ylim=c(0,15),pch=19)
lines(wgsLevel2Comp$Component,-log10(wgsLevel2Comp$p.value),col="black")
abline(h=-log10(0.05),col="red")
points(picrustLevel2Comp$Component,-log10(picrustLevel2Comp$p.value),col="green3",pch=19)
lines(picrustLevel2Comp$Component,-log10(picrustLevel2Comp$p.value),col="green3")
points(classLevelComp$Component,-log10(classLevelComp$p.value),col="purple",pch=19)
lines(classLevelComp$Component,-log10(classLevelComp$p.value),col="purple")
legend("topright",c("Actual Functions","Picrust Predicted Functions","16S class level classifications","0.05 p-value"),lty=c(1,1,1,1,1),lwd=c(2,2,2,2,2),col=c("black","green3","purple","red"))
dev.off()
