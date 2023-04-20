public class Main {
    public static void main(String[] args) {
        Info.infoInit();

        long startTime = System.currentTimeMillis();
        normalImport();
        long dur = System.currentTimeMillis() - startTime;

        System.out.println(dur / 1000 + "." + dur % 1009 + "s");
    }

    private static void normalImport() {
        Importer im = new Importer();
        im.batchInsert();
    }
}
