package org.definitylabs.flue2ent.plugin.screenshot;

import java.awt.*;
import java.util.IntSummaryStatistics;
import java.util.Set;

public class ScreenshotImageHighlighterFactory {

    private ScreenshotImageHighlighterFactory() {

    }

    public static ScreenshotImageHighlighter withoutHighlight() {
        return (g, notSamePoints) -> {};
    }

    public static ScreenshotImageHighlighter highlightingPixels() {
        return highlightingPixels(Color.RED);
    }

    public static ScreenshotImageHighlighter highlightingPixels(Color color) {
        return (g, notSamePoints) -> {
            if (!notSamePoints.isEmpty()) {
                g.setColor(color);
                notSamePoints.forEach(point -> g.fillRect(point.getX(), point.getY(), 1, 1));
            }
        };
    }

    public static ScreenshotImageHighlighter highlightingArea() {
        return highlightingArea(Color.RED);
    }

    public static ScreenshotImageHighlighter highlightingArea(Color color) {
        return highlightingArea(color, new BasicStroke(3));
    }

    public static ScreenshotImageHighlighter highlightingArea(Color color, Stroke stroke) {
        return (g, notSamePoints) -> {
            if (!notSamePoints.isEmpty()) {
                g.setColor(color);
                g.setStroke(stroke);

                Rectangle differentArea = getArea(notSamePoints);
                g.drawRect(differentArea.x, differentArea.y, differentArea.width, differentArea.height);
            }
        };
    }

    private static Rectangle getArea(Set<ScreenshotPoint> notSamePoints) {
        IntSummaryStatistics xStatistics = notSamePoints.stream().mapToInt(ScreenshotPoint::getX).summaryStatistics();
        IntSummaryStatistics yStatistics = notSamePoints.stream().mapToInt(ScreenshotPoint::getY).summaryStatistics();
        int x = xStatistics.getMin();
        int y = yStatistics.getMin();
        int width = xStatistics.getMax() - x;
        int height = yStatistics.getMax() - y;
        return new Rectangle(x, y, width, height);
    }

}
