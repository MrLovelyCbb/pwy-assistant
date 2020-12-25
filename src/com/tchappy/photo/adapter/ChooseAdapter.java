package com.tchappy.photo.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tchappy.photo.util.Bimp;
import com.tchappy.pwy.R;

public class ChooseAdapter extends BaseAdapter{
		private LayoutInflater inflater;
		private int selectedPosition = -1;
		private boolean shape;
		private Context m_context;
		private BaseAdapter m_adapter;
		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public ChooseAdapter(Context context) {
			m_adapter = this;
			m_context = context;
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}
		
		public void clearInfo() {
			for(int i=0;i<Bimp.max;i++) {
				System.out.println("i="+i);
				Bimp.tempSelectBitmap.remove(i);
				this.notifyDataSetChanged();
			}
		}

		public int getCount() {
			if(Bimp.tempSelectBitmap.size() == 2){
				return 2;
			}
			return (Bimp.tempSelectBitmap.size() + 1);
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				holder.text = (TextView) convertView.findViewById(R.id.item_grida_text);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			//提示文字
			switch (position) {
			case 0:
				holder.text.setText("手机全景");
				break;
			case 1:
				holder.text.setText("串号特写");
				break;

			default:
				break;
			}

			if (position ==Bimp.tempSelectBitmap.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						m_context.getResources(), R.drawable.icon_addpic_unfocused));
				if (position == 2) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
			}

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
			public TextView text;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					m_adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.tempSelectBitmap.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							Bimp.max += 1;
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						}
					}
				}
			}).start();
		}
	}
