package lt.tastybytes.receptaiserver.repository;

import lt.tastybytes.receptaiserver.model.recipe.Instruction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructionRepository extends JpaRepository<Instruction, Long> {
}
