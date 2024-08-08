package net.clesperanto.test;

import java.lang.reflect.Array;

import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;

import net.clesperanto.CLIJ3;
import net.clesperanto.core.ArrayJ;

public class YetAnotherPlayground {
    public static void main(String[] args) {

        new ImageJ();

        ImagePlus imp = IJ.openImage("./demo/blobs.tif");
        // IJ.run(imp, "32-bit", "");
        imp.show();

        CLIJ3 cle = CLIJ3.getInstance();
        
        ArrayJ labels = cle.create_like(imp);
        ArrayJ blurred = cle.gaussian_blur(imp, null, 10, 0, 0);
        ArrayJ binary = cle.threshold_otsu(blurred, null);
        cle.connected_components_labeling(binary, labels);


        cle.imshow(labels);
    }
}
