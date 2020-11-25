package com.ort.healthyfoods.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ort.healthyfoods.R

class PresentacionFragment : Fragment() {
    lateinit var vista: View
    lateinit var viewPager: ViewPager2
    lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vista = inflater.inflate(R.layout.fragment_presentacion, container, false)

        tabLayout = vista.findViewById(R.id.tab_layout)
        viewPager = vista.findViewById(R.id.view_pager)

        return vista
    }

    override fun onStart() {
        super.onStart()
        viewPager.setAdapter(createCardAdapter())

        TabLayoutMediator(tabLayout,viewPager,TabLayoutMediator.TabConfigurationStrategy { tab,position ->
            when (position) {
                0 -> tab.text = "Menúes"
                1 -> tab.text = "Comidas realizadas"
                2 -> tab.text = "Mi status"
                3 -> tab.text = "Mis Datos"
                else -> tab.text = "undefined"
            }
        }).attach()
    }

    private fun createCardAdapter():ViewPagerAdapter? {
        return ViewPagerAdapter(requireActivity())
    }

    class ViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
        override fun createFragment(position:Int):Fragment {
            return when(position) {
                0 -> PrincipalFragment()
                1 -> ComidasRealizadasFragment()
                2 -> ListRealizadasFragment()
                3 -> MisDatosFragment()

                else -> PrincipalFragment()
            }
        }

        override fun getItemCount(): Int {
            return TAB_COUNT
        }

        companion object {
            private const val TAB_COUNT = 4
        }
    }
}