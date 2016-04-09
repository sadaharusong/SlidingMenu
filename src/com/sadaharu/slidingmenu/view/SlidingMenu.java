package com.sadaharu.slidingmenu.view;

import com.example.slidingmenu.R;

import android.R.integer;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class SlidingMenu extends HorizontalScrollView
{
	private LinearLayout mWapper;
	private ViewGroup mMenu;
	private ViewGroup mContent;
	private int mScreenWidth;

	private int mMenuRightPadding = 50;

	private boolean once;
	
	private boolean isOpen;

	private int mMenuWidth;

	// 未使用自定义属性时。调用
	public SlidingMenu(Context context, AttributeSet attrs)
	{
		// TODO Auto-generated constructor stub
		this(context , attrs , 0);
	}

	
	//当使用自定义了属性时，会调用此构造方法
	public SlidingMenu(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		
		//获取自定义属性
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.SlidingMenu, defStyle, 0);
		
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++)
		{
			int attr = a.getIndex(i);
			switch (attr)
			{
			case R.styleable.SlidingMenu_rightPadding:
				mMenuRightPadding = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources()
						.getDisplayMetrics()));
				break;

		
			}
		}
		a.recycle();
		WindowManager wn = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wn.getDefaultDisplay().getMetrics(outMetrics);
		mScreenWidth = outMetrics.widthPixels;

		
	}

	
	
	public SlidingMenu(Context context)
	{
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	// 设置子View的宽和高，决定自己的宽和高
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		// TODO Auto-generated method stub

		if (!once)
		{
			mWapper = (LinearLayout) getChildAt(0);
			mMenu = (ViewGroup) mWapper.getChildAt(0);
			mContent = (ViewGroup) mWapper.getChildAt(1);
			mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth
					- mMenuRightPadding;
			mContent.getLayoutParams().width = mScreenWidth;
			once = true;
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	// 通过设置偏移量，将menu隐藏s
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{

		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		if (changed)
		{
			this.scrollTo(mMenuWidth, 0);
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		int action = ev.getAction();
		switch (action)
		{
		case MotionEvent.ACTION_UP:
			// 隐藏的宽度
			int scrollX = getScrollX();

			if (scrollX >= mMenuWidth / 2)
			{
				this.smoothScrollTo(mMenuWidth, 0);
				isOpen = false;

			} else
			{
				this.smoothScrollTo(0, 0);
				isOpen = true;
			}

			return true;

		}
		// TODO Auto-generated method stub
		return super.onTouchEvent(ev);
	}

	public void openMenu()
	{
		if (isOpen)
		{
			return;
		}
		this.smoothScrollTo(0, 0);
		isOpen = true;
		
	}
	
	public void closeMenu()
	{
		if (!isOpen)
		{
			return;
		}
		this.smoothScrollTo(mMenuWidth, 0);
		isOpen = false;
	}
	//切换菜单
	public void toggle()
	{
		if (isOpen)
		{
			closeMenu();
		}else {
			openMenu();
		}
	}
}
