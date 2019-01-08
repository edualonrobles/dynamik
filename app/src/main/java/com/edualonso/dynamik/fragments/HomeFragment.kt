package com.edualonso.dynamik.fragments

/**
 * Created by edu_g on 04/07/2017.
 */

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edualonso.dynamik.R
import com.edualonso.dynamik.activities.MainActivity.prefs
import com.edualonso.dynamik.activities.PhotoDetailActivity
import com.edualonso.dynamik.adapter.GameListAdapter
import com.edualonso.dynamik.adapter.TeamPhotosAdapter
import com.edualonso.dynamik.model.Game
import com.edualonso.dynamik.model.MyModel

/**
 * Esta clase sirve para representar el rootFragment HOME
 */


class HomeFragment : BaseFragment() {

    private lateinit var myAddButton: FloatingActionButton
    internal var listener: HomeFragment.MyActivityListener? = null
    private lateinit var lastGames: RecyclerView
    private lateinit var lastPhotos: RecyclerView
    private val photosAdapter: TeamPhotosAdapter by lazy { TeamPhotosAdapter(::onPhotoClicked) }
    private val allPhotos = mutableSetOf<String>()

    override fun getViewTitle(): String? = "Inicio"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myAddButton = view.findViewById(R.id.fab)
        lastGames = view.findViewById(R.id.last_games)
        lastPhotos = view.findViewById(R.id.last_photos)

        lastGames.layoutManager = LinearLayoutManager(requireContext())
        lastPhotos.layoutManager = GridLayoutManager(requireContext(), 3)
        lastGames.isNestedScrollingEnabled = false
        lastPhotos.isNestedScrollingEnabled = false
        lastPhotos.adapter = photosAdapter

        //---------------Boton añadir equipo ---------------
        myAddButton.setOnClickListener {
            listener?.onClick()
        }

        val games = MyModel.getInstace().games?.take(2) ?: emptyList()
        lastGames.adapter = GameListAdapter(requireActivity(), games) { listener?.onGameClicked(it) }

        /*Get users teams*/
        val userTeams =
            MyModel.getInstace().users?.find { prefs.getString("id", "") == it.id }?.let { user ->
                val userTeams = user.teams ?: emptyList()
                MyModel.getInstace().teams?.filter { it.idTeam in userTeams } ?: emptyList()
            } ?: emptyList()

        userTeams.forEach {
            TeamPhotosLiveData(it.idTeam).observe(viewLifecycleOwner, Observer { photos ->
                onTeamPhotos(photos ?: emptyList())
            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun onTeamPhotos(photos: List<String>) {
        allPhotos.addAll(photos)
        val filteredPhotos = allPhotos.sortedDescending().take(6)
        photosAdapter.onPhotos(filteredPhotos)
    }

    private fun onPhotoClicked(view: View, position: Int, photoUrl: String) {
        val intent = Intent(requireContext(), PhotoDetailActivity::class.java)
        intent.putExtra(PhotoDetailActivity.EXTRA_PHOTO_URL, photoUrl)
        intent.putExtra(PhotoDetailActivity.EXTRA_TRANSITION_NAME, position.toString())
        val options =
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                requireActivity(),
                view,
                position.toString()
            )
        startActivity(intent, options.toBundle())
    }

    interface MyActivityListener {
        fun onClick()
        fun onGameClicked(game: Game)
    }

    companion object {

        fun newInstance(listener: HomeFragment.MyActivityListener): HomeFragment {
            val f = HomeFragment()
            f.listener = listener
            return f
        }
    }
}