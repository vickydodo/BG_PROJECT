package com.example.bgproject.fragments.recruitment

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bgproject.R
import com.example.bgproject.databinding.FragmentRecruitmentBinding
import com.example.bgproject.model.Tgl
import com.example.bgproject.viewmodel.UserViewModel
import kotlinx.coroutines.launch
import java.util.Calendar

class RecruitmentFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var binding: FragmentRecruitmentBinding
    private var datePickerDialog: DatePickerDialog? = null
    private val PICK_IMAGE_REQUEST = 1
    private var selectedFileUri: Uri? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecruitmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        binding.btnRegister.setOnClickListener {
            registerTgl()
        }

        val sex = resources.getStringArray(R.array.Sex)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, sex)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)

        val state = resources.getStringArray(R.array.State)
        val arrayAdapterState = ArrayAdapter(requireContext(), R.layout.dropdown_item, state)
        binding.autoCompleteTextView2.setAdapter(arrayAdapterState)

        val idType = resources.getStringArray(R.array.idType)
        val arrayAdapterIdType = ArrayAdapter(requireContext(), R.layout.dropdown_item, idType)
        binding.autoCompleteTextView3.setAdapter(arrayAdapterIdType)


        binding.etDob.setOnClickListener {
            openDatePicker()
        }


        binding.tvImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

    }

    private fun registerTgl() {
        val fullName = binding.etFullname.text.toString()
        val number = binding.etPhone.text.toString()
        val sex = binding.autoCompleteTextView.text
        val dob = binding.etDob.text
        val bvn = binding.etBvn.text.toString()
        val nin = binding.etNin.text.toString()
        val state = binding.autoCompleteTextView2.text
        val lga = binding.lga.text.toString()
        val hub = binding.etHub.text.toString()
        val govType = binding.autoCompleteTextView3.text
        val govId = binding.govId.text.toString()
        val govImage = binding.imageView3.toString()


        when {
            fullName.length < 5 -> Toast.makeText(
                activity,
                "Name must be at least 5 characters long", Toast.LENGTH_LONG
            ).show()

            number.length < 10 -> Toast.makeText(
                activity,
                "Phone number must be at least 10 characters long", Toast.LENGTH_LONG
            ).show()

            sex.isEmpty() -> Toast.makeText(
                activity,
                "Please select a gender", Toast.LENGTH_LONG
            ).show()

            dob.isEmpty() -> Toast.makeText(
                activity,
                "Please enter your date of birth", Toast.LENGTH_LONG
            ).show()

            bvn.length < 11 -> Toast.makeText(
                activity,
                "BVN must be at least 11 characters long", Toast.LENGTH_LONG
            ).show()

            nin.length < 11 -> Toast.makeText(
                activity,
                "NIN must be at least 11 characters long", Toast.LENGTH_LONG
            ).show()

            state.isEmpty() -> Toast.makeText(
                activity,
                "Please select a state", Toast.LENGTH_LONG
            ).show()

            lga.isEmpty() -> Toast.makeText(
                activity,
                "Please enter your LGA", Toast.LENGTH_LONG
            ).show()

            hub.isEmpty() -> Toast.makeText(
                activity,
                "Please enter your hub", Toast.LENGTH_LONG
            ).show()

            govType.isEmpty() -> Toast.makeText(
                activity,
                "Please select an ID type", Toast.LENGTH_LONG
            ).show()

            govId.isEmpty() -> Toast.makeText(
                activity,
                "Please enter your ID number", Toast.LENGTH_LONG
            ).show()

            govImage == null -> Toast.makeText(
                activity,
                "Please select an Image", Toast.LENGTH_LONG
            ).show()
            else -> {
                lifecycleScope.launch {
                    mUserViewModel.tgl = Tgl(
                        generateTglId(),
                        fullName = fullName,
                        number = number,
                        sex = sex,
                        dob = dob,
                        bvn = bvn,
                        nin = nin,
                        state = state,
                        lga = lga,
                        hub = hub,
                        govType = govType,
                        govId = govId,
                        govImage =  govImage ,
                        officerId = ""
                    )
                    selectedFileUri?.let {
                        mUserViewModel.insertOfficerIdentification(
                            mUserViewModel.tgl!!,
                            requireContext(), // pass the context of the fragment
                            it // pass the selected image Uri
                        )
                    }
                    val sharedPreferences =
                        requireActivity().getSharedPreferences(
                            "my_preferences",
                            Context.MODE_PRIVATE
                        )
                    mUserViewModel.tgl?.officerId =
                        sharedPreferences.getString("OFFICER_ID", "").toString()
                    mUserViewModel.registerTgl()
                    Toast.makeText(
                        context,
                        "User Successfully Registered",
                        Toast.LENGTH_SHORT
                    ).show()
                    mUserViewModel.tgl = null
                    findNavController().navigate(R.id.action_recruitmentFragment_to_tglsFragment)
                }
            }


        }
    }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
                val imageUri = data.data
                binding.imageView3.setImageURI(imageUri)
                // Use the image URI to load the selected image
            }
        }


        private fun openDatePicker() {
            val calendar = Calendar.getInstance()
            val day = calendar[Calendar.DAY_OF_MONTH]
            val month = calendar[Calendar.MONTH]
            val year = calendar[Calendar.YEAR]

            datePickerDialog = DatePickerDialog(
                requireContext(),
                { View, year, monthOfYear, dayOfMonth ->
                    val month = monthOfYear + 1
                    var strMonth = "" + month
                    var strDayOfMonth = "" + dayOfMonth
                    if (month < 10) {
                        strMonth = "0$strMonth"
                    }
                    if (dayOfMonth < 10) {
                        strDayOfMonth = "0$strDayOfMonth"
                    }
                    val date = "$strDayOfMonth-$strMonth-$year"
                    binding.etDob.setText(date)
                }, year, month, day
            )
            datePickerDialog!!.setTitle("Select Date")
            datePickerDialog!!.show()
        }

        fun generateTglId(): String {
            return "tgl_${System.currentTimeMillis()}"
        }

}



