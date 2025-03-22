package br.com.lfpdev.lanterna

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.lfpdev.lanterna.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var cameraManager : CameraManager
    private var flashlightState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager


        setListeners()
    }

    private fun setListeners() {
        binding.cvFlashlight.setOnClickListener {
            manageFlashlight()
        }
    }

    private fun manageFlashlight() {
        val cameraId = cameraManager.cameraIdList.firstOrNull { id ->
            cameraManager.getCameraCharacteristics(id).keys.contains(CameraCharacteristics.FLASH_INFO_AVAILABLE)
        } ?: return
        flashlightState = !flashlightState
        cameraManager.setTorchMode(cameraId, flashlightState)
        if (flashlightState) {
            binding.ivFlashlight.setImageResource(R.drawable.ic_flashlight_off)
        } else {
            binding.ivFlashlight.setImageResource(R.drawable.ic_flashlight_on)
        }
    }
}