package com.tchappy.photo.util;

import java.util.HashMap;

import com.tchappy.photo.activity.LoginActivity;
import com.tchappy.photo.activity.MainActivity;
import com.tchappy.photo.fragment.OneFragment;
import com.tchappy.pwy.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 支付方式列表界面
 * 
 * @author 罗超
 * 
 */
@SuppressLint({ "ViewConstructor", "HandlerLeak" })
public class View_Pay_Card extends LinearLayout implements View.OnClickListener {
	static View_Pay_Card view_Pay_Card;
	public static Dialog dialog_pay_card;
	private Button pay_card_cancel;
	private Button pay_card_ok;
	private EditText pay_card_num;
	public static Activity activity;

	public View_Pay_Card(final Context context) {
		super(context);
		V(context);

	}

	private void V(final Context context) {
		LayoutInflater.from(context).inflate(R.layout.view_pay_card, this, true);
		pay_card_ok = (Button) findViewById(R.id.pay_card_ok);
		pay_card_num = (EditText) findViewById(R.id.pay_card_num);
		pay_card_cancel = (Button) findViewById(R.id.pay_card_cancel);
		pay_card_ok.setOnClickListener(this);
		pay_card_cancel.setOnClickListener(this);
	}

	public static void pay(final Activity act) {
		act.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				activity = act;
				View_Pay_Card.view_Pay_Card = new View_Pay_Card(act);
				dialog_pay_card = new Dialog(act, R.style.DialogSplash1);
				dialog_pay_card.setContentView(View_Pay_Card.view_Pay_Card);
				dialog_pay_card.show();
				WindowManager.LayoutParams params = dialog_pay_card.getWindow().getAttributes();
				params.width = (int) (Utils.dm.widthPixels / 1.2);
				params.height = (int) (Utils.dm.heightPixels / 3);
				dialog_pay_card.getWindow().setAttributes(params);
			}
		});
	}

	public static void cancel() {
		if (dialog_pay_card != null)
			dialog_pay_card.cancel();
	}

	@SuppressLint("ShowToast") @Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pay_card_ok:
			String cardNUM = pay_card_num.getText().toString();
			if (cardNUM.equals("")) {
//				Utils.Toast(activity, "请输入卡号");
				Toast.makeText(activity, "请输入卡号", Toast.LENGTH_SHORT);
			} else {
//				Message message = OneFragment.main_Handler.obtainMessage();
//				message.obj=cardNUM;
//				message.what=MessageType.PAY_CARD_START;
//				MainActivity.main_Handler.sendMessage(message);
//				activity.finish();
			}
			break;
		case R.id.pay_card_cancel:
			dialog_pay_card.cancel();
			break;

		default:
			break;
		}

	}
}
