/*
 * Created by Lee Oh Hyoung on 2020/04/30 .. 
 */
package kr.yapp.teamplay.presentation.myteam

import android.app.Activity
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kr.yapp.teamplay.R
import kr.yapp.teamplay.databinding.RvItemMyTeamBinding
import kr.yapp.teamplay.domain.entity.MyTeam
import kr.yapp.teamplay.util.dpToPixel

class MyTeamAdapter : RecyclerView.Adapter<MyTeamAdapter.MyTeamViewHolder>() {

    private val teams: ArrayList<MyTeam> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTeamViewHolder =
        MyTeamViewHolder(DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_item_my_team,
            parent,
            false
        ))

    override fun onBindViewHolder(holder: MyTeamViewHolder, position: Int) =
        holder.bindTo(teams[position])

    override fun getItemCount(): Int = teams.count()

    fun updateMyTeam(list: List<MyTeam>) {
        teams.clear()
        teams.addAll(list)
        notifyDataSetChanged()
    }

    inner class MyTeamViewHolder(
        private val binding: RvItemMyTeamBinding
    ): RecyclerView.ViewHolder(binding.root) {

        private val displayMetrics = DisplayMetrics()

        fun bindTo(myTeam: MyTeam) {
            binding.myTeam = myTeam
            (itemView.context as AppCompatActivity).windowManager.defaultDisplay.getMetrics(displayMetrics)
            itemView.layoutParams.width = (displayMetrics.widthPixels.toDouble() - 64.dpToPixel()).toInt()
        }
    }
}
