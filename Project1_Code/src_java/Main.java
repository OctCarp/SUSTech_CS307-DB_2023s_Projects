import myutils.Info;
import testload.TestImplement;

public class Main {
    public static void main(String[] args) {
        Info.infoInit();

//        normalImport();
        testLoader();
    }

    private static void normalImport() {
        long startTime = System.currentTimeMillis();

        Importer im = new Importer();
        im.batchInsert();

        long dur = System.currentTimeMillis() - startTime;
        System.out.printf("%d.%ds\n", dur / 1000, dur % 1000);
    }

    private static void testLoader() {
        TestImplement.startTestMain();
    }
}
