wgsLevel1Comp = read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/WGSLevel1_CagePvalues.txt")
wgsLevel2Comp = read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/WGSLevel2_CagePvalues.txt")
wgsLevel1Comp = wgsLevel1Comp[1:20,]
wgsLevel2Comp = wgsLevel2Comp[1:20,]
#wgsLevel3Comp = read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcaPvalues/WGSLevel3_CagePvalues.txt")
#wgsLevel3Comp = wgsLevel3Comp[1:10,]
#sixteenSLevel1Comp = read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/Corrected_16SLevel1_CagePvalues.txt")
#sixteenSLevel2Comp = read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/Corrected_16SLevel2_CagePvalues.txt")

picrustLevel1Comp = read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/picrustLevel1_CagePvalues.txt")
picrustLevel2Comp = read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/picrustLevel2_CagePvalues.txt")
picrustLevel1Comp = picrustLevel1Comp[1:20,]
picrustLevel2Comp = picrustLevel2Comp[1:20,]
#sixteenSLevel2Comp = sixteenSLevel2Comp[1:10,]
phylumLevelComp = read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/cage/AnovaCageqiime_Phylum.txt");
classLevelComp = read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/cage/AnovaCageqiime_Class.txt");
classLevelComp = classLevelComp[1:20,]
orderLevelComp = read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/cage/AnovaCageqiime_Order.txt");
familyLevelComp = read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/cage/AnovaCageqiime_Family.txt");
genusLevelComp = read.delim("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPvalues/cage/AnovaCageqiime_Genus.txt");

pdf("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/Picrust_ComponentsLevel1.pdf")
plot(wgsLevel1Comp$Component,-log10(wgsLevel1Comp$p.value),xlab="Components",ylab="-Log10(p-value)",col="black",ylim=c(0,15),xlim=c(0,20),pch=19)
lines(wgsLevel1Comp$Component,-log10(wgsLevel1Comp$p.value),col="black")
abline(h=-log10(0.05),col="red")
#points(wgsLevel1Comp$Component,-log10(wgsLevel1Comp$p.value),col="black",pch=19)
#lines(wgsLevel1Comp$Component,-log10(wgsLevel1Comp$p.value), col="black")
points(picrustLevel1Comp$Component,-log10(picrustLevel1Comp$p.value),col="green3",pch=19)
lines(picrustLevel1Comp$Component,-log10(picrustLevel1Comp$p.value),col="green3")
points(phylumLevelComp$Component,-log10(phylumLevelComp$p.value),col="purple",pch=19)
lines(phylumLevelComp$Component,-log10(phylumLevelComp$p.value),col="purple")
legend("topright",c("Actual Functions","Picrust Predicted Functions","16S phylum level classifications","0.05 p-value"),lty=c(1,1,1,1,1),lwd=c(2,2,2,2,2),col=c("black","green3","purple","red"))
dev.off()


pdf("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcoaPlots/Picrust_ComponentsLevel2.pdf")
plot(wgsLevel2Comp$Component,-log10(wgsLevel2Comp$p.value),xlab="Components",ylab="-Log10(p-value)",col="black",ylim=c(0,15),pch=19)
lines(wgsLevel2Comp$Component,-log10(wgsLevel2Comp$p.value),col="black")
abline(h=-log10(0.05),col="red")
#points(wgsLevel2Comp$Component,-log10(wgsLevel2Comp$p.value),col="black",pch=19)
#lines(wgsLevel2Comp$Component,-log10(wgsLevel2Comp$p.value), col="black")
points(picrustLevel2Comp$Component,-log10(picrustLevel2Comp$p.value),col="green3",pch=19)
lines(picrustLevel2Comp$Component,-log10(picrustLevel2Comp$p.value),col="green3")
points(classLevelComp$Component,-log10(classLevelComp$p.value),col="purple",pch=19)
lines(classLevelComp$Component,-log10(classLevelComp$p.value),col="purple")
legend("topright",c("Actual Functions","Picrust Predicted Functions","16S class level classifications","0.05 p-value"),lty=c(1,1,1,1,1),lwd=c(2,2,2,2,2),col=c("black","green3","purple","red"))
dev.off()



pdf("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/pcas/pcaPlots/Corrected_ComponentsLevel1.pdf")
plot(sixteenSLevel1Comp$Component,-log10(sixteenSLevel1Comp$p.value),col="blue",xlab="Components",ylab="-Log10(p-value)",ylim=c(0,7),pch=19)
lines(sixteenSLevel1Comp$Component,-log10(sixteenSLevel1Comp$p.value),col="blue")
abline(h=-log10(0.05),col="red")
points(wgsLevel1Comp$Component,-log10(wgsLevel1Comp$p.value),pch=19)
lines(wgsLevel1Comp$Component,-log10(wgsLevel1Comp$p.value))
legend("topleft",c("Functions Predicted From 16S seqs","Actual Functions","0.05 p-value"),lty=c(1,1,1),lwd=c(2.5,2.5,2.5),col=c("blue","black","red"))
dev.off()