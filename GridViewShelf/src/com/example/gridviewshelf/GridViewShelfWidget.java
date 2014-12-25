package com.example.gridviewshelf;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;

/**
 * gridview书架
 * Created by melody on 2014/12/25.
 */
public class GridViewShelfWidget extends GridView {

	private Bitmap mItemBackground;
	private Bitmap mItemLineBackground;

	public GridViewShelfWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		mItemBackground = BitmapFactory.decodeResource(getResources(),
				R.drawable.bookshelf_layer_center);
		mItemLineBackground = BitmapFactory.decodeResource(getResources(),
				R.drawable.bookshelf_dock);
	}
	
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 1, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
	
	@SuppressLint("DrawAllocation")
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			AbsListView.LayoutParams params = new AbsListView.LayoutParams(child.getLayoutParams());
			params.height = mItemBackground.getHeight();
			child.setLayoutParams(params);
		}
	}
	
	/**
	 * ViewGroup容器组件的绘制,当它没有背景时直接调用的是dispatchDraw()方法.
	 * 当它有背景的时候就调用draw()方法,而draw()方法里包含了dispatchDraw()方法的调用.
	 * 分发给子组件进行绘制
	 */
	@Override
	protected void dispatchDraw(Canvas canvas) {
		int count = getChildCount();
		int top = count > 0 ? getChildAt(0).getTop() : 0;
		int backgroundWidth = mItemBackground.getWidth();
		int backgroundHeight = mItemBackground.getHeight();
		int width = getWidth();
		int height = getHeight();

		for (int y = top; y < height; y += backgroundHeight) {
			for (int x = 0; x < width; x += backgroundWidth) {
				//防止屏幕密度大，图片小。显示不全。
				//canvas.drawBitmap(mItemBackground, 0, y, null);
				drawImage(canvas, mItemBackground, 0, y, width,mItemBackground.getHeight(), 0, 0);
				if (y != 0) {
					drawImage(canvas, mItemLineBackground, 0, y, width - 6,mItemLineBackground.getHeight() / 2, 0, 0);
				}
			}
		}
		super.dispatchDraw(canvas);
	}
	
	/**
	 * 用Canvas绘制指定位置和宽高度的图片
	 * @param canvas
	 * @param bitmap 要绘制的图片
	 * @param x 屏幕上的x坐标    
	 * @param y 屏幕上的y坐标    
	 * @param w 要绘制的图片的宽度 
	 * @param h 要绘制的图片的高度 
	 * @param bitmapX 图片上的x坐标
	 * @param bitmapY 图片上的y坐标
	 */
	public static void drawImage(Canvas canvas, Bitmap bitmap, int x, int y,
			int w, int h, int bitmapX, int bitmapY) {
		// x,y表示绘画的起点，
		Rect src = new Rect();// 图片
		Rect dst = new Rect();// 屏幕位置及尺寸
		
		// src 这个是表示绘画图片的大小
		src.left = bitmapX; // 0,0
		src.top = bitmapY;
		src.right = bitmapX + w;// 这个是桌面图的宽度，
		src.bottom = bitmapY + h;// // 这个是桌面图的高度的一半
		
		// 下面的 dst 是表示 绘画这个图片的位置
		dst.left = x; // 这个是可以改变的，也就是绘图的起点X位置
		dst.top = y; // 这个是图片的高度。 也就相当于 桌面图片绘画起点的Y坐标
		dst.right = x + w;// 表示需绘画的图片的右上角
		dst.bottom = y + h; // 表示需绘画的图片的右下角
		canvas.drawBitmap(bitmap, src, dst, null);
		//这个方法 第一个参数是图片原来的大小;
		//第二个参数是 绘画该图片需显示多少。也就是说你想绘画该图片的某一些地方，而不是全部图片;
		//第三个参数表示该图片绘画的位置;
		
		src = null;
		dst = null;
	}
}
