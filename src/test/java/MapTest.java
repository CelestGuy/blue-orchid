import dev.theoduval.orchid.Map;
import dev.theoduval.orchid.io.MapIO;
import org.junit.Test;

import java.io.File;

import static java.lang.Integer.parseInt;

public class MapTest {
    @Test
    public void testMap() {
        File boMap = new File("test.bomap");

        try {
            Map map = MapIO.readMap(boMap);
            System.out.println();
            //ssert map.getSectors().size() == 2;
        } catch (Exception ignored) {
            System.err.println(ignored);
        }
    }
}
