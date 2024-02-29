package com.ryandhikaba.belajarfundamentalaplikasiandroid.MyNavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.ryandhikaba.belajarfundamentalaplikasiandroid.R
import com.ryandhikaba.belajarfundamentalaplikasiandroid.databinding.FragmentNavDetailCategoryBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NavDetailCategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NavDetailCategoryFragment : Fragment() {

    private var _binding: FragmentNavDetailCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNavDetailCategoryBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        Mengguanakan bundle
//        val dataName = arguments?.getString(NavCategoryFragment.EXTRA_NAME)
//        val dataDescription = arguments?.getLong(NavCategoryFragment.EXTRA_STOCK)

//        Menggunakan argumen
        val dataName = NavDetailCategoryFragmentArgs.fromBundle(arguments as Bundle).name
        val dataDescription = NavDetailCategoryFragmentArgs.fromBundle(arguments as Bundle).stock

        binding.tvCategoryName.text = dataName
        binding.tvCategoryDescription.text = "Stock : $dataDescription"

        binding.btnHome.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_navDetailCategoryFragment_to_navHomeFragment)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}