package hha.main;

import hha.robot.R;
import hha.util.ExitApplication;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MenuList extends ListActivity {
	// public static final ColorStateList white = null;
	String[] map = new String[6];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenulist);
		map[0] = "健康数据统计";
		map[1] = "备忘事件";
		map[2] = "查看健康建议";
		map[3] = "软件设置";
		map[4] = "关于我们";
		map[5] = "退出";
		Button returnbutton = (Button) this.findViewById(R.id.returnbutton);
		returnbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MenuList.this, MainActivity.class);
				MenuList.this.startActivity(intent);
			}
		});

		this.getListView().setAdapter(new MyAdapter());
		// MenuList.this.getListView().setCacheColorHint(Color.TRANSPARENT);
		this.getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				switch (arg2) {
				case 0:
					break;
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					
					break;
				case 5:
					ExitApplication.getInstance().exit();
					System.exit(0);
					break;
				default:
					return;
				}
			}

		});
	}

	public class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return map.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView text = new TextView(MenuList.this);
			text.setText(map[position]);
			text.setTextSize(40);
			return text;
		}

	}

}
