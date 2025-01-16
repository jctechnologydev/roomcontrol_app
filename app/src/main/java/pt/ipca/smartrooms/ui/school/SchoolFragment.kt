package pt.ipca.smartrooms.ui.school

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.ipca.smartrooms.R
import pt.ipca.smartrooms.adapters.SchoolsAdapter
import pt.ipca.smartrooms.net.fetchImage
import pt.ipca.smartrooms.viewmodel.SchoolViewModel

class SchoolFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var recyclerViewAdapter: SchoolsAdapter? = null
    val viewmodel by viewModels<SchoolViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_school, container, false)

        setViews(view)
        setObservers()

        return view
    }


    fun setObservers() {
        viewmodel.school.observe(viewLifecycleOwner) {
            it?.let { listRooms ->
                recyclerViewAdapter?.setSchools(listRooms)
            }
        }

        viewmodel.error.observe(viewLifecycleOwner){
            it?.getContentIfNotHandled()?.let { error ->
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }
        }

        viewmodel.loading.observe(viewLifecycleOwner){
            it?.let { isLoading ->
                progressBar?.isVisible = isLoading
                recyclerView?.isVisible = !isLoading
            }
        }
    }

    private fun setViews(view: View) {
        progressBar = view.findViewById(R.id.school_pb)
        recyclerView = view.findViewById(R.id.school_lv)
        recyclerViewAdapter = SchoolsAdapter(requireActivity())
        recyclerViewAdapter?.fetchImage = { url, callback ->
            fetchImage(lifecycleScope, url, callback)
        }
        recyclerView?.apply {
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}