package com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.interfaces;

import com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.models.WalkingMonthResult;
import com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.models.WalkingdayResult;

public interface TrackingNoteView {
    void updateMonth(WalkingMonthResult walkingMonthResult);
    void initialTackingNot(WalkingMonthResult walkingMonthResult);
    void updateDay(WalkingdayResult walkingdayResult);
}
