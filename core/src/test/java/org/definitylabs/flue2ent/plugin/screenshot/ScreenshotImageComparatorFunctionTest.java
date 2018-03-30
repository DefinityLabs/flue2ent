package org.definitylabs.flue2ent.plugin.screenshot;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ImageIO.class)
public class ScreenshotImageComparatorFunctionTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void to_whenParameterIsFile_callsTo() {
        File file = mock(File.class);
        BufferedImage referenceImage = mock(BufferedImage.class);
        BufferedImage image = mock(BufferedImage.class);

        PowerMockito.mockStatic(ImageIO.class, invocationOnMock -> image);

        AtomicBoolean called = new AtomicBoolean();
        ScreenshotImageDiff imageDiff = new ScreenshotImageDiff(referenceImage, image, new HashSet<>());

        ScreenshotImageComparatorFunction function = ref -> {
            called.set(true);
            return imageDiff;
        };

        ScreenshotImageDiff result = function.to(file);

        assertThat(called).isTrue();
        assertThat(result).isSameAs(imageDiff);
    }

    @Test
    public void to_whenParameterIsFile_whenImageReadThrowsException_throwsRuntimeException() throws Exception {
        File file = mock(File.class);

        PowerMockito.mockStatic(ImageIO.class);
        IOException toBeThrown = new IOException("Error");
        PowerMockito.doThrow(toBeThrown).when(ImageIO.class, "read", file);

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(toBeThrown.getMessage());

        ScreenshotImageComparatorFunction function = referenceImage -> null;

        function.to(file);
    }

}