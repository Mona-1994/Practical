package com.mg.iqlance_practical.Activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.location.Address
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.mg.iqlance_practical.DataBase
import com.mg.iqlance_practical.Student
import kotlinx.android.synthetic.main.student_item.view.*
import java.io.IOException
import android.location.Location
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import com.mg.iqlance_practical.R


class AllStudentAdapter(val applicationContext: Context, var list: MutableList<Student>) :
    RecyclerView.Adapter<AllStudentAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(applicationContext).inflate(
                R.layout.student_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvStudentName.text = list.get(position).name

        val bitmap = list.get(position).image?.size?.let {
            BitmapFactory.decodeByteArray(
                list.get(position).image,
                0,
                it
            )
        }

        holder.ivStudentImage.setImageBitmap(bitmap)

        holder.ivEdit.setOnClickListener({


            applicationContext.startActivity(
                Intent(applicationContext, AddUserActivity::class.java)
                    .putExtra("isFrom", true)
                    .putExtra("data", list.get(position))
            )

            //DataBase.getDbInstance(applicationContext).student.updateStudent(list.get(position))

        })
        holder.ivDelete.setOnClickListener({
            DataBase.getDbInstance(applicationContext).student.deleteStudent(list.get(position))

            list.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, list.size)

            Toast.makeText(applicationContext, "Successfully Deleted", Toast.LENGTH_LONG)
                .show()


        })


        holder.itemView.setOnClickListener({

            val address = list.get(position).address
            val latlong: LatLng = getLocationFromAddress(address)

            var nearByStdList = ArrayList<LatLng>()
            for (i in list.indices) {
                val address = list.get(i).address
                val referenceLatLong: LatLng = getLocationFromAddress(address)
                val results = FloatArray(1)
                Location.distanceBetween(
                    latlong.latitude,
                    latlong.longitude,
                    referenceLatLong.latitude,
                    referenceLatLong.longitude,
                    results
                )


                val distanceInMeters = results[0]
                if (distanceInMeters < 1000) {
                    if (latlong != referenceLatLong)
                        nearByStdList.add(referenceLatLong)
                }

            }

            //ZEBRAVO

            applicationContext.startActivity(
                Intent(applicationContext, MapsActivity::class.java)
                    .putExtra("latlong", latlong)
                    .putExtra("data", list.get(position))
                    .putExtra("nearByStdList", nearByStdList)
            )


        })

    }




    private fun getLocationFromAddress(address: String): LatLng {
        val geocoder = Geocoder(applicationContext)

        var list: List<Address> = emptyList()
        var latLng = null

        try {
            list = geocoder.getFromLocationName(address, 5)
        } catch (e: IOException) {

        }

        var location: Address = list.get(0)

        return LatLng(location.latitude, location.longitude)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvStudentName = itemView.tvStudentName
        val ivStudentImage = itemView.ivStudentImage
        val ivEdit = itemView.ivEdit
        val ivDelete = itemView.ivDelete

    }

}
