import 'package:flutter_test/flutter_test.dart';
import 'package:gameball/gameball.dart';
import 'package:gameball/gameball_platform_interface.dart';
import 'package:gameball/gameball_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockGameballPlatform 
    with MockPlatformInterfaceMixin
    implements GameballPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final GameballPlatform initialPlatform = GameballPlatform.instance;

  test('$MethodChannelGameball is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelGameball>());
  });

  test('getPlatformVersion', () async {
    Gameball gameballPlugin = Gameball();
    MockGameballPlatform fakePlatform = MockGameballPlatform();
    GameballPlatform.instance = fakePlatform;
  
    expect(await gameballPlugin.getPlatformVersion(), '42');
  });
}
