import 'dart:collection';

import 'gameball_platform_interface.dart';

class Gameball {
  Future<String?> getPlatformVersion() {
    return GameballPlatform.instance.getPlatformVersion();
  }

  Future initMethodCall(String gameBallKey, String playerUniqueId, String playerName) {
    return GameballPlatform.instance
        .initMethodCall(gameBallKey, playerUniqueId,playerName);
  }

  Future sendGameballEvent(
      String eventName, HashMap<String, dynamic> enevtProperties) {
    return GameballPlatform.instance
        .sendGameballEvent(eventName, enevtProperties);
  }

  Future sendUserProprites(HashMap<String, dynamic> userProprites) {
    return GameballPlatform.instance
        .sendUserProprites( userProprites);
  }
  Future sendUserAreaRegionIds(HashMap<String, dynamic> userProprites) {
    return GameballPlatform.instance
        .sendUserAreaRegionIds( userProprites);
  }

}
