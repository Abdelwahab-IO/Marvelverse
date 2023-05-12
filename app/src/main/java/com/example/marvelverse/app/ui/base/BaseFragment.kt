package com.example.marvelverse.app.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<DB : ViewBinding>
    (val inflate: (LayoutInflater, ViewGroup?, Boolean) -> DB) : Fragment() {
    protected lateinit var binding: DB
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = inflate(layoutInflater, container, false)
        return binding.root
    }
    protected fun showAppBar(){
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }
    protected fun hideAppBar(){
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }
}