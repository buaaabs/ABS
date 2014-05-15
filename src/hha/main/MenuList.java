package hha.main;

import hha.robot.R;

import java.util.ArrayList;
import java.util.HashMap;


import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class MenuList extends ListActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenulist);
		ArrayList<HashMap<String, String>>list=new ArrayList<HashMap<String,String>>();
		HashMap<String, String>map1=new HashMap<String, String>();
		HashMap<String, String>map2=new HashMap<String, String>();
		HashMap<String, String>map3=new HashMap<String, String>();
		HashMap<String, String>map4=new HashMap<String, String>();
		HashMap<String, String>map5=new HashMap<String, String>();
		map1.put("options", "��һ��");
		map2.put("options", "�ڶ���");
		map3.put("options", "������");
		map4.put("options", "������");
		map5.put("options", "������");
		list.add(map1);
		list.add(map2);
		list.add(map3);
		list.add(map4);
		list.add(map5);
		
		SimpleAdapter listAdapter=new SimpleAdapter(this, list, R.layout.mainmenulisttext, new String[]{"options"}, 
				new int[]{R.id.options});
		setListAdapter(listAdapter);
		MenuList.this.getListView().setCacheColorHint(Color.TRANSPARENT);
		this.getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(MenuList.this, MainActivity.class);
				MenuList.this.startActivity(intent);
			}
		});
	}	

}
