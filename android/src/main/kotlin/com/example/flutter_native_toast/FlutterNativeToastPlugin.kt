package com.example.flutter_native_toast

import android.app.Activity
import android.widget.Toast
import androidx.annotation.NonNull

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** FlutterNativeToastPlugin */
class FlutterNativeToastPlugin : FlutterPlugin, MethodCallHandler, ActivityAware {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel
    private lateinit var activity: Activity

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_native_toast")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        if (call.method == "showToast") {
            val arguments = call.arguments

            if (arguments != null && arguments is Map<*, *>) {
                val message = arguments["message"]
                val durationCode = arguments["durationCode"]
                val duration =
                    if (durationCode != null && durationCode is Int && (durationCode == Toast.LENGTH_LONG || durationCode == Toast.LENGTH_SHORT)) durationCode else Toast.LENGTH_SHORT

                if (message != null && message is String) {
                    Toast.makeText(
                        activity.applicationContext,
                        message.trim(),
                        duration
                    )
                        .show()
                } else {
                    result.error("INVALID_MESSAGE", "The message is INVALID", null)
                }
            } else {
                result.error("INVALID_ARGUMENTS", "The arguments are INVALID", null)
            }

            result.success("SUCCESS")
        } else {
            result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        activity = binding.activity;
    }

    override fun onDetachedFromActivityForConfigChanges() {
        TODO("Not yet implemented")
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        TODO("Not yet implemented")
    }

    override fun onDetachedFromActivity() {
        TODO("Not yet implemented")
    }
}
