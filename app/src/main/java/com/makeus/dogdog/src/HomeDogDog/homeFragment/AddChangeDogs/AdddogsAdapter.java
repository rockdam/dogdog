package com.makeus.dogdog.src.HomeDogDog.homeFragment.AddChangeDogs;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.AddChangeDogs.models.Result;

import java.util.ArrayList;
import java.util.List;

//https://developside.tistory.com/88
public class AdddogsAdapter  extends RecyclerView.Adapter<AdddogsAdapter.ItemViewHolder> {

    Context mContext;
    ArrayList<Result> adddogsData;
    LayoutInflater inflater;
    public interface OnItemClickListener {
        void onItemClick(View v, int position,int dogIdx) ;
    }
    private OnItemClickListener mListener = null ;

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }

    public AdddogsAdapter(Context mContext, ArrayList<Result> adddogsData) {
        this.mContext = mContext;
        this.adddogsData = adddogsData;
         inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.item_layout_add_change,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.onBind(adddogsData.get(position));
    }

    @Override
    public int getItemCount() {
        return adddogsData.size();
    }

    void updateItem(List<Result> result)
    {

        adddogsData.clear();
        adddogsData.addAll(result);

    }
    public class ItemViewHolder extends RecyclerView.ViewHolder{


        ImageView isCheckedImage;
        ImageView profileImage;
        TextView dogName;
        String isDisplayed;
        Typeface typeface = ResourcesCompat.getFont(mContext, R.font.spoqahansbold);
        Typeface typefaceRegular = ResourcesCompat.getFont(mContext, R.font.spoqahansregular);


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            dogName=itemView.findViewById(R.id.addchangeName_itemlayout);
            isCheckedImage=itemView.findViewById(R.id.isCheck_itemlayout);
            profileImage=itemView.findViewById(R.id.profileImage_itemlayout);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        // 리스너 객체의 메서드 호출.
                        if (mListener != null) {
                            mListener.onItemClick(v, pos,adddogsData.get(pos).getDogIdx()) ;
                        }
                    }
                }
            });
        }

        void onBind(Result adddogsData){

            dogName.setText(adddogsData.getName());

            isDisplayed=adddogsData.getIsDisplayed();
            if(isDisplayed.equals("Y"))
            {
                dogName.setTypeface(typeface);
                isCheckedImage.setVisibility(View.VISIBLE);
            }else{
                dogName.setTypeface(typefaceRegular);

                isCheckedImage.setVisibility(View.INVISIBLE);
            }


        }
    }

}
