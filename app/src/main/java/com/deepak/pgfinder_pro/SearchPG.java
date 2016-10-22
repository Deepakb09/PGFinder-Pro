package com.deepak.pgfinder_pro;


import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchPG extends Fragment {
    ListView listView1;
    Cursor cursor;
    //SimpleCursorAdapter simpleCursorAdapter;
    PGDatabase pgDatabase;
    PGDetails pgDetails;

    ArrayList<PGDetails> pgDetailsArrayList = new ArrayList<PGDetails>();
    byte[] sample1;
    DbBitmapUtility dbu;
    MyAdapter myAdapter;


    public SearchPG() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_pg, container, false);
        listView1 = (ListView) view.findViewById(R.id.listView1);
        myAdapter = new MyAdapter();

        pgDatabase = new PGDatabase(getActivity());
        pgDatabase.open();

        pgDetailsArrayList = pgDatabase.queryPGDetails();

        listView1.setAdapter(myAdapter);

        return view;
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return pgDetailsArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return pgDetailsArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            pgDetails = pgDetailsArrayList.get(position);

            View view = getActivity().getLayoutInflater().inflate(R.layout.row, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView1);
            TextView textView1 = (TextView) view.findViewById(R.id.advertisername);
            TextView textView2 = (TextView) view.findViewById(R.id.city);
            TextView textView3 = (TextView) view.findViewById(R.id.pgName);
            TextView textView4 = (TextView) view.findViewById(R.id.contact);
            TextView textView5 = (TextView) view.findViewById(R.id.rent);

            dbu = new DbBitmapUtility();
            sample1 = pgDetails.getImage1();
            Bitmap bitmap = null;

            if (sample1[0] == 0) {
                //bitmap = dbu.getImage()
            }
            else
                bitmap = dbu.getImage(sample1);


            imageView.setImageBitmap(bitmap);

            textView1.setText(pgDetails.getAdvertisername());
            textView2.setText(pgDetails.getPgcity());
            textView3.setText(pgDetails.getPgname());
            textView4.setText(pgDetails.getContact());
            //textView5.setText(pgDetails.getPgrent());

            //Toast.makeText(getActivity(), "Lat : "+pgDetails.getLatitude()+"\nLon :"+pgDetails.getLongitude(), Toast.LENGTH_SHORT).show();

            return view;
        }
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
