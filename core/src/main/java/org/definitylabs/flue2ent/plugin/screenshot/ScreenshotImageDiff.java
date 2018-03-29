package org.definitylabs.flue2ent.plugin.screenshot;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.IntSummaryStatistics;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public final class ScreenshotImageDiff {

    private final BufferedImage image;
    private final Set<ScreenshotPoint> notSamePoints;
    private final Rectangle differentArea;

    public ScreenshotImageDiff(BufferedImage image, Set<ScreenshotPoint> notSamePoints) {
        this.image = image;
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

    public ScreenshotImageDiff ignoring(ScreenshotRectangle rectangle) {
        Set<ScreenshotPoint> notSamePoints = this.notSamePoints.stream()
                .filter(point -> !rectangle.contains(point))
                .collect(Collectors.toSet());
        return new ScreenshotImageDiff(image, notSamePoints);
    }

    public Rectangle getDifferentArea() {
        return differentArea;
    }

    public ScreenshotImage image() {
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
