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
 * gridview���
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
	 * ViewGroup��������Ļ���,����û�б���ʱֱ�ӵ��õ���dispatchDraw()����.
	 * �����б�����ʱ��͵���draw()����,��draw()�����������dispatchDraw()�����ĵ���.
	 * �ַ�����������л���
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
				//��ֹ��Ļ�ܶȴ�ͼƬС����ʾ��ȫ��
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
	 * ��Canvas����ָ��λ�úͿ�߶ȵ�ͼƬ
	 * @param canvas
	 * @param bitmap Ҫ���Ƶ�ͼƬ
	 * @param x ��Ļ�ϵ�x����    
	 * @param y ��Ļ�ϵ�y����    
	 * @param w Ҫ���Ƶ�ͼƬ�Ŀ�� 
	 * @param h Ҫ���Ƶ�ͼƬ�ĸ߶� 
	 * @param bitmapX ͼƬ�ϵ�x����
	 * @param bitmapY ͼƬ�ϵ�y����
	 */
	public static void drawImage(Canvas canvas, Bitmap bitmap, int x, int y,
			int w, int h, int bitmapX, int bitmapY) {
		// x,y��ʾ�滭����㣬
		Rect src = new Rect();// ͼƬ
		Rect dst = new Rect();// ��Ļλ�ü��ߴ�
		
		// src ����Ǳ�ʾ�滭ͼƬ�Ĵ�С
		src.left = bitmapX; // 0,0
		src.top = bitmapY;
		src.right = bitmapX + w;// ���������ͼ�Ŀ�ȣ�
		src.bottom = bitmapY + h;// // ���������ͼ�ĸ߶ȵ�һ��
		
		// ����� dst �Ǳ�ʾ �滭���ͼƬ��λ��
		dst.left = x; // ����ǿ��Ըı�ģ�Ҳ���ǻ�ͼ�����Xλ��
		dst.top = y; // �����ͼƬ�ĸ߶ȡ� Ҳ���൱�� ����ͼƬ�滭����Y����
		dst.right = x + w;// ��ʾ��滭��ͼƬ�����Ͻ�
		dst.bottom = y + h; // ��ʾ��滭��ͼƬ�����½�
		canvas.drawBitmap(bitmap, src, dst, null);
		//������� ��һ��������ͼƬԭ���Ĵ�С;
		//�ڶ��������� �滭��ͼƬ����ʾ���١�Ҳ����˵����滭��ͼƬ��ĳһЩ�ط���������ȫ��ͼƬ;
		//������������ʾ��ͼƬ�滭��λ��;
		
		src = null;
		dst = null;
	}
}
