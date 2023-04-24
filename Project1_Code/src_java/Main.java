import myutils.Info;
import testload.TestImplement;

public class Main {
    public static void main(String[] args) {
        Info.infoInit();

//        normalImport();
        testLoader();
    }

    private static void normalImport() {
        Importer im = new Importer();
        long dur = im.batchInsert();

        System.out.printf("%d.%ds\n", dur / 1000, dur % 1000);
    }

    private static void testLoader() {
        TestImplement.startTestMain();
    }
}
