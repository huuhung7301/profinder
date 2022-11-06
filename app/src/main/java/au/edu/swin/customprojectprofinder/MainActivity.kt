package au.edu.swin.customprojectprofinder

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlin.math.abs

class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener {
    val lNames = arrayOf("Angela", "Ngoc Trinh", "Lan Ngoc", "Minh Hang", "Tra My", "Tlinh", "Chau Bui")
    val lGender = arrayOf("Female", "Female", "Female", "Female", "Female", "Female", "Female")
    val lAge = arrayOf(21, 22, 32, 19, 18, 20, 18)
    val lPrice = arrayOf(30, 40, 34, 45, 20, 50, 30)
    val lBio = arrayOf("I have doctor degree in Computer Science", "Hi, nice to teach you", "More than a study buddy",
        "I can teach you alphabet", "Singing is my profession", "Hey, Study with me for a cheap price", "Fashion? Me and you passion")
    val lImages = arrayOf(R.drawable.model1, R.drawable.model2, R.drawable.model3, R.drawable.model4, R.drawable.model5,
        R.drawable.model6, R.drawable.model7)
    var i =0
    lateinit var gestureDetector: GestureDetector
    var x1:Float = 0.0f
    var x2:Float = 0.0f
    var y1:Float = 0.0f
    var y2:Float = 0.0f
    companion object{
        const val MIN_DISTANCE = 150
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPref = this.getSharedPreferences("login",Context.MODE_PRIVATE)
        val loginstatus = sharedPref.getString("login", "fail").toString()
        Toast.makeText(this, "Welcome ${sharedPref.getString("name", "unknown").toString()}", Toast.LENGTH_SHORT).show()
        if(loginstatus == "fail"){
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }

        gestureDetector = GestureDetector(this, this)

        val name = findViewById<TextView>(R.id.name)
        val bio = findViewById<TextView>(R.id.bio)
        val price = findViewById<TextView>(R.id.price)
        val image = findViewById<ImageView>(R.id.mainPic)

        name.text = lNames[0]
        bio.text = lBio[0]
        price.text = "$" + lPrice[0].toString() + "/hour"
        image.setImageResource(lImages[0])

        val button1 = findViewById<ImageView>(R.id.b1)
        button1.setOnClickListener(){
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }

        val button2 = findViewById<ImageView>(R.id.b2)
        button2.setOnClickListener(){
            val intent = Intent(this, subjectlist::class.java)
            startActivity(intent)
        }

        val button3 = findViewById<ImageView>(R.id.b3)
        button3.setOnClickListener(){
            val intent = Intent(this, editbio::class.java)
            startActivity(intent)
        }

        val button4 = findViewById<ImageView>(R.id.b4)
        button4.setOnClickListener(){
            sharedPref ?: return@setOnClickListener
            with (sharedPref.edit()){
                putString("login", "fail")
                putString("name", "none")
                apply()
            }
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)
        val name = findViewById<TextView>(R.id.name)
        val bio = findViewById<TextView>(R.id.bio)
        val price = findViewById<TextView>(R.id.price)
        val image = findViewById<ImageView>(R.id.mainPic)
        when (event?.action){

            0->
            {
                x1 = event.x
                y1 = event.y
            }
            1->
            {
                x2 = event.x
                y2 = event.y
                val valueX:Float = x2-x1
                val valueY:Float = y2-y1

                if(abs(valueX) > MIN_DISTANCE)
                {
                    if(x2>x1)
                    {
                        Toast.makeText(this, "Right swipe", Toast.LENGTH_SHORT).show()
                        i++
                    }
                    else
                    {
                        Toast.makeText(this, "Left swipe", Toast.LENGTH_SHORT).show()
                        i++
                    }
                }
                else if(abs(valueY) > MIN_DISTANCE)
                {
                    if(y2>y1)
                    {
                        Toast.makeText(this, "Bottom swipe", Toast.LENGTH_SHORT).show()
                        i++
                    }
                    else
                    {
                        Toast.makeText(this, "Top swipe", Toast.LENGTH_SHORT).show()
                        i++
                    }
                }
                name.text = lNames[i]
                bio.text = lBio[i]
                price.text = "$" + lPrice[i].toString() + "/hour"
                image.setImageResource(lImages[i])
            }
        }
        return super.onTouchEvent(event)
    }


    override fun onDown(p0: MotionEvent?): Boolean {
        //TODO("Not yet implemented")
        return false
    }

    override fun onShowPress(p0: MotionEvent?) {
        //TODO("Not yet implemented")
    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        //TODO("Not yet implemented")
        return false
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        //TODO("Not yet implemented")
        return false
    }

    override fun onLongPress(p0: MotionEvent?) {
        //TODO("Not yet implemented")
    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        //TODO("Not yet implemented")
        return false
    }
}