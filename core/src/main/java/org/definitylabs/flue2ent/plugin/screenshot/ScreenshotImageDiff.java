package org.definitylabs.flue2ent.plugin.screenshot;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static org.definitylabs.flue2ent.plugin.screenshot.ImageUtils.createImage;
import static org.definitylabs.flue2ent.plugin.screenshot.ScreenshotImageHighlighterFactory.highlightingArea;

public final class ScreenshotImageDiff {

    private final BufferedImage referenceImage;
    private final BufferedImage image;
    private final Set<ScreenshotPoint> notSamePoints;

    public ScreenshotImageDiff(BufferedImage referenceImage, BufferedImage image, Set<ScreenshotPoint> notSamePoints) {
        this.referenceImage = referenceImage;
        this.image = image;
        this.notSamePoints = Collections.unmodifiableSet(notSamePoints);
    }

    public Set<ScreenshotPoint> getPoints() {
        return notSamePoints;
    }

    public ScreenshotImageDiff ignoring(ScreenshotRectangle rectangle) {
        Set<ScreenshotPoint> notSamePoints = this.notSamePoints.stream()
                .filter(point -> !rectangle.contains(point))
                .collect(Collectors.toSet());
        return new ScreenshotImageDiff(referenceImage, image, notSamePoints);
    }

    public ScreenshotImage image() {
        return image(highlightingArea());
    }

    public ScreenshotImage image(ScreenshotImageHighlighter highlighter) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage diffImage = createImage(width, height, image.getType());
        Graphics2D g = diffImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        highlighter.highlight(g, notSamePoints);
        g.dispose();

        return new ScreenshotImage(diffImage);
    }

    public boolean isEqual() {
        return notSamePoints.isEmpty();
    }

}
