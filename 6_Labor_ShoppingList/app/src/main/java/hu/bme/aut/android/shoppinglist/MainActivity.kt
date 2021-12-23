package hu.bme.aut.android.shoppinglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.shoppinglist.adapter.ShoppingAdapter
import hu.bme.aut.android.shoppinglist.data.ShoppingItem
import hu.bme.aut.android.shoppinglist.data.ShoppingListDatabase
import hu.bme.aut.android.shoppinglist.databinding.ActivityMainBinding
import hu.bme.aut.android.shoppinglist.fragments.NewShoppingItemDialogFragment
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), ShoppingAdapter.ShoppingItemClickListener,
    NewShoppingItemDialogFragment.NewShoppingItemDialogListener {
    private lateinit var binding: ActivityMainBinding

    private lateinit var database: ShoppingListDatabase
    /** ShoppingAdapter példánya **/
    private lateinit var adapter: ShoppingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        database = ShoppingListDatabase.getDatabase(applicationContext)

        /** FloatingActionButton oncliklistenerje **/
        binding.fab.setOnClickListener{
            NewShoppingItemDialogFragment().show(
                supportFragmentManager,
                NewShoppingItemDialogFragment.TAG
            )
        }

        initRecyclerView()
    }

    /** RecyclerView-t initializáló két függvény: initRecyclerView, loadItemsInBackground **/
    private fun initRecyclerView() {
        adapter = ShoppingAdapter(this)
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapter
        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.shoppingItemDao().getAll()
            runOnUiThread {
                adapter.update(items)
            }
        }
    }

    /** ShoppingAdapterben lévő ShoppingItemClickListener interface implementációja **/
    override fun onItemChanged(item: ShoppingItem) {
        thread {
            database.shoppingItemDao().update(item)
            Log.d("MainActivity", "ShoppingItem update was successful")
        }
    }

    /** ShoppingAdapterben lévő interface Delete függvényének implementációja **/
    override fun onItemDeleted(item: ShoppingItem) {
        thread {
            database.shoppingItemDao().deleteItem(item)
            Log.d("MainActivity", "ShoppingItem update was successful")
        }
    }

    /** NewShoppingItemDialogFragment-ban definiált NewShoppingItemDialogListener interface implementációja **/
    override fun onShoppingItemCreated(newItem: ShoppingItem) {
        thread {
            database.shoppingItemDao().insert(newItem)

            runOnUiThread {
                adapter.addItem(newItem)
            }
        }
    }

}