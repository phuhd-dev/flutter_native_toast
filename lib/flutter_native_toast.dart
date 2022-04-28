// ignore_for_file: constant_identifier_names

import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

class FlutterNativeToast {
  static const MethodChannel _channel = MethodChannel('flutter_native_toast');

  static Future<void> show(
    String message, {
    ToastDuration duration = ToastDuration.LENGTH_SHORT,
  }) async {
    try {
      await _channel.invokeMethod(
        'showToast',
        _FlutterNativeToastArguments(
          message: message,
          duration: duration,
        ).toJson(),
      );
    } catch (e) {
      debugPrint(e.toString());
    }
  }
}

class _FlutterNativeToastArguments {
  final String message;
  final ToastDuration duration;

  _FlutterNativeToastArguments({
    required this.message,
    this.duration = ToastDuration.LENGTH_SHORT,
  });

  Map<String, dynamic> toJson() {
    return {
      'message': message,
      'durationCode': duration.nativeValue,
    };
  }
}

enum ToastDuration { LENGTH_SHORT, LENGTH_LONG }

extension ToastDurationExt on ToastDuration {
  int get nativeValue {
    switch (this) {
      case ToastDuration.LENGTH_SHORT:
        return 0;

      case ToastDuration.LENGTH_LONG:
        return 1;

      default:
        return 0;
    }
  }
}
