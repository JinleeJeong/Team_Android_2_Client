/*
 * Created by Lee Oh Hyoung on 2020/06/14 .. 
 */
package kr.yapp.teamplay.presentation.match_result

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kr.yapp.teamplay.R

import kr.yapp.teamplay.databinding.ActivityMatchDetailedResultBinding
import kr.yapp.teamplay.presentation.match_result.adapter.MatchResultAdapter
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast

class MatchDetailedResultActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_MATCH_ID: String = "match_id"
        private const val DEFAULT_MATCH_ID: Int = -1

        fun start(context: Context, matchId: Int) {
            context.startActivity(
                context.intentFor<MatchDetailedResultActivity>(
                    EXTRA_MATCH_ID to matchId
                )
            )
        }
    }

    private lateinit var binding: ActivityMatchDetailedResultBinding
    private val viewModel: MatchDetailedResultViewModel by viewModels()

    private var matchId: Int = DEFAULT_MATCH_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding()
        setLiveDataObserver()
        setListener()
        getMatchDetailedResult()
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_match_detailed_result)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun setLiveDataObserver() {
        viewModel.uiState.observe(this, Observer { state ->
            when(state) {
                is MatchDetailedResultUiState.Content ->{
                    binding.matchDetailRecyclerView.layoutManager = LinearLayoutManager(this)
                    binding.matchDetailRecyclerView.adapter = MatchResultAdapter(state.resultScores, state.hostName, state.guestName)
                    binding.individualScoreRecyclerView.layoutManager = GridLayoutManager(this, 2)
                    //binding.individualScoreRecyclerView.adapter =
                }
                is MatchDetailedResultUiState.Error -> toast(state.message)
            }
        })
    }

    private fun setListener() {
        binding.backButton.setOnClickListener { onBackPressed() }
    }

    private fun getMatchDetailedResult() {
        matchId = intent.getIntExtra(EXTRA_MATCH_ID, DEFAULT_MATCH_ID)
        if(matchId != DEFAULT_MATCH_ID) {
            viewModel.getMatchDetailedResult(matchId = matchId)
        }
    }

}
