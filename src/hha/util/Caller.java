package hha.util;

//用于通过名字或号码（均为String）拨打电话的类
//构造方法Caller(Context context)需要参数为Activity的实例（this）
//打电话有两种方法 void callName(String name)通过名字打电话; void callNumber(String num)通过号码
//提供其他方法 flush();刷新联系人信息；  
//String getName(String number);通过号码获得名字
//String getNumber(String name);通过名字得到号码
//int getContAmount(); 得到联系人数量
//String getInfo(int i);返回第i个人的“姓名+号码”，可以与通过getContAmount()得到数量后for遍历，i从0开始

import hha.main.MainActivity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.RawContacts;

public class Caller {
	class Information {
		private String name;
		private String number;

		public void setName(String name) {
			this.name = name;
		}

		public void setNum(String num) {
			this.number = num;
		}

		public void setAll(String name, String num) {
			this.name = name;
			this.number = num;
		}

		public String getName() {
			return name;
		}

		public String getNumber() {
			return number;
		}

		public Information() {
			name = null;
			number = null;
		}

		public Information(String name, String number) {
			this.name = name;
			this.number = number;
		}
	}

	private ArrayList<Information> infoArr = new ArrayList<Information>();
	private MainActivity context = null;

	public Caller(MainActivity context) {
		this.context = context;
		flush();
	}

	public void flush() {
		// 获取用来操作数据的类的对象，对联系人的基本操作都是使用这个对象
		ContentResolver cr = context.getContentResolver();
		// 查询contacts表的所有记录
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, null);
		// 如果记录不为空
		if (cur.getCount() > 0) {
			// 游标初始指向查询结果的第一条记录的上方，执行moveToNext函数会判断
			// 下一条记录是否存在，如果存在，指向下一条记录。否则，返回false。
			while (cur.moveToNext()) {
				String rawContactsId = "";

				String id = cur.getString(cur
						.getColumnIndex(ContactsContract.Contacts._ID));
				String name = cur.getString(cur
						.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				// 读取rawContactsId
				Cursor rawContactsIdCur = cr.query(RawContacts.CONTENT_URI,
						null, RawContacts.CONTACT_ID + " = ?",
						new String[] { id }, null);
				// 该查询结果一般只返回一条记录，所以我们直接让游标指向第一条记录
				if (rawContactsIdCur.moveToFirst()) {
					// 读取第一条记录的RawContacts._ID列的值
					rawContactsId = rawContactsIdCur.getString(rawContactsIdCur
							.getColumnIndex(RawContacts._ID));
				}
				rawContactsIdCur.close();
				// 读取号码
				if (Integer
						.parseInt(cur.getString(cur
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
					// 根据查询RAW_CONTACT_ID查询该联系人的号码
					Cursor PhoneCur = cr
							.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
									null,
									ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID
											+ " = ?",
									new String[] { rawContactsId }, null);
					// 上面的ContactsContract.CommonDataKinds.Phone.CONTENT_URI
					// 可以用下面的phoneUri代替
					// Uri
					// phoneUri=Uri.parse("content://com.android.contacts/data/phones");二、对联系人的基本操作(6)
					// 一个联系人可能有多个号码，需要遍历
					String phoneNumber = null;
					while (PhoneCur.moveToNext()) {
						// 号获取码
						String number = PhoneCur
								.getString(PhoneCur
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						if (phoneNumber == null) phoneNumber = number;
						// 获取号码类型
						String numberType = PhoneCur
								.getString(PhoneCur
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
					}
					PhoneCur.close();
					Information info = new Information(name, phoneNumber);
					this.infoArr.add(info);
				}
			}
		}
	}

	public void flush_bf() {
		infoArr.clear();

		Cursor cursor = context.getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

		int contactIdIndex = 0;
		int nameIndex = 0;
		if (cursor.getCount() > 0) {
			contactIdIndex = cursor
					.getColumnIndex(ContactsContract.Contacts._ID);
			nameIndex = cursor
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
		}
		
		while (cursor.moveToNext()) {

			String contactId = cursor.getString(contactIdIndex);
			String name = cursor.getString(nameIndex);
			String phoneNumber = null;
			Cursor phones = context.getContentResolver().query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
					null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
							+ contactId, null, null);
			int phoneIndex = 0;
			if (phones.getCount() > 0) {
				phoneIndex = phones
						.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
			}

			while (phones.moveToNext()) {
				phoneNumber = phones.getString(phoneIndex);
			}

			Information info = new Information(name, phoneNumber);
			this.infoArr.add(info);

			contactIdIndex = cursor
					.getColumnIndex(android.provider.ContactsContract.Contacts._ID);
			nameIndex = cursor
					.getColumnIndex(android.provider.ContactsContract.Contacts.DISPLAY_NAME);
		}
	}

	// 有名字查找电话号码,找不到返回null
	public String getNumber(String name) {
		for (int i = 0; i < infoArr.size(); ++i) {
			if (infoArr.get(i).getName().equals(name)) {
				return infoArr.get(i).getNumber();
			}
		}
		return null;
	}

	// 通过电话查找名字，找不到返回null
	public String getName(String number) {
		for (int i = 0; i < infoArr.size(); ++i) {
			if (infoArr.get(i).getNumber().equals(number)) {
				return infoArr.get(i).getName();
			}
		}
		return null;
	}

	// 连续得到信息
	public String getInfo(int i) {
		if (i < infoArr.size()) {
			return infoArr.get(i).getName() + "  " + infoArr.get(i).getNumber();
		} else {
			return null;
		}
	}

	// 得到联系人数量
	public int getContAmount() {
		return infoArr.size();
	}

	// 通过名字打电话
	public boolean callName(String name) {
		String number = this.getNumber(name);
		if (number != null) {
			Intent intent = new Intent("android.intent.action.CALL",
					Uri.parse("tel:" + number));
			context.startActivity(intent);
			return true;
		} else {
			return false;
		}
	}

	// 通过号码打电话
	public boolean callNumber(String num) {
		if (!isNum(num))
			return false;
		Intent intent = new Intent("android.intent.action.CALL",
				Uri.parse("tel:" + num));
		context.startActivity(intent);
		return true;
	}

	// 判断是否是正常号码
	private boolean isNum(String num) {
		try {
			Long.parseLong(num);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}