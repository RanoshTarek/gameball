package com.fatura.gameball.gameball

import android.content.Context
import android.widget.Toast
import androidx.annotation.NonNull
import com.gameball.gameball.GameBallApp
import com.gameball.gameball.model.request.Action
import com.gameball.gameball.model.response.PlayerAttributes
import com.gameball.gameball.network.Callback
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** GameballPlugin */

// initUserProperties


class GameballPlugin : FlutterPlugin, MethodCallHandler {
    private lateinit var channel: MethodChannel
    private var context: Context? = null
    var gameballInstance: GameBallApp? = null;
    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "gameball")
        channel.setMethodCallHandler(this)
        context = flutterPluginBinding.applicationContext;
        gameballInstance = GameBallApp.getInstance(context)

    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        when (call.method) {
            INIT_METHOD_CALL -> {
                gameballInstance?.init(
                    call.argument(GAMEBALL_KEY),
                    call.argument(PLAYER_UNIQUE_ID),
                    R.drawable.ic_notification
                )
                PlayerAttributes.Builder()
                    .withDisplayName(call.argument(PLAYER_NAME)).build();
            }
            SEND_GAMEBALL_EVENT -> {
                val action = Action();
                val metaData =  call.argument(EVENT_PROPERTIES) as HashMap<String, Any>?
                action.addEvent(call.argument(EVENT_NAME), metaData)
                gameballInstance?.addAction(action, object : Callback<Any> {
                    override fun onSuccess(t: Any?) {
                        print("success gameBall")
                    }

                    override fun onError(e: Throwable?) {
                        print("error gameBall ")
                    }

                })
            }

            "getPlatformVersion" -> {
                result.success("Android ${android.os.Build.VERSION.RELEASE}")
            }
            else -> {
                result.notImplemented()
            }
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    companion object {
        var INIT_METHOD_CALL = "initMethodCall"
        var GAMEBALL_KEY = "gameBallKey"
        var PLAYER_UNIQUE_ID = "playerUniqueId"
        var PLAYER_NAME = "playerName"
        var SEND_GAMEBALL_EVENT = "sendGameballEvent"
        var EVENT_NAME = "eventName"
        var EVENT_PROPERTIES = "eventProperties"
    }

}
