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
				Toast.makeText(getApplicationContext(), "请输入查询词！", 3000).show();
				return;
			}
			if (!StringUtil.isEmpty(jgdmValue) && jgdmValue.length() != 9){
				Toast.makeText(SearchActivity.this, "需输入9位的机构代码!", 3000).show();
				return;
			}
	
			Log.v("searchapp", "手机号码为"+DisplayTool.getPhoneNum(SearchActivity.this)+"的用户,"+DateProcess.getSysTime()+"：机构信息查询条件（"+(!StringUtil.isEmpty(jgdmValue)?"机构代码："+jgdmValue:"")+(!StringUtil.isEmpty(jgmcValue)?"机构名称："+jgmcValue:"")+"），执行了精确条件查询，登录用户为："+Variable.user.getUserName()+"。");
			
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
				Toast.makeText(getApplicationContext(), "请输入查询词！", 3000).show();
				return;
			}
			if (!StringUtil.isEmpty(jgdmValue) && jgdmValue.length() != 9){
				Toast.makeText(SearchActivity.this, "需输入9位的机构代码!", 3000).show();
				return;
			}
			
			
			Log.v("searchapp", "手机号码为"+DisplayTool.getPhoneNum(SearchActivity.this)+"的用户,"+DateProcess.getSysTime()+"：机构信息查询条件（"+(!StringUtil.isEmpty(jgdmValue)?"机构代码："+jgdmValue:"")+(!StringUtil.isEmpty(jgmcValue)?"机构名称："+jgmcValue:"")+"），执行了模糊条件查询，登录用户为："+Variable.user.getUserName()+"。");

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
