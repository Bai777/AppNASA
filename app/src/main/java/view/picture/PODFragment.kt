package view.picture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.appnasa.R
import com.example.appnasa.databinding.FragmentMainBinding
import utils.showSnackBar
import viewModel.PODData
import viewModel.PODViewModel

class PODFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    val binding: FragmentMainBinding
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
        _binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.sendServerRequest()
    }

    private fun renderData(data: PODData) {
        when (data) {
            is PODData.Success -> {
                binding.imageView.load(data.serverResponseData.url) {
                    error(R.drawable.ic_load_error_vector)
                }
            }
            is PODData.Loading -> {
                binding.main.showSnackBar(getString(R.string.load))
            }
            is PODData.Error -> {
                binding.main.showSnackBar(getString(R.string.error))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = PODFragment()
    }


}