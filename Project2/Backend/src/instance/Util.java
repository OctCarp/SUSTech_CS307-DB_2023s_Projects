package instance;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static boolean writeLog(String level, String username, String operation, boolean succeed) throws IOException {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        FileWriter log = new FileWriter(dateFormat.format(date), true);
        String info = "[%s] [%s] %b | %s %s".formatted(timeFormat.format(date), level, succeed, username, operation);
        log.write(info);
        log.close();
        return true;
    }

}