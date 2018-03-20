package com.example.himanshupalve.pictcanteen;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * Created by Himanshu Palve on 3/16/2018.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuItemViewHolder>
{
    private int NumberOfItems;
    final private ArrayList<String> Names;
    private ArrayList<ImageView> imageViews;

    private Toast toast;

    public MenuAdapter(int numberOfItems, ArrayList<String> names, ArrayList<ImageView> imageArray)
    {
        Names=names;
        NumberOfItems=numberOfItems;
        imageViews=imageArray;
    }

    @Override
    public MenuItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        toast=new Toast(parent.getContext());
        int layoutIdForListItem=R.layout.menu_list_item;
        LayoutInflater inflater=LayoutInflater.from(context);
        boolean attachParentImmediately=false;
        View view=inflater.inflate(layoutIdForListItem,parent,attachParentImmediately);
        MenuItemViewHolder viewHolder=new MenuItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MenuItemViewHolder holder, final int position) {
        holder.bind(position);
        holder.mAddToCart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toast!=null)
                {
                    toast.cancel();
                }
                toast.makeText(view.getContext(),"Added To Cart",Toast.LENGTH_LONG).show();
//                DatabaseReference mDataRef;
//                mDataRef= FirebaseDatabase.getInstance().getReference("Orders");
//                mDataRef.child(Names.get(position)).addListenerForSingleValueEvent(new Va);
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//                String id = mDataRef.push().getKey();
//                Order order=new Order(Names.get(position),1);
//                mDataRef.child(id).setValue(order);
                SharedPreferences sharedPreferences=getDefaultSharedPreferences(view.getContext());
                int cartsize=sharedPreferences.getInt("CartSize",0);
                int count=sharedPreferences.getInt(Names.get(position),0);
                count++;
                cartsize++;
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt(Names.get(position),count);
                editor.putInt("CartSize",cartsize);
                editor.apply();
            }
        });
    }

    @Override
    public int getItemCount() {
        return NumberOfItems;
    }

    class MenuItemViewHolder extends RecyclerView.ViewHolder{
    TextView tv;
    Button mAddToCart_btn;
        public MenuItemViewHolder(View itemView) {
            super(itemView);
            tv= itemView.findViewById(R.id.tv_menu_item_name);
            mAddToCart_btn=itemView.findViewById(R.id.btn_AddToCart);
        }
        public void bind(int position)  {
            tv.setText(Names.get(position));
        }

    }
}
