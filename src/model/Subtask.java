package model;

public class Subtask extends Task {
    private final int idEpic; // Определяем ID по эпику, к которому относится

    public Subtask(String name, String description, Status status, int idEpic) {
        super(name, description, status);
        this.idEpic = idEpic;
    }

    public int getIdEpic() {
        return idEpic;
    }

    @Override
    public String toString() {
        return "Subtask{" + "id=" + getId() + ", name='" + getName() + '\'' + 
        ", description='" + getDescription() + '\'' + ", status='" + getStatus() +
        '\'' + ", idEpic=" + idEpic + '}';
    }
}
