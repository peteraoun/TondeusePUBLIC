package orientation;

// Énumération représentant les orientations (N, E, S, W)
public enum Orientation {
    N, E, S, W;

    public Orientation turnRight() {
        return values()[(ordinal() + 1) % values().length];
    }

    public Orientation turnLeft() {
        return values()[(ordinal() + values().length - 1) % values().length];
    }
}