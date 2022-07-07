package am.epam.locks.reentrant.conditional;

public class TilerService {



    public void putTiles(Tiler tiler) {
        tiler.putTiler();
    }

    public void putMixture(Tiler tiler) {
        tiler.putMixture();
    }

    public boolean isFinishedTheWork() {
        return Tiler.getStep() == 0;
    }
}
