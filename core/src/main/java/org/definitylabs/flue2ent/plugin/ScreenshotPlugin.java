package org.definitylabs.flue2ent.plugin;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

public class ScreenshotPlugin implements WebDriverPlugin {

    private static final SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    private static final String SCREENSHOT_DIRECTORY_NAME = "screenshot";

    protected final TakesScreenshot driver;

    public ScreenshotPlugin(TakesScreenshot driver) {
        this.driver = driver;
    }

    public void takeAsBytes(Consumer<byte[]> consumer) {
        byte[] screenshotAsBytes = driver.getScreenshotAs(OutputType.BYTES);
        consumer.accept(screenshotAsBytes);
    }

    public void takeAsFile(Consumer<File> consumer) {
        File screenshotAsFile = driver.getScreenshotAs(OutputType.FILE);
        consumer.accept(screenshotAsFile);
    }

    public void take(String fileName) {
        takeAsFile(file -> {
            File screenshotDirectory = new File(SCREENSHOT_DIRECTORY_NAME);
            File screenshotFile = new File(screenshotDirectory, fileName);

            try {
                FileUtils.forceMkdir(screenshotDirectory);
                FileUtils.copyFile(file, screenshotFile);
            } catch (IOException e) {
                throw new RuntimeException("Cannot save screenshot file", e);
            }
        });
    }

    public void take() {
        String fileName = "screenshot_" + timestampFormat.format(new Date()) + ".png";
        take(fileName);
    }

}
