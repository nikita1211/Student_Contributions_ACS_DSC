package com.example.himanshupalve.pictcanteen;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;


public class BreakfastFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<String> Names;
    private final static int NUM_LIST_ITEMS =10;
    private RecyclerView mMenu;
    private MenuAdapter mAdapter;
    LinearLayoutManager layoutManager;
    // TODO: Rename and change types of parameters

    public BreakfastFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static BreakfastFragment newInstance(Context main) {
        BreakfastFragment fragment = new BreakfastFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View RootView=inflater.inflate(R.layout.fragment_breakfast, container, false);
        mMenu= RootView.findViewById(R.id.rv_menu);
        if(Names==null)
            Names=getNames();
        layoutManager=new LinearLayoutManager(this.getActivity());
        mMenu.setLayoutManager(layoutManager);
        mMenu.setHasFixedSize(true);
        mAdapter =new MenuAdapter(NUM_LIST_ITEMS,getNames());
        mMenu.setAdapter(mAdapter);
        return RootView;
    }

    private ArrayList<String> getNames( )
    {
//        SharedPreferences sharedPreferences=getDefaultSharedPreferences(this.getActivity());
//        SharedPreferences.Editor editor=sharedPreferences.edit();
        ArrayList<String> mArrayList=new ArrayList<>();
        AssetManager assetManager=this.getActivity().getAssets();
        try {
            InputStream inputStream = assetManager.open("itemsnames.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while((line = in.readLine()) != null) {
                String word = line.trim();
//                editor.putInt(word,0);
//                editor.apply();
                mArrayList.add(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this.getActivity(),"Could not load menu",Toast.LENGTH_LONG).show();
        }
//        editor.putInt("CartSize",0);
//        editor.apply();
        return mArrayList;
    }
    private ArrayList<ImageView>getImages()
    {
                
    }
    // TODO: Rename method, update argument and hook method into UI event




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
