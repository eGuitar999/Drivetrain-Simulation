// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.PathConstants;
import frc.robot.commands.RotateToAngle;
import frc.robot.commands.RotateToSpeaker;
import frc.robot.commands.RunPath;
import frc.robot.subsystems.Drivetrain;

public class RobotContainer {
  private final Drivetrain drivetrain = new Drivetrain();

  private final CommandXboxController driver = new CommandXboxController(0);
  
  SendableChooser<Trajectory> pathChooser = new SendableChooser<>();

  public RobotContainer() {
    configureBindings();
    Command redCentre = new InstantCommand(() -> drivetrain.resetOdometry(DriveConstants.redSubWooferCentre)).ignoringDisable(true).withName("Red Centre");
    Command redSource = new InstantCommand(() -> drivetrain.resetOdometry(DriveConstants.redSubWooferSource)).ignoringDisable(true);
    Command redAmp = new InstantCommand(() -> drivetrain.resetOdometry(DriveConstants.redSubWooferAmp)).ignoringDisable(true);

    pathChooser.setDefaultOption("Blue Amp Two", PathConstants.blueAmpSideTwoNote);
    pathChooser.addOption("All Mid", PathConstants.blueAllMidNotes);
    pathChooser.addOption("Red Amp Two", PathConstants.redAmpTwoSide);

    SmartDashboard.putData("PathChooser", pathChooser);

    drivetrain.setDefaultCommand(
      new RunCommand(
        () -> drivetrain.arcadeDrive(
          -driver.getLeftY(), -driver.getRightX()), 
          drivetrain));
  }

  private void configureBindings() {
    /*
    driver.y().onTrue(new InstantCommand(() -> drivetrain.resetOdometry(DriveConstants.blueSubWooferAmp)).ignoringDisable(true));
    driver.b().onTrue(new InstantCommand(() -> drivetrain.resetOdometry(DriveConstants.blueSubWooferCentre)).ignoringDisable(true));
    driver.a().onTrue(new InstantCommand(() -> drivetrain.resetOdometry(DriveConstants.blueSubWooferSource)).ignoringDisable(true));
    */
    driver.a().onTrue(new RotateToAngle(drivetrain, () -> drivetrain.getAngleToSpeaker()));
    driver.b().onTrue(new InstantCommand(() -> drivetrain.resetOdometry(new Pose2d(0, 0, new Rotation2d())), drivetrain));
    driver.x().onTrue(new RotateToAngle(drivetrain, () -> (0.0)));
  }

  public Command getAutonomousCommand() {
/*
    Trajectory trajectory = PathPlanning.blueAllMidNotes;
    
    RamseteCommand ramseteCommand =
            new RamseteCommand(
                trajectory,
                drivetrain::getPose,
                new RamseteController(DriveConstants.kRamseteB, DriveConstants.kRamseteZeta),
                new SimpleMotorFeedforward(
                    DriveConstants.ksVolts,
                    DriveConstants.kvVoltSecondsPerMeter,
                    DriveConstants.kaVoltSecondsSquaredPerMeter),
                DriveConstants.kDriveKinematics,
                drivetrain::getWheelSpeeds,
                new PIDController(DriveConstants.kPVel, 0, 0),
                new PIDController(DriveConstants.kPVel, 0, 0),
                // RamseteCommand passes volts to the callback
                drivetrain::tankDriveVolts,
                drivetrain);
    
    Command runTraj = Commands.runOnce(() -> drivetrain.resetOdometry(trajectory.getInitialPose()))
      .andThen(ramseteCommand)
      .andThen(Commands.runOnce(() -> drivetrain.tankDriveVolts(0, 0)));

    return runTraj;
*/
    //return new RunPath(drivetrain, pathChooser.getSelected());
    return pathPlannerAuto();
  }

  public Command pathPlannerAuto() {
    return new PathPlannerAuto("test1");
  }
}
