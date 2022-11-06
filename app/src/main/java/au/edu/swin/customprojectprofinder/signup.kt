package au.edu.swin.customprojectprofinder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import au.edu.swin.customprojectprofinder.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class signup : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)

        setContentView(binding.root)


        binding.signup.setOnClickListener(){
            val name = binding.name.text.toString()
            val email = binding.email.text.toString()
            val phone = binding.phone.text.toString()
            val password = binding.password.text.toString()
            val male = binding.male
            val female = binding.female
            val student = binding.student
            val teacher = binding.teacher
            database = FirebaseDatabase.getInstance().getReference("users")
            var gender = "0"
            var role = "0"

            if(email.isNotEmpty() && name.isNotEmpty() && phone.isNotEmpty()
                && password.isNotEmpty() && (male.isChecked() || female.isChecked()) &&
                (student.isChecked() || teacher.isChecked())){

                if(male.isChecked())
                {gender = "1"}
                if(teacher.isChecked())
                {role = "1"}
                val profileModel = ProfileModel(name,email,phone,password,gender,role)

                database.child(email).setValue(profileModel).addOnSuccessListener {
                    Toast.makeText(this, "Created an account successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, au.edu.swin.customprojectprofinder.login::class.java)
                    startActivity(intent)
                }.addOnFailureListener{
                    Toast.makeText(this, "Fail!", Toast.LENGTH_SHORT).show()
                }
                }
            else{
                Toast.makeText(this, "All fields need to be filled", Toast.LENGTH_SHORT).show()
            }
        }

        binding.back.setOnClickListener(){
            val intent = Intent(this, au.edu.swin.customprojectprofinder.login::class.java)
            startActivity(intent)
        }
    }
}