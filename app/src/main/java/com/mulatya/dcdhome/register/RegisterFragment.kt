package com.mulatya.dcdhome.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mulatya.dcdhome.R
import com.mulatya.dcdhome.database.RegisterDatabase
import com.mulatya.dcdhome.database.RegisterDatabaseDao
import com.mulatya.dcdhome.database.RegisterRepository
import com.mulatya.dcdhome.databinding.RegisterHomeFragmentBinding

class RegisterFragment : Fragment() {

    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: RegisterHomeFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.register_home_fragment, container, false)

        val application = requireActivity().application

        val dao = RegisterDatabase.getInstance(application).registerDatabaseDao

        val repository = RegisterRepository(dao)

        val factory = RegisterViewModelFactory(repository, application)

       // registerViewModel = ViewModelProvider(this, factory).get(RegisterViewModel::class.java)

        binding.myViewModel = registerViewModel

        binding.lifecycleOwner = this

        registerViewModel.navigateToDrawerActivity.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished == true){
                Log.i("MY_TAG", "Inside Observe")
                // displayUserList is a function
                registerViewModel.doneNavigatingToDrawerActivity()
            }
        })

        registerViewModel.userDetailsLiveData.observe(viewLifecycleOwner, Observer {
            Log.i("MY_TAG", it.toString()+ "-------------------------------")
        })

        registerViewModel.errorToast.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError == true) {
                Toast.makeText(requireContext(), "Please fill all the Fields", Toast.LENGTH_SHORT)
                    .show()
                registerViewModel.doneToast()
            }
        })

        registerViewModel.errorToastUsername.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError == true) {
                Toast.makeText(requireContext(), "Username already Taken", Toast.LENGTH_SHORT)
                    .show()
                registerViewModel.doneToastErrorUsername()
            }
        })

        return binding.root
    }

    /*
    // I have to figure out if I really need this function,
    // Maybe I will need it when I want to reflect the Users as output.
    private fun displayUsersList() {
        Log.i("MY_TAG","inside displayUsersList")
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        NavHostFragment.findNavController(this).navigate(action)

    }
    */

}