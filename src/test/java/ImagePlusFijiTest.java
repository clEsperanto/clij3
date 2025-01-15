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


import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;

import net.clesperanto.CLIJ3;
import net.clesperanto.core.ArrayJ;

import org.junit.Test;

public class ImagePlusFijiTest {

    @Test
    public void ImagePlusTest() throws Exception {

        ImagePlus imp = IJ.openImage("./demo/blobs.tif");

        CLIJ3 cle = CLIJ3.getInstance();

        ArrayJ blurred = cle.gaussian_blur(imp, null, 10, 0, 0);
        ArrayJ binary = cle.threshold_otsu(blurred, null);
        ArrayJ labels = cle.connected_components_labeling(binary, null, "box");
        float nbLabels = cle.maximum_of_all_pixels(labels);

        ImagePlus result = cle.pull(labels);
        
        assert(nbLabels == 27.0);
    }
}
