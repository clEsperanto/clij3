package net.clesperanto.converters.implementations;

import net.clesperanto.wrapper.clesperantoj.BufferJ;
import net.clesperanto.wrapper.clesperantoj.MemoryJ;
import net.clesperanto.converters.AbstractConverter;
import net.clesperanto.converters.ConverterPlugin;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.array.ArrayImgs;
import org.scijava.plugin.Plugin;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

@Plugin(type = ConverterPlugin.class)
public class ObjectJToRandomAccessibleIntervalConverter extends AbstractConverter<BufferJ, RandomAccessibleInterval> {

    @Override
    public RandomAccessibleInterval convert(BufferJ source) {
        int numberOfPixels = (int) (source.getWidth() * source.getHeight() * source.getDepth());
        if (numberOfPixels <= 0) {
            throw new IllegalArgumentException("Wrong image size!");
        }

        long[] dimensions;
        if(source.getDepth() > 1) {
            dimensions = new long[]{
                    source.getWidth(),
                    source.getHeight(),
                    source.getDepth()
            };
        }else {
            dimensions = new long[]{
                    source.getWidth(),
                    source.getHeight()
            };
        }

        // Todo: in case of large images, we might use PlanarImgs!
        /*
        if (source.getDataType().getString().contains("char")) {

            ByteBuffer byteBuffer = ByteBuffer.allocate(numberOfPixels);
            cle._native.CharPull(byteBuffer, source);
            if (source.getDataType().getString().compareTo("uchar") == 0) {
                return ArrayImgs.bytes(byteBuffer.array(), dimensions);
            } else {
                byte[] array = byteBuffer.array();
                for (int i = 0; i < byteBuffer.limit(); i++) {
                    array[i] = (byte) (255 & array[i]);
                }
                return ArrayImgs.unsignedBytes(array, dimensions);
            }
        } else if (source.getDataType().getString().contains("short")) {

            ShortBuffer shortBuffer = ShortBuffer.allocate(numberOfPixels);
            cle._native.ShortPull(shortBuffer, source);
            if (source.getDataType().getString().compareTo("short") == 0) {
                return ArrayImgs.shorts(shortBuffer.array(), dimensions);
            } else {
                return ArrayImgs.unsignedShorts(shortBuffer.array(), dimensions);
            }
        } else*/
        if (source.getDataType().compareTo("float") == 0) {
            FloatBuffer floatBuffer = FloatBuffer.allocate(numberOfPixels);
            MemoryJ.readFloatBuffer(source, floatBuffer, numberOfPixels);
            return ArrayImgs.floats(floatBuffer.array(), dimensions);
        } else {
            throw new RuntimeException(
                    "Cannot convert image of type "
                            + source.getDataType());
        }
    }

    @Override
    public Class<BufferJ> getSourceType() {
        return BufferJ.class;
    }

    @Override
    public Class<RandomAccessibleInterval> getTargetType() {
        return RandomAccessibleInterval.class;
    }
}
