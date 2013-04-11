package org.nacao.app.adater;

import java.util.List;

import org.nacao.app.R;
import org.nacao.app.model.EnterpriseInfo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EnterinfosAadpter extends BaseAdapter {
	private List<EnterpriseInfo> enterprises = null;
	private LayoutInflater inflater = null;
	private Context context = null;
	public EnterinfosAadpter(List<EnterpriseInfo> enterprises, Context context) {
		inflater = LayoutInflater.from(context);
		this.enterprises = enterprises;
		this.context = context;
	}

	@Override
	public int getCount() {
         if(enterprises!=null){
        	 return enterprises.size();
         }
		return 0;
	}

	@Override
	public Object getItem(int position) {
		  if(enterprises!=null){
			  enterprises.get(position);
	         }
		return enterprises.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}
    public void remove(int positon){
    	if(enterprises!=null){
    		EnterpriseInfo ent=enterprises.get(positon);
    		enterprises.remove(ent);
    	}
    }
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.list_info, null);
			viewHolder.codeId = (TextView) convertView.findViewById(R.id.codeId);
			viewHolder.codeName = (TextView) convertView
					.findViewById(R.id.codeName);
			viewHolder.jjhy = (TextView) convertView
					.findViewById(R.id.jjhy);
			viewHolder.bzjgName = (TextView) convertView
					.findViewById(R.id.bzjgName);
			viewHolder.addressName = (TextView) convertView.findViewById(R.id.addressName);
			viewHolder.addTime = (TextView)convertView.findViewById(R.id.addTime);
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		EnterpriseInfo ent = enterprises.get(position);
		
		if (ent != null) {
			viewHolder.codeId.setText("机构代码："+ent.getCodeId());
			viewHolder.codeName.setText("机构名称："+ent.getCodeCn());
			viewHolder.jjhy.setText("经济行业："+ent.getIndustryId());
			viewHolder.addressName.setText("机构地址："+ent.getAddressname());
			viewHolder.bzjgName.setText("办证点："+ent.getWorkUnit());

			viewHolder.addTime.setTextSize(10.0f);
			viewHolder.addTime.setTextColor(Color.GRAY);
			viewHolder.addTime.setText("办证时间："+ent.getFinishdate());
			
		}
		return convertView;
	}

	class ViewHolder {
		TextView codeId;
		TextView codeName;
		TextView jjhy;
		TextView bzjgName;
		TextView addressName;
		TextView addTime;
	}
}
