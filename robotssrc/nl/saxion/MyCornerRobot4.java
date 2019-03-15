package nl.saxion;

import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

import java.util.ArrayList;


public class MyCornerRobot4 extends Robot {

    private ArrayList<Vector> waypoints = new ArrayList<>();

    @Override
    public void run(){
        double width = getBattleFieldWidth() / 2;
        double height = getBattleFieldHeight() / 2;
        double w = getWidth();
        double h = getHeight();

        Vector boundary = new Vector(w, h).scale(1.5);

        waypoints.add(new Vector(width + 0+boundary.x,0+boundary.y));
        waypoints.add(new Vector(width + width-boundary.x, boundary.y));
        waypoints.add(new Vector(width + 0+boundary.x, height-boundary.y));
        waypoints.add(new Vector(width + width-boundary.x, height-boundary.y));

        int waypointIndex = 0;
        double minDistance = Double.MAX_VALUE;

        //find the closest
        Vector currentPos = new Vector(getX(), getY());
        for (int i = 0; i < waypoints.size();i++) {
            double distance =Vector.distance(waypoints.get(i), currentPos);
            if (distance < minDistance) {
                minDistance = distance;
                waypointIndex = i;
            }
        }

        while (true) {
            System.out.println("While");
            double currentAngle = getHeading();
            currentPos = new Vector(getX(), getY());
            Vector targetPos = waypoints.get(waypointIndex);
            Vector delta = targetPos.clone().sub(currentPos);
            double targetAngle = delta.angleInDegrees();

            double requiredTurn = Utils.normalRelativeAngleDegrees(targetAngle-currentAngle);
            turnRight(requiredTurn);

            ahead (delta.length());

            waypointIndex++;
            waypointIndex %= waypoints.size();
        }
    }
    /**
     * Fire when we see a robot
     */
    public void onScannedRobot(ScannedRobotEvent e) {
        fire(1);
    }
}
