package hha.xhb;
import hha.main.MainActivity;
import hha.mode.Mode;
import hha.mode.PArray;
import hha.mode.subclass.HealthyMode;
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

	SQLHelper helper;
	public SqlLiteHelper(Context context,SQLHelper helper) {
		super(context, SQLString.DB_NAME_STRING, null, VERSION);
		this.helper = helper;
		
	}

	private void Creating(PArray atts, String pclass) {
		for (int i = 0; i < atts.list.length; i++) {

			helper.createTable(atts.list[i], new String[] { "time", "value" },
					new String[] { "varchar(30)", "double" });
//			helper.add("Main", new String[] { "name", "unit", "fre", "class" },
//					new Object[] { "dbt_" + atts.list[i], atts.unit[i],
//							atts.fre, pclass });
		}
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		//����Main��
		this.mDefaultWritableDatabase = db;
		db.execSQL(SQLString.CREATE_TABLE_MAIN_STRING);
//		MainActivity.main.Show("每次都执行？", "只执行一次");
		HealthyMode hmode = (HealthyMode) Mode.getMode("healthy");
		helper.createTable("data", new String[] { "key", "value" },
				new String[] { "varchar(50)", "varchar(200)" });

		helper.createTable("relationship", new String[] { "name1", "name2",
				"relation" }, new String[] { "varchar(20)", "varchar(20)",
				"varchar(20)" });

		Creating(hmode.getElement(), "element");
		Creating(hmode.getSenior(), "senior");
	}

	private SQLiteDatabase mDefaultWritableDatabase = null;

	@Override
		public SQLiteDatabase getWritableDatabase() {
			final SQLiteDatabase db;
			if(mDefaultWritableDatabase != null){
				db = mDefaultWritableDatabase;
			} else {
				db = super.getWritableDatabase();
			}
			return db;
		}

	@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			this.mDefaultWritableDatabase = db;
	}

	@Override
		public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			this.mDefaultWritableDatabase = db;
	}

}
