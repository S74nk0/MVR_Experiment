package com.example.s74nk0.mvr_experiment.activities


import android.app.Activity
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.view.View
import android.widget.EditText
import com.example.s74nk0.mvr_experiment.R
import com.example.s74nk0.mvr_experiment.email.EmailHelper
import com.example.s74nk0.mvr_experiment.util.Util
import java.io.UnsupportedEncodingException
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.Transport
import javax.mail.internet.AddressException

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goToMeasureActivity(v: View) {
        startActivity(Intent(this, MeasureActivity::class.java))
    }

    fun insertPhoneWeight(v: View) {
        var builder = AlertDialog.Builder(this)
        builder.setTitle("teza telefona (v gramih)")
        val input = EditText(this)
        input.setInputType(InputType.TYPE_CLASS_NUMBER)
        input.setText(Util.getPhoneWeight(this))
        builder.setView(input)
        builder.setPositiveButton("Ok", DialogInterface.OnClickListener { dialogInterface, i ->
            val inputWeight = input.text.toString()
            Util.savePhoneWeight(this, inputWeight)
        })
        builder.show()
    }

    fun sendEmail(v: View) {
        try {
            val message = EmailHelper.create_FULL_LOG_Msg(this)
            SendMailTask().execute(message)
        } catch (e: AddressException) {
            e.printStackTrace()
        } catch (e: MessagingException) {
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
    }

    private inner class SendMailTask : AsyncTask<Message, Void?, Void?>() {
        private var progressDialog: ProgressDialog? = null

        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog = ProgressDialog.show(this@MainActivity, "Please wait", "Sending mail", true, false)
        }

        override fun onPostExecute(aVoid: Void?) {
            super.onPostExecute(aVoid)
            progressDialog!!.dismiss()
        }

        override fun doInBackground(vararg messages: Message): Void? {
            try {
                Transport.send(messages[0])
            } catch (e: MessagingException) {
                e.printStackTrace()
            }

            return null
        }
    }

}
