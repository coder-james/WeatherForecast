package com.example.WeatherForecast.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;

public class CustomEditText extends EditText {

	private Drawable mClear;

	private Rect mBounds;

	public CustomEditText(Context context) {
		super(context);
		init();
	}

	public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CustomEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		setDrawable();
		// 增加文本监听器.
		addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@Override
			public void afterTextChanged(Editable s) {
				setDrawable();
			}
		});
	}

	// 输入框右边的图标显示控制
	private void setDrawable() {
		Drawable right = mClear;
		if (length() == 0) {
			right = null;
		}
		setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
	}

	@Override
	public void setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom) {
		if (mClear == null) {
			this.mClear = right;
		}
		super.setCompoundDrawables(left, top, right, bottom);
	}

	// 输入事件处理
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mClear != null && event.getAction() == MotionEvent.ACTION_UP) {
			this.mBounds = mClear.getBounds();
			int eventX = (int) event.getX();
			int width = mBounds.width();
			int right = getRight();
			int left = getLeft();
			if (eventX > (right - width * 2 - left)) {
				setText("");
				event.setAction(MotionEvent.ACTION_CANCEL);
			}
		}
		return super.onTouchEvent(event);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		this.mClear = null;
		this.mBounds = null;
	}

}