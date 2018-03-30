package org.definitylabs.flue2ent.plugin.screenshot;

import org.junit.Test;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static org.definitylabs.flue2ent.plugin.screenshot.ScreenshotImageHighlighterFactory.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class ScreenshotImageHighlighterFactoryTest {

    @Test
    public void withoutHighlight_doNothing() {
        ScreenshotImageHighlighter highlighter = withoutHighlight();
        highlighter.highlight(null, null);
    }

    @Test
    public void highlightingPixels_whenThereAreNoPoints_doNothing() {
        ScreenshotImageHighlighter highlighter = highlightingPixels();
        highlighter.highlight(null, new HashSet<>());
    }

    @Test
    public void highlightingPixels_drawRedPoints() {
        Set<ScreenshotPoint> notSamePoints = new HashSet<>();
        notSamePoints.add(new ScreenshotPoint(2, 2));
        notSamePoints.add(new ScreenshotPoint(4, 4));

        Graphics2D g = mock(Graphics2D.class);

        ScreenshotImageHighlighter highlighter = highlightingPixels();
        highlighter.highlight(g, notSamePoints);

        verify(g).setColor(Color.RED);
        verify(g, times(2)).fillRect(anyInt(), anyInt(), anyInt(), anyInt());
        verify(g).fillRect(2, 2, 1, 1);
        verify(g).fillRect(4, 4, 1, 1);
    }

    @Test
    public void highlightingPixels_withColor_drawRedPoints() {
        Set<ScreenshotPoint> notSamePoints = new HashSet<>();
        notSamePoints.add(new ScreenshotPoint(2, 2));
        notSamePoints.add(new ScreenshotPoint(4, 4));

        Graphics2D g = mock(Graphics2D.class);

        ScreenshotImageHighlighter highlighter = highlightingPixels(Color.BLUE);
        highlighter.highlight(g, notSamePoints);

        verify(g).setColor(Color.BLUE);
        verify(g, times(2)).fillRect(anyInt(), anyInt(), anyInt(), anyInt());
        verify(g).fillRect(2, 2, 1, 1);
        verify(g).fillRect(4, 4, 1, 1);
    }

    @Test
    public void highlightingArea_whenThereAreNoPoints_doNothing() {
        ScreenshotImageHighlighter highlighter = highlightingArea();
        highlighter.highlight(null, new HashSet<>());
    }

    @Test
    public void highlightingArea_drawRedRectangle() {
        Set<ScreenshotPoint> notSamePoints = new HashSet<>();
        notSamePoints.add(new ScreenshotPoint(2, 2));
        notSamePoints.add(new ScreenshotPoint(4, 4));

        Graphics2D g = mock(Graphics2D.class);

        ScreenshotImageHighlighter highlighter = highlightingArea();
        highlighter.highlight(g, notSamePoints);

        verify(g).setColor(Color.RED);
        verify(g, times(1)).drawRect(anyInt(), anyInt(), anyInt(), anyInt());
        verify(g).drawRect(2, 2, 2, 2);
    }

    @Test
    public void highlightingArea_withColor_drawRedRectangle() {
        Set<ScreenshotPoint> notSamePoints = new HashSet<>();
        notSamePoints.add(new ScreenshotPoint(2, 2));
        notSamePoints.add(new ScreenshotPoint(4, 4));

        Graphics2D g = mock(Graphics2D.class);

        ScreenshotImageHighlighter highlighter = highlightingArea(Color.BLUE);
        highlighter.highlight(g, notSamePoints);

        verify(g).setColor(Color.BLUE);
        verify(g, times(1)).drawRect(anyInt(), anyInt(), anyInt(), anyInt());
        verify(g).drawRect(2, 2, 2, 2);
    }

}