import 'dart:async';
import 'dart:collection';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:gameball/gameball.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  final _gameballPlugin = Gameball();

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    // We also handle the message potentially returning null.
    try {
      platformVersion = await _gameballPlugin.getPlatformVersion() ??
          'Unknown platform version';
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Column(
          children: [
            Center(
              child: Text('Running on: $_platformVersion\n'),
            ),
            GestureDetector(
              onTap: () async {
                await _gameballPlugin.initMethodCall(
                    "74e7445d880447a282e3ab466aa2352b", "Ranosh@test.com");
              },
              child: const Text("init User"),
            ),
            SizedBox(height: 200,),
            GestureDetector(
              onTap: () async {
                HashMap<String, dynamic> eventsPropreties = HashMap();
                eventsPropreties.addAll({"count": '2'});
                eventsPropreties.addAll({"count3": '3'});
                await _gameballPlugin.sendGameballEvent(
                    "test", eventsPropreties);
              },
              child: const Text("sent event"),
            )
          ],
        ),
      ),
    );
  }
}
