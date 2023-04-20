package net.clesperanto.test;

import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import net.clesperanto.CLIJ3;
import net.clesperanto.wrapper.clesperantoj.BufferJ;

public class YetAnotherPlayground {
    public static void main(String[] args) {

        new ImageJ();

        ImagePlus imp = IJ.openImage("./demo/blobs.tif");
        IJ.run(imp, "32-bit", "");
        imp.show();

        CLIJ3 cle = CLIJ3.getInstance();
        BufferJ blurred = cle.add_image_and_scalar(imp, null, 3);
        cle.imshow(blurred);

        IJ.run("3-3-2 RGB");
    }
}
