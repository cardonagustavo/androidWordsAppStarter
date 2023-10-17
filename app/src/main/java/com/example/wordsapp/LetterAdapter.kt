package com.example.wordsapp

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView

/**
 * Adaptador para el [RecyclerView] en [MainActivity].
 */
class LetterAdapter :
    RecyclerView.Adapter<LetterAdapter.LetterViewHolder>() {

    // Genera un [CharRange] de 'A' a 'Z' y lo convierte en una lista
    private val list = ('A').rangeTo('Z').toList()

    /**
     * Proporciona una referencia a las vistas necesarias para mostrar elementos en la lista.
     */
    class LetterViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val button = view.findViewById<Button>(R.id.button_item)
    }

    /**
     * Devuelve el número total de elementos en la lista.
     */
    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * Crea nuevas vistas con R.layout.item_view como su plantilla.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_view, parent, false)
        // Configura un delegado de accesibilidad personalizado para establecer el texto leído.
        layout.accessibilityDelegate = Accessibility
        return LetterViewHolder(layout)
    }

    /**
     * Reemplaza el contenido de una vista existente con nuevos datos.
     */
    override fun onBindViewHolder(holder: LetterViewHolder, position: Int) {

        holder.button.setOnClickListener() {
            val action = LetterListFragmentDirections.actionLetterListFragmentToWordListFragment(letter = holder.button.text.toString())
        }
        val item = list.get(position)
        holder.button.text = item.toString()
    }

    // Configura un delegado de accesibilidad personalizado para establecer el texto leído con
    // un servicio de accesibilidad.
    companion object Accessibility : View.AccessibilityDelegate() {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onInitializeAccessibilityNodeInfo(
            host: View,
            info: AccessibilityNodeInfogit
        ) {
            super.onInitializeAccessibilityNodeInfo(host, info)
            // Con `null` como el segundo argumento de [AccessibilityAction], el
            // servicio de accesibilidad anunciará "pulsa dos veces para activar".
            // Si se proporciona una cadena personalizada,
            // anunciará "pulsa dos veces para <cadena personalizada>".
            val customString = host.context?.getString(R.string.look_up_words)
            val customClick =
                AccessibilityNodeInfo.AccessibilityAction(
                    AccessibilityNodeInfo.ACTION_CLICK,
                    customString
                )
            info.addAction(customClick)
        }
    }
}
