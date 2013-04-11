package org.nacao.searchapp.adater;

import java.util.List;

import org.nacao.searchapp.R;
import org.nacao.searchapp.model.JgdmVO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class JgdmListAadpter extends BaseAdapter {
	private List<JgdmVO> enterprises = null;
	private LayoutInflater inflater = null;
	private Context context = null;
	public JgdmListAadpter(List<JgdmVO> enterprises, Context context) {
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
    		JgdmVO ent=enterprises.get(positon);
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
			
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if(enterprises!=null && enterprises.size()>0){
			JgdmVO ent = enterprises.get(position);
			if (ent != null) {
				viewHolder.codeId.setText(ent.getCodeId());
				viewHolder.codeName.setText(ent.getCodeCn());
			}
		}
		return convertView;
	}

	class ViewHolder {
		TextView codeId;
		TextView codeName;

	}
}
