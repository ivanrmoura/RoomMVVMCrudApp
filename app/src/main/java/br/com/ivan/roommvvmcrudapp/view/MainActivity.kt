package br.com.ivan.roommvvmcrudapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.ivan.roommvvmcrudapp.R
import br.com.ivan.roommvvmcrudapp.data.SubscriberDatabase
import br.com.ivan.roommvvmcrudapp.data.model.Subscriber
import br.com.ivan.roommvvmcrudapp.data.repository.SubscriberRepository
import br.com.ivan.roommvvmcrudapp.databinding.ActivityMainBinding
import br.com.ivan.roommvvmcrudapp.viewmodel.SubscribeViewModelFactory
import br.com.ivan.roommvvmcrudapp.viewmodel.SubscriberViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel
    private lateinit var adapter: MyRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = SubscriberDatabase.getDatabase(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscribeViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this, factory).get(SubscriberViewModel::class.java)

        binding.myViewModel = subscriberViewModel
        binding.lifecycleOwner = this

        subscriberViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        initRecyclerView()
    }

    private fun listItemClicked(subscriber: Subscriber){
        subscriberViewModel.initUpdateAndDelete(subscriber)
    }

    private fun initRecyclerView(){
        binding.subscriberRecyclerView.layoutManager= LinearLayoutManager(this)
        adapter = MyRecyclerViewAdapter({selectedItem: Subscriber -> listItemClicked(selectedItem)})
        binding.subscriberRecyclerView.adapter = adapter
        displaySubscribersList()
    }

    private fun displaySubscribersList(){
        subscriberViewModel.getSavedSubscribers().observe(this, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }
}