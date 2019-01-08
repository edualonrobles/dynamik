package com.edualonso.dynamik.fragments

import android.Manifest
import android.app.AlertDialog
import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.ablanco.imageprovider.ImageProvider
import com.ablanco.imageprovider.ImageSource
import com.edualonso.dynamik.R
import com.edualonso.dynamik.activities.PhotoDetailActivity
import com.edualonso.dynamik.adapter.TeamPhotosAdapter
import com.edualonso.dynamik.model.Team
import com.edualonso.dynamik.views.SquareImageView


/**
 * Created by edu_g on 21/07/2017.
 */

/**
 * Esta clase sirve para mostrar las imágenes asociadas a un equipo.
 */

class PhotosTeamFragment : BaseFragment() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var tvNoResults: TextView
    private lateinit var viewLoading: View
    private lateinit var fabMore: View
    private val adapter: TeamPhotosAdapter by lazy { TeamPhotosAdapter(::onPhotoClicked) }
    private var teamPhotosLiveData: TeamPhotosLiveData? = null
    private var team: Team? = null

    private val avatarSourceMenuListener = PopupMenu.OnMenuItemClickListener {
        when (it.itemId) {
            POP_UP_MENU_CAMERA_ID -> {
                if (ActivityCompat.checkSelfPermission(
                        requireActivity(),
                        Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    takePhoto()
                } else {
                    requestPermissions(arrayOf(Manifest.permission.CAMERA), RC_CAMERA_PERMISSION)
                }
            }
            POP_UP_MENU_GALLERY_ID -> {
                ImageProvider(requireActivity()).getImage(ImageSource.GALLERY) { bitmap ->
                    bitmap?.let(::showUploadConfirmationDialog)
                }
            }
        }
        true
    }

    override fun getViewTitle(): String? = "Fotos"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (arguments?.getSerializable(ARG_TEAM) as? Team)?.let {
            team = it
            teamPhotosLiveData = TeamPhotosLiveData(it.idTeam)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_teamphotos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRecyclerView = view.findViewById(R.id.list_teamphotos)
        fabMore = view.findViewById(R.id.fabMore)
        viewLoading = view.findViewById(R.id.viewLoading)
        tvNoResults = view.findViewById(R.id.tvNoResults)
        fabMore.setOnClickListener {
            showAvatarSourceSelector()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        mRecyclerView.adapter = adapter

        teamPhotosLiveData?.let {
            it.observe(viewLifecycleOwner, Observer { photos ->
                viewLoading.visibility = View.GONE
                photos?.let(adapter::onPhotos)
            })
        } ?: run {
            viewLoading.visibility = View.GONE
            tvNoResults.visibility = View.VISIBLE
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults.any { it == PackageManager.PERMISSION_GRANTED }) {
            takePhoto()
        }
    }

    private fun showAvatarSourceSelector() {
        PopupMenu(requireContext(), fabMore, Gravity.TOP).apply {
            menu.add(Menu.NONE, POP_UP_MENU_CAMERA_ID, 0, "Cámara")
            menu.add(Menu.NONE, POP_UP_MENU_GALLERY_ID, 1, "Galería")
            setOnMenuItemClickListener(avatarSourceMenuListener)
        }.show()
    }

    private fun takePhoto() {
        ImageProvider(requireActivity()).getImage(ImageSource.CAMERA) { bitmap ->
            bitmap?.let(::showUploadConfirmationDialog)
        }
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

    private fun showUploadConfirmationDialog(bitmap: Bitmap) {
        val imageView = SquareImageView(requireContext()).apply {
            setImageBitmap(bitmap)
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("¿Subir foto?")
            .setPositiveButton("Si") { _, _ ->
                team?.idTeam?.let {
                    val progressDialog = ProgressDialog(requireContext())
                    progressDialog.setCancelable(false)
                    progressDialog.show()
                    UploadPhotoTask().execute(it, bitmap) {
                        progressDialog.dismiss()
                    }
                }
            }
            .setNegativeButton("No", null)
            .setView(imageView)
        dialog.show()
    }

    companion object {
        private const val POP_UP_MENU_CAMERA_ID = 0
        private const val POP_UP_MENU_GALLERY_ID = 1
        private const val RC_CAMERA_PERMISSION = 42424
        private const val ARG_TEAM = "arg:team"

        fun newInstance(t: Team): PhotosTeamFragment =
            PhotosTeamFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_TEAM, t)
                }
            }
    }

}
