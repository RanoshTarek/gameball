package com.fatura.gameball.gameball

import android.content.Context
import androidx.annotation.NonNull
import com.gameball.gameball.GameBallApp
import com.gameball.gameball.local.SharedPreferencesUtils
import com.gameball.gameball.model.request.Action
import com.gameball.gameball.model.response.PlayerAttributes
import com.gameball.gameball.model.response.PlayerRegisterResponse
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
            }
            SEND_GAMEBALL_EVENT -> {
                val action = Action();
                val metaData = call.argument(EVENT_PROPERTIES) as HashMap<String, Any>?
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

            SEND_USER_DATA -> {
                val metaData = call.argument(USER_PROPERTIES) as HashMap<String, String>?
                SharedPreferencesUtils.getInstance().putPlayerUniqueId(
                    metaData!![PLAYER_UNIQUE_ID]?.toString()
                        ?: ""
                )
                if (metaData?.get(PLAYER_NAME) != null) {
                    sendUserData(
                        PlayerAttributes.Builder().withDisplayName(
                            metaData.get(
                                PLAYER_NAME
                            )
                        ).build()
                    )
                }
                if (metaData?.get(PLAYER_JOIN_DATE) != null) {
                    sendUserData(
                        PlayerAttributes.Builder().withJoinDate(
                            metaData.get(
                                PLAYER_JOIN_DATE
                            )
                        ).build()
                    )
                }
                if (metaData?.get(PLAYER_MOBILE) != null) {
                    sendUserData(
                        PlayerAttributes.Builder().withMobileNumber(
                            metaData.get(
                                PLAYER_MOBILE
                            )
                        ).build()
                    )
                }
            }
            SEND_USER_AREA_REGION_ID -> {
                val metaData = call.argument(USER_PROPERTIES) as HashMap<String, String>?
                SharedPreferencesUtils.getInstance().putPlayerUniqueId(
                    metaData!![PLAYER_UNIQUE_ID]?.toString()
                        ?: ""
                )
                if (metaData?.get(PLAYER_REGION) != null) {
                    val playerAttributes = PlayerAttributes.Builder().build()
                    playerAttributes.addCustomAttribute(
                        PLAYER_REGION,
                        metaData.get(
                            PLAYER_REGION
                        ).toString()
                    )
                    sendUserData(
                        playerAttributes
                    )
                }
                if (metaData?.get(PLAYER_AREA) != null) {
                    val playerAttributes = PlayerAttributes.Builder().build()
                    playerAttributes.addCustomAttribute(
                        PLAYER_AREA,
                        metaData.get(
                            PLAYER_AREA
                        ).toString()
                    )
                    sendUserData(
                        playerAttributes
                    )
                }

            }

            "getPlatformVersion" -> {
                result.success("Android ${android.os.Build.VERSION.RELEASE}")
            }
            else -> {
                result.notImplemented()
            }
        }
    }

    fun sendUserData(playerAttributes: PlayerAttributes) {
        gameballInstance?.editPlayerAttributes(
            playerAttributes,
            object : Callback<PlayerRegisterResponse?> {
                override fun onSuccess(playerRegisterResponse: PlayerRegisterResponse?) {
                }

                override fun onError(e: Throwable) {
                }
            })
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    companion object {
        var INIT_METHOD_CALL = "initMethodCall"
        var GAMEBALL_KEY = "gameBallKey"

        var PLAYER_UNIQUE_ID = "playerUniqueId"
        var PLAYER_NAME = "playerName"
        var PLAYER_MOBILE = "playerMobile"
        var PLAYER_AREA = "area"
        var PLAYER_REGION = "region"
        var PLAYER_JOIN_DATE = "playerJoinDate"

        var SEND_GAMEBALL_EVENT = "sendGameballEvent"
        var SEND_USER_DATA = "sendUserData"
        var SEND_USER_AREA_REGION_ID = "sendUserAreaRegionIds"
        var EVENT_NAME = "eventName"
        var EVENT_PROPERTIES = "eventProperties"
        var USER_PROPERTIES = "userProperties"
    }

}
