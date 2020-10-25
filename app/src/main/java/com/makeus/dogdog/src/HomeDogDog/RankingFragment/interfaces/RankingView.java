package com.makeus.dogdog.src.HomeDogDog.RankingFragment.interfaces;

import com.makeus.dogdog.src.HomeDogDog.RankingFragment.models.RankingData;
import com.makeus.dogdog.src.HomeDogDog.RankingFragment.models.RankingResponse;
import com.makeus.dogdog.src.HomeDogDog.RankingFragment.models.RankingResult;

import java.util.List;

public interface RankingView {
    void refreshRanking(RankingResponse rankingResponse);
}
