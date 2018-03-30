package org.definitylabs.flue2ent.plugin;

import org.apache.commons.io.FileUtils;
import org.definitylabs.flue2ent.plugin.screenshot.ScreenshotImage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ImageIO.class)
public class ScreenshotPluginTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void takeAsBytes_executesConsumer() {
        Consumer<byte[]> mockedConsumer = mock(Consumer.class);

        byte[] screenshotBytes = new byte[0];

        RemoteWebDriver mockedDriver = mock(RemoteWebDriver.class);
        when(mockedDriver.getScreenshotAs(OutputType.BYTES)).thenReturn(screenshotBytes);

        ScreenshotPlugin screenshotPlugin = new ScreenshotPlugin(mockedDriver);
        screenshotPlugin.takeAsBytes(mockedConsumer);

        verify(mockedConsumer).accept(screenshotBytes);
    }

    @Test
    public void takeAsFile_executesConsumer() {
        Consumer<File> mockedConsumer = mock(Consumer.class);

        File file = mock(File.class);

        RemoteWebDriver mockedDriver = mock(RemoteWebDriver.class);
        when(mockedDriver.getScreenshotAs(OutputType.FILE)).thenReturn(file);

        ScreenshotPlugin screenshotPlugin = new ScreenshotPlugin(mockedDriver);
        screenshotPlugin.takeAsFile(mockedConsumer);

        verify(mockedConsumer).accept(file);
    }

    @Test
    public void take_savesFileToScreenshotDirectory() throws IOException {
        File file = File.createTempFile("temporary", ".png");

        RemoteWebDriver mockedDriver = mock(RemoteWebDriver.class);
        when(mockedDriver.getScreenshotAs(OutputType.FILE)).thenReturn(file);

        ScreenshotPlugin screenshotPlugin = new ScreenshotPlugin(mockedDriver);
        screenshotPlugin.take();

        File folder = new File("screenshot");
        assertThat(folder.exists()).isTrue();
        assertThat(folder.isDirectory()).isTrue();
        assertThat(folder.listFiles()).hasSize(1);

        FileUtils.forceDeleteOnExit(folder);
    }

    @Test
    public void take_whenCopyThrowsIOException_throwsRuntimeException() throws IOException {
        File file = mock(File.class);

        RemoteWebDriver mockedDriver = mock(RemoteWebDriver.class);
        when(mockedDriver.getScreenshotAs(OutputType.FILE)).thenReturn(file);

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Cannot save screenshot file");

        ScreenshotPlugin screenshotPlugin = new ScreenshotPlugin(mockedDriver);

        try {
            screenshotPlugin.take();
        } finally {
            File folder = new File("screenshot");
            FileUtils.forceDeleteOnExit(folder);
        }
    }

    @Test
    public void takeAnd_returnsScreenshotImage() throws Exception {
        File file = mock(File.class);

        RemoteWebDriver mockedDriver = mock(RemoteWebDriver.class);
        when(mockedDriver.getScreenshotAs(OutputType.FILE)).thenReturn(file);

        PowerMockito.mockStatic(ImageIO.class);
        BufferedImage image = mock(BufferedImage.class);
        PowerMockito.when(ImageIO.class, "read", file).thenReturn(image);

        ScreenshotPlugin screenshotPlugin = new ScreenshotPlugin(mockedDriver);
        ScreenshotImage screenshotImage = screenshotPlugin.takeAnd();

        assertThat(screenshotImage).isEqualTo(new ScreenshotImage(image));
    }

    @Test
    public void takeAnd_whenImageIOWriteThrowsIOException_throwsRuntimeException() throws Exception {
        File file = mock(File.class);

        RemoteWebDriver mockedDriver = mock(RemoteWebDriver.class);
        when(mockedDriver.getScreenshotAs(OutputType.FILE)).thenReturn(file);

        PowerMockito.mockStatic(ImageIO.class);
        PowerMockito.doThrow(new IOException("Error")).when(ImageIO.class, "read", file);

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Error");

        ScreenshotPlugin screenshotPlugin = new ScreenshotPlugin(mockedDriver);
        screenshotPlugin.takeAnd();
    }

}