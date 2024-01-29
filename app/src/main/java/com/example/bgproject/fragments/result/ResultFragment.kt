package com.example.bgproject.fragments.result

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bgproject.R
import com.example.bgproject.databinding.FragmentResultBinding
import com.example.bgproject.databinding.FragmentTglsBinding
import com.example.bgproject.fragments.question.Question
import com.example.bgproject.fragments.question.QuestionAdapter
import com.example.bgproject.fragments.recruitment.TglAdapter
import com.example.bgproject.viewmodel.UserViewModel


class ResultFragment : Fragment() {

    private lateinit var adapter: ResultAdapter
    private lateinit var binding: FragmentResultBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var userViewModel: UserViewModel
    private lateinit var question: ArrayList<Question>



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        val myDataset = userViewModel.getQuestions()
        question = myDataset

        adapter = ResultAdapter()
        recyclerView = binding.resultRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        userViewModel.getResultByTgl.observe(viewLifecycleOwner, Observer { result ->
            adapter.setData(result)

        })

        val score = userViewModel.evaluateAnswers(question)
        displayScore(score)



    }

    private fun displayScore(score: Int) {
        binding.resultTextView.text = "Score: $score/${adapter.itemCount}"
    }

}