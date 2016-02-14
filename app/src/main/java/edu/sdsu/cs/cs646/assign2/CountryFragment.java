/**
 * File Name : CountryFragment.java
 * Created by: Humaira Patel
 * Date: 02/4/2016
 *
 */
package edu.sdsu.cs.cs646.assign2;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * This class represents the CountryFragment. It contains a list of
 * countries in a list view.
 *
 */
public class CountryFragment extends Fragment {

    private ListView countryListView;
    private String countryChosen = null;
    private OnCountrySelectedListener onCountrySelectedListener;

    /**
     * Overriding the onCreate method.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     *
     * Overriding the onCreateView method. This method Inflates the
     * fragment with the list of countries.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i("Creating fragment ",this.getClass().getSimpleName());

        //Inflating the view
        View v = inflater.inflate(R.layout.fragment_country, container, false);
        countryListView = (ListView) v.findViewById(R.id.country_list);
        countryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int pos, long id) {
                countryChosen = (String) countryListView.getItemAtPosition(pos);
                onCountrySelectedListener.countrySelected(countryChosen,pos);
            }
        });
        return v;
    }

    /**
     * Overriding the onAttach method. This method attaches a fragment to the
     * activity.
     *
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onCountrySelectedListener = (OnCountrySelectedListener) activity;
        } catch (Exception ex) {
            Log.e("Exception caught ", this.getClass().getSimpleName(), ex);
        }
    }

    /**
     * This interface is used to attach the fragment to the activity. Any activity
     * which implements this interface can use the fragment.
     *
     */
    public interface OnCountrySelectedListener {
        void countrySelected(String countryChosen,int pos);
    }

}