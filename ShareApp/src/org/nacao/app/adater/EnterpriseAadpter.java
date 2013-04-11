package org.nacao.app.adater;

import java.util.List;

import org.nacao.app.R;
import org.nacao.app.model.EnterpriseInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EnterpriseAadpter extends BaseAdapter {
	private List<EnterpriseInfo> enterprises = null;
	private LayoutInflater inflater = null;
	private Context context = null;
	public EnterpriseAadpter(List<EnterpriseInfo> enterprises, Context context) {
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
			convertView = inflater.inflate(R.layout.list_item, null);
			viewHolder.codeId = (TextView) convertView.findViewById(R.id.codeId);
			viewHolder.codeName = (TextView) convertView.findViewById(R.id.codeName);
			
			/*
			 * viewHolder.jjhy = (TextView) convertView
					.findViewById(R.id.jjhy);
			viewHolder.bzjgName = (TextView) convertView
					.findViewById(R.id.bzjgName);*/
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if(enterprises!=null && enterprises.size()>0){
			EnterpriseInfo ent = enterprises.get(position);
			if (ent != null) {
				viewHolder.codeId.setText(ent.getCodeId());
				viewHolder.codeName.setText(ent.getCodeCn());
				
		/*		viewHolder.jjhy.setText(ent.getIndustryId());
				viewHolder.bzjgName.setText(ent.getWorkUnit());*/
				
			}
		}
		return convertView;
	}

	class ViewHolder {
		TextView codeId;
		TextView codeName;
/*		TextView jjhy;
 		TextView bzjgName;
		*/
	}
}
