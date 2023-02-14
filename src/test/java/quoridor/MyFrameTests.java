package quoridor;

import jdk.jfr.Threshold;
import org.junit.jupiter.api.Test;
import quoridor.graphics.MyFrame;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MyFrameTests {

    @Test
    void testingBasicGraphicFrame() throws InterruptedException {
        MyFrame myFrame = new MyFrame();

        assertNotNull(myFrame);
    }
}
