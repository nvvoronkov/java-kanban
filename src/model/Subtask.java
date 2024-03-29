package model;

import java.time.LocalDateTime;

import model.constants.Status;
import model.constants.Type;

public class Subtask extends Task {
    private final int idEpic; // Определяем ID по эпику, к которому относится

    public Subtask(String name, String description, Status status, Type type, 
                   LocalDateTime startTime, long duration, int idEpic) {
        super(name, description, status, type, startTime, duration);
        this.idEpic = idEpic;
        setType(Type.SUBTASK);
    }

    public Subtask(String name, String description, Status status, int idEpic) {
        super(name, description, status);
        this.idEpic = idEpic;
    }

    public int getIdEpic() {
        return idEpic;
    }

    @Override
    public String toString() {
        return "Subtask{" +
        "id=" + getId() +
        ", name='" + getName() + '\'' +
        ", description='" + getDescription() + '\'' +
        ", status='" + getStatus() + '\'' +
        ", type='" + getType() + '\'' +
        ", startTime='" + getStartTime() + '\'' +
        ", duration='" + getDuration() + '\'' +
        ", idEpic=" + getIdEpic() + '}';
    }
}
