package edu.sdsu.cs.cs646.assign2;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class CountryFragment extends Fragment {

    private ListView countryListView;
    private String countryChosen = null;
    OnCountrySelectedListener onCountrySelectedListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_country, container, false);

        countryListView = (ListView) v.findViewById(R.id.country_list);
        countryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int pos, long id) {
                countryChosen = (String) countryListView.getItemAtPosition(pos);
                onCountrySelectedListener.countrySelected(countryChosen);
                SportFragment mySportFragment = new SportFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("Country", pos);
                mySportFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getActivity().getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_sport, mySportFragment);
                fragmentTransaction.commit();

            }
        });


        return v;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onCountrySelectedListener = (OnCountrySelectedListener) activity;
        } catch (Exception ex) {
        }
    }

    public interface OnCountrySelectedListener {
        void countrySelected(String countryChosen);

    }

}