package com.carrot.trucoder2.fragment.welcome

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.carrot.trucoder2.R
import com.carrot.trucoder2.database.CodeDatabase
import com.carrot.trucoder2.repository.CodeRespository
import com.carrot.trucoder2.viewmodel.DetailsActivityViewModelProviderFactory
import com.carrot.trucoder2.viewmodel.DetailsViewModel
import kotlinx.android.synthetic.main.fragment_codechef_handle.*


class CodechefHandleFragment : Fragment(R.layout.fragment_codechef_handle) {


    lateinit var viewModel: DetailsViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val codeRepository = CodeRespository(CodeDatabase(requireContext()))
        val viewModelProviderFactory = DetailsActivityViewModelProviderFactory(codeRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(DetailsViewModel::class.java)
        var handle = "=_="


        w_codechef_getHandle.setOnEditorActionListener(OnEditorActionListener { _, i, _ ->
            if(i == EditorInfo.IME_ACTION_NEXT){
                handle = w_codechef_getHandle.text.toString()
                viewModel.getCodeChefUser(handle)
                true
            }else false
        })



        viewModel.result2.observe(viewLifecycleOwner, {
            when (it) {
                1 -> {
                    val sharedPreferences = requireContext().getSharedPreferences("secret" , Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("CCH", handle)
                    editor.apply()
                    findNavController().navigate(R.id.action_codechefHandleFragment_to_codeforcesHandleFragment)
                }
                0 -> {
                    Toast.makeText(
                        requireContext(),
                        "There was an error fetching your profile maybe there was a typo or you have not participated in enough contests",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })


        w_codechef_skip.setOnClickListener {
            val sharedPreferences = requireContext().getSharedPreferences(
                "secret",
                Context.MODE_PRIVATE
            )
            val editor = sharedPreferences.edit()
            editor.putString("CCH", handle)
            editor.apply()
            findNavController().navigate(R.id.action_codechefHandleFragment_to_codeforcesHandleFragment)
        }
    }
}