package com.tchappy.photo.fragment;

import com.tchappy.photo.activity.MainActivity;
import com.tchappy.photo.activity.QueryCommissionActivity;
import com.tchappy.photo.activity.QueryMoneyHistoryActivity;
import com.tchappy.photo.activity.QueryRepairHistoryActivity;
import com.tchappy.photo.activity.QuerySellersActivity;
import com.tchappy.photo.view.FragmentIndicator;
import com.tchappy.pwy.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author yangyu
 *	功能描述：搜索fragment页面
 */
public class ThreeFragment extends Fragment {

	private View mParent;
	private FragmentActivity mActivity;
	
	private TextView m_Title;
	private ImageView m_Image;
	private Button m_Button;
	
	/**
	 * Create a new instance of DetailsFragment, initialized to show the text at
	 * 'index'.
	 */
	public static ThreeFragment newInstance(int index) {
		ThreeFragment f = new ThreeFragment();

		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putInt("index", index);
		f.setArguments(args);

		return f;
	}

	public int getShownIndex() {
		return getArguments().getInt("index", 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_three, container,
				false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mParent = getView();
		mActivity = getActivity();

		m_Title = (TextView) mParent.findViewById(R.id.top_bar_title);
		m_Title.setText(R.string.repair_item8_str);
		
		m_Image = (ImageView) mParent.findViewById(R.id.top_bar_left);
		
		m_Button = (Button) mParent.findViewById(R.id.top_bar_right);
		
		final LinearLayout btnSellers = (LinearLayout) mParent.findViewById(R.id.query_sellers);
		final LinearLayout btnCommission = (LinearLayout) mParent.findViewById(R.id.query_commission);
		final LinearLayout btnMoneyHistory = (LinearLayout) mParent.findViewById(R.id.query_moneyhistory);
		final LinearLayout btnRepairHistory = (LinearLayout) mParent.findViewById(R.id.query_repairhistory);
		
		btnSellers.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goSellersActivity();
			}
		});
		
		btnCommission.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goCommissionActivity();
			}
		});
		
		btnMoneyHistory.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goMoneyHistoryActivity();
			}
		});
		
		btnRepairHistory.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goRepairHistoryActivity();
			}
		});

		m_Image.setVisibility(View.INVISIBLE);
		m_Button.setVisibility(View.INVISIBLE);
	}
	private void goSellersActivity() {
		Intent intent = new Intent(mActivity, QuerySellersActivity.class);
		mActivity.startActivity(intent);
	}
	
	private void goCommissionActivity() {
		Intent intent = new Intent(mActivity, QueryCommissionActivity.class);
		mActivity.startActivity(intent);
	}
	
	private void goMoneyHistoryActivity() {
		Intent intent = new Intent(mActivity, QueryMoneyHistoryActivity.class);
		mActivity.startActivity(intent);
	}
	
	private void goRepairHistoryActivity() {
		Intent intent = new Intent(mActivity, QueryRepairHistoryActivity.class);
		mActivity.startActivity(intent);
	}
	
	/**
	 * 返回到首页
	 */
	private void backHomeFragment() {
		getFragmentManager().beginTransaction()
				.hide(MainActivity.mFragments[1])
				.show(MainActivity.mFragments[0]).commit();
		FragmentIndicator.setIndicator(0);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (!hidden) {
		}
	}


}
