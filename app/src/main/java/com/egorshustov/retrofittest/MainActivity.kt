package com.egorshustov.retrofittest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var editId: EditText? = null
    private var editTitle: EditText? = null
    private var editDescription: EditText? = null
    private var editCompleted: EditText? = null
    private val retriesMax: Int = 3
    private val apiService = ApiInterface.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editId = findViewById(R.id.editId)
        editTitle = findViewById(R.id.editTitle)
        editDescription = findViewById(R.id.editDescription)
        editCompleted = findViewById(R.id.editCompleted)

        btnGet.setOnClickListener {
            templateRequest(apiService.apiGetRequest())
        }

        btnGetWithAlias.setOnClickListener {
            templateRequest(apiService.apiGetRequestWithAlias(editId!!.text.toString()))
        }

        btnPost.setOnClickListener {
            templateRequest(
                apiService.apiPostRequest(
                    Item(
                        editId!!.text.toString(), editTitle!!.text.toString(),
                        editDescription!!.text.toString(), editCompleted!!.text.toString()
                    )
                )
            )
        }

        btnPut.setOnClickListener {
            templateRequest(
                apiService.apiPutRequestWithAlias(
                    editId!!.text.toString(), Item(
                        editId!!.text.toString(),
                        editTitle!!.text.toString(), editDescription!!.text.toString(), editCompleted!!.text.toString()
                    )
                )
            )
        }

        btnDelete.setOnClickListener {
            templateRequest(apiService.apiDeleteRequestWithAlias(editId!!.text.toString()))
        }
    }

    private fun <T> templateRequest(call: Call<T>) {
        var retryCount = 0
        textResponse.text = ""
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>?) {
                if (response == null) {
                    return
                }

                val code = response.code()
                when (code) {
                    200 -> {
                        textResponse.append(responseItemToString(response.body()))
                        (response.body() as? List<*>)?.let {
                            var lines = ""
                            for (item in it.iterator()) {
                                lines += responseItemToString(item)
                            }
                            textResponse.append(lines)
                        }
                    }

                    500 -> if (retryCount++ < retriesMax) {
                        textResponse.append("Error code: $code. Retry count: $retryCount\n")
                        retry()
                    }

                    else -> textResponse.append("Error code: $code")
                }
            }

            private fun responseItemToString(responseItem: Any?) : String {
                (responseItem as? Item)?.let {
                    return it.id + " " + it.title + "\n"
                }
                return ""
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                textResponse.append(t.toString())
            }

            fun retry() {
                call.clone().enqueue(this)
            }
        })
    }
}
