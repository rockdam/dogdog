package com.makeus.dogdog.src.HomeDogDog.homeFragment.AddChangeDogs;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.AddChangeDogs.models.Result;

import java.util.ArrayList;

//https://developside.tistory.com/88
public class AdddogsAdapter  extends RecyclerView.Adapter<AdddogsAdapter.ItemviewHolder> {

    Context mContext;
    ArrayList<Result> adddogsData;
    LayoutInflater inflater;

    public AdddogsAdapter(Context mContext, ArrayList<Result> adddogsData) {
        this.mContext = mContext;
        this.adddogsData = adddogsData;
         inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ItemviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.item_layout_add_change,parent,false);
        return new ItemviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemviewHolder holder, int position) {
        holder.onBind(adddogsData.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    void addItem(ArrayList<Result> adddogsData)
    {
        this.adddogsData=adddogsData;

    }
    public class ItemviewHolder extends RecyclerView.ViewHolder{


        ImageView isCheckedImage;
        TextView dogName;
        String isDisplayed;

        public ItemviewHolder(@NonNull View itemView) {
            super(itemView);
            dogName=itemView.findViewById(R.id.addchangeName_itemlayout);
            isCheckedImage=itemView.findViewById(R.id.isCheck_itemlayout);

        }

        void onBind(Result adddogsData){

            dogName.setText(adddogsData.getName());

            isDisplayed=adddogsData.getIsDisplayed();
            if(isDisplayed.equals("Y"))
            {

                isCheckedImage.setVisibility(View.VISIBLE);
            }else{

                isCheckedImage.setVisibility(View.INVISIBLE);
            }


        }
    }

}
