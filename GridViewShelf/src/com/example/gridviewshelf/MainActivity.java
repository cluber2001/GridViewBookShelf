package com.example.gridviewshelf;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by melody on 2014/12/25.
 */
public class MainActivity extends Activity {
	private GridViewShelfWidget mGridView;
	private List<String> mList=new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mGridView=(GridViewShelfWidget) findViewById(R.id.bookshelf);
		for (int i = 0; i < 20; i++) {
			mList.add(""+i);
		}
		DataAdapter adapter=new DataAdapter();
		mGridView.setAdapter(adapter);
	}
	
	class DataAdapter extends BaseAdapter{
		@Override
		public int getCount() {
			return mList.size();
		}

		@Override
		public Object getItem(int position) {
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView==null){
				//���inflate�Ĳ������������õ�:inflate(R.layout.items,null);��ôgridview���һ����ʾ��������ʾ��������
				//��������ò���������ʾ ��������ʾscrollviewǶ��gridview��ʾ���ݲ������������
				//see:http://blog.csdn.net/zhaokaiqiang1992/article/details/36006467
				convertView=LayoutInflater.from(MainActivity.this).inflate(R.layout.items, parent,false);
			}
			return convertView;
		}
	}
}
