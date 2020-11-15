package com.eliseylobanov.cloudnotes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.test_fragment.*

class TestFragment : Fragment(R.layout.test_fragment) {

    private lateinit var viewModel: TestViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TestViewModel::class.java)
        viewModel.testData.observe(viewLifecycleOwner) { data: String ->
            textView.text = data
        }

        button.setOnClickListener {
            viewModel.changeText()
        }
    }

}