package com.project.wissemcomp.veloapp.fragment.introFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.wissemcomp.veloapp.MainActivity;
import com.project.wissemcomp.veloapp.fragment.pieceListFragment.PieceListFragment;
import com.project.wissemcomp.veloapp.R;
import com.project.wissemcomp.veloapp.fragment.bikeListFragment.BikeListFragment;

import zh.wang.android.yweathergetter4a.WeatherInfo;
import zh.wang.android.yweathergetter4a.YahooWeather;
import zh.wang.android.yweathergetter4a.YahooWeatherInfoListener;

public class IntroFragment extends Fragment implements IntroView, View.OnClickListener, YahooWeatherInfoListener {

    private IntroPresenterImpl presenter;

    private View view;
    private Button button_bikes;
    private Button button_sales;
    private Button button_pieces;
    private TextView city;
    private TextView temp;
    private ImageView icon;

    private YahooWeather mYahooWeather = YahooWeather.getInstance(5000, true);

    public static IntroFragment newInstance() {
        IntroFragment fragment = new IntroFragment();
        return fragment;
    }

    public IntroFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new IntroPresenterImpl();

    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle(getString(R.string.app_name));
        ((MainActivity)getActivity()).updateStatusBarColor(R.color.statusbar_velo, R.color.actionbar_velo);
        mYahooWeather.setNeedDownloadIcons(true);
        mYahooWeather.setUnit(YahooWeather.UNIT.CELSIUS);
        mYahooWeather.setSearchMode(YahooWeather.SEARCH_MODE.PLACE_NAME);
        mYahooWeather.queryYahooWeatherByPlaceName(getContext(), "Le Muy France", this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_intro, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        city = (TextView) view.findViewById(R.id.city_weather);
        temp = (TextView) view.findViewById(R.id.temp_weather);
        icon = (ImageView) view.findViewById(R.id.ic_localisation);

        // Initialize button with listener
        button_bikes = (Button) view.findViewById(R.id.button_bikes);
        button_sales = (Button) view.findViewById(R.id.button_sales);
        button_pieces = (Button) view.findViewById(R.id.button_pieces);

        button_bikes.setOnClickListener(this);
        button_pieces.setOnClickListener(this);
        button_pieces.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button_bikes:
                startBikesFragment();
                break;

            case R.id.button_sales:
                startSalesFragment();
                break;

            case R.id.button_pieces:
                startPiecesFragment();
                break;

            default:
                break;
        }

    }

    private void startBikesFragment() {

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, BikeListFragment.newInstance()).addToBackStack(null).commit();
    }

    private void startSalesFragment() {

    }

    private void startPiecesFragment() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, PieceListFragment.newInstance()).addToBackStack(null).commit();
    }

    @Override
    public void gotWeatherInfo(WeatherInfo weatherInfo, YahooWeather.ErrorType errorType) {

        if (weatherInfo != null) {
            city.setText("Le Muy");
            temp.setText(weatherInfo.getCurrentTemp() + "°C");
            icon.setVisibility(View.VISIBLE);
        }
    }
}
