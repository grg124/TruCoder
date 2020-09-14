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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.carrot.trucoder2.R
import com.carrot.trucoder2.database.CodeDatabase
import com.carrot.trucoder2.repository.CodeRespository
import com.carrot.trucoder2.viewmodel.DetailsActivityViewModelProviderFactory
import com.carrot.trucoder2.viewmodel.DetailsViewModel
import com.google.android.material.textfield.TextInputLayout

class DetailsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

    }

}