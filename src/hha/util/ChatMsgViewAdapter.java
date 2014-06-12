package hha.util;

import hha.robot.R;

import java.util.ArrayList;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChatMsgViewAdapter extends BaseAdapter {
	private static final String TAG = ChatMsgViewAdapter.class.getSimpleName();

	private ArrayList<ChatMsgEntity> coll;
	LayoutInflater inflater;

	public ArrayList<ChatMsgEntity> getColl() {
		return coll;
	}

	public void setColl(ArrayList<ChatMsgEntity> coll) {
		this.coll = coll;
	}

	private Context ctx;

	public ChatMsgViewAdapter(Context context, ArrayList<ChatMsgEntity> coll) {
		ctx = context;
		inflater = LayoutInflater.from(ctx);
		this.coll = coll;
	}

	public boolean areAllItemsEnabled() {
		return false;
	}

	public boolean isEnabled(int arg0) {
		return false;
	}

	public int getCount() {
		return coll.size();
	}

	public Object getItem(int position) {
		return coll.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public int getItemViewType(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		ChatMsgEntity entity = coll.get(position);
		TextView tvText;
		if (convertView == null) {
			// LinearLayout layout = (LinearLayout)convertView.getTag();
			int itemLayout = entity.getLayoutID();
			convertView = inflater.inflate(itemLayout, null);
			tvText = (TextView) convertView
					.findViewById(R.id.messagedetail_row_text);
			convertView.setTag(tvText);
		}
		else
		{
			tvText = (TextView) convertView.getTag();
		}
		
		tvText.setText(entity.getText());
		
		// convertView.setTag(entity);
		return convertView;

	}

	public int getViewTypeCount() {
		return coll.size();
	}
	
}
