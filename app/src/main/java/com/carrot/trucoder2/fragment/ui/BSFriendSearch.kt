package com.carrot.trucoder2.fragment.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.coroutineScope
import com.carrot.trucoder2.R
import com.carrot.trucoder2.activity.MainActivity
import com.carrot.trucoder2.database.CodeDatabase
import com.carrot.trucoder2.repository.CodeRespository
import com.carrot.trucoder2.utils.Constants.Companion.hideSoftKeyboard
import com.carrot.trucoder2.utils.Constants.Companion.showSoftKeyboard
import com.carrot.trucoder2.viewmodel.MainActivityViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch


class BSFriendSearch(val platform: Int) : BottomSheetDialogFragment(){

    private lateinit var viewModel : MainActivityViewModel
    private lateinit var codeRespository: CodeRespository
    val result : MutableLiveData<Int> = MutableLiveData();

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val contentView = View.inflate(context, R.layout.bs_friend_search, null)
        dialog.setContentView(contentView)
        showSoftKeyboard(contentView, activity as MainActivity)
        viewModel = (activity as MainActivity).viewModel
        codeRespository = CodeRespository(CodeDatabase(requireContext()))
        val handle = contentView.findViewById<TextInputEditText>(R.id.friend_bottomsheet_name_edit_text)
        val search = contentView.findViewById<Button>(R.id.bs_friend_find)
        handle.isSelected = true

        handle.requestFocus()
        val imm =  requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);


        search.setOnClickListener(View.OnClickListener {
            val name = handle.text.toString()
            findUser(platform, name)
            result.observe(this, {
                if (it == 1) {
                   hideSoftKeyboard(requireActivity())
                    dialog.dismiss()
                } else if (it == 0) {
                    val toast = Toast.makeText(
                        requireContext(),
                        "No user found with the handle ${handle.text}",
                        Toast.LENGTH_LONG
                    )
                    toast.show()
                    handle.setText("")
                }
            })
        })
    }


    private fun findUser(id: Int, handle: String) = lifecycle.coroutineScope.launch{
            when(id){
                1 -> {
                    val res = codeRespository.fetchFriendListCF(handle)

                    if (res.isSuccessful) {
                        res.body()?.result?.get(0)?.let { viewModel.InsertFriend(res.body()!!) }
                        result.postValue(1)
                    } else if (!res.isSuccessful) {
                        result.postValue(0)
                    }
                }
                2 -> {
                    val res = codeRespository.fetchFriendListCC(handle)
                    if (res.isSuccessful) {
                        res.body()?.result?.get(0)?.let { viewModel.InsertFriend(res.body()!!) }
                        result.postValue(1)
                    } else {
                        result.postValue(0)
                    }
                }
            }
    }


}