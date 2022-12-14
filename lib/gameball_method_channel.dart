import 'dart:collection';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:gameball/Constants.dart';

import 'gameball_platform_interface.dart';

/// An implementation of [GameballPlatform] that uses method channels.
class MethodChannelGameball extends GameballPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('gameball');

  @override
  Future<String?> getPlatformVersion() async {
    final version =
        await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  @override
  Future initMethodCall(String gameBallKey, String playerUniqueId,String playerName) async {
    await methodChannel.invokeMethod<String>(Constants.INIT_METHOD_CALL, {
      Constants.GAMEBALL_KEY: gameBallKey,
      Constants.PLAYER_UNIQUE_ID: playerUniqueId,
      Constants.PLAYER_NAME: playerName
    });
  }

  @override
  Future sendGameballEvent(
      String eventName, HashMap<String, dynamic> eventProperties) async {
    await methodChannel.invokeMethod<String>(Constants.SEND_GAMEBALL_EVENT, {
      Constants.EVENT_NAME: eventName,
      Constants.EVENT_PROPERTIES: eventProperties
    });
  }

  @override
  Future sendUserProprites(HashMap<String, dynamic> userProprites) async {
    await methodChannel.invokeMethod<String>(
        Constants.SEND_USER_DATA, {Constants.USER_PROPERTIES: userProprites});
  }

  @override
  Future sendUserAreaRegionIds(HashMap<String, dynamic> userProprites) async {
    await methodChannel.invokeMethod<String>(
        Constants.SEND_USER_AREA_REGION_IDS, {Constants.USER_PROPERTIES: userProprites});
  }
}
