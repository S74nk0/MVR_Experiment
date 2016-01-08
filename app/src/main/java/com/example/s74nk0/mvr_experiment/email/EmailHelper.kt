package com.example.s74nk0.mvr_experiment.email

import java.util.*
import android.content.Context
import android.util.Log
import com.example.s74nk0.mvr_experiment.util.Util

import java.io.IOException
import java.io.InputStream
import java.io.UnsupportedEncodingException
import java.util.Properties

import javax.activation.DataHandler
import javax.mail.Address
import javax.mail.BodyPart
import javax.mail.Folder
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.Multipart
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Store
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart
import javax.mail.util.ByteArrayDataSource

/**
 * Created by S74nk0 on 7. 01. 2016.
 */
object EmailHelper {
    // sender
    private val username = "mvrnamemvrlastnamemvrexperimen@gmail.com"
    private val password = "ad37981e66e5eee474fc486c46bb60708817e4a3"
    // personal string
    private val personal = "mvr_experiment"

    //reciver
    private val sendToAdress = "mvrrecivemvrrecive@gmail.com"

    private fun createSendSessionObject(): Session {
        val properties = Properties()
        properties.put("mail.smtp.auth", "true")
        properties.put("mail.smtp.starttls.enable", "true")
        properties.put("mail.smtp.host", "smtp.gmail.com")
        properties.put("mail.smtp.port", "587")

        return Session.getInstance(properties, object : javax.mail.Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(username, password)
            }
        })
    }

    fun TrySend(message: Message) {
        try {
            Transport.send(message)
        } catch (e: MessagingException) {
            e.printStackTrace()
            // TODO tukaj zabeleži kaj je bilo poslano kaj ne
        }
    }

    @Throws(MessagingException::class, UnsupportedEncodingException::class)
    private fun createMessage(subject: String, messageBody: String): Message {
        val message = MimeMessage(createSendSessionObject())
        message.setFrom(InternetAddress(username, personal))
        message.addRecipient(Message.RecipientType.TO, InternetAddress(sendToAdress, sendToAdress))
        message.subject = subject
        message.setText(messageBody)
        return message
    }

    @Throws(MessagingException::class, UnsupportedEncodingException::class)
    fun createHeartbeatMsg(): Message {
        return createMessage("heartbeat", "Pulse at: %s".format(Util.formatetDateAndTimeNow))
    }

    @Throws(MessagingException::class, UnsupportedEncodingException::class)
    fun create_FULL_LOG_Msg(context: Context): Message {
        val subject = "FULL LOG - " + Util.formatetDateAndTimeNow
        // Create a multipar message
        val multipart = MimeMultipart()

        val messageBody = Util.getFulAccountAndDevice(context) + "\n\n"

        // mms log file
//        addAttachmentString(multipart, "neki string", "text/plain", "MMS_${timeRangeString}_LOG.txt")

        val message = createMessage(subject, "subject")
        // Create the message part
        val messageBodyPart = MimeBodyPart()
        // Now set the actual message
        //        messageBodyPart.setText(stringBuffer.toString())
        messageBodyPart.setText(messageBody)
        // Set text message part
        multipart.addBodyPart(messageBodyPart)
        message.setContent(multipart)
        return message
    }

    // add atachment
    fun addAttachmentInputStream(multipart: Multipart, inputStream: InputStream, type: String, filename: String) {
        try {
            val attachment = MimeBodyPart()
            val ds = ByteArrayDataSource(inputStream, type)
            attachment.dataHandler = DataHandler(ds)
            attachment.fileName = filename
            multipart.addBodyPart(attachment)
        } catch (e: MessagingException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun addAttachmentString(multipart: Multipart, stringText: String, type: String, filename: String) {
        try {
            val attachment = MimeBodyPart()
            val ds = ByteArrayDataSource(stringText, type)
            attachment.dataHandler = DataHandler(ds)
            attachment.fileName = filename
            multipart.addBodyPart(attachment)
        } catch (e: MessagingException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}