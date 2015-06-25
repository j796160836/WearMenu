package com.johnnyworks.wearmenu.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by johnnysung on 15/06/25.
 */
public final class CircleMaskImageView extends ImageView {
	private Bitmap mBitmap;
	private Canvas mBitmapCanvas;
	private BitmapShader mBitmapShader;
	private Paint mPaint;

	public CircleMaskImageView(Context paramContext) {
		super(paramContext);
		init();
	}

	public CircleMaskImageView(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		init();
	}

	public CircleMaskImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
		init();
	}

	private void init() {
		setScaleType(ImageView.ScaleType.CENTER_CROP);
	}

	protected void onDraw(Canvas paramCanvas) {
		super.onDraw(this.mBitmapCanvas);
		int i = getWidth() - getPaddingLeft() - getPaddingRight();
		int j = getHeight() - getPaddingTop() - getPaddingBottom();
		int k = Math.min(i, j) / 2;
		int m = getPaddingLeft() + i / 2;
		int n = getPaddingTop() + j / 2;
		paramCanvas.drawCircle(m, n, k, this.mPaint);
	}

	protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
		super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
		this.mPaint = new Paint(1);
		if ((this.mBitmap == null) || (paramInt1 != this.mBitmap.getWidth()) || (paramInt2 != this.mBitmap.getHeight())) {
			this.mBitmap = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
			this.mBitmapCanvas = new Canvas(this.mBitmap);
			this.mBitmapShader = new BitmapShader(this.mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
			this.mPaint.setShader(this.mBitmapShader);
		}
	}
}
