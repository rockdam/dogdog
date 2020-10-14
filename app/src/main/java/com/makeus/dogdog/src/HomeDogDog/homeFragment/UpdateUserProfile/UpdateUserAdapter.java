package com.makeus.dogdog.src.HomeDogDog.homeFragment.UpdateUserProfile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.UpdateUserProfile.models.Result;

import java.util.ArrayList;

public class UpdateUserAdapter extends RecyclerView.Adapter<UpdateUserAdapter.ItemHolder> {


    Context mContext;

    ArrayList<Result> resultsArraylist;
    LayoutInflater inflater;

    public UpdateUserAdapter(Context mContext, ArrayList<Result> resultsArraylist) {
        this.mContext = mContext;
        this.resultsArraylist = resultsArraylist;
        inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

      View view= inflater.inflate(R.layout.itemlayout_updateuser,parent,false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {


        holder.onBind(resultsArraylist.get(position));


    }

    @Override
    public int getItemCount() {
        return resultsArraylist.size();
    }


    public class ItemHolder extends RecyclerView.ViewHolder{


        TextView name ,birthday, gender,breeds,weight;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            name =itemView.findViewById(R.id.name_userupdate_itemlayout);
            birthday=itemView.findViewById(R.id.birthday_userupdate_itemlayout);
            gender=itemView.findViewById(R.id.gender_userupdate_itemlayout);
            breeds=itemView.findViewById(R.id.breeds_userupdate_itemlayout);
            weight=itemView.findViewById(R.id.weight_userupdate_itemlayout);
        }

        void onBind(Result result)
        {

            name.setText(result.getDogInfo().getDogName());
            birthday.setText(result.getDogInfo().getBirth());
            gender.setText(result.getDogInfo().getGender());
            breeds.setText(result.getDogInfo().getBreed());
            weight.setText(String.valueOf(result.getDogInfo().getWeight()));

        }

    }
}
