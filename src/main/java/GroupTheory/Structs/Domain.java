package GroupTheory.Structs;

public interface Domain extends Iterable<Tuple> {
    boolean inDomain(Tuple tuple);
}
