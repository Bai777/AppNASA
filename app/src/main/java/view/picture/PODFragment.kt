package view.picture

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.TypefaceSpan
import android.view.*
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.provider.FontRequest
import androidx.core.provider.FontsContractCompat
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
                    val spannableStart = SpannableStringBuilder(it)
                    binding.includeBottomSheet.bottomSheetDescriptionHeader.setText(
                        spannableStart,
                        TextView.BufferType.EDITABLE
                    )
                    val spannable =
                        binding.includeBottomSheet.bottomSheetDescriptionHeader.text as SpannableStringBuilder
                    spannable.setSpan(
                        ForegroundColorSpan(resources.getColor(R.color.color_blue)),
                        0, spannable.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE
                    )

                    val request = FontRequest(
                        "com.google.android.gms.fonts",
                        "com.google.android.gms", "Architects Daughter",
                        R.array.com_google_android_gms_fonts_certs
                    )

                    val fontCallback = object: FontsContractCompat.FontRequestCallback() {
                        override fun onTypefaceRetrieved(typeface: Typeface?) {
                            super.onTypefaceRetrieved(typeface)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                                typeface?.let {
                                    spannable.setSpan(
                                        TypefaceSpan(it),
                                        0, spannable.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE
                                    )
                                }
                            }
                        }
                    }

                    FontsContractCompat.requestFont(requireContext(), request, fontCallback,
                        Handler(Looper.getMainLooper())
                    )
                }

                data.serverResponseData.explanation?.let {
                    val spannableStart = SpannableStringBuilder(it)
                    binding.includeBottomSheet.bottomSheetDescription.setText(
                        spannableStart,
                        TextView.BufferType.EDITABLE
                    )
                    val spannable =
                        binding.includeBottomSheet.bottomSheetDescription.text as SpannableStringBuilder
                    spannable.setSpan(
                        ForegroundColorSpan(resources.getColor(R.color.teal_200)),
                        0, spannable.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE
                    )
                    spannable.setSpan(
                        ResourcesCompat.getFont(requireContext(), R.font.architects_daughter),
                        0, spannable.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE
                    )
//                    spannable.setSpan(
//                        BackgroundColorSpan(resources.getColor(R.color.textColorSecondary)),
//                        1, spannable.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE
//                    )

                    val request = FontRequest(
                        "com.google.android.gms.fonts",
                        "com.google.android.gms", "Architects Daughter",
                        R.array.com_google_android_gms_fonts_certs
                    )

                    val fontCallback = object: FontsContractCompat.FontRequestCallback() {
                        override fun onTypefaceRetrieved(typeface: Typeface?) {
                            super.onTypefaceRetrieved(typeface)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                                typeface?.let {
                                    spannable.setSpan(
                                        TypefaceSpan(it),
                                        0, spannable.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE
                                    )
                                }
                            }
                        }
                    }

                    FontsContractCompat.requestFont(requireContext(), request, fontCallback,
                        Handler(Looper.getMainLooper())
                    )

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