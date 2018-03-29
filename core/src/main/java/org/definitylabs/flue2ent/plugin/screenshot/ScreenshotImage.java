package org.definitylabs.flue2ent.plugin.screenshot;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.definitylabs.flue2ent.plugin.screenshot.ScreenshotImageCrop.cropping;
import static org.definitylabs.flue2ent.plugin.screenshot.ScreenshotImagePixelComparator.pixelByPixel;
import static org.definitylabs.flue2ent.plugin.screenshot.ScreenshotImageResize.size;

public class ScreenshotImage {

    private final BufferedImage image;

    public ScreenshotImage(BufferedImage image) {
        this.image = image;
    }

    public ScreenshotImage crop(ScreenshotRectangle rectangle) {
        return adjust(cropping()).area(rectangle);
    }

    public ScreenshotImage resize(int percent) {
        return adjust(size()).to(percent);
    }

    public <T> T adjust(Function<BufferedImage, T> service) {
        return service.apply(image);
    }

    public ScreenshotImageComparatorFunction compare(ScreenshotImageComparator comparator) {
        return referenceImage -> comparator.compare(referenceImage, image);
    }

    public ScreenshotImageDiff compareTo(File file) {
        return compare(pixelByPixel()).to(file);
    }

    public File getFile() {
        try {
            File tmpFile = File.createTempFile("partial-screenshot", ".png");
            ImageIO.write(image, "png", tmpFile);
            return tmpFile;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void accept(Consumer<File> consumer) {
        consumer.accept(getFile());
    }

}
