    package com.carrot.trucoder2.fragment

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import com.bumptech.glide.Glide
import com.carrot.trucoder2.R
import com.carrot.trucoder2.activity.LoginActivity
import com.carrot.trucoder2.activity.MainActivity
import com.carrot.trucoder2.database.CodeDatabase
import com.carrot.trucoder2.repository.CodeRespository
import com.carrot.trucoder2.viewmodel.MainActivityViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.coroutines.launch

    class SettingsFragment : Fragment(R.layout.fragment_settings) {

        private lateinit var editText:EditText
        private lateinit var codeRespository:CodeRespository
        private lateinit var viewModel : MainActivityViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editText = EditText(requireContext())
        val auth  = FirebaseAuth.getInstance()
        val user = auth.currentUser
        codeRespository = CodeRespository(CodeDatabase(requireContext()))
        viewModel = (activity as MainActivity).viewModel

        if (user != null) {
            user_name.text = user.displayName
            Glide.with(this).load(user.photoUrl).into(user_photo)
        }



        codechef.setOnClickListener{
            alertDialog("Codechef Handle", 2)
        }

        codeforces.setOnClickListener{
            alertDialog("Codeforces Handle", 1)
        }

        bug.setOnClickListener{
            val emailIntent = Intent(Intent.ACTION_VIEW , Uri.parse("mailto:" + "2000apoorv@gmail.com"))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Bug in TruCoder")
            try {
                this.startActivity(
                    Intent.createChooser(
                        emailIntent,
                        "Send email using..."
                    )
                )
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(
                    activity,
                    "No email clients installed.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        feature.setOnClickListener{
            val emailIntent = Intent(Intent.ACTION_VIEW , Uri.parse("mailto:" + "2000apoorv@gmail.com"))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feature Request")
            try {
                this.startActivity(
                    Intent.createChooser(
                        emailIntent,
                        "Send email using..."
                    )
                )
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(
                    activity,
                    "No email clients installed.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        github.setOnClickListener{
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://github.com/MaskedCarrot?tab=repositories")
            )
            startActivity(browserIntent)
        }

        signout.setOnClickListener{
            auth.signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
        }

    }

        private fun alertDialog(title: String, id2: Int){
            val dialogBuilder = AlertDialog.Builder(requireActivity())
            dialogBuilder.setMessage("New handle")
                .setView(editText)
                .setCancelable(true)
                .setPositiveButton("Save") { _, _ ->
                    val view = editText.parent as ViewGroup
                    view.removeView(editText)
                    nameChangeProtocol(id2, editText.text.toString())


                }
                .setNegativeButton("Cancel") { _, _ ->
                    val view = editText.parent as ViewGroup
                    view.removeView(editText)
                }


                .setOnCancelListener{
                    val view = editText.parent as ViewGroup
                    view.removeView(editText)
                }


            val alert = dialogBuilder.create()
            alert.setTitle(title)
            alert.show()
        }




        private fun nameChangeProtocol(id: Int, handle: String) = lifecycle.coroutineScope.launch{
            val sharedPref = activity?.getSharedPreferences("secret", Context.MODE_PRIVATE)
            when(id){
                1 -> {
                    val res = codeRespository.fetchFriendListCF(handle)
                    if (res.isSuccessful) {
                        codeRespository.delteAllCF()
                        viewModel.getCodeforcesFriends(handle)
                        viewModel.getCodeforcesUser(handle)
                        if (sharedPref != null) {
                            val editor = sharedPref.edit()
                            editor.putString("CFH", handle)
                            val toast = Toast.makeText(
                                requireContext(),
                                "Handle changed successfully",
                                Toast.LENGTH_LONG
                            )
                            toast.show()
                            editor.commit()
                        }
                    } else if (!res.isSuccessful) {
                        val toast = Toast.makeText(
                            requireContext(),
                            "User handle not found",
                            Toast.LENGTH_LONG
                        )
                        toast.show()
                    }
                }
                2 -> {
                    val res = codeRespository.fetchFriendListCC(handle)
                    if (res.isSuccessful) {
                        codeRespository.deleteAllCC()
                        viewModel.getCodechefFriends("$handle;")
                        if (sharedPref != null) {
                            val editor = sharedPref.edit()
                            editor.putString("CCH", handle)
                            editor.commit()
                        }

                    } else {
                        val toast = Toast.makeText(
                            requireContext(),
                            "User handle not found",
                            Toast.LENGTH_LONG
                        )
                        toast.show()
                    }
                }
            }
        }


    }