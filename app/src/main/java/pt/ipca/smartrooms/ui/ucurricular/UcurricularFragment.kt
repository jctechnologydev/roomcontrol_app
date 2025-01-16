package pt.ipca.smartrooms.ui.ucurricular

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.ipca.smartrooms.KEY_USERTYPE
import pt.ipca.smartrooms.R
import pt.ipca.smartrooms.adapters.UCurricularsAdapter
import pt.ipca.smartrooms.model.UserType
import pt.ipca.smartrooms.viewmodel.UcurricularViewModel

class UcurricularFragment : Fragment() {
    private var recyclerView : RecyclerView? = null
    private var recyclerViewAdapter : UCurricularsAdapter? = null
    private var ucurricularPb : ProgressBar? = null

    val viewmodel by viewModels<UcurricularViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ucurricular, container, false)

        setViews(view)
        setObservers()

        return view
    }

    private fun setViews(view: View) {
        ucurricularPb = view.findViewById(R.id.ucurricular_pb)
        recyclerView = view.findViewById(R.id.ucurricular_data_rv)
        recyclerViewAdapter = UCurricularsAdapter(requireActivity(), viewmodel.userType)
        recyclerView?.apply {
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    fun setObservers(){
        viewmodel.loading.observe(viewLifecycleOwner){ isLoading ->
            if(isLoading != null){
                ucurricularPb?.isVisible = isLoading
            }
        }

        viewmodel.ucurricular.observe(viewLifecycleOwner){
            it?.let {listSensors ->
                recyclerViewAdapter?.setUcurriculars(listSensors)
            }
        }

        viewmodel.error.observe(viewLifecycleOwner){
            it?.getContentIfNotHandled()?.let { error ->
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object{
        fun newInstance(userType: UserType) : UcurricularFragment{
            return UcurricularFragment().apply {
                arguments = Bundle().apply {
                    this.putSerializable(KEY_USERTYPE, userType)
                }
            }

        }
    }

}