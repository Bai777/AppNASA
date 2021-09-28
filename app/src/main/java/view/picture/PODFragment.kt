package view.picture

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.TypefaceSpan
import android.view.*
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import api.ApiActivityBottom
import chips.SettingsFragment
import coil.api.load
import com.example.appnasa.R
import com.example.appnasa.databinding.FragmentMainStartBinding
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import view.MainActivity
import view.example.FragmentExampleConstraintLayout
import viewModel.PODData
import viewModel.PODViewModel

class PODFragment : Fragment() {
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private var _binding: FragmentMainStartBinding? = null
    val binding: FragmentMainStartBinding
        get() {
            return _binding!!
        }

    private val viewModel: PODViewModel by lazy {
        ViewModelProvider(this).get(PODViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainStartBinding.inflate(inflater)
        return binding.root
    }


    private fun setBottomSheet(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.sendServerRequest()
        setBottomAppBar()

        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })


        }
        setBottomSheet(binding.includeBottomSheet.bottomSheetContainer)
    }

    @SuppressLint("NewApi")
    private fun renderData(data: PODData) {
        when (data) {
            is PODData.Success -> {
                binding.imageView.load(data.serverResponseData.url) {
                    placeholder(R.drawable.progress_animation)
                    error(R.drawable.ic_load_error_vector)
                }

                data.serverResponseData.title?.let {
                    val textBottomSheetTitle: TextView =
                        binding.includeBottomSheet.bottomSheetDescriptionHeader
                    val spannable = SpannableStringBuilder(it)
                    spannable.setSpan(
                        ForegroundColorSpan(resources.getColor(R.color.color_blue)),
                        0, spannable.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE
                    )
                    textBottomSheetTitle.text = spannable
                }

                data.serverResponseData.explanation?.let {
                    val spannableStart = SpannableStringBuilder(it)
                    binding.includeBottomSheet.bottomSheetDescription.setText(spannableStart, TextView.BufferType.EDITABLE)
                    val spannable = binding.includeBottomSheet.bottomSheetDescription.text as SpannableStringBuilder
                    spannable.setSpan(
                        ForegroundColorSpan(resources.getColor(R.color.teal_200)),
                        0, spannable.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE
                    )
//                    spannable.setSpan(TypefaceSpan(resources.getFont(R.font.alfa_slab_one)),
//                    0, spannable.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

                }


            }
            is PODData.Loading -> {
                binding.imageView.load(R.drawable.progress_animation) {

                }
            }
            is PODData.Error -> {
                error(R.drawable.ic_load_error_vector)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = PODFragment()
        private var isMain = true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_api_activity -> {
                startActivity(Intent(context, ApiActivityBottom::class.java))
            }
            R.id.app_bar_fav -> {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, FragmentExampleConstraintLayout.newInstance())
                    .addToBackStack("").commit()
            }
            R.id.app_bar_settings -> {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, SettingsFragment.newInstance())
                    .addToBackStack("").commit()
            }
            // у бургера id home
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment.newInstance()
                        .show(it.supportFragmentManager, "tag")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBottomAppBar() {
        (activity as MainActivity).setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)
        binding.fab.setOnClickListener {
            if (isMain) {
                isMain = false
                binding.bottomAppBar.navigationIcon = null
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_back_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
            } else {
                isMain = true
                binding.bottomAppBar.navigationIcon =
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_hamburger_menu_bottom_bar
                    )
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_plus_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
            }

        }
    }

}