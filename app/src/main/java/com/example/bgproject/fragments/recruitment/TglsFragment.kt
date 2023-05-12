package com.example.bgproject.fragments.recruitment

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bgproject.R
import com.example.bgproject.databinding.FragmentTglsBinding
import com.example.bgproject.model.Tgl
import com.example.bgproject.viewmodel.UserViewModel
import kotlinx.coroutines.launch


class TglsFragment : Fragment() {

    private lateinit var binding: FragmentTglsBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTglsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TglAdapter()
        recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        mUserViewModel.getTglByUser.observe(viewLifecycleOwner, Observer { tgl ->
            adapter.setData(tgl)
        })

        binding.btnSignOut.setOnClickListener {
            findNavController().navigate(R.id.action_tglsFragment_to_signInFragment)
        }

        binding.btnNewTglRegistration.setOnClickListener {
            findNavController().navigate(R.id.action_tglsFragment_to_recruitmentFragment)
        }

//        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]
//
//        lifecycleScope.launch {
//            mUserViewModel.getTglByUser("")
//                .observe(viewLifecycleOwner, Observer { tgl ->
//                adapter.setData(tgl)
//            })
//        }

    }


}