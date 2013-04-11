package org.nacao.app.manage;

import org.nacao.app.R;
import org.nacao.app.util.DisplayTool;
import org.nacao.app.util.StringUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
		
	}
	
	private void initView(){
		jgdmView = (EditText)findViewById(R.id.jgdmCode);
		jgmcView = (EditText)findViewById(R.id.jgmcCode);
		jqcx = (Button)findViewById(R.id.jqcx);
		mhcx = (Button)findViewById(R.id.mhcx);
		
		jqcx.setOnClickListener(new MhLisenter());
		mhcx.setOnClickListener(new JqLisenter());
		
	}

	private class JqLisenter implements OnClickListener{
		@Override
		public void onClick(View v) {
			jgdmValue = ""+jgdmView.getText();
			jgmcValue = ""+jgmcView.getText();
					
			if(StringUtil.isEmpty(jgdmValue) && StringUtil.isEmpty(jgmcValue)){
				Toast.makeText(getApplicationContext(), "«Î ‰»Î≤È—Ø¥ £°", 3000).show();
				return;
			}
			
			Intent inte = new Intent(SearchActivity.this,AllEnters.class);
			Bundle bun = new Bundle();
			bun.putSerializable("jgdm", jgdmValue);
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
				Toast.makeText(getApplicationContext(), "«Î ‰»Î≤È—Ø¥ £°", 3000).show();
				return;
			}
			
			Intent inte = new Intent(SearchActivity.this,EntinfosActivity.class);
			Bundle bun = new Bundle();
			bun.putSerializable("codeid", jgdmValue);
			bun.putSerializable("jgmc", jgmcValue);
			
			inte.putExtras(bun);
			startActivityForResult(inte, 1);
			
		}
		
	}
	
	public void seaBack(View view){
		ImageView back = (ImageView)findViewById(R.id.back);

		Intent inte = new Intent(getApplicationContext(),LoginActivity.class);
		startActivityForResult(inte, 1);
		this.finish();
	}
		
	public void findJgdm(View view){
		ImageView back = (ImageView)findViewById(R.id.enterlist);

		Intent inten = new Intent(getApplicationContext(), AllEnters.class);
		startActivity(inten);
	}
	
}
