package com.dicoding.aquaculture.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.dicoding.aquaculture.R
import com.dicoding.aquaculture.databinding.FragmentProfileBinding
import com.dicoding.aquaculture.view.ViewModelFactory

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserName().observe(viewLifecycleOwner, Observer { userName ->
            binding.tvNama.text = getString(R.string.nama) + " " + userName

            binding.tvNama.animate().alpha(1f).setDuration(250).start()
        })

        viewModel.getEmail()
        viewModel.getUsernameOnLaunch()

        viewModel.getUserEmail().observe(viewLifecycleOwner, Observer{ userEmail ->
            binding.tvEmail.text = getString(R.string.email_profile) + " " + userEmail

            binding.tvEmail.animate().alpha(1f).setDuration(250).start()
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            showLoading(isLoading)
        })

        binding.btnLogout.setOnClickListener {
            viewModel.logout()
        }
    }

    private fun showLoading(isLoading : Boolean) {
        binding.progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}