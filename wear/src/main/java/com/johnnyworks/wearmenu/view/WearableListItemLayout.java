package com.johnnyworks.wearmenu.view;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.johnnyworks.wearmenu.R;

public class WearableListItemLayout extends LinearLayout
		implements WearableListView.OnCenterProximityListener {

	private static final int ANIMATION_DURATION = 150;
	private static final float SCALE_MIN = 0.75f;
	private static final float SCALE_MAX = 1.0f;

	private View icon_iv;
	private TextView text_tv;
	private int mSelectedCircleColor;
	private int mFadedCircleColor;
	private ObjectAnimator mScalingDownAnimator;
	private ObjectAnimator mScalingUpAnimator;

	public WearableListItemLayout(Context context) {
		this(context, null);
	}

	public WearableListItemLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public WearableListItemLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mSelectedCircleColor = context.getResources().getColor(R.color.colorPrimary);
		mFadedCircleColor = context.getResources().getColor(android.R.color.darker_gray);

		mScalingUpAnimator = ObjectAnimator.ofPropertyValuesHolder(icon_iv,
				PropertyValuesHolder.ofFloat("scaleX", SCALE_MIN, SCALE_MAX),
				PropertyValuesHolder.ofFloat("scaleY", SCALE_MIN, SCALE_MAX));
		mScalingUpAnimator.setDuration(ANIMATION_DURATION);

		mScalingDownAnimator = ObjectAnimator.ofPropertyValuesHolder(icon_iv,
				PropertyValuesHolder.ofFloat("scaleX", SCALE_MAX, SCALE_MIN),
				PropertyValuesHolder.ofFloat("scaleY", SCALE_MAX, SCALE_MIN));
		mScalingDownAnimator.setDuration(ANIMATION_DURATION);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		icon_iv = findViewById(R.id.icon_iv);
		text_tv = (TextView) findViewById(R.id.text_tv);
	}


	public void onCenterPosition(boolean animate) {
		Log.v("TAG", "onCenterPosition = " + animate);
		text_tv.setAlpha(1.0F);
		if (icon_iv instanceof CircledImageView) {
			((CircledImageView) icon_iv).setCircleColor(mSelectedCircleColor);
		}
		if (animate) {
			this.mScalingDownAnimator.cancel();
			if (!this.mScalingUpAnimator.isRunning()) {
				mScalingUpAnimator = ObjectAnimator.ofPropertyValuesHolder(icon_iv,
						PropertyValuesHolder.ofFloat("scaleX", icon_iv.getScaleX(), SCALE_MAX),
						PropertyValuesHolder.ofFloat("scaleY", icon_iv.getScaleY(), SCALE_MAX));
				mScalingUpAnimator.setDuration(ANIMATION_DURATION);
				mScalingUpAnimator.start();
			}
			return;
		}
		this.mScalingUpAnimator.cancel();
		setViewImageScale(SCALE_MAX);
	}

	public void onNonCenterPosition(boolean animate) {
		Log.v("TAG", "onNonCenterPosition = " + animate);
		text_tv.setAlpha(0.4F);
		if (icon_iv instanceof CircledImageView) {
			((CircledImageView) icon_iv).setCircleColor(mFadedCircleColor);
		}
		if (animate) {
			this.mScalingUpAnimator.cancel();
			if (!this.mScalingDownAnimator.isRunning()) {
				mScalingDownAnimator = ObjectAnimator.ofPropertyValuesHolder(icon_iv,
						PropertyValuesHolder.ofFloat("scaleX", icon_iv.getScaleX(), SCALE_MIN),
						PropertyValuesHolder.ofFloat("scaleY", icon_iv.getScaleY(), SCALE_MIN));
				mScalingDownAnimator.setDuration(ANIMATION_DURATION);
				mScalingDownAnimator.start();
			}
			return;
		}
		this.mScalingDownAnimator.cancel();
		setViewImageScale(SCALE_MIN);
	}

	private void setViewImageScale(float paramFloat) {
		this.icon_iv.setScaleX(paramFloat);
		this.icon_iv.setScaleY(paramFloat);
	}
}
