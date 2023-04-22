import myutils.Info;

public class Main {
    public static void main(String[] args) {
        Info.infoInit();

        normalImport();
    }

    private static void normalImport() {
        long startTime = System.currentTimeMillis();

        Importer im = new Importer();
        im.batchInsert();

        long dur = System.currentTimeMillis() - startTime;
        System.out.printf("%d.%ds\n", dur / 1000, dur % 1000);
    }
}
