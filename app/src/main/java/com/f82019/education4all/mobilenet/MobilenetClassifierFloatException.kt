package com.f82019.education4all.mobilenet

import android.app.Activity
import android.graphics.RectF
import android.os.SystemClock
import android.os.Trace
import android.util.Log
import java.util.*

class MobilenetClassifierFloatException private constructor(
    activity: Activity,
    imageSize: Int,
    modelPath: String,
    labelPath: String,
    numBytesPerChannel: Int = 1 // a 32bit float value requires 4 bytes
) : MobilenetClassifier(activity, imageSize, modelPath, labelPath, numBytesPerChannel) {

    private var isModelQuantized: Boolean = true

    // outputLocations: array of shape [Batchsize, NUM_DETECTIONS,4]
    // contains the location of detected boxes
    var outputLocations: Array<Array<FloatArray>>? =
        Array<Array<FloatArray>>(1, { Array<FloatArray>(NUM_DETECTIONS, { FloatArray(4) }) })

    // outputClasses: array of shape [Batchsize, NUM_DETECTIONS]
    // contains the classes of detected boxes
    var outputClasses: Array<FloatArray>? =
        Array<FloatArray>(1, { FloatArray(NUM_DETECTIONS) })

    // outputScores: array of shape [Batchsize, NUM_DETECTIONS]
    // contains the scores of detected boxes
    var outputScores: Array<FloatArray>? =
        Array<FloatArray>(1, { FloatArray(NUM_DETECTIONS) })

    // numDetections: array of shape [Batchsize]
    // contains the number of detected boxes
    var numDetections: FloatArray? = FloatArray(1)

    var outputMap: HashMap<Int, Any> = HashMap()


    override fun addPixelValue(pixelValue: Int) {
        Trace.beginSection("preprocessBitmap")
        if (isModelQuantized) {
            // Quantized model
            imgData?.put(((pixelValue shr 16) and 0xFF).toByte())
            imgData?.put(((pixelValue shr 8) and 0xFF).toByte())
            imgData?.put((pixelValue and 0xFF).toByte())
        } else { // Float model
            imgData?.putFloat((((pixelValue shr 16) and 0xFF) - IMAGE_MEAN) / IMAGE_STD)
            imgData?.putFloat((((pixelValue shr 8) and 0xFF) - IMAGE_MEAN) / IMAGE_STD)
            imgData?.putFloat(((pixelValue and 0xFF) - IMAGE_MEAN) / IMAGE_STD)
        }
        Trace.endSection() // preprocessBitmap
    }

    override fun getProbability(labelIndex: Int): Float {
        //    return heatMapArray[0][labelIndex];
        return 0f
    }

    override fun setProbability(
        labelIndex: Int,
        value: Number
    ) {
        //    heatMapArray[0][labelIndex] = value.floatValue();
    }

    override fun getNormalizedProbability(labelIndex: Int): Float {
        return getProbability(labelIndex)
    }

    override fun runInference() {
        Trace.beginSection("recognizeImage")

        val inputArray = arrayOf<Any>(imgData!!)

        outputMap.put(0, outputLocations!!)
        outputMap.put(1, outputClasses!!)
        outputMap.put(2, outputScores!!)
        outputMap.put(3, numDetections!!)


        Trace.beginSection("run")
        tflite?.runForMultipleInputsOutputs(inputArray, outputMap)
        Trace.endSection()

        recognitions.clear()
        for (i in 0 until NUM_DETECTIONS) {
            if(outputScores!![0][i] > 0.7f){
                val detection = RectF(
                    outputLocations!![0][i][1] * imageSize,
                    outputLocations!![0][i][0] * imageSize,
                    outputLocations!![0][i][3] * imageSize,
                    outputLocations!![0][i][2] * imageSize
                )
                // SSD Mobilenet V1 Model assumes class 0 is background class
                // in label file and class labels start from 1 to number_of_classes+1,
                // while outputClasses correspond to class index from 0 to number_of_classes
                val labelOffset = 1

                recognitions.add(
                    Recognition(
                        "" + i,
                        labels.get(outputClasses!![0][i].toInt() + labelOffset),
                        outputScores!![0][i],
                        detection
                    )
                )
            }
        }

        Log.d(TAG, "Inference Result : " + recognitions.toString())


        val modelEndTime = SystemClock.uptimeMillis()

        Trace.endSection() // "recognizeImage"
    }


    companion object {

        // quantization model 을 위한 const val
        const val IMAGE_MEAN: Float = 128.0f
        const val IMAGE_STD: Float = 128.0f

        val TAG = "MobileNet"

        const val NUM_DETECTIONS: Int = 10


        /**
         *
         * @param imageSize Get the image size along the x,y axis.
         * @param modelPath Get the name of the model file stored in Assets.
         * @param numBytesPerChannel Get the number of bytes that is used to store a single
         * color channel value.
         */
        fun create(
            activity: Activity,
            imageSize: Int = 300,
            modelPath: String = "detect.tflite",
            labelPath: String = "coco_labels_list.txt",
            numBytesPerChannel: Int = 1
        ): MobilenetClassifierFloatException =
            MobilenetClassifierFloatException(
                activity,
                imageSize,
                modelPath,
                labelPath,
                numBytesPerChannel
            )
    }
}