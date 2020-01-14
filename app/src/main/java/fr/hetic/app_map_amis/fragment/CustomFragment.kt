package fr.hetic.app_map_amis.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.hetic.app_map_amis.data.ContactList
import fr.hetic.app_map_amis.R
import fr.hetic.app_map_amis.adapter.ContactRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_custom.*


/**
 * A simple [Fragment] subclass.
 */
class CustomFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_custom, container, false)
    }


    override fun onStart() {
        super.onStart()
        
        contactRecyclerView.layoutManager = LinearLayoutManager(context)

        contactRecyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))

        // Liaison de l'adaptateur à la RecyclerView
        contactRecyclerView.adapter = contactAdapter

        // accès aux vues

        // ex : ajout des onClickListener
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

    }



}
