package com.carrot.trucoder2.fragment.welcome

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.carrot.trucoder2.R
import com.carrot.trucoder2.database.CodeDatabase
import com.carrot.trucoder2.repository.CodeRespository
import com.carrot.trucoder2.viewmodel.DetailsActivityViewModelProviderFactory
import com.carrot.trucoder2.viewmodel.DetailsViewModel
import kotlinx.android.synthetic.main.fragment_codeforces_handle.*

class CodeforcesHandleFragment : Fragment(R.layout.fragment_codeforces_handle) {

    lateinit var viewModel: DetailsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val codeRepository = CodeRespository(CodeDatabase(requireContext()))
        val viewModelProviderFactory = DetailsActivityViewModelProviderFactory(codeRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(DetailsViewModel::class.java)
        var handle = "=_="

        w_codeforces_getHandle.setOnEditorActionListener { _, i, _ ->
            if(i == EditorInfo.IME_ACTION_NEXT){
                handle = w_codeforces_getHandle.text.toString()
                viewModel.getCodeforcesUser(handle)
            }
            false
        }

        viewModel.result1.observe(viewLifecycleOwner , {
            when(it){
                1->{
                    val sharedPreferences = requireContext().getSharedPreferences("secret" , Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("CFH" , handle)
                    editor.apply()
                    findNavController().navigate(R.id.action_codeforcesHandleFragment_to_timeZomeFragment)
                }
                0->{
                    Toast.makeText(requireContext() , "There was an error fetching your profile maybe there was a typo or you have not participated in enough contests" , Toast.LENGTH_LONG).show()
                }
            }
        })


        w_codeforces_skip.setOnClickListener {
            val sharedPreferences = requireContext().getSharedPreferences("secret" , Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("CFH" , handle)
            editor.apply()
            findNavController().navigate(R.id.action_codeforcesHandleFragment_to_timeZomeFragment)
        }
    }
}