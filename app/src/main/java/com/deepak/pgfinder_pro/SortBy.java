package com.deepak.pgfinder_pro;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class SortBy extends Fragment {
    TextView textView1,textView2,textView3;
    Spinner spinner1,spinner2;
    Button button;

    RadioGroup radioGroup1;
    RadioButton radioButton1, radioButton2;

    String[] noOfDays = {"By Days", "0", "1", "5", "10", "15", "20", "25", "30"};
    ArrayList<String> arrayList1,arrayList2;
    ArrayAdapter arrayAdapter1,arrayAdapter2;
    int day;
    int rent;
    private int mSelectedIndex = -1;

    public SortBy() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sort_by, container, false);
        textView1 = (TextView) v.findViewById(R.id.textView1);
        textView2 = (TextView) v.findViewById(R.id.textView2);
        textView3 = (TextView) v.findViewById(R.id.textView3);

        radioGroup1 = (RadioGroup) v.findViewById(R.id.radiogroup1);
        radioButton1 = (RadioButton) v.findViewById(R.id.lowToHigh);
        radioButton2 = (RadioButton) v.findViewById(R.id.highToLow);

        spinner1 = (Spinner) v.findViewById(R.id.spinner1);
        //spinner2 = (Spinner) v.findViewById(R.id.spinner2);
        button = (Button) v.findViewById(R.id.button1);

        arrayList1 = new ArrayList<String>();
        arrayList1 = new ArrayList<String>();
        arrayAdapter1 = new ArrayAdapter(getActivity(), R.layout.spinnerrow, noOfDays);
        //arrayAdapter2 = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, arrayList2);
        spinner1.setAdapter(arrayAdapter1);
        //spinner2.setAdapter(arrayAdapter2);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                /*int selected = spinner1.getSelectedItemPosition();
                if (position == selected) {
                    spinner1.setBackgroundColor(Color.rgb(56,184,226));
                } else {
                    spinner1.setBackgroundColor(Color.TRANSPARENT);
                }*/
                // On selecting a spinner item
                String day1 = noOfDays[position];
                if(day1.matches("[0-9]+"))
                    day = Integer.parseInt(day1);
                else
                    day = 30;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rent = 1;
                    }
                });

                radioButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rent = 2;
                    }
                });
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");//'T'HH:mm:ss'Z'");

                Date date = new Date();
                String todate = dateFormat.format(date);
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -day);
                Date todate1 = cal.getTime();
                String fromdate = dateFormat.format(todate1);
                Toast.makeText(getActivity(), fromdate, Toast.LENGTH_SHORT).show();

                SearchPG searchPG = new SearchPG();
                Bundle bundle = new Bundle();
                bundle.putString("fromdate", fromdate);
                bundle.putInt("rent", rent);
                searchPG.setArguments(bundle);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.replace(R.id.container1, searchPG);
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return v;
    }

}
