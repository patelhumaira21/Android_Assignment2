package edu.sdsu.cs.cs646.assign2;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class SportFragment extends Fragment {

    private ListView sportListView;
    TextView tv;
    private int countrySelected;
    private String sportSelected;
    OnSportSelectedListener onSportSelectedListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            countrySelected = bundle.getInt("Country",-1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayAdapter<CharSequence> adapterList = null;
        View v = inflater.inflate(R.layout.fragment_sport, container, false);
        sportListView = (ListView)v.findViewById(R.id.sport_list);
        tv = (TextView)v.findViewById(R.id.sportTextView);

        switch(countrySelected) {
            case (0): {
                adapterList = ArrayAdapter.createFromResource(getActivity(),
                        R.array.india_sport_array, android.R.layout.simple_list_item_1);
                break;
            }

            case (1): {
                adapterList = ArrayAdapter.createFromResource(getActivity(),
                        R.array.usa_sport_array, android.R.layout.simple_list_item_1);
                break;
            }

            case (2): {
                adapterList = ArrayAdapter.createFromResource(getActivity(),
                        R.array.mexico_sport_array, android.R.layout.simple_list_item_1);
                break;
            }
            default:
                break;
        }

        sportListView.setAdapter(adapterList);
        sportListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                       int pos, long id) {
                System.out.println("Sport abc ");
                sportSelected = (String) sportListView.getItemAtPosition(pos);
                onSportSelectedListener.sportSelected(sportSelected);
                System.out.println("Sport" + sportSelected);
                tv.setText(sportSelected);
            }

        });

        return v;

    }

    public void onAttach(Activity activity){
        super.onAttach(activity);
        try {
            onSportSelectedListener = (OnSportSelectedListener) activity;
        }
        catch (Exception ex){}
    }

    public interface OnSportSelectedListener{
        void sportSelected(String selectedSport);
    }
}