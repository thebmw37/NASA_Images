package com.nasaImages.ui

import android.graphics.drawable.ScaleDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.nasaImages.R

class XmlFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_xml, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val searchView = view.findViewById<EditText>(R.id.search_bar)

        /*searchView?.apply {
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.search_icon)
            val scaleDrawable = ScaleDrawable(drawable, 0, 50F, 50F)
            scaleDrawable.setBounds(0, 0, 50, 50)
            setCompoundDrawablesWithIntrinsicBounds(scaleDrawable, null, null, null)
        }*/
    }
}