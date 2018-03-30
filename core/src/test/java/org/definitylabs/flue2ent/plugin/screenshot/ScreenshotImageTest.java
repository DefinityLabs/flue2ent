package org.definitylabs.flue2ent.plugin.screenshot;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.definitylabs.flue2ent.plugin.screenshot.ScreenshotRectangle.rectangle;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ScreenshotImageCrop.class, ScreenshotImageResize.class, ImageIO.class, ScreenshotImagePixelComparator.class})
public class ScreenshotImageTest {

    private BufferedImage image;

    private ScreenshotImage screenshotImage;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void beforeEach() {
        image = PowerMockito.mock(BufferedImage.class);
        screenshotImage = new ScreenshotImage(image);
    }

    @Test
    public void crop_callsScreenshotImageCrop() throws Exception {
        ScreenshotImageCrop screenshotImageCrop = PowerMockito.mock(ScreenshotImageCrop.class);

        ScreenshotRectangle rectangle = rectangle(2, 2, 2, 2);

        ScreenshotImage croppedImage = mock(ScreenshotImage.class);
        when(screenshotImageCrop.area(rectangle)).thenReturn(croppedImage);

        PowerMockito.mockStatic(ScreenshotImageCrop.class);

        Function<BufferedImage, ScreenshotImageCrop> cropping = image -> screenshotImageCrop;
        PowerMockito.when(ScreenshotImageCrop.class, "cropping").thenReturn(cropping);

        ScreenshotImage result = screenshotImage.crop(rectangle);

        verify(screenshotImageCrop).area(rectangle);

        assertThat(result).isSameAs(croppedImage);
    }

    @Test
    public void resize_callsScreenshotImageResize() throws Exception {
        ScreenshotImageResize screenshotImageResize = PowerMockito.mock(ScreenshotImageResize.class);

        ScreenshotImage resizedImage = mock(ScreenshotImage.class);
        when(screenshotImageResize.to(50)).thenReturn(resizedImage);

        PowerMockito.mockStatic(ScreenshotImageResize.class);

        Function<BufferedImage, ScreenshotImageResize> size = image -> screenshotImageResize;
        PowerMockito.when(ScreenshotImageResize.class, "size").thenReturn(size);

        ScreenshotImage result = screenshotImage.resize(50);

        verify(screenshotImageResize).to(50);

        assertThat(result).isSameAs(resizedImage);
    }

    @Test
    public void compareTo_callsScreenshotImagePixelComparator() throws Exception {
        BufferedImage referenceImage = mock(BufferedImage.class);

        ScreenshotImagePixelComparator screenshotImagePixelComparator = PowerMockito.mock(ScreenshotImagePixelComparator.class);

        ScreenshotImageDiff diff = new ScreenshotImageDiff(referenceImage, image, new HashSet<>());
        when(screenshotImagePixelComparator.compare(any(BufferedImage.class), eq(image))).thenReturn(diff);

        PowerMockito.mockStatic(ScreenshotImagePixelComparator.class);
        PowerMockito.when(ScreenshotImagePixelComparator.class, "pixelByPixel").thenReturn(screenshotImagePixelComparator);

        File file = mock(File.class);

        PowerMockito.mockStatic(ImageIO.class);
        PowerMockito.when(ImageIO.class, "read", file).thenReturn(referenceImage);

        screenshotImage.compareTo(file);
    }

    @Test
    public void getFile_callsImageIOWrite() throws Exception {
        PowerMockito.mockStatic(ImageIO.class);

        screenshotImage.getFile();

        PowerMockito.verifyStatic(ImageIO.class);
        ImageIO.write(eq(image), eq("png"), any(File.class));
    }

    @Test
    public void getFile_whenImageIOThrowsException_throwsRuntimeException() throws Exception {
        Method writeMethod = ImageIO.class.getMethod("write", RenderedImage.class, String.class, File.class);

        PowerMockito.mockStatic(ImageIO.class);
        PowerMockito.doThrow(new IOException("Error")).when(ImageIO.class, writeMethod).withArguments(eq(image), eq("png"), any(File.class));

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Error");

        screenshotImage.getFile();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void accept_callsConsumerAccept() throws Exception {
        PowerMockito.mockStatic(ImageIO.class);

        Consumer<File> consumer = mock(Consumer.class);
        screenshotImage.accept(consumer);

        verify(consumer).accept(any(File.class));
    }

    @Test
    public void equalsAndHashCode_areWellImplemented() {
        BufferedImage anotherImage = mock(BufferedImage.class);

        ScreenshotImage screenshotImage = new ScreenshotImage(image);
        assertThat(screenshotImage).isEqualTo(screenshotImage);
        assertThat(screenshotImage).isEqualTo(new ScreenshotImage(image));
        assertThat(screenshotImage).isNotEqualTo(new ScreenshotImage(anotherImage));
        assertThat(screenshotImage).isNotEqualTo(null);
        assertThat(screenshotImage).isNotEqualTo(new Object());

        assertThat(screenshotImage.hashCode()).isNotZero();
    }

}