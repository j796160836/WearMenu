package com.johnnyworks.wearmenu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.johnnyworks.wearmenu.adapter.WearableListAdapter;

/**
 * Created by johnnysung on 15/06/24.
 */
public class DotListActivity extends WearableActivity implements AdapterView.OnItemClickListener {

	public static final String TAG = DotListActivity.class.getSimpleName();

	private RelativeLayout root_view;
	private FrameLayout header;
	private TextView title_tv;
	private WearableListView list_view;

	private WearableListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		root_view = (RelativeLayout) findViewById(R.id.root_view);
		header = (FrameLayout) findViewById(R.id.header_include);
		title_tv = (TextView) header.findViewById(R.id.title_tv);

		list_view = (WearableListView) findViewById(R.id.list_view);
		list_view.setGreedyTouchMode(true);
		list_view.addOnScrollListener(new WearableListView.OnScrollListener() {
			@Override
			public void onScroll(int i) {
			}

			@Override
			public void onAbsoluteScrollChange(int i) {
				if (i > 0) {
					header.setY(-i);
				} else {
					header.setY(0);
				}
			}

			@Override
			public void onScrollStateChanged(int i) {
			}

			@Override
			public void onCentralPositionChanged(int i) {
			}
		});
		String[] itemStrings = mockStringItems();
		mAdapter = new WearableListAdapter(DotListActivity.this, WearableListAdapter.ListType.DOT, itemStrings);
		mAdapter.setOnItemClickListener(this);
		list_view.setAdapter(mAdapter);
		title_tv.setText(R.string.app_name);

		setAmbientEnabled();
	}

	@Override
	public void onEnterAmbient(Bundle ambientDetails) {
		updateAmbientUI(true);
		super.onEnterAmbient(ambientDetails);
	}

	@Override
	public void onExitAmbient() {
		super.onExitAmbient();
		updateAmbientUI(false);
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
		Log.v(TAG, "Click item " + pos);
		Intent intent = new Intent(DotListActivity.this, PictureListActivity.class);
		startActivity(intent);
		finish();
	}

	private void updateAmbientUI(boolean ambient) {
		if (!ambient) {
			root_view.setBackgroundColor(Color.WHITE);
			title_tv.setTextColor(getResources().getColor(R.color.title_text));
		} else {
			root_view.setBackgroundColor(Color.BLACK);
			title_tv.setTextColor(Color.WHITE);
		}
		mAdapter.setAmbient(ambient);
	}

	private String[] mockStringItems() {
		String[] data = new String[10];
		for (int i = 0; i < data.length; i++) {
			data[i] = "Item " + (i + 1);
		}
		return data;
	}

	private int[] mockDrawablesResId() {
		int[] data = new int[10];
		for (int i = 0; i < data.length; i++) {
			data[i] = R.drawable.android;
		}
		return data;
	}
}
