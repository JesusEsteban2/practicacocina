package com.example.practicacocina.adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.example.practicacocina.R
import com.example.practicacocina.activities.data.Tarea
import com.example.practicacocina.databinding.ItemLayoutBinding
import android.view.LayoutInflater
import androidx.core.view.get
import com.example.practicacocina.activities.DetailActivity
import com.example.practicacocina.data.DaoTask

class ReciclerAdapter (var dataSet: List<Tarea>,
                       val onDelClickListener: (position:Int) -> Unit ) :
    RecyclerView.Adapter<TareasAdapter.ViewHolder>()) {


        lateinit var binding:ItemLayoutBinding

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)


        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.render(dataSet[position])

            // Listener para el boton do/undo.
            binding.taskChip.setOnClickListener({onDoClickListener(position)})

            //listener para edicion en texto
            binding.taskText.setOnClickListener({onTexClickListener(position)})

            //Listener para el icono borrar
            binding.delImage.setOnClickListener({onDelClickListener(position)})

            // Listener para toda la línea.
            //holder.itemView.setOnClickListener { onDelClickListener(position) }
        }

        override fun getItemCount(): Int = dataSet.size

        fun updateItems(results: List<Tarea>) {
            dataSet = results
            notifyDataSetChanged()
        }

        private fun onDoClickListener(pos: Int){

            if (dataSet[pos].doit) {
                dataSet[pos].doit = false
            } else {
                dataSet[pos].doit = true
            }

            if (dataSet[pos].doit) {
                binding.taskChip.text = binding.root.context.getString(R.string.Done)
                binding.taskChip.chipIcon = binding.root.context.getDrawable(R.drawable.checkmark)
            } else {
                binding.taskChip.text = binding.root.context.getString(R.string.Pending)
                binding.taskChip.chipIcon = binding.root.context.getDrawable(R.drawable.uncheck)
            }

            var ta=dataSet[pos]

            DaoTask(binding.root.context).update(ta)

            dataSet = DaoTask(binding.root.context).queryAll()
            updateItems(dataSet)
        }

        private fun onTexClickListener(position:Int){
            val context: Context =binding.root.context
            val intent= Intent(context, DetailActivity::class.java)
            var mod:Int=position
            intent.putExtra("MODE",mod)
            context.startActivity(intent)
        }

        class ViewHolder(val binding: ItemLayoutBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun render(tarea: Tarea) {
                var context=binding.root.context
                var icon: Drawable? = context.getDrawable(R.drawable.uncheck)
                var te:String=context.getString(R.string.Pending)
                if (tarea.doit) {
                    icon = context.getDrawable(R.drawable.checkmark)
                    te=context.getString(R.string.Done)
                }
                binding.taskChip.chipIcon = icon
                binding.taskChip.text = te
                binding.taskText.text = tarea.task

                when (tarea.cat) {
                    0 -> binding.catImage.setImageDrawable(context.getDrawable(R.drawable.work_36))
                    1 -> binding.catImage.setImageDrawable(context.getDrawable(R.drawable.home_36))
                    2 -> binding.catImage.setImageDrawable(context.getDrawable(R.drawable.shoping_36))
                    3 -> binding.catImage.setImageDrawable(context.getDrawable(R.drawable.travel_36))
                    4 -> binding.catImage.setImageDrawable(context.getDrawable(R.drawable.urgent_36))
                    else -> binding.catImage.setImageDrawable(context.getDrawable(R.drawable.error_36))
                }
            }

            fun update (dataSet: List<Tarea>,adapter: TareasAdapter){
                adapter.updateItems(dataSet)
            }
        }

}