package com.example.tugasbookmark

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ArrayAdapter
import android.widget.GridLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.Grid
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.localdatabase.database.Post
import com.example.tugasbookmark.Dao.PostDao
import com.example.tugasbookmark.adapter.PostAdapter
import com.example.tugasbookmark.database.PostDatabase
import com.example.tugasbookmark.databinding.ActivityMainBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var postDao:PostDao;
    lateinit var executorService:ExecutorService;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = PostDatabase.getDatabase(this)
        postDao = database!!.postDao()!!
        executorService = Executors.newSingleThreadExecutor()

        with(binding){
            rvPosts.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener{
                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                    if (e.action == MotionEvent.ACTION_UP) {
                        val childView = rv.findChildViewUnder(e.x, e.y)
                        childView?.let {
                            val position = rv.getChildAdapterPosition(it)

                            val adapter = rv.adapter as PostAdapter

                            val item = adapter.getItem(position)

                        }
                    }
                    return false
                }

                override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                }

                override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
                }
            })


            btnBookmark.setOnClickListener(){
                val intentToBookmark = Intent(this@MainActivity, BookmarkActivity::class.java)
                startActivity(intentToBookmark)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun  getAllPost(){
        postDao.allPost.observe(this){
            posts->
                val postAdapter = PostAdapter(posts);
            with(binding){
                rvPosts.apply {
                    adapter = postAdapter
                    layoutManager =GridLayoutManager(this@MainActivity, 1)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getAllPost()
    }

    fun insertPost(post: Post){
        executorService.execute {
            postDao.insert(post)
        }

    }

    fun hapusDataTable(){
        executorService.execute {
            postDao.nukeTable()
        }
    }


    fun bookmark(id:Int, bookmark:Boolean){
        if (!bookmark){
            executorService.execute {
                postDao.bookmarking(id)
            }
        } else{
            executorService.execute {
                postDao.unbookmarking(id)
            }
        }
    }



}
