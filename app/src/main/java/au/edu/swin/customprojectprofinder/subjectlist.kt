package au.edu.swin.customprojectprofinder

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class subjectlist : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subjectlist)
        val listView = findViewById<RecyclerView>(R.id.subjectlist)

        val txtInput = resources.openRawResource(R.raw.subjects).bufferedReader()
        val list = findViewById<RecyclerView>(R.id.subjectlist)
        var parentid = "a"
        var parentname = "a"
        var parentpic = "a"
        var childname = "a"
        var childpic = "a"
        var childsubject: MutableList<ChildData> = ArrayList()
        var childsubject2: MutableList<ChildData> = ArrayList()
        var listall: MutableList<subject> = ArrayList()


        databaseReference = FirebaseDatabase.getInstance().getReference("subject")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (dataSnapshot in snapshot.children) {
                        if (dataSnapshot.child("parentID").getValue().toString() == "0") {
                            parentid = dataSnapshot.child("id").getValue().toString()
                            parentname = dataSnapshot.child("name").getValue().toString()
                            parentpic = dataSnapshot.child("pic").getValue().toString()
                            for (dataSnapshot2 in snapshot.children) {
                                if (dataSnapshot2.child("parentID").getValue().toString() == parentid) {
                                    childname = dataSnapshot2.child("name").getValue().toString()
                                    childpic = dataSnapshot2.child("pic").getValue().toString()
                                    childsubject.add(ChildData(childname, childpic))
                                }
                            }
                            childsubject2 = ArrayList(childsubject)
                            val parentObj =
                                subject(name = parentname, parentpic, subList = childsubject2)
                            listall.add(parentObj)
                            childsubject.clear()
                        }
                    }
                }
                Log.i("aaa", "$listall")
                list.adapter = adapter(this@subjectlist, listall)
                list.layoutManager = GridLayoutManager(this@subjectlist, 1)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@subjectlist, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }
}
