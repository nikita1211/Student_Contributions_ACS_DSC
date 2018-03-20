package com.example.himanshupalve.pictcanteen;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * Created by Himanshu Palve on 3/18/2018.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartItemViewHolder>{
    public int CART_SIZE;
    public ArrayList<String>Names;
    public ArrayList<Integer> quantity;
    public CartAdapter(int numberOfItems,ArrayList<String> names,ArrayList<Integer> i)
    {
        quantity=i;
        Names=names;
        CART_SIZE=numberOfItems;
    }

    @Override
    public CartItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        int layoutIdForListItem=R.layout.cart_list_item;
        LayoutInflater inflater=LayoutInflater.from(context);
        boolean attachParentImmediately=false;
        View view=inflater.inflate(layoutIdForListItem,parent,attachParentImmediately);
        CartItemViewHolder viewHolder=new CartItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CartItemViewHolder holder, final int position) {
        holder.bind(position);
        holder.mDeleteItemCart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem(position,view);
                holder.bind(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return CART_SIZE;
    }
    public void deleteItem(int pos,View view)
    {
        Log.e("position",String.valueOf(pos));
        SharedPreferences sharedPreferences=getDefaultSharedPreferences(view.getContext());
        int cartsize=sharedPreferences.getInt("CartSize",0);
        int count=sharedPreferences.getInt(Names.get(pos),0);
        count--;
        cartsize--;
        quantity.set(pos,quantity.get(pos)-1);
        Log.e("count",String.valueOf(count));
        Log.e("Name",Names.get(pos));
        Log.e("itemsize",String.valueOf(cartsize));
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(Names.get(pos),count);
        editor.putInt("CartSize",cartsize);
        editor.apply();
        if(count == 0)
        {
            CART_SIZE--;
            quantity.remove(pos);
            Names.remove(pos);
            Log.e("Size of names",String.valueOf(Names.size()));
            notifyItemRemoved(pos);
        }
        Log.e("cartsize",String.valueOf(CART_SIZE));
    }

    class CartItemViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name,tv_quant;
        Button mDeleteItemCart_btn;
        public CartItemViewHolder(View itemView) {
            super(itemView);
            tv_name= itemView.findViewById(R.id.tv_cart_item_name);
            tv_quant= itemView.findViewById(R.id.CartItemQuantity);
            mDeleteItemCart_btn=itemView.findViewById(R.id.btn_DeleteCartItem);
        }
        public void bind(int position)  {
            tv_name.setText(Names.get(position));
            tv_quant.setText(String.valueOf(quantity.get(position)));

        }

    }
}
