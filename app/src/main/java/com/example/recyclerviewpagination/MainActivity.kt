package com.example.recyclerviewpagination

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var arrayList = ArrayList<ModelClass>()
    private var data = arrayOf("a", "b", "c", "d", "e", "f", "g", "h", "i")
    private var data2 = arrayOf("a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2", "i2")
    private var loading = true
    private var pastVisiblesItems = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0
    private var mLayoutManager: LinearLayoutManager? = null
    private var myAdapter: MyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myAdapter = MyAdapter(this, getData())
        mLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
        recyclerView.adapter = myAdapter
        setupPagination()
    }

    private fun setupPagination() {
        recyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount = mLayoutManager!!.childCount
                    totalItemCount = mLayoutManager!!.itemCount
                    pastVisiblesItems = mLayoutManager!!.findFirstVisibleItemPosition()
                    if (loading) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            loading = false
                            Toast.makeText(
                                this@MainActivity,
                                "This is the last item!",
                                Toast.LENGTH_SHORT
                            ).show()
                            getData2()
                            myAdapter!!.notifyDataSetChanged()
                            loading = true
                        }
                    }
                }
            }
        })
    }

    private fun getData(): ArrayList<ModelClass> {
        for (i in data.indices) {
            arrayList.add(ModelClass(data[i]))
        }
        return arrayList
    }

    private fun getData2(): ArrayList<ModelClass> {
        for (i in data2.indices) {
            arrayList.add(ModelClass(data2[i]))
        }
        return arrayList
    }
}