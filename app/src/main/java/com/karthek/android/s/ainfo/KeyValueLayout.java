package com.karthek.android.s.ainfo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class KeyValueLayout extends LinearLayout {

	TextView ValueTextView;

	public KeyValueLayout(Context context) {
		super(context);
	}

	public KeyValueLayout(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public KeyValueLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}


}
