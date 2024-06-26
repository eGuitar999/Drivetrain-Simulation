// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.List;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.math.util.Units;

/** Add your docs here. */
public class Constants {
    public static final class DriveConstants {
        public static final int frontRightID = 2;
        public static final int backRightID = 4;

        public static final int frontLeftID = 1;
        public static final int backLeftID = 3;
        
        public static final double RevToMetre = 1188.0 * Math.PI / 82991.96;
        public static final double RPMToMetresPerSecond = 1188.0 * Math.PI / 4979517.6;

        public static final int pigeonID = 12;

        public static final double ksVolts = 0.21888; //Garage: 0.21888, Carpet: 0.21599
        public static final double kvVoltSecondsPerMeter = 2.6459; //Garage: 2.6459, Carpet: 2.4096
        public static final double kaVoltSecondsSquaredPerMeter = 0.62854; //Garage: 0.62854, Carpet: 0.87369

        public static final double kPVel = 3.739; //Garage: 3.739, Carpet: 3.538
        
        public static final double kTrackwidthMeters = 0.683;
        public static final DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(kTrackwidthMeters);

        public static final double kSlowMaxSpeedMetersPerSecond = 1;
        public static final double kSlowMaxAccelerationMetersPerSecondSquared = 0.25;
        public static final double kFastMaxSpeedMetersPerSecond = 3;
        public static final double kFastMaxAccelerationMetersPerSecondSquared = 2;

        public static final double kRamseteB = 2;
        public static final double kRamseteZeta = 0.7;

        public static final double kRobotLength = Units.inchesToMeters(37.5);

        public static final Pose2d blueSubWooferAmp = new Pose2d(0.755, 6.71, new Rotation2d(Units.degreesToRadians(60)));
        public static final Pose2d blueSubWooferCentre = new Pose2d(Units.inchesToMeters(36.37) + kRobotLength / 2.0, Units.inchesToMeters(218.42), new Rotation2d());
        public static final Pose2d blueSubWooferSource = new Pose2d(0.755, 4.47, new Rotation2d(Units.degreesToRadians(-60)));
        
        public static final Pose2d redSubWooferAmp = new Pose2d(15.78, 6.69, new Rotation2d(Units.degreesToRadians(120)));
        public static final Pose2d redSubWooferCentre = new Pose2d(Units.inchesToMeters(616.36) - kRobotLength / 2.0, Units.inchesToMeters(218.42), new Rotation2d(Units.degreesToRadians(180)));
        public static final Pose2d redSubWooferSource = new Pose2d(15.78, 4.39, new Rotation2d(Units.degreesToRadians(-120)));

        public static final Pose2d noteBlueCloseAmp = new Pose2d(Units.inchesToMeters(114.0), Units.inchesToMeters(275.42), new Rotation2d());
        public static final Pose2d noteBlueCentreNote = new Pose2d(Units.inchesToMeters(114.0), Units.inchesToMeters(218.42), new Rotation2d());
        public static final Pose2d noteBlueCloseSource = new Pose2d(Units.inchesToMeters(114.0), Units.inchesToMeters(161.42), new Rotation2d());

        public static final Pose2d noteRedCloseAmp = new Pose2d(Units.inchesToMeters(538.73), Units.inchesToMeters(275.42), new Rotation2d(Units.degreesToRadians(180)));
        public static final Pose2d noteRedCentreNote = new Pose2d(Units.inchesToMeters(538.73), Units.inchesToMeters(218.42), new Rotation2d(Units.degreesToRadians(180)));
        public static final Pose2d noteRedCloseSource = new Pose2d(Units.inchesToMeters(538.73), Units.inchesToMeters(161.42), new Rotation2d(Units.degreesToRadians(180)));

        public static final Pose2d noteFarAmp1 = new Pose2d(Units.inchesToMeters(324.6), Units.inchesToMeters(293.64), new Rotation2d());
        public static final Pose2d noteFarAmp2 = new Pose2d(Units.inchesToMeters(324.6), Units.inchesToMeters(227.64), new Rotation2d());
        public static final Pose2d noteFarCentre = new Pose2d(Units.inchesToMeters(324.6), Units.inchesToMeters(161.64), new Rotation2d());
        public static final Pose2d noteFarSource2 = new Pose2d(Units.inchesToMeters(324.6), Units.inchesToMeters(95.64), new Rotation2d());
        public static final Pose2d noteFarSource1 = new Pose2d(Units.inchesToMeters(324.6), Units.inchesToMeters(29.64), new Rotation2d());
    }

    public static final class IntakeConstants {
        public static final int pivotID = 10;
        public static final int wheelID = 11;

        public static final double intakeOutAngle = 0.08; //0.08
        public static final double intakeInAngle = 0.657;

        public static final int intakeBottomLimit = 3;

        public static final double intakeSpeed = 0.5;
    }

    public static final class PathConstants {

        public static DifferentialDriveVoltageConstraint autoVoltageConstraint =
            new DifferentialDriveVoltageConstraint(
                new SimpleMotorFeedforward(
                    DriveConstants.ksVolts,
                    DriveConstants.kvVoltSecondsPerMeter,
                    DriveConstants.kaVoltSecondsSquaredPerMeter),
                DriveConstants.kDriveKinematics,
                10);
        
        public static TrajectoryConfig fastConfig =
            new TrajectoryConfig(
                    DriveConstants.kFastMaxSpeedMetersPerSecond,
                    DriveConstants.kFastMaxAccelerationMetersPerSecondSquared)
                // Add kinematics to ensure max speed is actually obeyed
                .setKinematics(DriveConstants.kDriveKinematics)
                // Apply the voltage constraint
                .addConstraint(autoVoltageConstraint);

        public static TrajectoryConfig slowConfig =
            new TrajectoryConfig(
                    DriveConstants.kSlowMaxSpeedMetersPerSecond,
                    DriveConstants.kSlowMaxAccelerationMetersPerSecondSquared)
                // Add kinematics to ensure max speed is actually obeyed
                .setKinematics(DriveConstants.kDriveKinematics)
                // Apply the voltage constraint
                .addConstraint(autoVoltageConstraint);
        
        public static Trajectory blueAmpSideTwoNote =
            TrajectoryGenerator.generateTrajectory(List.of(
                DriveConstants.blueSubWooferAmp, 
                DriveConstants.noteBlueCloseAmp),
                fastConfig);
        
        public static Trajectory redAmpTwoSide = 
            TrajectoryGenerator.generateTrajectory(List.of(
                DriveConstants.redSubWooferAmp, 
                DriveConstants.noteRedCloseAmp),
                fastConfig);
        
        public static Trajectory blueAllMidNotes = 
            TrajectoryGenerator.generateTrajectory(
                DriveConstants.blueSubWooferAmp,
                List.of(
                    DriveConstants.noteFarAmp1.getTranslation(),
                    DriveConstants.noteFarAmp2.getTranslation(),
                    DriveConstants.noteFarCentre.getTranslation(),
                    DriveConstants.noteFarSource2.getTranslation(),
                    DriveConstants.noteFarSource1.getTranslation()
                ),
                DriveConstants.blueSubWooferSource,
                fastConfig);
    }
}
