package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(12.247)
            .forwardZeroPowerAcceleration(-44.53477128940915)
            .lateralZeroPowerAcceleration(-70.55230107414124)
            .translationalPIDFCoefficients(new PIDFCoefficients(0.15,0,0.065,0.03))
            .headingPIDFCoefficients(new PIDFCoefficients(0.4,0.2,0.1,0.06))
            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.5,0.02,0.0,0.01, 5))

            .centripetalScaling(0.0005)
            /*.useSecondaryDrivePIDF(true)
            .useSecondaryHeadingPIDF(true)

            .useSecondaryTranslationalPIDF(true)*/
            ;


    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)

            .rightFrontMotorName("rightFront")
            .rightRearMotorName("rightRear")
            .leftRearMotorName("leftRear")
            .leftFrontMotorName("leftFront")
            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .xVelocity(52.12062132827879)
            .yVelocity(40.7335620790016)
            .useBrakeModeInTeleOp(true)
            ;
    public static PinpointConstants localizerConstants = new PinpointConstants()
            .forwardPodY(-2.5)
            .strafePodX(-7.25)
            .distanceUnit(DistanceUnit.INCH)
            .hardwareMapName("pinpoint")
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_SWINGARM_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD)
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD);

    public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 1, 1);

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pinpointLocalizer(localizerConstants)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .build();
    }
}
