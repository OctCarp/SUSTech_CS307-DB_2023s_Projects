package testload;

import myutils.Ins;

import java.sql.SQLException;
import java.sql.Timestamp;

public class TestImplement {
    public static void startTestMain() {
        TestInfo.init();
        testLoader();
    }

    private static void testLoader() {
        AbstractLoaders t1 = new Awful();
        t1.testStart();
        AbstractLoaders t2 = new Connect();
        t2.testStart();
        AbstractLoaders t3 = new Prepare();
        t3.testStart();
        AbstractLoaders t4 = new Transaction();
        t4.testStart();
        AbstractLoaders t5 = new Batch();
        t5.testStart();
    }
}

class Awful extends NormalStmt {
    @Override
    public String toString() {
        return "Awful Loader";
    }

    @Override
    void testStart() {
        clearDataInit();

        startTime = System.currentTimeMillis();
        insLogic();
        endTime = System.currentTimeMillis();

        printMessage();
    }

    @Override
    void insertAuthor(int a_id, String author, Timestamp time, String author_id, String phone) {
        getTestConnection();
        try {
            if (conn != null) {
                stmt = conn.createStatement();
                stmt.execute(String.format(TestInfo.insFormat, a_id, author, time.toString(), author_id, phone));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        closeConnect();

        cnt++;
        if (cnt % BATCH_SIZE == 0) {
            System.out.printf("%s: insert %d data during about %d seconds!\n",
                    this, cnt, (System.currentTimeMillis() - startTime) / 1000);
        }
    }
}

class Connect extends NormalStmt {
    @Override
    public String toString() {
        return "Connect Loader";
    }

    @Override
    void testStart() {
        clearDataInit();

        startTime = System.currentTimeMillis();

        getTestConnection();
        insLogic();
        closeConnect();

        endTime = System.currentTimeMillis();

        printMessage();
    }

    @Override
    void insertAuthor(int a_id, String author, Timestamp time, String author_id, String phone) {
        try {
            if (conn != null) {
                stmt = conn.createStatement();
                stmt.execute(String.format(TestInfo.insFormat, a_id, author, time.toString(), author_id, phone));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        cnt++;
    }
}

class Prepare extends PrepareStmt {
    @Override
    public String toString() {
        return "Prepare Loader";
    }

    @Override
    void testStart() {
        clearDataInit();

        startTime = System.currentTimeMillis();

        getTestConnection();
        setPrepareStatement();
        insLogic();
        closeConnect();

        endTime = System.currentTimeMillis();

        printMessage();
    }

    @Override
    void insertAuthor(int a_id, String author, Timestamp time, String author_id, String phone) {
        cnt++;

        if (conn != null) {
            prepareAuthor(a_id, author, time, author_id, phone);
        }
    }
}

class Transaction extends PrepareStmt {
    @Override
    public String toString() {
        return "Transaction Loader";
    }

    @Override
    void testStart() {
        clearDataInit();

        startTime = System.currentTimeMillis();

        try {
            getTestConnection();
            setPrepareStatement();

            conn.setAutoCommit(false);
            insLogic();
            conn.commit();

            closeConnect();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

        endTime = System.currentTimeMillis();

        printMessage();
    }

    @Override
    void insertAuthor(int a_id, String author, Timestamp time, String author_id, String phone) {
        if (conn != null) {
            prepareAuthor(a_id, author, time, author_id, phone);
        }

        cnt++;
    }
}

class Batch extends PrepareStmt {
    @Override
    public String toString() {
        return "Batch Loader";
    }

    @Override
    void testStart() {
        clearDataInit();

        startTime = System.currentTimeMillis();

        try {
            getTestConnection();
            setPrepareStatement();

            conn.setAutoCommit(false);
            insLogic();
            conn.commit();

            closeConnect();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

        endTime = System.currentTimeMillis();

        printMessage();
    }

    @Override
    void prepareAuthor(int a_id, String name, Timestamp reg_time, String author_id, String phone) {
        Ins.author_bat(pStmt, a_id, name, reg_time, author_id, phone);
    }

    @Override
    void insertAuthor(int a_id, String author, Timestamp time, String author_id, String phone) {
        if (conn != null) {
            prepareAuthor(a_id, author, time, author_id, phone);
        }

        cnt++;
        try {
            if (cnt % BATCH_SIZE == 0) {
                pStmt.executeBatch();
                pStmt.clearBatch();
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }
}
