package com.github.symplelife.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.github.symplelife.ProgressBarAsyncTask;
import com.github.symplelife.R;
import com.github.symplelife.base.BaseApplication;
import com.github.symplelife.common.UserData;
import com.github.symplelife.tools.FileUtil;

public class SettingFragment extends BaseFragment implements View.OnClickListener ,CompoundButton.OnCheckedChangeListener{

	public static final int NONE_THEME = 0;
	public static final int DEFAULT_THEME = 1;
	public static final int DEFAULT_LIGHT_THEME = 2;
	public static final int DEFAULT_LIGH_DARK_THEME = 3;
	public static final int RED_THEME = 4;
	public static final int PINK_THEME = 5;
	public static final int PURPLE_THEME = 6;
	public static final int DEEP_PURPLE_THEME = 7;
	public static final int INDIGO_THEME = 8;
	public static final int BLUE_THEME = 9;
	public static final int LIGHT_BLUE_THEME = 10;
	public static final int CYAN_THEME = 11;
	public static final int TEAL_THEME = 12;
	public static final int GREEN_THEME = 13;
	public static final int LIGHT_THEME = 14;
	public static final int LIME_THEME = 15;
	public static final int YELLOW_THEME = 16;
	public static final int AMBER_THEME = 17;
	public static final int ORANGE_THEME = 18;
	public static final int DEEP_ORANGE_THEME = 19;
	public static final int BROWN_THEME = 20;
	public static final int GREY_THEME = 21;
	public static final int BLUE_GREY_THEME = 22;


	private Button clearCache;
	private TextView cacheSize;
	private Switch vistorSwitch;
	private TextView switchModel;
	private boolean isVistor;
	ProgressBarAsyncTask asyncTask;

	protected View initView(){
		View view = View.inflate(getActivity(), R.layout.fragment_style,null);

		clearCache = (Button) view.findViewById(R.id.btn_clearcache);
		cacheSize = (TextView) view.findViewById(R.id.tv_cache);
		vistorSwitch = (Switch) view.findViewById(R.id.sw_model);
		switchModel = (TextView) view.findViewById(R.id.tv_model);

		vistorSwitch.setOnCheckedChangeListener(this);
		isVistor = UserData.getInstance(getContext()).getModel();
		vistorSwitch.setChecked(isVistor);
		if (isVistor){
			switchModel.setText("访客模式");
		}else {
			switchModel.setText("登录模式");
		}

		clearCache.setOnClickListener(this);
		long size = FileUtil.getTotalSize(getActivity().getExternalCacheDir());
		cacheSize.setText(FileUtil.FormetFileSize(size));

		Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
		String[] array = getActivity().getResources().getStringArray(R.array.style_names);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getActionBar().getThemedContext(),android.R.layout.simple_list_item_activated_1,array);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (position != NONE_THEME) {
					setTheme(position);
					UserData.getInstance(getContext()).setThemeId(position);
					reload();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		return view;
	}

	private void setTheme(int index) {
		switch (index) {
			case DEFAULT_THEME:
				BaseApplication.mTheme =R.style.DefaultTheme;
				break;
			case DEFAULT_LIGHT_THEME:
				BaseApplication.mTheme = R.style.DefaultLightTheme;
				break;
			case DEFAULT_LIGH_DARK_THEME:
				BaseApplication.mTheme = R.style.DefaultLightDarkTheme;
				break;
			case RED_THEME:
				BaseApplication.mTheme = R.style.RedTheme;
				break;
			case PINK_THEME:
				BaseApplication.mTheme = R.style.PinkTheme;
				break;
			case PURPLE_THEME:
				BaseApplication.mTheme = R.style.PurpleTheme;
				break;
			case DEEP_PURPLE_THEME:
				BaseApplication.mTheme = R.style.DeepPurpleTheme;
				break;
			case INDIGO_THEME:
				BaseApplication.mTheme = R.style.IndigoTheme;
				break;
			case BLUE_THEME:
				BaseApplication.mTheme = R.style.BlueTheme;
				break;
			case LIGHT_BLUE_THEME:
				BaseApplication.mTheme = R.style.LightBlueTheme;
				break;
			case CYAN_THEME:
				BaseApplication.mTheme = R.style.CyanTheme;
				break;
			case TEAL_THEME:
				BaseApplication.mTheme = R.style.TealTheme;
				break;
			case GREEN_THEME:
				BaseApplication.mTheme = R.style.GreenTheme;
				break;
			case LIGHT_THEME:
				BaseApplication.mTheme = R.style.LightGreenTheme;
				break;
			case LIME_THEME:
				BaseApplication.mTheme = R.style.LimeTheme;
				break;
			case YELLOW_THEME:
				BaseApplication.mTheme = R.style.YellowTheme;
				break;
			case AMBER_THEME:
				BaseApplication.mTheme = R.style.AmberTheme;
				break;
			case ORANGE_THEME:
				BaseApplication.mTheme = R.style.OrangeTheme;
				break;
			case DEEP_ORANGE_THEME:
				BaseApplication.mTheme = R.style.DeepOrangeTheme;
				break;
			case BROWN_THEME:
				BaseApplication.mTheme = R.style.BrownTheme;
				break;
			case GREY_THEME:
				BaseApplication.mTheme = R.style.GreyTheme;
				break;
			case BLUE_GREY_THEME:
				BaseApplication.mTheme = R.style.BlueGreyTheme;
				break;
			default:
				break;
		}
	}


	protected void reload() {
		Intent intent = getActivity().getIntent();
		getActivity().overridePendingTransition(0, 0);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		getActivity().finish();
		FragmentFactory.clear();
		getActivity().overridePendingTransition(0, 0);
		startActivity(intent);
	}

	@Override
	public String getUrl() {
		return "file:///android_asset/theme.html";
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_clearcache){

			asyncTask = new ProgressBarAsyncTask(getActivity() , cacheSize);
			asyncTask.execute();

		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (buttonView.getId() == R.id.sw_model){

			UserData.getInstance(getContext()).setModel(isChecked);
			isVistor = isChecked;
			if (isVistor){
				switchModel.setText("访客模式");
			}else {
				switchModel.setText("登录模式");
			}
		}
	}
}
