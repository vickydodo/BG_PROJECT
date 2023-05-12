package com.example.bgproject.fragments

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bgproject.R
import com.example.bgproject.databinding.FragmentSignInBinding
import com.example.bgproject.viewmodel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        binding.btnLogIn.setOnClickListener {
            val email = binding.etLoginEmail.text.toString()
            val password = binding.etLoginPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                lifecycleScope.launch {
                    mUserViewModel.login(email, password)

                    if (mUserViewModel.user == null) {
                        Toast.makeText(
                            context,
                            "User doesn't exist, please register",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@launch
                    }
                    val sharedPreferences = requireActivity().getSharedPreferences(
                        "my_preferences",
                        Context.MODE_PRIVATE
                    )
                    sharedPreferences.edit()
                        .putString("OFFICER_ID", mUserViewModel.user?.officerId).apply()

                    mUserViewModel.user = null

                    findNavController().navigate(R.id.action_signInFragment_to_homePageFragment)

                }
            }else{
                Toast.makeText(context, "Please enter all fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.llSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }
}