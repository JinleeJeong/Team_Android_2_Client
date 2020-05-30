package kr.yapp.teamplay.domain.repository

import io.reactivex.Single
import kr.yapp.teamplay.domain.entity.matchschedule.MatchScheduleOuterItem

interface MatchScheduleRepository {
    fun getMatchScheduleItem(clubId: String): Single<List<MatchScheduleOuterItem>>
}
