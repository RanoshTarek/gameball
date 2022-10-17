import 'dart:collection';

import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'gameball_method_channel.dart';

abstract class GameballPlatform extends PlatformInterface {
  /// Constructs a GameballPlatform.
  GameballPlatform() : super(token: _token);

  static final Object _token = Object();

  static GameballPlatform _instance = MethodChannelGameball();

  /// The default instance of [GameballPlatform] to use.
  ///
  /// Defaults to [MethodChannelGameball].
  static GameballPlatform get instance => _instance;
  
  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [GameballPlatform] when
  /// they register themselves.
  static set instance(GameballPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future initMethodCall(String gameBallKey,String playerUniqueId,String playerName) {
   return _instance.initMethodCall(gameBallKey,playerUniqueId,playerName);
  }

  Future sendGameballEvent(String eventName,HashMap <String,dynamic> enevtProperties) {
    return _instance.sendGameballEvent(eventName,enevtProperties);
  }
}
