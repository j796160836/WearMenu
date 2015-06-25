package com.johnnyworks.wearmenu.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WearableListView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.johnnyworks.wearmenu.R;

import static com.johnnyworks.wearmenu.adapter.WearableListAdapter.ListType.ICON;

/**
 * Created by johnnysung on 15/06/16.
 */
public class WearableListAdapter extends WearableListView.Adapter {

	public enum ListType {
		DOT, ICON, PICTURE
	}

	private final Context mContext;
	private final LayoutInflater mInflater;
	private String[] itemStrings;
	private int[] drawablesResId;
	private ListType listType = ICON;

	private boolean ambient;

	private AdapterView.OnItemClickListener onItemClickListener;

	private View.OnClickListener onClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			int pos = (int) view.getTag();
			if (onItemClickListener != null) {
				onItemClickListener.onItemClick(null, view, pos, pos);
			}
		}
	};

	public WearableListAdapter(Context context, ListType listType, String[] itemStrings) {
		this(context, listType, itemStrings, new int[]{});
	}

	public WearableListAdapter(Context context, ListType listType, String[] itemStrings, int[] drawablesResId) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		this.listType = listType;
		this.itemStrings = itemStrings;
		this.drawablesResId = drawablesResId;
	}

	public boolean isAmbient() {
		return ambient;
	}

	public void setAmbient(boolean ambient) {
		this.ambient = ambient;
		notifyDataSetChanged();
	}

	public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	@Override
	public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		int layoutResId = R.layout.list_item_dot;
		switch (listType) {
			case ICON:
				layoutResId = R.layout.list_item_icon;
				break;
			case PICTURE:
				layoutResId = R.layout.list_item_picture;
				break;
			case DOT:
			default:
				layoutResId = R.layout.list_item_dot;
				break;
		}
		return new WearableListView.ViewHolder(mInflater.inflate(layoutResId, null));
	}

	@Override
	public void onBindViewHolder(WearableListView.ViewHolder holder, int pos) {
		View viewImage = holder.itemView.findViewById(R.id.icon_iv);
		TextView labelName = (TextView) holder.itemView.findViewById(R.id.text_tv);
		if (pos < itemStrings.length && !TextUtils.isEmpty(itemStrings[pos])) {
			labelName.setText(itemStrings[pos]);
		}
		if (pos < drawablesResId.length && drawablesResId[pos] > 0) {
			if (viewImage instanceof ImageView) {
				((ImageView) viewImage).setImageResource(drawablesResId[pos]);
			} else if (viewImage instanceof CircledImageView) {
				((CircledImageView) viewImage).setImageResource(drawablesResId[pos]);
			}
		}
		holder.itemView.setTag(pos);
		holder.itemView.setOnClickListener(onClickListener);

		if (!ambient) {
			labelName.setTextColor(mContext.getResources().getColor(R.color.item_text));
		} else {
			labelName.setTextColor(Color.WHITE);
		}
	}

	@Override
	public int getItemCount() {
		return itemStrings.length;
	}
}