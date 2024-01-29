package com.example.bgproject.fragments.question

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bgproject.R
import com.example.bgproject.databinding.FragmentQuestionsBinding
import com.example.bgproject.viewmodel.UserViewModel


class QuestionsFragment : Fragment() {

    private lateinit var adapter: QuestionAdapter
    private lateinit var binding: FragmentQuestionsBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: UserViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentQuestionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        val myDataset = viewModel.getQuestions()

        adapter = QuestionAdapter(myDataset, viewModel)
        recyclerView = binding.questionRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        binding.btnSubmit.setOnClickListener {
            val userSelections = viewModel.getSelectedOptions()
            if (userSelections.keys.size != viewModel.questionList.size) {
                Toast.makeText(context, "please answer all question", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            val score = viewModel.evaluateAnswers(myDataset)
//            displayScore(score)

            viewModel.registerResult()

            findNavController().navigate(R.id.action_questionsFragment_to_resultFragment)

        }

    }

//    private fun displayScore(score: Int) {
//        binding.scoreTextView.text = "Score: $score/${adapter.itemCount}"
//    }


}

