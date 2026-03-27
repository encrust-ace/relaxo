package `in`.encrust.relaxo

import android.app.*
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import kotlin.system.exitProcess

class AudioService : Service(), AudioManager.OnAudioFocusChangeListener {
    private val binder = LocalBinder()
    
    private var rainPlayer: MediaPlayer? = null
    private var rainVolume: String = "1.0"
    
    private var birdPlayer: MediaPlayer? = null
    private var birdVolume: String = "1.0"
    
    private var tentPlayer: MediaPlayer? = null
    private var tentVolume: String = "1.0"
    
    private var thunderPlayer: MediaPlayer? = null
    private var thunderVolume: String = "1.0"
    
    private var grassPlayer: MediaPlayer? = null
    private var grassVolume: String = "1.0"
    
    private var audioManager: AudioManager? = null
    private var wasRainPlaying = false
    private var wasBirdPlaying = false
    private var wasTentPlaying = false
    private var wasThunderPlaying = false
    private var wasGrassPlaying = false

    private var timer = 0
    private val handler = Handler(Looper.getMainLooper())

    private var notificationManager: NotificationManager? = null

    inner class LocalBinder : Binder() {
        fun getService(): AudioService = this@AudioService
    }

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        initMediaPlayers()
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager?.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
    }

    override fun onAudioFocusChange(focusChange: Int) {
        if (focusChange <= 0) {
            wasRainPlaying = isRainPlaying()
            wasBirdPlaying = isBirdPlaying()
            wasTentPlaying = isTentPlaying()
            wasThunderPlaying = isThunderPlaying()
            wasGrassPlaying = isGrassPlaying()
            
            rainPlayer?.pause()
            birdPlayer?.pause()
            tentPlayer?.pause()
            thunderPlayer?.pause()
            grassPlayer?.pause()
        } else {
            if (wasRainPlaying) { rainPlayer?.start(); initNotification() }
            if (wasBirdPlaying) { birdPlayer?.start(); initNotification() }
            if (wasTentPlaying) { tentPlayer?.start(); initNotification() }
            if (wasThunderPlaying) { thunderPlayer?.start(); initNotification() }
            if (wasGrassPlaying) { grassPlayer?.start(); initNotification() }
        }
    }

    private fun initMediaPlayers() {
        rainPlayer = try { MediaPlayer.create(this, R.raw.rain_main) } catch (e: Exception) { null }
        rainPlayer?.isLooping = true
        
        birdPlayer = try { MediaPlayer.create(this, R.raw.birds_main) } catch (e: Exception) { null }
        birdPlayer?.isLooping = true
        
        tentPlayer = try { MediaPlayer.create(this, R.raw.rain_on_tent) } catch (e: Exception) { null }
        tentPlayer?.isLooping = true
        
        thunderPlayer = try { MediaPlayer.create(this, R.raw.thunder) } catch (e: Exception) { null }
        thunderPlayer?.isLooping = true
        
        grassPlayer = try { MediaPlayer.create(this, R.raw.rain_on_grass) } catch (e: Exception) { null }
        grassPlayer?.isLooping = true
        
        createChannel()
    }

    fun rainPlayPause() {
        val player = rainPlayer
        if (player != null) {
            if (player.isPlaying) {
                player.pause()
            } else {
                player.start()
                initNotification()
            }
        }
    }

    fun birdPlayPause() {
        val player = birdPlayer
        if (player != null) {
            if (player.isPlaying) {
                player.pause()
            } else {
                player.start()
                initNotification()
            }
        }
    }

    fun tentPlayPause() {
        val player = tentPlayer
        if (player != null) {
            if (player.isPlaying) {
                player.pause()
            } else {
                player.start()
                initNotification()
            }
        }
    }

    fun thunderPlayPause() {
        val player = thunderPlayer
        if (player != null) {
            if (player.isPlaying) {
                player.pause()
            } else {
                player.start()
                initNotification()
            }
        }
    }

    fun grassPlayPause() {
        val player = grassPlayer
        if (player != null) {
            if (player.isPlaying) {
                player.pause()
            } else {
                player.start()
                initNotification()
            }
        }
    }

    fun isRainPlaying(): Boolean = rainPlayer?.isPlaying ?: false
    fun isBirdPlaying(): Boolean = birdPlayer?.isPlaying ?: false
    fun isTentPlaying(): Boolean = tentPlayer?.isPlaying ?: false
    fun isThunderPlaying(): Boolean = thunderPlayer?.isPlaying ?: false
    fun isGrassPlaying(): Boolean = grassPlayer?.isPlaying ?: false

    fun setRainVolume(value: String) { 
        val v = value.toFloatOrNull() ?: 1.0f
        rainPlayer?.setVolume(v, v)
        rainVolume = value 
    }
    fun setBirdVolume(value: String) { 
        val v = value.toFloatOrNull() ?: 1.0f
        birdPlayer?.setVolume(v, v)
        birdVolume = value 
    }
    fun setTentVolume(value: String) { 
        val v = value.toFloatOrNull() ?: 1.0f
        tentPlayer?.setVolume(v, v)
        tentVolume = value 
    }
    fun setThunderVolume(value: String) { 
        val v = value.toFloatOrNull() ?: 1.0f
        thunderPlayer?.setVolume(v, v)
        thunderVolume = value 
    }
    fun setGrassVolume(value: String) { 
        val v = value.toFloatOrNull() ?: 1.0f
        grassPlayer?.setVolume(v, v)
        grassVolume = value 
    }

    fun getRainVolume(): String = rainVolume
    fun getBirdVolume(): String = birdVolume
    fun getTentVolume(): String = tentVolume
    fun getThunderVolume(): String = thunderVolume
    fun getGrassVolume(): String = grassVolume

    private fun createChannel() {
        if (notificationManager == null) {
            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
        val channel = NotificationChannel("nature", "Nature", NotificationManager.IMPORTANCE_LOW)
        notificationManager?.createNotificationChannel(channel)
    }

    private fun initNotification() {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, 
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notification = Notification.Builder(this, "nature")
            .setShowWhen(false)
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setContentTitle("Relaxo Playing")
            .setAutoCancel(false)
            .setOngoing(true)
            .setContentIntent(pendingIntent)
            .build()
        notificationManager?.notify(1, notification)
    }

    fun deleteNotification() {
        if (!isRainPlaying() && !isBirdPlaying() && !isThunderPlaying() && !isTentPlaying() && !isGrassPlaying()) {
            notificationManager?.cancel(1)
        }
    }

    fun setTimer(t: String) {
        timer = t.toIntOrNull() ?: 0
        handler.removeCallbacksAndMessages(null)
        handler.post(object : Runnable {
            override fun run() {
                if (timer > 0) {
                    timer--
                    handler.postDelayed(this, 1000)
                } else {
                    stopAll()
                    exitProcess(0)
                }
            }
        })
    }
    
    private fun stopAll() {
        rainPlayer?.pause()
        birdPlayer?.pause()
        tentPlayer?.pause()
        thunderPlayer?.pause()
        grassPlayer?.pause()
        notificationManager?.cancelAll()
    }

    fun getTimer(): String = timer.toString()

    fun cancelTimer() {
        handler.removeCallbacksAndMessages(null)
    }

    override fun onDestroy() {
        stopAll()
        rainPlayer?.release()
        birdPlayer?.release()
        tentPlayer?.release()
        thunderPlayer?.release()
        grassPlayer?.release()
        super.onDestroy()
    }
}
