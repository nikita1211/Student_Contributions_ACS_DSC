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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CartFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private ArrayList<String> Names;
    private ArrayList<Integer> quantity;
    private int NUM_LIST_ITEMS=0 ;
    private RecyclerView mMenu;
    private CartAdapter mAdapter;
    LinearLayoutManager layoutManager;
    private Button mConfirm_btn;
    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View RootView=inflater.inflate(R.layout.fragment_cart, container, false);
        mMenu= RootView.findViewById(R.id.rv_cart);
        TextView tv=RootView.findViewById(R.id.EmptyCart_tv);
        getNames();
//        NUM_LIST_ITEMS=getcartsize();
        if(NUM_LIST_ITEMS==0)
        {
            tv.setVisibility(View.VISIBLE);
        }
        layoutManager=new LinearLayoutManager(this.getActivity());
        mMenu.setLayoutManager(layoutManager);
        mMenu.setHasFixedSize(true);
        mAdapter =new CartAdapter(NUM_LIST_ITEMS,Names,quantity);
        mMenu.setAdapter(mAdapter);
        mConfirm_btn=RootView.findViewById(R.id.btn_confirmOrder);
        mConfirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<mAdapter.CART_SIZE;i++)
                {
                    DatabaseReference mDataRef;
                    mDataRef= FirebaseDatabase.getInstance().getReference("Orders").child(mAdapter.Names.get(i));
//                    mDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot)
//                        {
//                            int quantity=mDataRef.child(mAdapter.Names.get(i)).getValue();
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
                    Order order=new Order(mAdapter.Names.get(i),mAdapter.quantity.get(i));
//                    String id = mDataRef.push().getKey();
                    mDataRef.setValue(order);
                }
            }
        });
        return RootView;
    }
//todo get cart size
    private void getNames( )
    {
        SharedPreferences sharedPreferences=getDefaultSharedPreferences(this.getActivity());
//        ArrayList<String> mArrayList=new ArrayList<>();
        Names=new ArrayList<>();
        quantity=new ArrayList<>();
        AssetManager assetManager=this.getActivity().getAssets();
        try {
            InputStream inputStream = assetManager.open("itemsnames.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while((line = in.readLine()) != null) {
                String word = line.trim();
                int count;
                count=sharedPreferences.getInt(word,0);
                if(count!=0)
                {
                    NUM_LIST_ITEMS++;
                    Names.add(word);
                    quantity.add(count);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this.getActivity(),"Could not load menu",Toast.LENGTH_LONG).show();
        }
//        return mArrayList;
    }
    private  int getcartsize()
    {
        SharedPreferences sharedPreferences=getDefaultSharedPreferences(this.getActivity());
        return sharedPreferences.getInt("CartSize",0);
    }
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
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
