package com.example.mubarak_ansari_practicals.features.home.presantation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mubarak_ansari_practicals.R
import com.example.mubarak_ansari_practicals.databinding.FragmentUserListingBinding
import com.example.mubarak_ansari_practicals.features.common.presantation.fragment.SortOptionBottomSheetFragment
import com.example.mubarak_ansari_practicals.features.home.data.model.Item
import com.example.mubarak_ansari_practicals.features.home.presantation.adapter.ItemClick
import com.example.mubarak_ansari_practicals.features.home.presantation.adapter.UserListAdapter
import com.example.mubarak_ansari_practicals.features.home.presantation.viewmodel.UserListState
import com.example.mubarak_ansari_practicals.features.home.presantation.viewmodel.UserViewModel
import com.example.mubarak_ansari_practicals.utils.Constant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserListingFragment : Fragment(), ItemClick {

    private lateinit var binding: FragmentUserListingBinding
    private val viewmodel: UserViewModel by viewModels()
    private lateinit var mAdapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserListingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialise()
    }

    private fun initialise() {
        setup()
        fetchData()
        collectUiState()
        bottomSheet()
    }

    private fun fetchData() {
        if (Constant.isNetworkConnectionAvailable(requireContext())) {
            viewmodel.getUserListing()
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.please_check_internet), Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setup() {
        binding.apply {
            swipeRefreshLayout.isRefreshing = false
            mAdapter = UserListAdapter(this@UserListingFragment)
            rvUsersListing.adapter = mAdapter



            swipeRefreshLayout.setOnRefreshListener {
                viewmodel.reset()
                binding.rvUsersListing.visibility = View.GONE
                fetchData()
            }
        }
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewmodel.userListState.collect {
                when (it) {
                    is UserListState.Loading -> {
                        binding.progressCircular.root.visibility = View.VISIBLE
                    }

                    is UserListState.Error -> {
                        binding.progressCircular.root.visibility = View.GONE
                        binding.rvUsersListing.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.something_went_wrong),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is UserListState.Success -> {
                        binding.swipeRefreshLayout.isRefreshing = false
                        binding.progressCircular.root.visibility = View.GONE
                        binding.rvUsersListing.visibility = View.VISIBLE
                        mAdapter.setData(it.response)
                        mAdapter.notifyDataSetChanged()
                    }

                    else -> {}
                }
            }
        }
    }

    private fun bottomSheet() {
        binding.btnSortData.setOnClickListener {
            val bottomSheetFragment = SortOptionBottomSheetFragment()
            bottomSheetFragment.btnName = binding.btnSortData.text.toString()
            bottomSheetFragment.callbackDidSelect = { sortName ->
                viewmodel.sortFilter = sortName.toString()
                viewmodel.reset()
                fetchData()
                binding.btnSortData.text = setFilterButtonTitle(sortName)
            }
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }
    }

    private fun setFilterButtonTitle(sortName: String): String {
        return if (sortName.equals(
                "reputation",
                true
            )
        ) getString(R.string.sort_by_reputation) else getString(R.string.sort_by_name)
    }

    override fun getUserDetails(model: Item) {
        Toast.makeText(requireContext(), model.display_name, Toast.LENGTH_SHORT).show()
    }
}