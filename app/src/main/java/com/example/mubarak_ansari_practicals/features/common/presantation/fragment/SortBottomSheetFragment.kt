package com.example.mubarak_ansari_practicals.features.common.presantation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mubarak_ansari_practicals.R
import com.example.mubarak_ansari_practicals.databinding.SortOptionsBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SortOptionBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: SortOptionsBottomSheetBinding? = null
    private val binding get() = _binding!!

    var btnName: String? = null
    var callbackDidSelect: ((String) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SortOptionsBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureCallbacks()
    }

    private fun configureCallbacks() {
        binding.closeButton.setOnClickListener {
            dismiss()
        }

        binding.sortName.setOnClickListener {
            callbackDidSelect?.invoke("name")
            dismiss()
        }

        binding.sortReputation.setOnClickListener {
            callbackDidSelect?.invoke("reputation")
            dismiss()
        }

        if (btnName != null && btnName == getString(R.string.sort_by_reputation)) {
            binding.sortReputation.isChecked = true
        } else {
            binding.sortName.isChecked = true
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        //destroyed
        _binding = null
    }
}
