package cz.st52530.recipes.model.base

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseModel {

    /**
     * Initialized with default value so we don't need to pass it through a constructor.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = -1
}