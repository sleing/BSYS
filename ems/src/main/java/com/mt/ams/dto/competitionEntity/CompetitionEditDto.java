package com.mt.ams.dto.competitionEntity;


import com.mt.ams.entity.competitionEntity.Competition;

import java.util.List;

import com.mt.ams.entity.unitEntity.Unit;

public class CompetitionEditDto {

    private Competition competition;


    //外键实体是：Unit
    private List<Unit> unitsUnits;


    public Competition getCompetition() {
        return this.competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }


    public List<Unit> getUnitsUnits() {
        return this.unitsUnits;
    }

    public void setUnitsUnits(List<Unit> unitsUnits) {
        this.unitsUnits = unitsUnits;
    }
}
