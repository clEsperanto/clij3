/*-
 * #%L
 * clij3
 * %%
 * Copyright (C) 2023 - 2025 Robert Haase, Stéphane Rigaud, Institut Pasteur Paris, DFG Cluster of Excellence "Physice of Life" TU Dresden
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the clesperanto nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package net.clesperanto.test;

import net.clesperanto.CLIJ3;
import net.clesperanto.core.ArrayJ;

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

        Img<FloatType> img = ij.op().convert().float32((Img) dataset);
        ij.ui().show("input", img);

        CLIJ3 cle = CLIJ3.getInstance();
        ArrayJ output = cle.gaussian_blur(img, null, 3, 0, 0);
        cle.imshow(output);

        RandomAccessibleInterval result = cle.pullRAI(output);
        ij.ui().show("output", result);
    }
}
