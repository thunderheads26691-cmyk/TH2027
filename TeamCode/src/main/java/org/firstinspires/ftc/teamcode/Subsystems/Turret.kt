package org.firstinspires.ftc.teamcode.Subsystems

import com.pedropathing.follower.Follower
import com.pedropathing.math.Vector
import com.qualcomm.robotcore.hardware.DcMotor.RunMode
import com.qualcomm.robotcore.hardware.DcMotorEx
import org.firstinspires.ftc.robotcore.external.Telemetry

class Turret(
    val follower: Follower,
    val turretMotor: DcMotorEx
) : Subsystem() {
    companion object {
        private const val MIN_ANGLE: Double = -90.0
        private const val MAX_ANGLE: Double = 90.0
        private const val ENCODER_ANGLE_RATIO: Double = 8192 / 360.0
        private val TARGET: Vector = Vector(0.0, 0.0)
    }

    init {
        turretMotor.mode = RunMode.RUN_USING_ENCODER
    }

    fun tick() {
        var angle = (TARGET - follower.pose.asVector).theta
        if (angle < MIN_ANGLE) {
            angle = MIN_ANGLE
        } else if (angle > MAX_ANGLE) {
            angle = MAX_ANGLE
        }
        turretMotor.targetPosition = (angle * ENCODER_ANGLE_RATIO).toInt()
    }

    override fun printState(telemetry: Telemetry) {
        TODO("Not yet implemented")
    }
}