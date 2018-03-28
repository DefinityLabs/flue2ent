package org.definitylabs.flue2ent.plugin.screenshot;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public class ScreenshotImage {

    private final BufferedImage image;

    public ScreenshotImage(BufferedImage image) {
        this.image = image;
    }

    public ScreenshotImage crop(ScreenshotRectangle rectangle) {
        BufferedImage subImage = image.getSubimage(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        return new ScreenshotImage(subImage);
    }

    public ScreenshotImage resize(int percent) {
        int width = image.getWidth() * (percent / 100);
        int height = image.getHeight() * (percent / 100);

        BufferedImage resizedImage = new BufferedImage(width, height, image.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();

        return new ScreenshotImage(resizedImage);
    }

    public ScreenshotImageComparator compareTo(File file) {
        BufferedImage referenceImage;

        try {
            referenceImage = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        if (image.getWidth() != referenceImage.getWidth()
                || image.getHeight() != referenceImage.getHeight()) {
            throw new RuntimeException("The screenshot size is not the same");
        }

        Set<ScreenshotPoint> notSamePoints = new HashSet<>();

        for (int y = 0; y < referenceImage.getHeight(); y++) {
            for (int x = 0; x < referenceImage.getWidth(); x++) {
                if (image.getRGB(x, y) != referenceImage.getRGB(x, y)) {
                    notSamePoints.add(new ScreenshotPoint(x, y));
                }
            }
        }

        return new ScreenshotImageComparator(notSamePoints);
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

    public final class ScreenshotImageComparator {

        private final Set<ScreenshotPoint> notSamePoints;
        private final Rectangle differentArea;

        private ScreenshotImageComparator(Set<ScreenshotPoint> notSamePoints) {
            this.notSamePoints = notSamePoints;

            if (notSamePoints.isEmpty()) {
                differentArea = null;
            } else {
                IntSummaryStatistics xStatistics = notSamePoints.stream().mapToInt(ScreenshotPoint::getX).summaryStatistics();
                IntSummaryStatistics yStatistics = notSamePoints.stream().mapToInt(ScreenshotPoint::getY).summaryStatistics();
                int x = xStatistics.getMin();
                int y = yStatistics.getMin();
                int width = xStatistics.getMax() - x;
                int height = yStatistics.getMax() - y;
                differentArea = new Rectangle(x, y, width, height);
            }
        }

        public ScreenshotImageComparator ignoring(ScreenshotRectangle rectangle) {
            Set<ScreenshotPoint> notSamePoints = this.notSamePoints.stream()
                    .filter(point -> !rectangle.contains(point))
                    .collect(Collectors.toSet());
            return new ScreenshotImageComparator(notSamePoints);
        }

        public Rectangle getDifferentArea() {
            return differentArea;
        }

        public ScreenshotImage diff() {
            int width = image.getWidth();
            int height = image.getHeight();

            BufferedImage diffImage = new BufferedImage(width, height, image.getType());
            Graphics2D g = diffImage.createGraphics();
            g.drawImage(image, 0, 0, width, height, null);

            if (nonNull(differentArea)) {
                g.setColor(Color.RED);
                g.setStroke(new BasicStroke(3));
                g.drawRect(differentArea.x, differentArea.y, differentArea.width, differentArea.height);
            }

            g.dispose();

            return new ScreenshotImage(diffImage);
        }

        public boolean isEqual() {
            return notSamePoints.isEmpty();
        }
    }
}
