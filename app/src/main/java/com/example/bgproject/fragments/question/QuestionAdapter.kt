package com.example.bgproject.fragments.question

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bgproject.R
import com.example.bgproject.viewmodel.UserViewModel

class QuestionAdapter(
    private val questionList: ArrayList<Question>,
    private val viewModel: UserViewModel

) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {


    class QuestionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val questionNumber: TextView = view.findViewById(R.id.questionNumber)
        val question: TextView = view.findViewById(R.id.question)
        val optionsRadioGroup: RadioGroup = view.findViewById(R.id.optionsRadioGroup)
        val option1RadioButton: RadioButton = view.findViewById(R.id.option1RadioButton)
        val option2RadioButton: RadioButton = view.findViewById(R.id.option2RadioButton)
        val option3RadioButton: RadioButton = view.findViewById(R.id.option3RadioButton)
        val option4RadioButton: RadioButton = view.findViewById(R.id.option4RadioButton)
        val option5RadioButton: RadioButton = view.findViewById(R.id.option5RadioButton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.question_list, parent, false)
        return QuestionViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int {
        return questionList.size
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val item = questionList[position]
        holder.questionNumber.text = (position + 1).toString()
        holder.question.text = item.question
        holder.option1RadioButton.text = item.options[0]
        holder.option2RadioButton.text = item.options[1]
        holder.option3RadioButton.text = item.options[2]
        holder.option4RadioButton.text = item.options[3]
        holder.option5RadioButton.text = item.options[4]



        // Set the checked state of the RadioButton based on the selected option
        val selectedOptionPosition = viewModel.getSelectedOption(position)
        holder.optionsRadioGroup.clearCheck()

        if (selectedOptionPosition != null) {
            val radioButton =
                holder.optionsRadioGroup.getChildAt(selectedOptionPosition) as RadioButton
            radioButton.isChecked = true
        }
// Set the click listener for each RadioButton
        holder.option1RadioButton.setOnClickListener {
            if(!item.isAnswered) {
                updateSelectedOption(holder, position, 0)
            }
        }

        holder.option2RadioButton.setOnClickListener {
            if(!item.isAnswered) {
                updateSelectedOption(holder, position, 1)
            }
        }

        holder.option3RadioButton.setOnClickListener {
            if(!item.isAnswered) {
                updateSelectedOption(holder, position, 2)
            }
        }

        holder.option4RadioButton.setOnClickListener {
            if(!item.isAnswered) {
                updateSelectedOption(holder, position, 3)
            }
        }

        holder.option5RadioButton.setOnClickListener {
            if(!item.isAnswered) {
                updateSelectedOption(holder, position, 4)
            }
        }

        if (item.isAnswered) {
            holder.option1RadioButton.isEnabled = false
            holder.option2RadioButton.isEnabled = false
            holder.option3RadioButton.isEnabled = false
            holder.option4RadioButton.isEnabled = false
            holder.option5RadioButton.isEnabled = false
        } else {
            holder.option1RadioButton.isEnabled = true
            holder.option2RadioButton.isEnabled = true
            holder.option3RadioButton.isEnabled = true
            holder.option4RadioButton.isEnabled = true
            holder.option5RadioButton.isEnabled = true
        }

    }

    private fun updateSelectedOption(
        holder: QuestionViewHolder,
        position: Int,
        selectedPosition: Int,
    ) {
        holder.optionsRadioGroup.clearCheck()
        holder.optionsRadioGroup.check(holder.optionsRadioGroup.getChildAt(selectedPosition).id)
        viewModel.updateUserSelection(position, selectedPosition)
    }



}



