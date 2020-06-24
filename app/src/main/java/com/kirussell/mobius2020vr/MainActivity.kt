package com.kirussell.mobius2020vr

import android.content.*
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.button
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout


class MainActivity : AppCompatActivity() {
    val mobius2020BroadcastReceiver = Mobius2020BroadcastReceiver() {
        log("PONG")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(
//            verticalLayout {
//                background
//                val textView = textView("Hello")
//                val name = editText("Hello!")
//                button("Mobius 2020") { onClick { textView.text = name.text } }
//            }
//        )
//        Moved to VRUIActivity
        registerPingPong()

        val intent = Intent(Intent.ACTION_VIEW);
        intent.setComponent(ComponentName("com.oculus.vrshell", "com.oculus.vrshell.MainActivity"))
        intent.setData(Uri.parse("com.oculus.vrshell.desktop"))
        intent.putExtra("uri", "vrdesktop://com.kirussell.mobius2020vr/.VRUIActivity")
        startActivity(intent)
    }

    private fun registerPingPong() {
        log("Registered: com.kirussell.PING receiver")
        registerReceiver(mobius2020BroadcastReceiver, IntentFilter("com.kirussell.PING"))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mobius2020BroadcastReceiver)
    }
}

private fun log(text: String) = Log.i("kirussell", text)

class Mobius2020BroadcastReceiver(private val onReceive: () -> Unit) : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {
        onReceive()
    }
}

class VRUIActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            verticalLayout {
                val textView = textView("Hello, stranger!")
                button("Mobius 2020") { onClick { textView.text = "Hello, Mobius 2020!" } }
            }
        )
    }
}
