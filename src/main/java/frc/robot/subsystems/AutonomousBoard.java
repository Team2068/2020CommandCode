package frc.robot.shuffleboard;
package frc.robot.subsystems;
s
import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;
//import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SendableRegistry;


public class AutonomousTab {
    //-------------------------------------------------------------------//
    public enum StartingLocation {
        kNone, kRight, kCenter, kLeft;
    }

    //-------------------------------------------------------------------//

    public enum OrderOfOperations {
        kShootThenMove, kMoveThenShoot;
    }

    //-------------------------------------------------------------------//

    public enum ShootPowerCell {
        kYes, kNo;
    }

    public enum ShootDelay {
        k0, k1, k2, k3, k4, k5;
    }

    //-------------------------------------------------------------------//

    public enum MoveOffLine {
        kYes, kNo;
    }

    public enum MoveDelay {
        k0, k1, k2, k3, k4, k5;
    }

    public enum DirectionToMove {
        kAwayFromPowerPort, kTowardPowerPort;
    }

    //-------------------------------------------------------------------//

    public enum PickUpPowerCell {
        kYes, kNo;
    }

    public enum PickUpLocation {
        kTrench, kRendezvousPoint;
    }

    public enum ShootNewPowerCells {
        kYes, kNo;
    }

    public enum TargetColor {
        kBlue, kGreen, kYellow, kRed;
    }

    // Create a class to hold the data on the Shuffleboard tab
    protected static class Data {
        public StartingLocation startingLocation = StartingLocation.kNone;

        public OrderOfOperations orderOfOperations = OrderOfOperations.kShootThenMove;

        public ShootPowerCell shootPowerCell = ShootPowerCell.kYes;
        public ShootDelay shootDelay = ShootDelay.k0;

        public MoveOffLine moveOffLine = MoveOffLine.kYes;
        public MoveDelay moveDelay = MoveDelay.k0;
        public DirectionToMove directionToMove = DirectionToMove.kAwayFromPowerPort;

        public PickUpPowerCell pickUpPowerCell = PickUpPowerCell.kYes;
        public PickUpLocation pickUpLocation = PickUpLocation.kTrench;
        public ShootNewPowerCells shootNewPowerCells = ShootNewPowerCells.kYes;

        public TargetColor targetColor = TargetColor.kBlue;
    }

    // Create a Shuffleboard Tab
    private static AutonomousTab autonomousTab = Shuffleboard.getTab("Autonomous");

    // Create an object to store the data in the Boxes
    private final Data autonomousData = new Data();

    // Create the Box objects
    private final SendableChooser<StartingLocation> startingLocationBox = new SendableChooser<>();

    private final SendableChooser<OrderOfOperations> orderOfOperationsBox = new SendableChooser<>();

    private final SendableChooser<ShootPowerCell> shootPowerCellBox = new SendableChooser<>();
    private final SendableChooser<ShootDelay> shootDelayBox = new SendableChooser<>();

    private final SendableChooser<MoveOffLine> moveOffLineBox = new SendableChooser<>();
    private final SendableChooser<MoveDelay> moveDelayBox = new SendableChooser<>();
    private final SendableChooser<DirectionToMove> directionToMoveBox = new SendableChooser<>();

    private final SendableChooser<PickUpPowerCell> pickUpPowerCellBox = new SendableChooser<>();
    private final SendableChooser<PickUpLocation> pickUpLocationBox = new SendableChooser<>();
    private final SendableChooser<ShootNewPowerCells> shootNewPowerCellBox = new SendableChooser<>();

    private final SendableChooser<TargetColor> targetColorBox = new SendableChooser<>();

    private final NetworkTableEntry goodToGo;

    // Create the Button object
    private final SendableChooser<Boolean> sendDataButton = new SendableChooser<>();

    private boolean previousStateOfSendButton = false;

    private static AutonomousTab instance = new AutonomousTab();

    private AutonomousTab() {
        entry(
            "Starting Location Box", startingLocationBox,
             new String[]{"Left", "Center", "Right"}, 
             StartingLocation.values(), 0, 0, 8, 2
        );
        entry(
            "Target Color", targetColorBox,
            new String[]{"Blue","Green", "Yellow", "Red"}, 
            TargetColor.values(), 9, 0, 8, 2
        );

        createOrderOfOperationsBox();
        
        createShootPowerCellBox();
        createShootDelayBox();
        
        createMoveOffLineBox();
        createMoveDelayBox();
        createDirectionToMoveBox();

        createPickUpPowerCellBox();
        createPickUpLocationBox();
        createShootNewPowerCellBox();
        
        // sendDataButton = createSendDataButton();
        // sendDataButton.setBoolean(false);
        createSendDataButton();

        goodToGo = createRedLightGreenLightBox();
        goodToGo.setBoolean(false);
    }

    protected static AutonomousTab getInstance() {
        return instance;
    }

    private static <T extends Enum<T>> void entry(final String name, final SendableChooser<T> box, final String[] optionNames, final T[] options, final int x, final int y, final int width, final int height) {
        SendableRegistry.add(box, name);
        SendableRegistry.setName(box, name);

        // Default option
        box.setDefaultOption(optionNames[0], options[0]);

        // Option
        int iter = 0;
        for (final T option : java.util.Arrays.asList(options)) {
            box.addOption(optionNames[iter], option);
            iter++;
        }

        // Put widget on the shuffleboard
        autonomousTab.add(box)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(x, y)
            .withSize(width, height);
    }


    private void updateAutonomousTabData()
    {
        autonomousData.startingLocation = startingLocationBox.getSelected();

        autonomousData.orderOfOperations = orderOfOperationsBox.getSelected();
        
        autonomousData.shootPowerCell = shootPowerCellBox.getSelected();
        autonomousData.shootDelay = shootDelayBox.getSelected();

        autonomousData.moveOffLine = moveOffLineBox.getSelected();
        autonomousData.moveDelay = moveDelayBox.getSelected();
        autonomousData.directionToMove = directionToMoveBox.getSelected();

        autonomousData.pickUpPowerCell = pickUpPowerCellBox.getSelected();
        autonomousData.pickUpLocation = pickUpLocationBox.getSelected();
        
        autonomousData.shootNewPowerCells = shootNewPowerCellBox.getSelected();
    }

    public void checkForNewAutonomousTabData()
    {
        final boolean isSendDataButtonPressed = sendDataButton.getSelected();

        if(isSendDataButtonPressed && !previousStateOfSendButton)
        {
            previousStateOfSendButton = true;

            // Get values from the Boxes
            updateAutonomousTabData();

            System.out.println(autonomousData);
            
            if(isDataValid())
            {
                goodToGo.setBoolean(true);   
            }
            else
            {
                goodToGo.setBoolean(false);
            }
        }
        
        if (!isSendDataButtonPressed && previousStateOfSendButton)
        {
            previousStateOfSendButton = false;
        }
    }

    public AutonomousTab getAutonomousTabData(){
        return autonomousTab;
    }

    private boolean isDataValid()
    {
        boolean isValid = true;

        final boolean pickUpPowerCell = (autonomousData.pickUpPowerCell == PickUpPowerCell.kYes);

        final boolean moveTowardPowerPort = (autonomousData.directionToMove == DirectionToMove.kTowardPowerPort);

        final boolean shootNewPowerCells = (autonomousData.shootNewPowerCells == ShootNewPowerCells.kYes);

        final boolean startingLocationLeft = (autonomousData.startingLocation == StartingLocation.kLeft);
        final boolean startingLocationRight = (autonomousData.startingLocation == StartingLocation.kRight);

        final boolean pickUpLocation = (autonomousData.pickUpLocation == PickUpLocation.kTrench);

        //if trying to pick up new cell and moving away from them
        if(pickUpPowerCell && moveTowardPowerPort)
        {
            isValid = false;
            
            DriverStation.reportWarning("Cannot Pick Up Power Cells and Move Toward Power Port", false);
        }

        //if not picking up new cell and trying to shoot it
        if(!pickUpPowerCell && shootNewPowerCells)
        {
            isValid = false;
            DriverStation.reportWarning("Must Pick Up Power Cell to Shoot New Power Cell", false);

        }

        //if starting left and going to trench
        if(startingLocationLeft && pickUpLocation)
        {
            isValid = false;
            DriverStation.reportWarning("Advised not to start Left with Pick Up Location: Trench.\n Potential crossover collision.", false);
        }

        //if starting right and going to rendezvous point
        if(startingLocationRight && !pickUpLocation)
        {
            isValid = false;
            DriverStation.reportWarning("Advised not to start Right with Pick Up Location: Rendezvous Point.\n Potential crossover collision", false);

        }

        return isValid;
    }
}