# This Jython script can be run from within FIJI's script editor
from ij import IJ
from net.clesperanto import CLIJ3

cle = CLIJ3.getInstance()

imp = IJ.openImage("https://github.com/clEsperanto/clesperanto_example_data/raw/main/Haase_MRT_tfl3d1.tif");
imp.show()

gpu_result = cle.maximum_x_projection(imp, None)
cle.imshow(gpu_result)

gpu_result = cle.maximum_y_projection(imp, None)
cle.imshow(gpu_result)

gpu_result = cle.maximum_z_projection(imp, None)
cle.imshow(gpu_result)
