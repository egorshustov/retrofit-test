package com.egorshustov.retrofittest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnGet.setOnClickListener {
            getRequest()
        }
        btnGetWithAlias.setOnClickListener {
            getRequestWithAlias()
        }
        btnPost.setOnClickListener {
            postRequest()
        }
        btnPut.setOnClickListener {
            putRequest()
        }
        btnDelete.setOnClickListener {
            deleteRequest()
        }
    }

    private fun getRequest() {
        val apiService = ApiInterface.create()
        val call = apiService.apiGetRequest()

        call.enqueue(object : Callback<List<Item>> {
            override fun onResponse(call: Call<List<Item>>, response: retrofit2.Response<List<Item>>?) {
                if (response != null) {
                    val list: List<Item> = response.body()!!
                    var lines = ""
                    for (item: Item in list.iterator()) {
                        lines = lines + item.id + " " + item.title + "\n"
                    }
                    textResponse.text = lines
                }
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
            }
        })
    }

    private fun getRequestWithAlias() {
        val apiService = ApiInterface.create()
        val call = apiService.apiGetRequestWithAlias("125")

        call.enqueue(object : Callback<Item> {
            override fun onResponse(call: Call<Item>, response: retrofit2.Response<Item>?) {
                if (response != null) {
                    val item: Item = response.body()!!
                    textResponse.text = (item.id + " " + item.title)
                }
            }

            override fun onFailure(call: Call<Item>, t: Throwable) {
            }
        })
    }

    private fun postRequest() {
        val apiService = ApiInterface.create()
        val call = apiService.apiPostRequest(Item("7878765", "ne7656w", "not7567e", "0"))

        call.enqueue(object : Callback<Item> {
            override fun onResponse(call: Call<Item>, response: retrofit2.Response<Item>?) {
                if (response != null) {
                    val item: Item = response.body()!!
                    textResponse.text = (item.id + " " + item.title)
                }
            }

            override fun onFailure(call: Call<Item>, t: Throwable) {
            }
        })
    }

    private fun putRequest() {
        val apiService = ApiInterface.create()
        val call = apiService.apiPutRequestWithAlias("136", Item("13576", "145736", "13446", "1"))

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: retrofit2.Response<String>?) {
                // Ответ не приходит, запись изменяется
                if (response != null) {
                    textResponse.text = response.toString()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
            }
        })
    }

    private fun deleteRequest() {
        val apiService = ApiInterface.create()
        val call = apiService.apiDeleteRequestWithAlias("5765")

        call.enqueue(object : Callback<Item> {
            override fun onResponse(call: Call<Item>, response: retrofit2.Response<Item>?) {
                if (response != null) {
                    val item: Item = response.body()!!
                    textResponse.text = (item.id + " " + item.title)
                }
            }

            override fun onFailure(call: Call<Item>, t: Throwable) {
            }
        })
    }
}
