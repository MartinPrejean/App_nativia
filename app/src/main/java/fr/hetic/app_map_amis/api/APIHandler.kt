package fr.hetic.app_map_amis.api

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class APIHandler(ctx: Context) {
    private val context = ctx

    fun get(url: String, cb: (JSONObject) -> Unit) {
        val queue = Volley.newRequestQueue(this.context)

        val req = object: JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                val data = JSONObject("{error: false, data: ${response}}")
                cb(data)
            }, Response.ErrorListener { error ->
                val data = JSONObject("{error: true, msg: ${error}}")
                cb(data)
            }
        )
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "0dbdc129-2b4f-4827-b826-9379293b5869" // Replace with your own auth token
                return headers
            }
        }

        queue.add(req)
    }
}