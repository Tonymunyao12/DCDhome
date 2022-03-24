package com.mulatya.dcdhome.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.mulatya.dcdhome.R
import com.mulatya.dcdhome.database.RegisterDatabase
import com.mulatya.dcdhome.database.RegisterRepository
import com.mulatya.dcdhome.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding: FragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        // val application = requireNotNull(this.activity).application
        val application = requireActivity().application

        val dao = RegisterDatabase.getInstance(application).registerDatabaseDao

        val repository = RegisterRepository(dao)

        val factory = LoginViewModelFactory(repository, application)

       // loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        binding.myLoginViewModel = LoginFragment()

        binding.lifecycleOwner = this

        loginViewModel.navigateToRegister.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished == true){
                Log.i("MY_TAG", "Inside Observe")
                loginViewModel.doneNavigatingToRegister()
            }

        })

        loginViewModel.errorToast.observe(viewLifecycleOwner, Observer {
            hasError ->
            if (hasError == true){
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                loginViewModel.doneToast()
            }
        })

        loginViewModel.errorToastUsername.observe(viewLifecycleOwner, Observer {              hasError ->
               if (hasError == true){
                   Toast.makeText(requireContext(), "User doesn't Exist Please Register!", Toast.LENGTH_SHORT).show()
                   loginViewModel.doneToastErrorUsername()
               }
        })

        loginViewModel.errorToastInvalidPassword.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError == true) {
                Toast.makeText(requireContext(), "Please check your password", Toast.LENGTH_SHORT)
                    .show()
                loginViewModel.doneToastInvalidPassword()
            }
        })

        loginViewModel.navigateToDrawerActivity.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished == true) {
                Log.i("MY_TAG", "Inside Observe")
                // navigateDrawerActivityMain()
                loginViewModel.doneNavigatingToDrawerActivity()
            }

        })



        return binding.root
    }
/*
    private fun navigateDrawerActivityMain(){
        Log.i("MY_TAG", "Inside Drawer Activity")
        val action = LoginFragmentDirections.actionLoginFragmentToDrawerActivityMain()
        NavHostFragment.findNavController(this).navigate(action)


    }
    */
}