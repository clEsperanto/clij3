package net.clesperanto;

import net.clesperanto.core.ArrayJ;
import net.clesperanto.kernels.*;


public abstract interface CLIJ3Ops {

    CLIJ3 clij = CLIJ3.getInstance();
    

    default ArrayJ absolute(Object input, Object output) {
        return Tier1.absolute(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ gaussian_blur(Object input, Object output, float sigma_x, float sigma_y, float sigma_z) {
        return Tier1.gaussianBlur(clij.device, clij.push(input), clij.push(output), sigma_x, sigma_y, sigma_z);
    }

}
