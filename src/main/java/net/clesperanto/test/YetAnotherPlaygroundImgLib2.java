package net.clesperanto.test;

import net.clesperanto.CLIJ3;
import net.clesperanto.wrapper.clesperantoj.BufferJ;
import net.imagej.Dataset;
import net.imagej.ImageJ;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.Img;
import net.imglib2.type.numeric.real.FloatType;

public class YetAnotherPlaygroundImgLib2 {

    final static net.imagej.ImageJ ij = new ImageJ();

    public static void main(String[] args) throws Exception {
        ij.launch(args);

        Dataset dataset = (Dataset) ij.io().open("./demo/boats.tif");

        Img<FloatType> img = ij.op().convert().float32((Img)dataset);
        ij.ui().show("input", img);

        CLIJ3 cle = CLIJ3.getInstance();
        BufferJ output = cle.add_image_and_scalar(img, null, 3);
        cle.imshow(output);

        RandomAccessibleInterval result = cle.pullRAI(output);
        ij.ui().show("output", result);
    }
}
