package ru.com.radio.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import ru.com.radio.models.PlayerModel
import ru.com.radio.models.StationModel
import ru.com.radio.service.ACTION_START_FOREGROUND_SERVICE
import ru.com.radio.service.ACTION_STOP_FOREGROUND_SERVICE
import ru.com.radio.service.EXTRA_FILENAME_ID
import ru.com.radio.service.RadioService
import ru.com.radio.viewmodel.MainViewModel

open class BaseFragment: Fragment(){
    protected lateinit var model: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        model = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        return null
    }

    fun onStationClick(station: StationModel) {
        val act = if (PlayerModel.isPlaying) {
            if (PlayerModel.currentPlay == station) ACTION_STOP_FOREGROUND_SERVICE else ACTION_START_FOREGROUND_SERVICE
        } else ACTION_START_FOREGROUND_SERVICE

        PlayerModel.currentPlay = station

        Intent(activity, RadioService::class.java).apply {
            action = act
            putExtra(EXTRA_FILENAME_ID, station.stream)
            activity?.startService(this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        PlayerModel.currentRecyclerItem = null
    }
}