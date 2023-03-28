package model;

public class Subtask extends Task {
    private final int idEpic; // Определяем ID по эпику, к которому относится

    public Subtask(String name, String description, Status status, Type type, int idEpic) {
        super(name, description, status, type);
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
        ", idEpic=" + idEpic + '}';
    }
}
