package com.carrot.trucoder2.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.carrot.trucoder2.R
import com.carrot.trucoder2.database.CodeDatabase
import com.carrot.trucoder2.repository.CodeRespository
import com.carrot.trucoder2.viewmodel.DetailsActivityViewModelProviderFactory
import com.carrot.trucoder2.viewmodel.DetailsViewModel
import com.carrot.trucoder2.viewmodel.MainActivityViewModel
import com.carrot.trucoder2.viewmodel.MainActivityViewModelProviderFactory
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.coroutines.launch

class DetailsActivity : AppCompatActivity() {

    var flag =1
    lateinit var viewModel:DetailsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val codeRepository = CodeRespository(CodeDatabase(this))
        val viewModelProviderFactory = DetailsActivityViewModelProviderFactory(codeRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(DetailsViewModel::class.java)

        val info = findViewById<TextView>(R.id.giveuserinfo)
        val e1 = findViewById<EditText>(R.id.codechef_getHandle)
        val e2 = findViewById<EditText>(R.id.codeforces_getHandle)
        val t1 = findViewById<TextInputLayout>(R.id.details_name_text_input)
        val t2 = findViewById<TextInputLayout>(R.id.details_name_text_input2)
        val save = findViewById<CircularProgressButton>(R.id.continu)
        val save2 = findViewById<CircularProgressButton>(R.id.continu2)
        val sharedPref = this.getSharedPreferences("secret" , Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        viewModel.result1.observe(this , Observer {
            when(it){
                1->{
                    editor.putString("CFH",e1.text.toString())
                    info.text = "Your CodeChef handle"
                    t2.visibility = View.VISIBLE
                    t1.visibility = View.GONE
                    save.visibility = View.GONE
                    save2.visibility = View.VISIBLE
                }
                0->{
                    val toast = Toast.makeText(this , "No user found with handle ${e1.text}" , Toast.LENGTH_LONG)
                    toast.show()
                    save.revertAnimation()
                }
            }
        })


        viewModel.result2.observe(this , Observer {
            when(it){
                1->{
                    editor.putString("CCH",e2.text.toString()  )
                    editor.commit()
                    save.stopAnimation()
                    save.revertAnimation()
                    startActivity(Intent(this , MainActivity::class.java))
                    finish()
                }
                0->{
                    val toast = Toast.makeText(this , "No user found with handle ${e2.text}" , Toast.LENGTH_LONG)
                    toast.show()
                    save2.revertAnimation()
                }
            }
        })

        save.setOnClickListener{
            save.startAnimation()
            viewModel.getCodeforcesUser(e1.text.toString())

        }

        save2.setOnClickListener{
            save2.startAnimation()
            viewModel.getCodeChefUser(e2.text.toString())
        }


    }

}