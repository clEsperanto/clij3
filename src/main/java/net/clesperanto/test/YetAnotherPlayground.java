package net.clesperanto.test;

import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;

import net.clesperanto.CLIJ3;
import net.clesperanto.core.ArrayJ;

public class YetAnotherPlayground {
    public static void main(String[] args) {

        new ImageJ();

        ImagePlus imp = IJ.openImage("./demo/blobs.tif");
        imp.show();

        CLIJ3 cle = CLIJ3.getInstance();

        ArrayJ blurred = cle.gaussian_blur(imp, null, 10, 0, 0);
        ArrayJ binary = cle.threshold_otsu(blurred, null);
        ArrayJ labels = cle.connected_components_labeling(binary, null, "box");

        cle.imshow(labels);
        ImagePlus result = cle.pull(labels);

        // now print information about the result in ImageJ console
        IJ.log("ImagePlus object: " + result);
        IJ.log("Image dimensions: " + result.getWidth() + "x" + result.getHeight() + "x" + result.getNSlices());
        IJ.log("Image type: " + result.getType());
        IJ.log("Image title: " + result.getTitle());


        // now print information about the result in ImageJ console
        IJ.log("ArrayJ object: " + labels);
        IJ.log("ArrayJ dimensions: " + labels.width() + "x" + labels.height() + "x" + labels.depth());
        IJ.log("ArrayJ type: " + labels.dataType());
        
    }
}
