/**
 * File Name : SportFragment.java
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
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * This class represents the SportFragment. It contains a list of
 * sports in a list view.
 *
 */
public class SportFragment extends Fragment {

    private ListView sportListView;
    private int countrySelected;
    private String sportSelected;
    private OnSportSelectedListener onSportSelectedListener;
    private int arrayIds [] = {
                        R.array.india_sport_array,
                        R.array.usa_sport_array,
                        R.array.mexico_sport_array
                      };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            countrySelected = bundle.getInt("Country",-1);
        }
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

        //Inflating the view
        ArrayAdapter<CharSequence> adapterList = null;
        View v = inflater.inflate(R.layout.fragment_sport, container, false);
        sportListView = (ListView)v.findViewById(R.id.sport_list);

        adapterList = ArrayAdapter.createFromResource(getActivity(),
                arrayIds[countrySelected], android.R.layout.simple_list_item_1);

        sportListView.setAdapter(adapterList);
        sportListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                       int pos, long id) {
                sportSelected = (String) sportListView.getItemAtPosition(pos);
                onSportSelectedListener.sportSelected(sportSelected);
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
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try {
            onSportSelectedListener = (OnSportSelectedListener) activity;
        }
        catch (Exception ex){
            Log.e("Exception caught ", this.getClass().getSimpleName(), ex);
        }
    }

    /**
     * This interface is used to attach the fragment to the activity. Any activity
     * which implements this interface can use the fragment.
     *
     */
    public interface OnSportSelectedListener{
        void sportSelected(String selectedSport);
    }
}