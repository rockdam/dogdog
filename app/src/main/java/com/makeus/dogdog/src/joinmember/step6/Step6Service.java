package com.makeus.dogdog.src.joinmember.step6;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.makeus.dogdog.src.ApplicationClass;
import com.makeus.dogdog.src.joinmember.step6.interfaces.JoinMemberRetrofitInterface;
import com.makeus.dogdog.src.joinmember.step6.interfaces.MoveAcitivity7Interface;
import com.makeus.dogdog.src.joinmember.step6.interfaces.PassValueDialog;
import com.makeus.dogdog.src.joinmember.step6.interfaces.SearchBreedsListRetrofitInterface;
import com.makeus.dogdog.src.joinmember.step6.models.JoinMemberResponse;
import com.makeus.dogdog.src.joinmember.step6.models.PostJoinMember;
import com.makeus.dogdog.src.joinmember.step6.models.SearchBreedsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makeus.dogdog.src.ApplicationClass.getRetrofit;
import static com.makeus.dogdog.src.ApplicationClass.sSharedPreferences;

public class Step6Service {


    PassValueDialog mPassValueDialog;
    String mUserId;

    SearchBreedsResponse searchBreedsResponse;
    JoinMemberResponse joinMemberResponse;
    MoveAcitivity7Interface moveAcitivity7Interface;
    Context mContext;
    public Step6Service(PassValueDialog passValueDialog) {
        this.mPassValueDialog = passValueDialog;

    }
    public Step6Service(MoveAcitivity7Interface moveAcitivity7Interface)
    {
        this.moveAcitivity7Interface=moveAcitivity7Interface;

    }
    public Step6Service(MoveAcitivity7Interface moveAcitivity7Interface,Context mContext)
    {
        this.moveAcitivity7Interface=moveAcitivity7Interface;
        this.mContext=mContext;
    }


    public void searchBreeds() {

        final SearchBreedsListRetrofitInterface searchBreedsListRetrofitInterface = getRetrofit().create(SearchBreedsListRetrofitInterface.class);


        searchBreedsListRetrofitInterface.getSearch().enqueue(new Callback<SearchBreedsResponse>() {


            @Override
            public void onResponse(Call<SearchBreedsResponse> call, Response<SearchBreedsResponse> response) {

                searchBreedsResponse=response.body();
                if(response.code() ==200)
                {

                    mPassValueDialog.passValueListView(searchBreedsResponse);


                }else{


//                    여기서 실패
                }
            }

            @Override
            public void onFailure(Call<SearchBreedsResponse> call, Throwable t) {

            }


        });


    }

    public void postJoinMember(PostJoinMember postJoinMember) {

        final JoinMemberRetrofitInterface joinMemberRetrofitInterface = getRetrofit().create(JoinMemberRetrofitInterface.class);


        joinMemberRetrofitInterface.postJoinMember(postJoinMember).enqueue(new Callback<JoinMemberResponse>() {


            @Override
            public void onResponse(Call<JoinMemberResponse> call, Response<JoinMemberResponse> response) {

                joinMemberResponse=response.body();
                if(response.code() ==200)
                {





                    if(joinMemberResponse.getIsSuccess()) {
                        Log.e("jwt", joinMemberResponse.getJwt());
                        SharedPreferences.Editor editor = sSharedPreferences.edit();
                        editor.putString("X-ACCESS-TOKEN", joinMemberResponse.getJwt());

                        editor.apply();
                        moveAcitivity7Interface.move();

                    }else{


                        Toast.makeText(mContext,joinMemberResponse.getMessage(),Toast.LENGTH_LONG).show();

                    }


                }else{


//                    여기서 실패
                }
            }

            @Override
            public void onFailure(Call<JoinMemberResponse> call, Throwable t) {

            }




        });


    }

}
