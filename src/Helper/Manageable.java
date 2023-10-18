package Helper;

public interface Manageable {
    void add();
    void delete(Integer id);
    void view (Integer id);
    void edit(Integer id);
    void search(String name);
}
