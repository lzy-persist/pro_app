package org.nacao.searchapp.manage;

import org.nacao.searchapp.R;
import org.nacao.searchapp.util.DateProcess;
import org.nacao.searchapp.util.DisplayTool;
import org.nacao.searchapp.util.ExitApplication;
import org.nacao.searchapp.util.StringUtil;
import org.nacao.searchapp.util.Variable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
public class SearchActivity extends Activity {

	private EditText jgdmView;
	private EditText jgmcView;
	private Button jqcx;
	private Button mhcx;
	private String jgdmValue;
	private String jgmcValue;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jgdm_searchs);
		initView();
		ExitApplication.getInstance().addActivity(this);
	}
	
	private void initView(){
		jgdmView = (EditText)findViewById(R.id.jgdmCode);
		jgmcView = (EditText)findViewById(R.id.jgmcCode);
		jqcx = (Button)findViewById(R.id.jqcx);
		mhcx = (Button)findViewById(R.id.mhcx);
		
		jqcx.setOnClickListener(new JqLisenter());
		mhcx.setOnClickListener(new MhLisenter());
		
	}

	private class JqLisenter implements OnClickListener{
		@Override
		public void onClick(View v) {
			jgdmValue = ""+jgdmView.getText();
			jgmcValue = ""+jgmcView.getText();
					
			if(StringUtil.isEmpty(jgdmValue) && StringUtil.isEmpty(jgmcValue)){
				Toast.makeText(getApplicationContext(), "�������ѯ�ʣ�", 3000).show();
				return;
			}
			if (!StringUtil.isEmpty(jgdmValue) && jgdmValue.length() != 9){
				Toast.makeText(SearchActivity.this, "������9λ�Ļ�������!", 3000).show();
				return;
			}
	
			Log.v("searchapp", "�ֻ�����Ϊ"+DisplayTool.getPhoneNum(SearchActivity.this)+"���û�,"+DateProcess.getSysTime()+"��������Ϣ��ѯ������"+(!StringUtil.isEmpty(jgdmValue)?"�������룺"+jgdmValue:"")+(!StringUtil.isEmpty(jgmcValue)?"�������ƣ�"+jgmcValue:"")+"����ִ���˾�ȷ������ѯ����¼�û�Ϊ��"+Variable.user.getUserName()+"��");
			
			Intent inte = new Intent(SearchActivity.this,JgdmInfoActivity.class);
			Bundle bun = new Bundle();
			bun.putSerializable("codeid", jgdmValue);
			bun.putSerializable("jgmc", jgmcValue);
			
			inte.putExtras(bun);
			startActivityForResult(inte, 1);
		}
		
	}
	
	private class MhLisenter implements OnClickListener{
		@Override
		public void onClick(View v) {
			jgdmValue = ""+jgdmView.getText();
			jgmcValue = ""+jgmcView.getText();
					
			if(StringUtil.isEmpty(jgdmValue) && StringUtil.isEmpty(jgmcValue)){
				Toast.makeText(getApplicationContext(), "�������ѯ�ʣ�", 3000).show();
				return;
			}
			if (!StringUtil.isEmpty(jgdmValue) && jgdmValue.length() != 9){
				Toast.makeText(SearchActivity.this, "������9λ�Ļ�������!", 3000).show();
				return;
			}
			
			
			Log.v("searchapp", "�ֻ�����Ϊ"+DisplayTool.getPhoneNum(SearchActivity.this)+"���û�,"+DateProcess.getSysTime()+"��������Ϣ��ѯ������"+(!StringUtil.isEmpty(jgdmValue)?"�������룺"+jgdmValue:"")+(!StringUtil.isEmpty(jgmcValue)?"�������ƣ�"+jgmcValue:"")+"����ִ����ģ��������ѯ����¼�û�Ϊ��"+Variable.user.getUserName()+"��");

			Intent inte = new Intent(SearchActivity.this,JgdmListActivity.class);
			Bundle bun = new Bundle();
			bun.putSerializable("jgdm", jgdmValue);
			bun.putSerializable("jgmc", jgmcValue);
			
			inte.putExtras(bun);
			startActivityForResult(inte, 1);
			
		}
		
	}
	
	public void seaBack(View view){
		ImageView back = (ImageView)findViewById(R.id.seaback);

		Intent inte = new Intent(getApplicationContext(),LoginActivity.class);
		startActivityForResult(inte, 1);
		this.finish();
	}
		
	public void findJgdm(View view){
		Intent inten = new Intent(getApplicationContext(), JgdmListActivity.class);
		startActivity(inten);
	}
	
}
