import 'package:flutter/material.dart';
import 'package:flutter_native_toast/flutter_native_toast.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final _messageController = TextEditingController();

  @override
  void dispose() {
    _messageController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: SingleChildScrollView(
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                TextField(
                  controller: _messageController,
                  decoration: const InputDecoration.collapsed(
                    hintText: 'Input message and click button',
                  ),
                ),
                const SizedBox(height: kToolbarHeight),
                ElevatedButton(
                  onPressed: _showToast,
                  child: const Text('Show'),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  void _showToast() {
    FlutterNativeToast.show(_messageController.text.trim());
  }
}
