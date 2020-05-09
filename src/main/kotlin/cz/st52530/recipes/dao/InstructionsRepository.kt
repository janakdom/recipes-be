package cz.st52530.recipes.dao

import cz.st52530.recipes.model.database.Instruction
import org.springframework.data.jpa.repository.JpaRepository

interface InstructionsRepository : JpaRepository<Instruction, Int>