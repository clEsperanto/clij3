package net.clesperanto;

import ij.ImagePlus;
import net.clesperanto.converters.ConverterPlugin;
import net.clesperanto.converters.ConverterService;
import net.clesperanto.wrapper.clesperantoj.ProcessorJ;
import net.clesperanto.wrapper.clesperantoj.BufferJ;
import net.clesperanto.wrapper.clesperantoj.MemoryJ;
import net.clesperanto.wrapper.kernelj.Tier1;
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
        return MemoryJ.makeFloatBuffer(this.processor, source.getWidth(), source.getHeight(), source.getDepth(), "buffer");
    }

    private BufferJ create_like_if_none(Object source, Object target) {
        BufferJ sourceJ = push(source); // that might be not necessary and slow
        BufferJ targetJ = push(target); // that might be not necessary and slow
        if (targetJ != null) {
            return targetJ;
        }
        return create_like(sourceJ);
    }

    public BufferJ add_image_and_scalar(Object source, Object target, float scalar) {
        BufferJ sourceJ = push(source);
        BufferJ targetJ = create_like_if_none(sourceJ, target);
        Tier1.addImageAndScalar(processor, sourceJ, targetJ, scalar);

        return targetJ;
    }

    public BufferJ gaussian_blur(Object source, Object target, float sigma_x, float sigma_y, float sigma_z) {
        BufferJ sourceJ = push(source);
        BufferJ targetJ = create_like_if_none(sourceJ, target);
        Tier1.gaussianBlur(processor, sourceJ, targetJ, sigma_x, sigma_y, sigma_z);
        return targetJ;
    }
}
