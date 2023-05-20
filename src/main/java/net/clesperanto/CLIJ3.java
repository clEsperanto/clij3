package net.clesperanto;

import ij.ImagePlus;
import net.clesperanto.converters.ConverterPlugin;
import net.clesperanto.converters.ConverterService;
import net.clesperanto.wrapper.clesperantoj.ProcessorJ;
import net.clesperanto.wrapper.clesperantoj.BufferJ;
import net.clesperanto.wrapper.clesperantoj.MemoryJ;
import net.clesperanto.wrapper.kernelj.Tier1;
import net.clesperanto.wrapper.kernelj.Tier2;
import net.clesperanto.wrapper.kernelj.Tier3;
import net.clesperanto.wrapper.kernelj.Tier4;
import net.clesperanto.wrapper.kernelj.Tier5;
import net.clesperanto.wrapper.kernelj.Tier6;
import net.imglib2.RandomAccessibleInterval;
import org.scijava.Context;

public class CLIJ3 {

    ProcessorJ processor;

    @Deprecated
    public CLIJ3() {
        processor = new ProcessorJ();
    }

    private static CLIJ3 instance = null;
    public static CLIJ3 getInstance() {
        if (instance == null) {
            instance = new CLIJ3();
        }
        return instance;
    }

    @Deprecated
    public ProcessorJ getProcessor() {
        return processor;
    }

    public BufferJ push(Object image) {
        return convert(image, BufferJ.class);
    }

    public ImagePlus pull(Object image) {
        return convert(image, ImagePlus.class);
    }

    public RandomAccessibleInterval pullRAI(Object image) {
        return convert(image, RandomAccessibleInterval.class);
    }


    private ConverterService converterService = null;

    public <S, T> T convert(S source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        if (targetClass.isAssignableFrom(source.getClass())) {
            return (T) source;
        }
        synchronized (this) {
            //try {
                if (converterService == null) {
                    converterService = new Context(ConverterService.class).service(ConverterService.class);
                }
            //} catch (RuntimeException e) {
            //    converterService = FallBackCLIJConverterService.getInstance();
            //}
            converterService.setCLE(this);
            ConverterPlugin<S, T> converter = (ConverterPlugin<S, T>) converterService.getConverter(source.getClass(), targetClass);
            converter.setCLE(this);
            T result = converter.convert(source);

            return  result;
        }
    }

    public void imshow(Object gpu_image) {
        ImagePlus image = pull(gpu_image);
        image.show();
    }

    public BufferJ create(long width, long height, long depth) {
        return MemoryJ.makeFloatBuffer(this.processor, width, height, depth, "buffer");
    }


    public BufferJ create_like(BufferJ source) {
        // Todo: This should have the same pixel type as the source
        return MemoryJ.makeFloatBuffer(this.processor, source.getWidth(), source.getHeight(), source.getDepth(), "buffer");
    }

    public BufferJ create_labels_like(BufferJ source) {
        // Todo: this should be uint32
        return MemoryJ.makeFloatBuffer(this.processor, source.getWidth(), source.getHeight(), source.getDepth(), "buffer");
    }

    public BufferJ create_binary_like(BufferJ source) {
        // Todo: this should be uint8
        return MemoryJ.makeFloatBuffer(this.processor, source.getWidth(), source.getHeight(), source.getDepth(), "buffer");
    }

    public BufferJ create_2d_yz_like(BufferJ source) {
        // Todo: This should have the same pixel type as the source
        if (source.getDimension() == 3 && source.getDepth() > 1) {
            return MemoryJ.makeFloatBuffer(this.processor, source.getDepth(), source.getHeight(), 1, "buffer");
        } else {
            return MemoryJ.makeFloatBuffer(this.processor, 1, source.getHeight(), 1, "buffer");
        }
    }

    public BufferJ create_2d_zx_like(BufferJ source) {
        // Todo: This should have the same pixel type as the source
        if (source.getDimension() == 3 && source.getDepth() > 1) {
            return MemoryJ.makeFloatBuffer(this.processor, source.getDepth(), source.getWidth(), 1, "buffer");
        } else {
            return MemoryJ.makeFloatBuffer(this.processor, 1, source.getWidth(), 1, "buffer");
        }
    }

    public BufferJ create_2d_yx_like(BufferJ source) {
        // Todo: This should have the same pixel type as the source
        if (source.getDimension() == 3 && source.getDepth() > 1) {
            return MemoryJ.makeFloatBuffer(this.processor, source.getHeight(), source.getWidth(), 1, "buffer");
        } else {
            return MemoryJ.makeFloatBuffer(this.processor, 1, source.getWidth(), 1, "buffer");
        }
    }

    private BufferJ create_like_if_none(Object source, Object target) {
        BufferJ sourceJ = push(source); // that might be not necessary and slow
        BufferJ targetJ = push(target); // that might be not necessary and slow
        if (targetJ != null) {
            return targetJ;
        }
        return create_like(sourceJ);
    }

    private BufferJ create_labels_like_if_none(Object source, Object target) {
        BufferJ sourceJ = push(source); // that might be not necessary and slow
        BufferJ targetJ = push(target); // that might be not necessary and slow
        if (targetJ != null) {
            return targetJ;
        }
        return create_labels_like(sourceJ);
    }

    private BufferJ create_binary_like_if_none(Object source, Object target) {
        BufferJ sourceJ = push(source); // that might be not necessary and slow
        BufferJ targetJ = push(target); // that might be not necessary and slow
        if (targetJ != null) {
            return targetJ;
        }
        return create_binary_like(sourceJ);
    }

    private BufferJ create_2d_yz_like_if_none(Object source, Object target) {
        BufferJ sourceJ = push(source); // that might be not necessary and slow
        BufferJ targetJ = push(target); // that might be not necessary and slow
        if (targetJ != null) {
            return targetJ;
        }
        return create_2d_yz_like(sourceJ);
    }

    private BufferJ create_2d_zx_like_if_none(Object source, Object target) {
        BufferJ sourceJ = push(source); // that might be not necessary and slow
        BufferJ targetJ = push(target); // that might be not necessary and slow
        if (targetJ != null) {
            return targetJ;
        }
        return create_2d_zx_like(sourceJ);
    }

    private BufferJ create_2d_yx_like_if_none(Object source, Object target) {
        BufferJ sourceJ = push(source); // that might be not necessary and slow
        BufferJ targetJ = push(target); // that might be not necessary and slow
        if (targetJ != null) {
            return targetJ;
        }
        return create_2d_yx_like(sourceJ);
    }











    // GENERATOR //
    public BufferJ absolute(Object src, Object dst) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.absolute(processor, srcJ, dstJ);
        return dstJ;
    }
    
    
    public BufferJ add_image_and_scalar(Object src, Object dst, float value) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.addImageAndScalar(processor, srcJ, dstJ, value);
        return dstJ;
    }
    
    
    public BufferJ add_images_weighted(Object src1, Object src2, Object dst, float w1, float w2) {
        BufferJ src1J = push(src1);
        BufferJ src2J = push(src2);
        BufferJ dstJ = create_like_if_none(src1J, dst);
        Tier1.addImagesWeighted(processor, src1J, src2J, dstJ, w1, w2);
        return dstJ;
    }
    
    
    public BufferJ binary_and(Object src1, Object src2, Object dst) {
        BufferJ src1J = push(src1);
        BufferJ src2J = push(src2);
        BufferJ dstJ = create_like_if_none(src1J, dst);
        Tier1.binaryAnd(processor, src1J, src2J, dstJ);
        return dstJ;
    }
    
    
    public BufferJ binary_edge_detection(Object src, Object dst) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.binaryEdgeDetection(processor, srcJ, dstJ);
        return dstJ;
    }
    
    
    public BufferJ binary_not(Object src, Object dst) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.binaryNot(processor, srcJ, dstJ);
        return dstJ;
    }
    
    
    public BufferJ binary_or(Object src1, Object src2, Object dst) {
        BufferJ src1J = push(src1);
        BufferJ src2J = push(src2);
        BufferJ dstJ = create_like_if_none(src1J, dst);
        Tier1.binaryOr(processor, src1J, src2J, dstJ);
        return dstJ;
    }
    
    
    public BufferJ binary_subtract(Object src1, Object src2, Object dst) {
        BufferJ src1J = push(src1);
        BufferJ src2J = push(src2);
        BufferJ dstJ = create_like_if_none(src1J, dst);
        Tier1.binarySubtract(processor, src1J, src2J, dstJ);
        return dstJ;
    }
    
    
    public BufferJ binary_xor(Object src1, Object src2, Object dst) {
        BufferJ src1J = push(src1);
        BufferJ src2J = push(src2);
        BufferJ dstJ = create_like_if_none(src1J, dst);
        Tier1.binaryXor(processor, src1J, src2J, dstJ);
        return dstJ;
    }
    
    
    public BufferJ block_enumerate(Object src, Object sum, Object dst, int value) {
        BufferJ srcJ = push(src);
        BufferJ sumJ = push(sum);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.blockEnumerate(processor, srcJ, sumJ, dstJ, value);
        return dstJ;
    }
    
    
    public BufferJ convolve(Object src, Object convolve_kernel, Object dst) {
        BufferJ srcJ = push(src);
        BufferJ convolve_kernelJ = push(convolve_kernel);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.convolve(processor, srcJ, convolve_kernelJ, dstJ);
        return dstJ;
    }
    
    
    public BufferJ copy(Object src, Object dst) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.copy(processor, srcJ, dstJ);
        return dstJ;
    }
    
    
    public BufferJ crop(Object src, Object dst, int index0, int index1, int index2) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.crop(processor, srcJ, dstJ, index0, index1, index2);
        return dstJ;
    }
    
    
    public BufferJ detect_maxima(Object src, Object dst) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.detectMaxima(processor, srcJ, dstJ);
        return dstJ;
    }
    
    
    public BufferJ dilate_box(Object src, Object dst) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.dilateBox(processor, srcJ, dstJ);
        return dstJ;
    }
    
    
    public BufferJ dilate_sphere(Object src, Object dst) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.dilateSphere(processor, srcJ, dstJ);
        return dstJ;
    }
    
    
    public BufferJ divide_image_and_scalar(Object src, Object dst, float scalar) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.divideImageAndScalar(processor, srcJ, dstJ, scalar);
        return dstJ;
    }
    
    
    public BufferJ divide_images(Object src1, Object src2, Object dst) {
        BufferJ src1J = push(src1);
        BufferJ src2J = push(src2);
        BufferJ dstJ = create_like_if_none(src1J, dst);
        Tier1.divideImages(processor, src1J, src2J, dstJ);
        return dstJ;
    }
    
    
    public BufferJ equal_constant(Object src, Object dst, float value) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.equalConstant(processor, srcJ, dstJ, value);
        return dstJ;
    }
    
    
    public BufferJ equal(Object src1, Object src2, Object dst) {
        BufferJ src1J = push(src1);
        BufferJ src2J = push(src2);
        BufferJ dstJ = create_like_if_none(src1J, dst);
        Tier1.equal(processor, src1J, src2J, dstJ);
        return dstJ;
    }
    
    
    public BufferJ erode_box(Object src, Object dst) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.erodeBox(processor, srcJ, dstJ);
        return dstJ;
    }
    
    
    public BufferJ erode_sphere(Object src, Object dst) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.erodeSphere(processor, srcJ, dstJ);
        return dstJ;
    }
    
    
    public BufferJ flag_existing_labels(Object src, Object dst) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.flagExistingLabels(processor, srcJ, dstJ);
        return dstJ;
    }
    
    
    public BufferJ gaussian_blur(Object src, Object dst, float sigma_x, float sigma_y, float sigma_z) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.gaussianBlur(processor, srcJ, dstJ, sigma_x, sigma_y, sigma_z);
        return dstJ;
    }
    
    
    public BufferJ gradient_x(Object src, Object dst) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.gradientX(processor, srcJ, dstJ);
        return dstJ;
    }
    
    
    public BufferJ gradient_y(Object src, Object dst) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.gradientY(processor, srcJ, dstJ);
        return dstJ;
    }
    
    
    public BufferJ gradient_z(Object src, Object dst) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.gradientZ(processor, srcJ, dstJ);
        return dstJ;
    }
    
    
    public BufferJ greater_constant(Object src, Object dst, float value) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.greaterConstant(processor, srcJ, dstJ, value);
        return dstJ;
    }
    
    
    public BufferJ greater(Object src1, Object src2, Object dst) {
        BufferJ src1J = push(src1);
        BufferJ src2J = push(src2);
        BufferJ dstJ = create_like_if_none(src1J, dst);
        Tier1.greater(processor, src1J, src2J, dstJ);
        return dstJ;
    }
    
    
    public BufferJ greater_or_equal_constant(Object src, Object dst, float value) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.greaterOrEqualConstant(processor, srcJ, dstJ, value);
        return dstJ;
    }
    
    
    public BufferJ greater_or_equal(Object src1, Object src2, Object dst) {
        BufferJ src1J = push(src1);
        BufferJ src2J = push(src2);
        BufferJ dstJ = create_like_if_none(src1J, dst);
        Tier1.greaterOrEqual(processor, src1J, src2J, dstJ);
        return dstJ;
    }
    
    
    public BufferJ laplace_box(Object src, Object dst) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.laplaceBox(processor, srcJ, dstJ);
        return dstJ;
    }
    
    
    public BufferJ mask(Object src, Object mask, Object dst) {
        BufferJ srcJ = push(src);
        BufferJ maskJ = push(mask);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.mask(processor, srcJ, maskJ, dstJ);
        return dstJ;
    }
    
    
    public BufferJ maximum_box(Object src, Object dst, int radius_x, int radius_y, int radius_z) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.maximumBox(processor, srcJ, dstJ, radius_x, radius_y, radius_z);
        return dstJ;
    }
    
    
    public BufferJ maximum_images(Object src1, Object src2, Object dst) {
        BufferJ src1J = push(src1);
        BufferJ src2J = push(src2);
        BufferJ dstJ = create_like_if_none(src1J, dst);
        Tier1.maximumImages(processor, src1J, src2J, dstJ);
        return dstJ;
    }
    
    
    public BufferJ maximum_sphere(Object src, Object dst, float radius_x, float radius_y, float radius_z) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.maximumSphere(processor, srcJ, dstJ, radius_x, radius_y, radius_z);
        return dstJ;
    }
    
    
    public BufferJ maximum_x_projection(Object src, Object dst) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.maximumXProjection(processor, srcJ, dstJ);
        return dstJ;
    }
    
    
    public BufferJ maximum_y_projection(Object src, Object dst) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.maximumYProjection(processor, srcJ, dstJ);
        return dstJ;
    }
    
    
    public BufferJ maximum_z_projection(Object src, Object dst) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.maximumZProjection(processor, srcJ, dstJ);
        return dstJ;
    }
    
    
    public BufferJ mean_box(Object src, Object dst, float radius_x, float radius_y, float radius_z) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.meanBox(processor, srcJ, dstJ, radius_x, radius_y, radius_z);
        return dstJ;
    }
    
    
    public BufferJ mean_sphere(Object src, Object dst, float radius_x, float radius_y, float radius_z) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.meanSphere(processor, srcJ, dstJ, radius_x, radius_y, radius_z);
        return dstJ;
    }
    
    
    public BufferJ minimum_box(Object src, Object dst, int radius_x, int radius_y, int radius_z) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.minimumBox(processor, srcJ, dstJ, radius_x, radius_y, radius_z);
        return dstJ;
    }
    
    
    public BufferJ minimum_images(Object src1, Object src2, Object dst) {
        BufferJ src1J = push(src1);
        BufferJ src2J = push(src2);
        BufferJ dstJ = create_like_if_none(src1J, dst);
        Tier1.minimumImages(processor, src1J, src2J, dstJ);
        return dstJ;
    }
    
    
    public BufferJ minimum_sphere(Object src, Object dst, float radius_x, float radius_y, float radius_z) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.minimumSphere(processor, srcJ, dstJ, radius_x, radius_y, radius_z);
        return dstJ;
    }
    
    
    public BufferJ minimum_x_projection(Object src, Object dst) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.minimumXProjection(processor, srcJ, dstJ);
        return dstJ;
    }
    
    
    public BufferJ minimum_y_projection(Object src, Object dst) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.minimumYProjection(processor, srcJ, dstJ);
        return dstJ;
    }
    
    
    public BufferJ minimum_z_projection(Object src, Object dst) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.minimumZProjection(processor, srcJ, dstJ);
        return dstJ;
    }
    
    
    public BufferJ multiply_image_and_scalar(Object src, Object dst, float value) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.multiplyImageAndScalar(processor, srcJ, dstJ, value);
        return dstJ;
    }
    
    
    public BufferJ multiply_images(Object src1, Object src2, Object dst) {
        BufferJ src1J = push(src1);
        BufferJ src2J = push(src2);
        BufferJ dstJ = create_like_if_none(src1J, dst);
        Tier1.multiplyImages(processor, src1J, src2J, dstJ);
        return dstJ;
    }
    
    
    public BufferJ nonzero_minimum_box(Object src, Object dst, Object flag) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = push(dst);
        BufferJ flagJ = create_like_if_none(srcJ, flag);
        Tier1.nonzeroMinimumBox(processor, srcJ, dstJ, flagJ);
        return flagJ;
    }
    
    
    public BufferJ not_equal_constant(Object src, Object dst, float value) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.notEqualConstant(processor, srcJ, dstJ, value);
        return dstJ;
    }
    
    
    public BufferJ not_equal(Object src1, Object src2, Object dst) {
        BufferJ src1J = push(src1);
        BufferJ src2J = push(src2);
        BufferJ dstJ = create_like_if_none(src1J, dst);
        Tier1.notEqual(processor, src1J, src2J, dstJ);
        return dstJ;
    }
    
    
    public BufferJ onlyzero_overwrite_maximum_box(Object src, Object dst1, Object dst2) {
        BufferJ srcJ = push(src);
        BufferJ dst1J = push(dst1);
        BufferJ dst2J = create_like_if_none(srcJ, dst2);
        Tier1.onlyzeroOverwriteMaximumBox(processor, srcJ, dst1J, dst2J);
        return dst2J;
    }
    
    
    public BufferJ onlyzero_overwrite_maximum_diamond(Object src, Object dst1, Object dst2) {
        BufferJ srcJ = push(src);
        BufferJ dst1J = push(dst1);
        BufferJ dst2J = create_like_if_none(srcJ, dst2);
        Tier1.onlyzeroOverwriteMaximumDiamond(processor, srcJ, dst1J, dst2J);
        return dst2J;
    }
    
    
    public BufferJ power_images(Object src1, Object src2, Object dst) {
        BufferJ src1J = push(src1);
        BufferJ src2J = push(src2);
        BufferJ dstJ = create_like_if_none(src1J, dst);
        Tier1.powerImages(processor, src1J, src2J, dstJ);
        return dstJ;
    }
    
    
    public BufferJ power(Object src, Object dst, float scalar) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.power(processor, srcJ, dstJ, scalar);
        return dstJ;
    }
    
    
    public BufferJ replace_intensities(Object src, Object dst, Object map) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = push(dst);
        BufferJ mapJ = create_like_if_none(srcJ, map);
        Tier1.replaceIntensities(processor, srcJ, dstJ, mapJ);
        return mapJ;
    }
    
    
    public BufferJ replace_intensity(Object src, Object dst, float in_value, float out_value) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.replaceIntensity(processor, srcJ, dstJ, in_value, out_value);
        return dstJ;
    }
    
    
    public BufferJ set_column(Object src, int index, float value) {
        BufferJ srcJ = push(src);
        Tier1.setColumn(processor, srcJ, index, value);
        return srcJ;
    }
    
    
    public BufferJ set(Object src, float value) {
        BufferJ srcJ = push(src);
        Tier1.set(processor, srcJ, value);
        return srcJ;
    }
    
    
    public BufferJ set_nonzero_pixels_to_pixelindex(Object src, Object dst, float value) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.setNonzeroPixelsToPixelindex(processor, srcJ, dstJ, value);
        return dstJ;
    }
    
    
    public BufferJ smaller_constant(Object src, Object dst, float value) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.smallerConstant(processor, srcJ, dstJ, value);
        return dstJ;
    }
    
    
    public BufferJ smaller(Object src1, Object src2, Object dst) {
        BufferJ src1J = push(src1);
        BufferJ src2J = push(src2);
        BufferJ dstJ = create_like_if_none(src1J, dst);
        Tier1.smaller(processor, src1J, src2J, dstJ);
        return dstJ;
    }
    
    
    public BufferJ smaller_or_equal_constant(Object src, Object dst, float value) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.smallerOrEqualConstant(processor, srcJ, dstJ, value);
        return dstJ;
    }
    
    
    public BufferJ smaller_or_equal(Object src1, Object src2, Object dst) {
        BufferJ src1J = push(src1);
        BufferJ src2J = push(src2);
        BufferJ dstJ = create_like_if_none(src1J, dst);
        Tier1.smallerOrEqual(processor, src1J, src2J, dstJ);
        return dstJ;
    }
    
    
    public BufferJ sobel(Object src, Object dst) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.sobel(processor, srcJ, dstJ);
        return dstJ;
    }
    
    
    public BufferJ subtract_image_from_scalar(Object src, Object dst, float value) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.subtractImageFromScalar(processor, srcJ, dstJ, value);
        return dstJ;
    }
    
    
    public BufferJ sum_reduction_x(Object src, Object dst, int value) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.sumReductionX(processor, srcJ, dstJ, value);
        return dstJ;
    }
    
    
    public BufferJ sum_x_projection(Object src, Object dst) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.sumXProjection(processor, srcJ, dstJ);
        return dstJ;
    }
    
    
    public BufferJ sum_y_projection(Object src, Object dst) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.sumYProjection(processor, srcJ, dstJ);
        return dstJ;
    }
    
    
    public BufferJ sum_z_projection(Object src, Object dst) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier1.sumZProjection(processor, srcJ, dstJ);
        return dstJ;
    }
    


    public BufferJ dilate_labels(Object src, Object dst, float radius) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier2.dilateLabels(processor, srcJ, dstJ, radius);
        return dstJ;
    }
    
    
    public BufferJ extend_labeling_via_voronoi(Object src, Object dst) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier2.extendLabelingViaVoronoi(processor, srcJ, dstJ);
        return dstJ;
    }
    
    
    public BufferJ maximum_of_all_pixels(Object src, Object dst) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier2.maximumOfAllPixels(processor, srcJ, dstJ);
        return dstJ;
    }
    
    
    public BufferJ minimum_of_all_pixels(Object src, Object dst) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier2.minimumOfAllPixels(processor, srcJ, dstJ);
        return dstJ;
    }
    
    
    public BufferJ sum_of_all_pixels(Object src, Object dst) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier2.sumOfAllPixels(processor, srcJ, dstJ);
        return dstJ;
    }
    
    
    public BufferJ top_hat_box(Object src, Object dst, int radius_x, int radius_y, int radius_z) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier2.topHatBox(processor, srcJ, dstJ, radius_x, radius_y, radius_z);
        return dstJ;
    }
    


    public BufferJ close_index_gaps_in_label_map(Object src, Object dst, int value) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier3.closeIndexGapsInLabelMap(processor, srcJ, dstJ, value);
        return dstJ;
    }
    
    
    public BufferJ difference_of_gaussian(Object src, Object dst, float sigma1_x, float sigma1_y, float sigma1_z, float sigma2_x, float sigma2_y, float sigma2_z) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier3.differenceOfGaussian(processor, srcJ, dstJ, sigma1_x, sigma1_y, sigma1_z, sigma2_x, sigma2_y, sigma2_z);
        return dstJ;
    }
    
    
    public BufferJ histogram(Object src, Object dst, int bin, float min_intensity, float max_intensity) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier3.histogram(processor, srcJ, dstJ, bin, min_intensity, max_intensity);
        return dstJ;
    }
    


    public BufferJ connected_component_labeling_box(Object src, Object dst) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier4.connectedComponentLabelingBox(processor, srcJ, dstJ);
        return dstJ;
    }
    
    
    public BufferJ threshold_otsu(Object src, Object dst) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier4.thresholdOtsu(processor, srcJ, dstJ);
        return dstJ;
    }
    


    public BufferJ masked_voronoi_labeling(Object src, Object dst, Object mask) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = push(dst);
        BufferJ maskJ = create_like_if_none(srcJ, mask);
        Tier5.maskedVoronoiLabeling(processor, srcJ, dstJ, maskJ);
        return maskJ;
    }
    


    public BufferJ voronoi_otsu_labeling(Object src, Object dst, float sigma1, float sigma2) {
        BufferJ srcJ = push(src);
        BufferJ dstJ = create_like_if_none(srcJ, dst);
        Tier6.voronoiOtsuLabeling(processor, srcJ, dstJ, sigma1, sigma2);
        return dstJ;
    }
    // GENERATOR //
}
