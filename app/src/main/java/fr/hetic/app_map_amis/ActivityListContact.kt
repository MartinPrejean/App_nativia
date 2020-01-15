package fr.hetic.app_map_amis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.hetic.app_map_amis.adapter.ContactRecyclerAdapter
import fr.hetic.app_map_amis.data.ContactList
import kotlinx.android.synthetic.main.fragment_custom.*

class ActivityListContact : AppCompatActivity() {

    /*override fun onStart() {
        super.onStart()

        contactRecyclerView.layoutManager = LinearLayoutManager(this)

        contactRecyclerView.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))

        // Liaison de l'adaptateur à la RecyclerView
        contactRecyclerView.adapter = contactAdapter

    }

    private lateinit var contactAdapter: ContactRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        contactAdapter = ContactRecyclerAdapter()
        addDataSet()
    }

    private fun addDataSet(){
        val data = ContactList.createDataSet()
        contactAdapter.submitList(data)

    }*/
}
