package au.edu.swin.customprojectprofinder

import java.io.Serializable


data class subject(val name:String? = "",
                   val pic: String? = "",
                   var type:Int = Constants.PARENT,
                   var subList : MutableList<ChildData>? = ArrayList(),
                   var isExpanded:Boolean = false
                   ): Serializable{}


object Constants {
    const val PARENT = 0
    const val CHILD = 1
}