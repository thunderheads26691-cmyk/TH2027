package org.firstinspires.ftc.teamcode.Opmodes

import com.bylazar.configurables.annotations.Configurable
import com.pedropathing.follower.Follower
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotorEx
import org.firstinspires.ftc.teamcode.Subsystems.Turret
import org.firstinspires.ftc.teamcode.pedroPathing.Constants

@Configurable
@TeleOp
class Teleop : OpMode() {
    lateinit var follower: Follower
    lateinit var turret: Turret

    override fun init() {
        follower = Constants.createFollower(hardwareMap)
        turret = Turret(
            follower,
            hardwareMap.get(DcMotorEx::class.java, "turret")
        )
    }

    override fun init_loop() {
    }

    override fun start() {
        follower.startTeleopDrive()
    }

    override fun loop() {
        follower.update()
    }
}