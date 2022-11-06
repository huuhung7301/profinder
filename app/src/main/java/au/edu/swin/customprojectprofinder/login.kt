package au.edu.swin.customprojectprofinder

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import au.edu.swin.customprojectprofinder.databinding.ActivityLoginBinding
import au.edu.swin.customprojectprofinder.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().getReference("users")
        binding.signup.setOnClickListener(){
            val intent = Intent(this, au.edu.swin.customprojectprofinder.signup::class.java)
            startActivity(intent)
        }
        val sharedPref = this.getSharedPreferences("login",Context.MODE_PRIVATE)
        binding.login.setOnClickListener(){
            val email = binding.loginname.text.toString()
            val password = binding.password.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()){
                database.child(email).get().addOnSuccessListener{
                    if(it.exists()){
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        sharedPref ?: return@addOnSuccessListener
                        with (sharedPref.edit()){
                            putString("login", "successful")
                            putString("name", "${it.child("name").value}")
                            apply()
                        }
                    }
                    else{
                        Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "Something is wrong", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Missing username or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}