package hackeru.edu.sqlitenotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Sqlite Open Helper class
 */

public class NotesDBHelper extends SQLiteOpenHelper {
    private static final String createTableNotes =
            "CREATE TABLE " + NotesContract.NotesTable.tableName + "(" +
                    NotesContract.NotesTable.colID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NotesContract.NotesTable.colTitle + " TEXT NOT NULL," +
                    NotesContract.NotesTable.colContent + " TEXT" +
                    ");";

    public NotesDBHelper(Context context) {
        super(context,
                NotesContract.DB_NAME,
                null /*Don't know what's a cursor yet...*/,
                NotesContract.DB_VERSION
        );
    }


    //onCreate? when? once -> when we first use this class
    @Override
    public void onCreate(SQLiteDatabase db) {
        //create your tables here:

        db.execSQL(createTableNotes);
//        db.execSQL("CREATE TABLE " + NotesContract.NotesTable.tableName + "(" +
//                        NotesContract.NotesTable.colID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                        NotesContract.NotesTable.colTitle + " TEXT NOT NULL, " +
//                        NotesContract.NotesTable.colContent + " TEXT" +
//                ");");

        //Can create more tables:

      /*  db.execSQL("CREATE TABLE NOTES(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT NOT NULL," +
                "content TEXT " +
                ");");*/
    }

    //add the color column:
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE Notes");
        db.execSQL(createTableNotes);
        /* headlines:


        db.execSQL("CRATE TABLE TempTable...;");
        //Back Up-> Take all the notes from the old table And put into the temp table
        db.execSQL("DROP TABLE Notes;");
        db.execSQL("CREATE TABLE Notes (color TEXT...");
        db.execSQL("INSERT INTO Notes (from ... TempTable");
        */
    }


    //Contract Design Pattern:
    public static class NotesContract {
        public static final String DB_NAME = "NotesDB";
        public static final int DB_VERSION = 1;

        public static class NotesTable {
            public static String tableName = "Notes";

            public static String colID = "_ID";
            public static String colTitle = "title";
            public static String colContent = "content";
        }

        //Example: Don't really have this table.
        public static class UsersTable {
            public static String tableName = "Users";

            public static String colID = "_ID";
            public static String colFirstName = "firstName";
            public static String colLastName = "lastName";
        }
    }
}
