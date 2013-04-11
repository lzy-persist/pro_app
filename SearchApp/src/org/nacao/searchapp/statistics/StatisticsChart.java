package org.nacao.searchapp.statistics;

import android.content.Context;
import android.content.Intent;

public interface StatisticsChart {
    String NAME = "name";
    String DESC = "desc";
	  
	String getName();
	String getDesc();
	Intent execute(Context context);
}
