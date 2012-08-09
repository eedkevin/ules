package hk.hku.cs.msc.ules;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

public class SettingsActivity extends PreferenceActivity{

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.settings);
	}
	
	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		
		if(preference.getKey().equals("")){
			
		}else if(preference.getKey().equals("")){
			
		}
		
		return super.onPreferenceTreeClick(preferenceScreen, preference);	
	}
	
}
