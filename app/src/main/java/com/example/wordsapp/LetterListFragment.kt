package com.example.wordsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsapp.databinding.FragmentLetterListBinding

/**
 * Fragmento que muestra una lista de letras con la opción de cambiar entre diseños de lista y cuadrícula.
 */
class LetterListFragment : Fragment() {
    private var _binding: FragmentLetterListBinding? = null
    private val binding get() = _binding!!

    private var isLinearLayoutManager = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Habilita la opción de menú en este fragmento.
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLetterListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView
        // Configura el diseño inicial del RecyclerView.
        chooseLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Limpia los recursos y evita pérdidas de memoria.
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Infla el menú de opciones desde el archivo XML layout_menu.
        inflater.inflate(R.menu.layout_menu, menu)

        val layoutButton = menu.findItem(R.id.action_switch_layout)
        // Configura el ícono del botón de cambio de diseño.
        setIcon(layoutButton)
    }

    /**
     * Cambia entre un diseño de lista lineal y un diseño de cuadrícula para el RecyclerView.
     */
    private fun chooseLayout() {
        when (isLinearLayoutManager) {
            true -> {
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = LetterAdapter()
            }
            false -> {
                recyclerView.layoutManager = GridLayoutManager(context, 4)
                recyclerView.adapter = LetterAdapter()
            }
        }
    }

    /**
     * Establece el ícono en el botón de menú para cambiar entre diseños de lista y cuadrícula.
     */
    private fun setIcon(menuItem: MenuItem?) {
        if (menuItem == null)
            return

        menuItem.icon =
            if (isLinearLayoutManager)
            // Establece el ícono de lista si el diseño actual es lineal.
                ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_grid_layout)
            else
            // Establece el ícono de cuadrícula si el diseño actual es de cuadrícula.
                ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_linear_layout)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_switch_layout -> {
                // Cambia entre diseños al hacer clic en el botón de cambio de diseño.
                isLinearLayoutManager = !isLinearLayoutManager
                chooseLayout()
                setIcon(item)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
