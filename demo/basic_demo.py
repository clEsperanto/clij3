# This Jython script can be run from within FIJI's script editor
from ij import IJ
from net.clesperanto import CLIJ3

cle = CLIJ3.getInstance()

imp = IJ.openImage("http://imagej.nih.gov/ij/images/blobs.gif");
imp.show()

gpu_image = cle.push(imp)

gpu_result = cle.gaussian_blur(gpu_image, None, 5, 5, 5)
cle.imshow(gpu_result)


binary = cle.threshold_otsu(imp, None)
cle.imshow(binary)



segmentation = cle.voronoi_otsu_labeling(imp, None, 3, 1)
cle.imshow(segmentation)





