package lawnmower;

import orientation.Orientation;

public class LawnMower {
    private int x;
    private int y;
    private Orientation orientation;

    // Constructeur pour initialiser la tondeuse
    public LawnMower(int x, int y, Orientation orientation) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

    // Méthode pour déplacer la tondeuse selon les instructions
    public void move(String instructions, int maxX, int maxY) {
        for (char instruction : instructions.toCharArray()) {
            switch (instruction) {
                case 'D':
                    orientation = orientation.turnRight();
                    break;
                case 'G':
                    orientation = orientation.turnLeft();
                    break;
                case 'A':
                    moveForward(maxX, maxY);
                    break;
            }
        }
    }

    // Méthode auxiliaire pour faire avancer la tondeuse
    public void moveForward(int maxX, int maxY) {
        switch (orientation) {
            case N:
                if (y < maxY) y++;
                break;
            case E:
                if (x < maxX) x++;
                break;
            case S:
                if (y > 0) y--;
                break;
            case W:
                if (x > 0) x--;
                break;
        }

        // Pour que la tondeuse reste à l'intérieur des limites de la pelouse
        if (x < 0) x = 0;
        if (x > maxX) x = maxX;
        if (y < 0) y = 0;
        if (y > maxY) y = maxY;
    }

    // Méthode pour obtenir la position actuelle de la tondeuse
    public String getPosition() {
        return x + " " + y + " " + orientation;
    }
}