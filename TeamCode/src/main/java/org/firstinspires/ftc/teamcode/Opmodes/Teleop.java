package org.firstinspires.ftc.teamcode.Opmodes;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.robot.Robot;

import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Limelight;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake;
import org.firstinspires.ftc.teamcode.Subsystems.Transfer;
import org.firstinspires.ftc.teamcode.Subsystems.Turret;
import org.firstinspires.ftc.teamcode.Subsystems.constants.Alliance;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.constants.RobotConstants;
import com.qualcomm.robotcore.util.ElapsedTime;


@Configurable
@TeleOp
public class Teleop extends OpMode {
    private Follower follower;

    public static Pose startingPose = new Pose(7.5, 1.5, Math.toRadians(90));



    @Override
    public void init() {
        intake = new Intake(hardwareMap);
        limelight = new Limelight(hardwareMap);
        transfer = new Transfer(hardwareMap);
        setterTimer = new ElapsedTime();
        intakeTimer= new ElapsedTime();
        follower = Constants.createFollower(hardwareMap);
        outtake = new Outtake(hardwareMap, limelight);
        turret = new Turret(hardwareMap, limelight, follower);
        follower.setStartingPose(startingPose);
        follower.update();

        telemetry.addData("Hello", "Message");
        telemetry.update();
        alliance = Alliance.RED;
    }

    @Override
    public void init_loop() {
        if (gamepad1.aWasPressed()) {
            alliance = Alliance.RED;
        } else if (gamepad1.bWasPressed()) {
            alliance = Alliance.BLUE;
        }

        if (alliance != null) {
            RobotConstants.setAlliance(alliance);
            telemetry.addData("Alliance", alliance.toString());
            telemetry.update();
        }
        telemetry.addData("Follower", follower);
    }

    @Override
    public void start() {
        follower.startTeleopDrive();
        //limelight.switchToShoot();
        //intake.turnUp();   // SAME AS your old setIntakeTurnUp()
    }

    @Override
    public void loop() {

        standardControls();
        addTelemetry();
        if (isTurretOn) turret.tick();
        else turret.off();
        follower.update();
    }

    private void addTelemetry() {

        telemetry.addData("Pose", follower.getPose());
        telemetry.addData("Setter Position", transfer.getPosition());
        telemetry.addData("Tag ",limelight.getCurrentTagId());
        telemetry.addData("Turret State", turret.turretState());
        telemetry.addData("Turret On", isTurretOn);
        telemetry.addData("DistancetoTag" , limelight.getDistanceToShoot());
        telemetry.addData("Outtake Launch Power", outtake.getLaunchPower());

        telemetry.update();
    }




    private void standardControls() {
        follower.setTeleOpDrive(
                -gamepad1.left_stick_y,
                (-gamepad1.right_trigger + gamepad1.left_trigger),
                -gamepad1.left_stick_x,
                true
        );
        if (gamepad1.a) {
            isIntakeOnTimer = false;
            intake.on();
        }
        else if (gamepad1.b) {
            isIntakeOnTimer = false;
            intake.reverse();
        }
        else {
            if (!isIntakeOnTimer || intakeTimer.time() > 1) intake.off();
        }
        if (gamepad2.dpadUpWasPressed()){
            outtake.increasePower();
        }
        if (gamepad2.dpadDownWasPressed()){
            outtake.decreasePower();
        }
        if (gamepad2.x) outtake.on();
        else if (gamepad2.b) outtake.off();


        if (gamepad1.x) {
            setterTimer.reset();
            transfer.lift();
            while (setterTimer.time()<0.25) {
                //do nothing
            }
            isIntakeOnTimer = true;
            intakeTimer.reset();
            transfer.down();
            intake.on();
        }

        if (gamepad1.y) transfer.down();

        if (gamepad2.yWasPressed()) {
            isTurretOn=true;
        }
        if (gamepad2.bWasPressed()) {
            isTurretOn=false;
        }
        if (gamepad2.dpad_left) {
            turret.setMode(Turret.OperationMode.LIMELIGHT);
        }
        if (gamepad2.dpad_right) {
            turret.setMode(Turret.OperationMode.ODOMETRY);
        }

    }
}
