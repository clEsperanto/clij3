# This Jython script can be run from within FIJI's script editor
from ij import IJ
from net.clesperanto import CLIJ3

cle = CLIJ3.getInstance()

imp = IJ.openImage("/data/clesperanto/clij3/demo/blobs.tif")
imp.show()

gpu_image = cle.push(imp)

gpu_result = cle.gaussian_blur(gpu_image, None, 5, 5, 5)

imp_res = cle.pull(gpu_result)
imp_res.show()




