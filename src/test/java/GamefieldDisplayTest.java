import controller.impl.Controller;
import controller.impl.GamefieldAdapter;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lars on 08.10.2016.
 */
public class GamefieldDisplayTest {

    @Test
    public void test() {
        Controller controller = new Controller(new GamefieldAdapter());
        controller.setStone(2);
        String log;
        Map<Integer, Character> map = new HashMap<>();
        for(int i = 1; i <= 24; i++) {
            map.put(i, controller.getVertexColor(i));
        }
        log =   "%1-----------%2-----------%3\t\t"          + "1-----------2-----------3\n"  +
                "|           |           |\t\t"             + "|           |           |\n"  +
                "|   %4-------%5-------%6   |\t\t"          + "|   4-------5-------6   |\n"  +
                "|   |       |       |   |\t\t"             + "|   |       |       |   |\n"  +
                "|   |   %7---%8---%9   |   |\t\t"          + "|   |   7---8---9   |   |\n"  +
                "|   |   |       |   |   |\t\t"             + "|   |   |       |   |   |\n"  +
                "%10---%11---%12       %13---%14---%15\t\t" + "10--11--12      13--14--15\n" +
                "|   |   |       |   |   |\t\t"             + "|   |   |       |   |   |\n"  +
                "|   |   %16---%17---%18   |   |\t\t"       + "|   |   16--17--18  |   |\n"  +
                "|   |       |       |   |\t\t"             + "|   |       |       |   |\n"  +
                "|   %19-------%20-------%21   |\t\t"       + "|   19------20------21  |\n"  +
                "|           |           |\t\t"             + "|           |           |\n"  +
                "%22-----------%23-----------%24\t\t"       + "22----------23----------24\n";

        formatGamefield(log, map);
    }

    private void formatGamefield(String log, Map<Integer, Character> map) {
        StringBuilder colors = new StringBuilder(log);
        for(Map.Entry<Integer, Character> e : map.entrySet()) {
            String key = e.getKey().toString();
            String value = e.getValue().toString();
            String pattern = "%" + key;
            int start = colors.indexOf(pattern);

            colors.replace(start, start + pattern.length(), value);
        }

        System.out.println(colors.toString());
    }
}
