package hha.xhb;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SQLHelper {
	private SqlLiteHelper helper;
	private Activity mActivity;
	private final static String TAG_STRING = "SQLHelper: ";

	public SQLHelper(Activity activity) {
		this.mActivity = activity;
		helper = new SqlLiteHelper(activity.getBaseContext(), this);
	}

	private String getCreateString(String tableName, String[] attrNames,
			String[] typeNames, boolean isWithTime) {
		StringBuilder sBuilder = new StringBuilder();
		int j = 0;

		sBuilder.append("CREATE TABLE IF NOT EXISTS " + tableName + " ( ");

		// ��ӵ�һ������
		if (!isWithTime) {
			sBuilder.append(attrNames[0] + " " + typeNames[0]);
			sBuilder.append(" PRIMARY KEY ,");
			j = 1;
		} else {
			sBuilder.append("time DATETIME DEFAULT CURRENT_TIMESTAMP PRIMARY KEY ,");
			j = 0;
		}

		for (int i = j; i < attrNames.length; i++) {
			sBuilder.append(attrNames[i] + " " + typeNames[i]);

			if (i != attrNames.length - 1) {
				sBuilder.append(" , ");
			}

		}
		sBuilder.append(" )");
		System.out.println(TAG_STRING + sBuilder.toString());
		return sBuilder.toString();
	}

	private String getAddString(String tableName, String[] attrNames,
			boolean isWithTime) {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("INSERT INTO " + tableName + "( ");
		if (isWithTime) {
			sBuilder.append("time, ");
		}
		for (int i = 0; i < attrNames.length; i++) {
			// insert into person(name, age) values(�����ǡ�,3)
			sBuilder.append(attrNames[i]);
			if (i != attrNames.length - 1) {
				sBuilder.append(", ");
			}
		}

		sBuilder.append(" ) ");

		sBuilder.append("values( ");
		if (isWithTime) {
			sBuilder.append("?,");
		}

		for (int i = 0; i < attrNames.length; i++) {
			sBuilder.append("?");
			if (i != attrNames.length - 1) {
				sBuilder.append(", ");
			}
		}
		sBuilder.append(" )");
		return sBuilder.toString();
	}

	/**
	 * 
	 * @param tableName
	 * @param keys
	 *            key value
	 * @param values
	 *            isOk true
	 */
	public void AddOrUpdate(String tableName, String[] keys, String[] values) {
		Cursor cursor = query(tableName, new String[] { keys[0] },
				new String[] { values[0] }, null);
		boolean b = cursor.moveToNext();
		cursor.close();
		if (b) {
			update(tableName, keys[0], values[0], keys, values);
		} else {
			add(tableName, keys, values);
		}
	}

	/**
	 * 缺陷在于，只能更新列类型均为string的表
	 * 
	 * @param tableName
	 * @param key
	 * @param value
	 * @param keys
	 * @param values
	 */
	private void update(String tableName, String key, String value,
			String[] keys, String[] values) {
		SQLiteDatabase db = helper.getWritableDatabase();
		StringBuilder sBuilder = new StringBuilder();
		// "update person set name=?, phone=? where id=?"
		sBuilder.append("update " + tableName + " set ");
		for (int i = 0; i < keys.length; i++) {
			sBuilder.append(keys[i] + "=? ");
			if (i != keys.length - 1) {
				sBuilder.append(", ");
			}
		}
		sBuilder.append("where " + key + "=?");
		String[] params = new String[values.length + 1];
		List<String> paramsList = Arrays.asList(values);
		paramsList.toArray(params);
		params[values.length] = value;
		db.execSQL(sBuilder.toString(), params);
		db.close();
	}

	// ������time
	public void createTable(String tableName, String[] attrNames,
			String[] typeNames) {
		SQLiteDatabase db = helper.getWritableDatabase();
		String sqlString = getCreateString(tableName, attrNames, typeNames,
				false);
		db.execSQL(sqlString);
		db.close();
	}

	// ������time
	public void createTalbeWithTime(String tableName, String[] attrNames,
			String[] typeNames) {
		SQLiteDatabase db = helper.getWritableDatabase();
		String sqlString = getCreateString(tableName, attrNames, typeNames,
				true);
		db.execSQL(sqlString);
		db.close();
	}

	// ���������time
	public void add(String tableName, String[] attrNames, Object[] values) {
		SQLiteDatabase db = helper.getWritableDatabase();
		String sqlString = getAddString(tableName, attrNames, false);
		db.execSQL(sqlString, values);
		db.close();
	}

	// ���������time(��ʱ���ô�)
	public void addWithTime(String tableName, String[] attrNames,
			Object[] values) {
		SQLiteDatabase db = helper.getWritableDatabase();
		String sqlString = getAddString(tableName, attrNames, true);

		db.execSQL(sqlString, values);
		db.close();
	}

	// ��ѯ���
	public Cursor query(String tableName, String[] keys, String[] values,
			String orderBy) {
		SQLiteDatabase db = helper.getReadableDatabase();
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("select * from " + tableName + " where ");
		for (int i = 0; i < keys.length; i++) {
			sBuilder.append(keys[i] + " = ?");
			if (i != keys.length - 1) {
				sBuilder.append("and");
			}
		}
		if (orderBy != null) {
			sBuilder.append("order by " + orderBy);
		}
		Cursor cursor = db.rawQuery(sBuilder.toString(), values);
		db.close();
		return cursor;
	}

	public Cursor queryAll(String tableName, String orderBy) {
		SQLiteDatabase db = helper.getReadableDatabase();
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("select * from " + tableName);
		if (orderBy != null) {
			sBuilder.append("order by " + orderBy);
		}
		Cursor cursor = db.rawQuery(sBuilder.toString(), null);
		db.close();
		return cursor;
	}
}
