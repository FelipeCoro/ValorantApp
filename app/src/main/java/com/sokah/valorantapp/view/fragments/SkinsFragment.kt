package com.sokah.valorantapp.view.fragments

import android.app.Activity
import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sokah.valorantapp.R
import com.sokah.valorantapp.databinding.SkinsFragmentBinding
import com.sokah.valorantapp.view.adapters.AgentAdapter
import com.sokah.valorantapp.view.adapters.SkinAdapter
import com.sokah.valorantapp.viewmodel.SkinsViewModel
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.airbnb.paris.extensions.style
import com.google.gson.Gson
import com.sokah.valorantapp.model.weapons.Skin
import com.sokah.valorantapp.view.MainActivity


class SkinsFragment : Fragment(R.layout.skins_fragment), SkinAdapter.OnSkinListener {

    private lateinit var viewModel: SkinsViewModel
    private var _binding: SkinsFragmentBinding? = null
    private val binding get() = _binding!!
    lateinit var weapons: Array<String>
    lateinit var skinsList : MutableList<Skin>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = SkinsFragmentBinding.inflate(inflater, container, false)

        val layoutManager = GridLayoutManager(context, 2)
        binding.rvSkins.layoutManager = layoutManager

        val adapter = SkinAdapter(this)

        binding.rvSkins.adapter = adapter
        viewModel = ViewModelProvider(this).get(SkinsViewModel::class.java)


        viewModel.mutableSkinList.observe(this, {

            Log.e("TAG", it.size.toString())
            adapter.SetSkins(it)
            // hace que se suba el scroll cuando cambia algo en la lista
            layoutManager.scrollToPositionWithOffset(0, 0)
            skinsList=it
        })

        viewModel.isLoading.observe(this, {

            binding.progressBar3.isVisible = it
        })



          binding.autoCompleteTextView.setOnItemClickListener { _, _, position, id ->

              Toast.makeText(context, weapons.get(position), Toast.LENGTH_SHORT).show()

              if(position ==0){

                  viewModel.filterSkins("")
              }else{
                  viewModel.filterSkins(weapons.get(position))
              }

          }


        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()

        _binding = null

    }

    override fun onResume() {
        super.onResume()

        weapons = resources.getStringArray(R.array.weapon_types)

        val spinnerAdapter = ArrayAdapter(context!!, R.layout.custom_spinner, weapons)
        binding.autoCompleteTextView.setAdapter(spinnerAdapter)
    }

    override fun onSkinClick(position: Int) {

        val gson = Gson()

        val skin = gson.toJson(skinsList[position])

        binding.root.findNavController()
            .navigate(SkinsFragmentDirections.actionSkinsFragmentToSkinDetailFragment(skin))
    }


}