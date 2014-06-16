package hha.xhb;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlLiteHelper extends SQLiteOpenHelper {

	private final static int VERSION = 1;

	public SqlLiteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public SqlLiteHelper(Context context) {
		super(context, SQLString.DB_NAME_STRING, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//����Main��
		db.execSQL(SQLString.CREATE_TABLE_MAIN_STRING);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
