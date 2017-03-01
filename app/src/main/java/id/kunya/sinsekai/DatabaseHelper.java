package id.kunya.sinsekai;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by muhammad on 23/02/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME= "database.db";
    public static final String TABLE_NAME="tb_characters";
    public static final String COL_1="ID";
    public static final String COL_2="ASK_TYPE";//picture or string
    public static final String COL_3="ASK";
    public static final String COL_4="ANSWER";
    public static final String COL_5="POINT";//point if you get benar
    public static final String COL_6="ANSWERED";//is the ask answered?
    public static final String COL_7="LOCKED";//is the ask locked?
    public static final String COL_8="LEVEL";
    static Context ctx;

    private static final String DB_PATH_SUFFIX = "/databases/";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
//        SQLiteDatabase db = this.getWritableDatabase();
        ctx = context;

    }

    public void CopyDataBaseFromAsset() throws IOException {

        InputStream myInput = ctx.getAssets().open(DATABASE_NAME);

        // Path to the just created empty db
        String outFileName = getDatabasePath();

        // if the path doesn't exist first, create it
        File f = new File(ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
        if (!f.exists())
            f.mkdir();

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }
    private static String getDatabasePath() {
        return ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX
                + DATABASE_NAME;
    }

    public SQLiteDatabase openDataBase() throws SQLException {
        File dbFile = ctx.getDatabasePath(DATABASE_NAME);

        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                System.out.println("Copying sucess from Assets folder");
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }

        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, ASK_TYPE TEXT, ASK TEXT, ANSWER TEXT, POINT TEXT,ANSWERED INTEGER,LOCKED INTEGER, LEVEL INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String ask_type, String ask, String answer, String point, String answered, String locked, String level){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,ask_type);
        contentValues.put(COL_3,ask);
        contentValues.put(COL_4,answer);
        contentValues.put(COL_5,point);
        contentValues.put(COL_6, answered);
        contentValues.put(COL_7, locked);
        contentValues.put(COL_8, level);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result== -1)
            return false;
        else
            return true;
    }

    public Cursor updateNewQuiz(int id, String locked){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor ons=db.rawQuery("UPDATE " + TABLE_NAME+" SET LOCKED='"+locked+"' WHERE ID='"+id+"'", null);
        return ons;
    }

    public Cursor updateThisQuiz(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor ons=db.rawQuery("UPDATE " + TABLE_NAME+" SET ANSWERED='1' WHERE ID='"+id+"'", null);
        return ons;
    }

    public Cursor showCharByID(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor ons=db.rawQuery("select * from " + TABLE_NAME+" where ID="+id, null);
        return ons;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public Cursor deleteCharByID(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("delete from "+TABLE_NAME+" where ID="+id,null);
        return res;
    }

    public String[][] getFromArrayData(int levelID) {
// TODO Auto-generated method stub
        try {
            String arrData[][] = null;
            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data
            String strSQL = "SELECT  * FROM " + TABLE_NAME+" where LEVEL="+levelID;
            Cursor cursor = db.rawQuery(strSQL, null);
            if(cursor != null)
            {
                if (cursor.moveToFirst()) {
                    arrData = new String[cursor.getCount()][cursor.getColumnCount()];
                    int i= 0;
                    do {
                        arrData[i][0] = cursor.getString(0);
                        arrData[i][1] = cursor.getString(1);
                        arrData[i][2] = cursor.getString(2);
                        arrData[i][3] = cursor.getString(3);
                        arrData[i][4] = cursor.getString(4);
                        arrData[i][5] = cursor.getString(5);
                        arrData[i][6] = cursor.getString(6);
                        arrData[i][7] = cursor.getString(7);
                        i++;
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
            return arrData;
        } catch (Exception e) {
            return null;
        }
    }

    // Select All Data
    public Cursor SelectAllData(int byLevel) {
        // TODO Auto-generated method stub

        try {
            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data

            String strSQL = "SELECT  ID As _id , * FROM " + TABLE_NAME+" where LEVEL="+byLevel;
            Cursor cursor = db.rawQuery(strSQL, null);

            return cursor;

        } catch (Exception e) {
            return null;
        }

    }

    public boolean updateData(String id, String ask_type, String ask, String answer, String point, String answered, String locked){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,ask_type);
        contentValues.put(COL_3,ask);
        contentValues.put(COL_4,answer);
        contentValues.put(COL_5,point);
        contentValues.put(COL_6, answered);
        contentValues.put(COL_7, locked);
        db.update(TABLE_NAME, contentValues, "id=?",new String[]{id});
        return true;

    }
//    public Cursor DisplayData()
//    {
//        //Select query
//        return db.rawQuery("SELECT * FROM studenttable", null);
//        //return db.query(TABLE_NAME, new String[]{NAME, ROLL,COURSE}, null, null, null, null, null);
//    }
}