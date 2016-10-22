package com.deepak.pgfinder_pro;


import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class OwnerFragment extends Fragment implements TextWatcher {
    EditText editText1,editText2,editText3,editText4, editText5, editText6;
    TextView textView1,textView2,textView3;
    Button button1, button2, button3, button4;

    String latitude = null, longitude = null;
    //GPSTracker gps;

    String[] city = {"Attibele","Bommanahalli","BTM","Banaswadi","Banashankri","Hoskote","Hosur","Bommasandra","Begur",
            "Electronic City","SilkBoard","Bellandur","Marathalli","CV Raman Nagar","Tin Factory"};

    ArrayAdapter<String> arrayAdapter, arrayAdapter2;

    RadioGroup radioGroup1, radioGroup2, radioGroup3;
    RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5, radioButton6;
    String food,  gender, negotiable;

    AutoCompleteTextView autoCompleteTextView;

    int[] image = {R.drawable.house};

    PGDatabase pgDatabase;
    Cursor cursor;

    PGDetails pgDetails;

    //ArrayList<PGDetails> pgDetailsArrayList;

    DbBitmapUtility dbu;
    byte[] sample, sample1, sample2;

    public OwnerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_owner, container, false);
        editText1 = (EditText) v.findViewById(R.id.editText1);
        editText2 = (EditText) v.findViewById(R.id.editText2);
        editText3 = (EditText) v.findViewById(R.id.editText3);
        editText4 = (EditText) v.findViewById(R.id.editText4);
        editText5 = (EditText) v.findViewById(R.id.editText5);
        editText6 = (EditText) v.findViewById(R.id.editText6);

        /*editText1.addTextChangedListener(this);
        String text1 = editText1.getText().toString();
        if(text1.matches("\\d+")){
            Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
            editText1.startAnimation(shake);
        }*/

        textView1 = (TextView) v.findViewById(R.id.textView1);
        textView3 = (TextView) v.findViewById(R.id.textView3);
        button1 = (Button) v.findViewById(R.id.button1);
        button2 = (Button) v.findViewById(R.id.button2);
        button3 = (Button) v.findViewById(R.id.button3);
        //button4 = (Button) v.findViewById(R.id.button4);

        radioGroup1 = (RadioGroup) v.findViewById(R.id.radiogroup1);
        radioButton1 = (RadioButton) v.findViewById(R.id.nyes);
        radioButton2 = (RadioButton) v.findViewById(R.id.nno);

        radioGroup2 = (RadioGroup) v.findViewById(R.id.radiogroup2);
        radioButton3 = (RadioButton) v.findViewById(R.id.fyes);
        radioButton4 = (RadioButton) v.findViewById(R.id.fno);

        radioGroup3 = (RadioGroup) v.findViewById(R.id.radiogroup2);
        radioButton5 = (RadioButton) v.findViewById(R.id.gmale);
        radioButton6 = (RadioButton) v.findViewById(R.id.gfemale);

        arrayAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, city);
        autoCompleteTextView = (AutoCompleteTextView) v.findViewById(R.id.autoCompleteTextView1);
        autoCompleteTextView.setAdapter(arrayAdapter2);

        //pgDetailsArrayList = new ArrayList<PGDetails>();

        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        negotiable = "Yes";
                    }
                });

                radioButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        negotiable = "No";
                    }
                });
            }
        });

        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        food = "Yes";
                    }
                });

                radioButton4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        food = "No";
                    }
                });
            }
        });

        radioGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gender = "Male";
                    }
                });

                radioButton6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gender = "Female";
                    }
                });
            }
        });

        pgDatabase = new PGDatabase(getActivity());
        pgDatabase.open();

        dbu = new DbBitmapUtility();

       /* button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create class object
                gps = new GPSTracker(getActivity());

                // check if GPS enabled
                if(gps.canGetLocation()){

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    // \n is for new line
                    if(latitude == 0.0 || longitude == 0.0){
                        Toast.makeText(getActivity(), "Getting Location!! Please try again", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(getActivity(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }
            }
        });
*/
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);

                //sample1 = sample;
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);

                //sample2 = sample;
            }
        });

        /*Bundle bundle = getArguments();
        latitude = bundle.getString("latitude");
        longitude = bundle.getString("longitude");
        Toast.makeText(getActivity(), "Lat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_SHORT).show();*/

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int date = calendar.get(Calendar.DATE);
                String dateOP = ""+month+"/"+date+"/"+year;

                String advertisername = editText1.getText().toString();
                String pgname = editText2.getText().toString();
                String contact = editText3.getText().toString();
                String city = autoCompleteTextView.getText().toString();
                String area = editText4.getText().toString();
                String rent = editText5.getText().toString();
                String negotiable2 = negotiable;
                String gender2 = gender;
                String food2 = food;
                String desc = editText6.getText().toString();
                String dateofposting = dateOP;

                /*gps = new GPSTracker(getActivity());
                if(gps.canGetLocation()){
                    latitude = ""+gps.getLatitude();
                    longitude = ""+gps.getLongitude();
                    while (latitude.equals("0.0")){
                        Toast.makeText(getActivity(), "Getting location", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    gps.showSettingsAlert();*/
                //String latitude = "12.9855° N";
                //String longitude = "77.6639° E";

                if(advertisername.trim().equals("") || pgname.trim().equals("") || contact.trim().equals("")|| editText5.getText().toString().isEmpty()) {
                    if(advertisername.trim().equals("")){
                        Toast.makeText(getActivity(), "Please enter your name", Toast.LENGTH_SHORT).show();
                    }
                    else if(pgname.trim().equals("")){
                        Toast.makeText(getActivity(), "Please enter name of the PG", Toast.LENGTH_SHORT).show();
                    }else if(contact.trim().equals("")){
                        Toast.makeText(getActivity(), "Please enter your contact", Toast.LENGTH_SHORT).show();
                    }else if(editText5.getText().toString().isEmpty()){
                        Toast.makeText(getActivity(), "Please enter rent", Toast.LENGTH_SHORT).show();
                    }
                   /* else if(latitude.equals("0.0")){
                        Toast.makeText(getActivity(), "Getting location", Toast.LENGTH_SHORT).show();
                    }*/
                }

                else {
                    try {
                        pgDetails = new PGDetails(advertisername, pgname, contact, city, area, rent, negotiable2, gender2, food2, desc,
                                dateofposting, sample1, sample2, latitude, longitude);

                        pgDatabase.insertPgDetails(advertisername, pgname, contact, city, area, rent, negotiable2, gender2, food2, desc,
                                dateofposting, sample1, sample2, latitude, longitude);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                editText1.setText("");editText2.setText("");editText3.setText("");editText4.setText("");
                editText5.setText("");editText6.setText("");autoCompleteTextView.setText("");
                editText1.requestFocus();
                radioGroup1.clearCheck();radioGroup2.clearCheck();radioGroup3.clearCheck();

                SearchPG searchPG = new SearchPG();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.replace(R.id.container1, searchPG);
                fragmentTransaction.commit();
            }
        });

        return v;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 0) {
            Bitmap bp = null;

            bp = (Bitmap) data.getExtras().get("data");
            //imageView.setImageBitmap(bp);

            if(bp != null) {
                dbu = new DbBitmapUtility();
                sample1 = dbu.getBytes(bp);
            }
            else{
                //sample1 = dbu.getBytes(R.mipmap.housebitmap);
            }
        }else if(requestCode == 1){
            Bitmap bp = null;

            bp = (Bitmap) data.getExtras().get("data");
            //imageView.setImageBitmap(bp);

            if(bp != null) {
                dbu = new DbBitmapUtility();
                sample2 = dbu.getBytes(bp);
            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public class DbBitmapUtility {

        // convert from bitmap to byte array
        public byte[] getBytes(Bitmap bitmap) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            return stream.toByteArray();
        }

        // convert from byte array to bitmap
        public Bitmap getImage(byte[] image) {
            return BitmapFactory.decodeByteArray(image, 0, image.length);
        }
    }

}
