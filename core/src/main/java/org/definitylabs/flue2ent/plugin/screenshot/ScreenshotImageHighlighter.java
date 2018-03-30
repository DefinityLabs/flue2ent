package org.definitylabs.flue2ent.plugin.screenshot;

import java.awt.*;
import java.util.Set;

@FunctionalInterface
public interface ScreenshotImageHighlighter {

    void highlight(Graphics2D g, Set<ScreenshotPoint> notSamePoints);

}
