package `in`.encrust.relaxo

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {

    private lateinit var mService: AudioService
    private var mBound: Boolean = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as AudioService.LocalBinder
            mService = binder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            mBound = false
        }
    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        val messenger = flutterEngine.dartExecutor.binaryMessenger

        // Rain
        MethodChannel(messenger, "rainPlayPause").setMethodCallHandler { call, result ->
            if (!mBound) {
                result.success("paused")
                return@setMethodCallHandler
            }
            if (call.method == "rainInit") {
                result.success(if (mService.isRainPlaying()) "playing" else "paused")
            } else if (call.method == "rainPlayPause") {
                mService.rainPlayPause()
                result.success(if (mService.isRainPlaying()) "playing" else "paused")
            }
        }
        MethodChannel(messenger, "rainVolumeAction").setMethodCallHandler { call, result ->
            if (!mBound) {
                result.success("1.0")
                return@setMethodCallHandler
            }
            if (call.method == "rainInit") {
                result.success(mService.getRainVolume())
            } else {
                mService.setRainVolume(call.method)
                result.success(call.method)
            }
        }

        // Bird
        MethodChannel(messenger, "birdPlayPause").setMethodCallHandler { call, result ->
            if (!mBound) {
                result.success("paused")
                return@setMethodCallHandler
            }
            if (call.method == "birdInit") {
                result.success(if (mService.isBirdPlaying()) "playing" else "paused")
            } else if (call.method == "birdPlayPause") {
                mService.birdPlayPause()
                result.success(if (mService.isBirdPlaying()) "playing" else "paused")
            }
        }
        MethodChannel(messenger, "birdVolumeAction").setMethodCallHandler { call, result ->
            if (!mBound) {
                result.success("1.0")
                return@setMethodCallHandler
            }
            if (call.method == "birdInit") {
                result.success(mService.getBirdVolume())
            } else {
                mService.setBirdVolume(call.method)
                result.success(call.method)
            }
        }

        // Tent
        MethodChannel(messenger, "tentPlayPause").setMethodCallHandler { call, result ->
            if (!mBound) {
                result.success("paused")
                return@setMethodCallHandler
            }
            if (call.method == "tentInit") {
                result.success(if (mService.isTentPlaying()) "playing" else "paused")
            } else if (call.method == "tentPlayPause") {
                mService.tentPlayPause()
                result.success(if (mService.isTentPlaying()) "playing" else "paused")
            }
        }
        MethodChannel(messenger, "tentVolumeAction").setMethodCallHandler { call, result ->
            if (!mBound) {
                result.success("1.0")
                return@setMethodCallHandler
            }
            if (call.method == "tentInit") {
                result.success(mService.getTentVolume())
            } else {
                mService.setTentVolume(call.method)
                result.success(call.method)
            }
        }

        // Thunder
        MethodChannel(messenger, "thunderPlayPause").setMethodCallHandler { call, result ->
            if (!mBound) {
                result.success("paused")
                return@setMethodCallHandler
            }
            if (call.method == "thunderInit") {
                result.success(if (mService.isThunderPlaying()) "playing" else "paused")
            } else if (call.method == "thunderPlayPause") {
                mService.thunderPlayPause()
                result.success(if (mService.isThunderPlaying()) "playing" else "paused")
            }
        }
        MethodChannel(messenger, "thunderVolumeAction").setMethodCallHandler { call, result ->
            if (!mBound) {
                result.success("1.0")
                return@setMethodCallHandler
            }
            if (call.method == "thunderInit") {
                result.success(mService.getThunderVolume())
            } else {
                mService.setThunderVolume(call.method)
                result.success(call.method)
            }
        }

        // Grass
        MethodChannel(messenger, "grassPlayPause").setMethodCallHandler { call, result ->
            if (!mBound) {
                result.success("paused")
                return@setMethodCallHandler
            }
            if (call.method == "grassInit") {
                result.success(if (mService.isGrassPlaying()) "playing" else "paused")
            } else if (call.method == "grassPlayPause") {
                mService.grassPlayPause()
                result.success(if (mService.isGrassPlaying()) "playing" else "paused")
            }
        }
        MethodChannel(messenger, "grassVolumeAction").setMethodCallHandler { call, result ->
            if (!mBound) {
                result.success("1.0")
                return@setMethodCallHandler
            }
            if (call.method == "grassInit") {
                result.success(mService.getGrassVolume())
            } else {
                mService.setGrassVolume(call.method)
                result.success(call.method)
            }
        }

        // Timer
        MethodChannel(messenger, "setTimer").setMethodCallHandler { call, result ->
            if (!mBound) {
                result.success("0")
                return@setMethodCallHandler
            }
            when (call.method) {
                "0" -> result.success(mService.getTimer())
                "1" -> {
                    mService.cancelTimer()
                    result.success("cancelled")
                }
                else -> {
                    mService.setTimer(call.method)
                    result.success("started")
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        startService(Intent(this, AudioService::class.java))
        bindService(Intent(this, AudioService::class.java), connection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (mBound) {
            if (!mService.isRainPlaying() && !mService.isBirdPlaying() && !mService.isTentPlaying() && !mService.isThunderPlaying() && !mService.isGrassPlaying()) {
                mService.deleteNotification()
                stopService(Intent(this, AudioService::class.java))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mBound) {
            unbindService(connection)
            mBound = false
        }
    }
}
